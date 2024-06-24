package pegas.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/sql/tasks.sql")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class TaskRestControllerIT {

    @Autowired
    MockMvc mockMvc;
//    @Autowired
//    TaskRepository taskRepository;

//    @AfterEach
//    void tearDown(){
//        taskRepository.findAll().clear();
//    }

    @Test
    @WithMockUser
    void handleGetAllTaskTest() throws Exception{
        var builder = MockMvcRequestBuilders.get("/api/tasks");
        mockMvc.perform(builder)
                .andExpectAll(status().isOk(), MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
                //content().json("""[{},{},{}]""")
                );
    }

    @Test
    @WithMockUser
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
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//                        MockMvcResultMatchers.content().json("""
//                                {"id":5,
//                                "details":"Третья задача",
//                                "complete":false}
//                                """),
//                        jsonPath("$.id").exists()
//                );
    }

    @Test
    @WithMockUser
    void createTask_PayloadInvalid() throws Exception {
        var builder = MockMvcRequestBuilders.post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"details":null}
                        """);
        mockMvc.perform(builder)
                .andExpectAll(
                        status().is4xxClientError(),
                        header().doesNotExist(HttpHeaders.LOCATION)
//                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
//                        MockMvcResultMatchers.content().json("""
//                                {"errorsList":["Описание должно быть заполнено"]}
//                                """, true)
                );
//        assertEquals(3, taskRepository.findAll().size());
    }
}
