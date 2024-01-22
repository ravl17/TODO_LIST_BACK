package com.ravl0.TODO_LIST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    public TaskRepository taskRepository;
    public List<Task> allTasks(){return (List<Task>) taskRepository.findAll();}

    public Optional<Task> singleTask(String id){
        return taskRepository.findTaskById(Long.valueOf(id));
    }
    public List<Task> tasksByDate(Timestamp date){
        return taskRepository.findTasksByDate(date);
    }
    public List<Task> getAllTasksForDay(Timestamp timestamp) {
        LocalDate date = timestamp.toLocalDateTime().toLocalDate();
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);

        Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
        Timestamp endTimestamp = Timestamp.valueOf(endOfDay);

        return taskRepository.findByDateBetweenOrderByDateAsc (startTimestamp, endTimestamp);
    }
    public void updateIsDoneById(Long id,boolean is_done){
        taskRepository.updateIsDoneProperty(id,is_done);

    }
    public void addTask(Task newTask) {
        // Save the new task using the save method from CrudRepository
        taskRepository.save(newTask);
    }
    public void updateTask(Long taskId, Task updatedTask) {
        taskRepository.updateTask(taskId, updatedTask.getName(), updatedTask.getDate(), updatedTask.getIs_done(), updatedTask.getUser_id());
    }
}
