package pegas.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pegas.entity.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Primary
public class JDBCTaskRepository implements TaskRepository, RowMapper<Task> {

    private final JdbcOperations jdbcOperations;

    @Override
    public List<Task> findAll() {
        return jdbcOperations.query("select * from t_task", this);
    }

    @Override
    public void save(Task task) {
        jdbcOperations.update("""
                inset into t_task(id, c_details, c_complete) values(?,?,?)
                """, task.id(),task.details(), task.complete());
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return jdbcOperations.query("select * from t_task" +
                "where id = ?", new Object[]{id}, this).stream().findFirst();
    }

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(rs.getObject("id", Integer.class), rs.getString("c_details"),
                rs.getBoolean("c_complete"));
    }
}
