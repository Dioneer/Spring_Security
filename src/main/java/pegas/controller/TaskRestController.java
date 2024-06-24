package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pegas.dto.CreateUpdateTask;
import pegas.entity.Task;
import pegas.errors.ErrorsPresentation;
import pegas.repository.TaskRepository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskRestController {
    private final TaskRepository taskRepository;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<Task>> handleGetAllTask(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskRepository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createTask(@RequestBody CreateUpdateTask create,
                                           UriComponentsBuilder uriComponentsBuilder, Locale locale){
        if(create.details()==null || create.details().isBlank()){
            final var message = this.messageSource.getMessage("tasks.create.details.errors.not_set",
                    new Object[0], locale);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        }else {
            var task = new Task(create.details());
            taskRepository.save(task);
            return ResponseEntity.created(uriComponentsBuilder.path("api/tasks/{taskId}")
                            .build(Map.of("taskId", task.id())))
                    .contentType(MediaType.APPLICATION_JSON).body(task);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable("id") Integer id){
        return ResponseEntity.of(taskRepository.findById(id));

    }
}
