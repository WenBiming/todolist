package com.mycompany.app.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycompany.app.entity.Task;
import com.mycompany.app.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test") // Assumes you have application-test.properties configured for MySQL
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // Create and save a test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        testUser.setCreatedAt(new Timestamp(new Date().getTime()));
        testUser.setUpdatedAt(new Timestamp(new Date().getTime()));
        userRepository.save(testUser);
    }

    @Test
    public void testSaveTask() {
        // Create a new task
        Task task = new Task();
        task.setUser(testUser);
        task.setTitle("Test Task");
        task.setDescription("This is a test task.");
        task.setStatus(Task.Status.PENDING);
        task.setDueDate(new java.sql.Date(System.currentTimeMillis()));
        task.setCreatedAt(new Timestamp(new Date().getTime()));
        task.setUpdatedAt(new Timestamp(new Date().getTime()));

        // Save the task
        Task savedTask = taskRepository.save(task);

        // Verify the saved task
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isGreaterThan(0);
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
    }

    @Test
    public void testFindByUserId() {
        // Create and save a task for the test user
        Task task1 = new Task();
        task1.setUser(testUser);
        task1.setTitle("Task 1");
        task1.setDescription("This is task 1.");
        task1.setStatus(Task.Status.PENDING);
        task1.setDueDate(new java.sql.Date(System.currentTimeMillis()));
        task1.setCreatedAt(new Timestamp(new Date().getTime()));
        task1.setUpdatedAt(new Timestamp(new Date().getTime()));
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setUser(testUser);
        task2.setTitle("Task 2");
        task2.setDescription("This is task 2.");
        task2.setStatus(Task.Status.IN_PROGRESS);
        task2.setDueDate(new java.sql.Date(System.currentTimeMillis()));
        task2.setCreatedAt(new Timestamp(new Date().getTime()));
        task2.setUpdatedAt(new Timestamp(new Date().getTime()));
        taskRepository.save(task2);

        // Find tasks by user ID
        List<Task> tasks = taskRepository.findByUserId(testUser.getId());

        // Verify the tasks
        assertThat(tasks).isNotEmpty();
        assertThat(tasks.size()).isEqualTo(2);
    }
}