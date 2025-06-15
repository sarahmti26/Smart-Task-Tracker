package com.tasktracker.backend.repository;

import com.tasktracker.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// TaskRepository.java
public interface TaskRepository extends JpaRepository<Task, Long> {

    // This interface extends JpaRepository to provide CRUD operations for Task entities.
    // The Long type is used as the ID type for the Task entity.

    // Method to find tasks by their completion status
    List<Task> findByCompleted(boolean isCompleted); // To find completed tasks

} 
