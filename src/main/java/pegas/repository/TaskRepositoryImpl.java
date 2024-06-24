package pegas.repository;

import org.springframework.stereotype.Repository;
import pegas.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Task> findById(Integer id) {
        return tasks.stream().filter(i->i.id()==id).findFirst();
    }
}
