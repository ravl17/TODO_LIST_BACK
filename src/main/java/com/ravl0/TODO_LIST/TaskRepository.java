package com.ravl0.TODO_LIST;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    // Additional query methods can be added if needed
    Optional<Task> findTaskById(Long id);

    List<Task> findTasksByDate(Timestamp date);

    List<Task> findByDateBetweenOrderByDateAsc(Timestamp startOfDay, Timestamp endOfDay);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.is_done = :isDone WHERE t.id = :taskId")
    void updateIsDoneProperty(Long taskId, boolean isDone);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.name = :name, t.date = :date, t.is_done = :isDone, t.user_id = :userId WHERE t.id = :taskId")
    void updateTask(Long taskId, String name, Timestamp date, Boolean isDone, Long userId);
}
