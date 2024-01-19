package com.ravl0.TODO_LIST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask() {
//        return new ResponseEntity<List<Task>>(taskService.allTasks(), HttpStatus.OK);
        return ResponseEntity.ok(taskService.allTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getSingleTask(@PathVariable String id) {
        return new ResponseEntity<Optional<Task>>(taskService.singleTask(id), HttpStatus.OK);

    }
    @GetMapping("/date")
    public ResponseEntity<List<Task>> getTaskByDate(@RequestParam("dateTimestamp") String date) {
        try {
            long registrationTimestamp = Long.parseLong(date);
            return new ResponseEntity<List<Task>>(taskService.getAllTasksForDay(new Timestamp(registrationTimestamp)), HttpStatus.OK);
        } catch (NumberFormatException e) {
            // Handle the case where the parameter is not a valid long
            // You can throw a custom exception, log the error, or return an appropriate response.
            throw new IllegalArgumentException("Invalid registrationTimestamp format");
        }
    }
    @PostMapping("/is_done")
    public void setIs_Done(@RequestParam String taskId,@RequestParam boolean is_done){
        taskService.updateIsDoneById(Long.valueOf(taskId),is_done);
    }
}
