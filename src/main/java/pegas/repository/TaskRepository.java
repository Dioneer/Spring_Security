package pegas.repository;

import pegas.entity.Task;

import java.util.List;

public interface TaskRepository {
     List<Task> findAll();

     void save(Task task);
}
