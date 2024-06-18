package pegas.repository;

import org.springframework.stereotype.Repository;
import pegas.entity.Task;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository{

    private final List<Task> tasks = new ArrayList<>() {{this.add(new Task("1111"));
            this.add(new Task("222")); this.add(new Task("333"));}};

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }
}
