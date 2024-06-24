package pegas.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import pegas.controller.TaskRestController;
import pegas.dto.CreateUpdateTask;
import pegas.entity.Task;
import pegas.errors.ErrorsPresentation;
import pegas.repository.TaskRepository;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRestControllerTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    TaskRestController taskRestController;

    @Test
    void handleGetAllTaskTest(){
        var tasks = List.of(new Task(1, "1111", false),
                new Task(2, "222", false),
                new Task(3, "333", true));
        Mockito.doReturn(tasks).when(taskRepository).findAll();
        var result = taskRestController.handleGetAllTask();
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());
        assertEquals(tasks, result.getBody());
    }

    @Test
    void createTask_PayloadIsValid(){
        var task1 = new CreateUpdateTask("444");
        var result = taskRestController.createTask(task1, UriComponentsBuilder.fromUriString("http://localhost:8080"),
                Locale.ENGLISH);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());
        if(result.getBody() instanceof Task task2){
            assertEquals(task1.details(), task2.details());
            assertFalse(task2.complete());
            assertEquals(URI.create("http://localhost:8080/api/tasks/"+task2.id()), result.getHeaders().getLocation());

            verify(taskRepository).save(task2);
        }else{
            assertInstanceOf(Task.class, result.getBody());
        }
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void createTask_PayloadIsInvalid(){
        var details = " ";
        var local = Locale.US;
        var errorMessage = "Task details must be set";

        doReturn(errorMessage).when(messageSource).getMessage("tasks.create.details.errors.not_set", new Object[0],
                local);

        var result = taskRestController.createTask(new CreateUpdateTask(details),
                UriComponentsBuilder.fromUriString("http://localhost:8080"),
                local);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());
        if(result.getBody() instanceof ErrorsPresentation errors){
            assertEquals(new ErrorsPresentation(List.of(errorMessage)), result.getBody());
        }
        verifyNoMoreInteractions(taskRepository);
    }
}
