package pegas.repository;

import pegas.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
     List<Task> findAll();

     void save(Task task);

     Optional<Task> findById(Integer id);
}
