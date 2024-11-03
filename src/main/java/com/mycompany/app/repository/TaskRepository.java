package com.mycompany.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.app.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Custom method to find tasks by user ID
    List<Task> findByUserId(Long userId);

    // Additional custom queries can be added here as needed
}