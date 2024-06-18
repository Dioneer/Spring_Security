package pegas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pegas.dto.CreateUpdateTask;
import pegas.entity.Task;
import pegas.repository.TaskRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskRestController {
    private final TaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<List<Task>> handleGetAllTask(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(taskRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateUpdateTask create,
                                           UriComponentsBuilder uriComponentsBuilder){
        var task = new Task(create.details());
        taskRepository.save(task);
        return ResponseEntity.created(uriComponentsBuilder.path("api/tasks/{taskId}")
                        .build(Map.of("taskId", task.id())))
                .contentType(MediaType.APPLICATION_JSON).body(task);
    }
}
