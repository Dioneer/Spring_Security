package pegas.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pegas.entity.Task;
import pegas.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class TaskRestControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TaskRepository taskRepository;

    @Test
    void handleGetAllTaskTest() throws Exception{
        var builder = MockMvcRequestBuilders.get("/api/tasks");

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }



}
