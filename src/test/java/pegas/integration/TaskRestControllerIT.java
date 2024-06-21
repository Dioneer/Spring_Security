package pegas.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pegas.entity.Task;
import pegas.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class TaskRestControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TaskRepository taskRepository;

    @AfterEach
    void tearDown(){
        taskRepository.findAll().clear();
    }

    @Test
    void handleGetAllTaskTest() throws Exception{
        var builder = MockMvcRequestBuilders.get("/api/tasks");
        mockMvc.perform(builder)
                .andExpectAll(status().isOk(), MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
                //content().json("""[{},{},{}]""")
                );
    }

    @Test
    void createTask_PayloadIsValid() throws Exception {
        var builder = MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"details":"Третья задача"}
                        """);
        mockMvc.perform(builder)
                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().json("""
                                {"details":"Третья задача",
                                "complete":false}
                                """),
                        jsonPath("$.id").exists()
                );
        assertEquals(4, taskRepository.findAll().size());
        assertEquals("Третья задача", taskRepository.findAll().get(3).details());
    }

    @Test
    void createTask_PayloadInvalid() throws Exception {
        var builder = MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"details":null}
                        """);
        mockMvc.perform(builder)
                .andExpectAll(
                        status().isBadRequest(),
                        header().doesNotExist(HttpHeaders.LOCATION),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().json("""
                                {"errorsList":["Описание должно быть заполнено"]}
                                """, true)
                );
        assertEquals(3, taskRepository.findAll().size());
    }
}
