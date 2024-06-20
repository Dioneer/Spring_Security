package pegas.unit;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import pegas.controller.TaskRestController;
import pegas.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskRestControllerTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    MessageSource messageSource;
    @InjectMocks
    TaskRestController taskRestController;
}
