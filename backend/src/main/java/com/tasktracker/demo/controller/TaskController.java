package com.tasktracker.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tasktracker.demo.model.Task;
import com.tasktracker.demo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskRepository repo;

    //Get mappings 
    @GetMapping
    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/completed")
    public List<Task> getCompletedTasks() {
        return repo.findByCompleted(true);
    }

    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {
        return repo.findByCompleted(false);
    }

    //Other mappings
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return repo.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return repo.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());
            task.setCompletedAt(updatedTask.isCompleted() ? LocalDateTime.now() : null);
            task.setPriority(updatedTask.getPriority());
            task.setTag(updatedTask.getTag());
            return ResponseEntity.ok(repo.save(task));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    //Delete Mappings
    @DeleteMapping
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/completed")
    public ResponseEntity<Void> deleteCompletedTasks() {
      List<Task> completedTasks = repo.findByCompleted(true);
        repo.deleteAll(completedTasks);
        return ResponseEntity.noContent().build();
    }

}

//note to self: 
// get mapping to get tasks ? 
//delete mapping to deleted tasks (once completed?) 
//post mapping to create  new tasks
//put mapping to update tasks (e.g., change status, priority, etc.)