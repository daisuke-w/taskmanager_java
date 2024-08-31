package d.com.taskmanager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import d.com.taskmanager.entity.Task;
import d.com.taskmanager.exception.TaskNotFoundException;
import d.com.taskmanager.repository.TaskRepository;
import d.com.taskmanager.service.TaskServiceImpl;

public class TaskServiceTests {
    
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    private Task createTask(Long id, String title, String description, boolean completed) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        return task;
    }
    
    @Test
    void testCreateTask() {
        Task task = createTask(null, "Test Task", "This is a test task.", false);
        Task savedTask = createTask(1L, "Test Task", "This is a test task.", false);

        when(taskRepository.save(task)).thenReturn(savedTask);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask.getId());
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals("This is a test task.", createdTask.getDescription());
        assertFalse(createdTask.isCompleted());
    }
    
    @Test
    void testGetTaskById() {
        Task task = createTask(1L, "Test Task", "This is a test task.", false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getTitle());
        assertEquals("This is a test task.", foundTask.getDescription());
        assertFalse(foundTask.isCompleted());
    }
    
    @Test
    void testGetTaskByIdNotFound() {
    	doReturn(Optional.empty()).when(taskRepository).findById(1L);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(1L);
        });
    }
    
    @Test
    void testUpdateTask() {
        Task existingTask = createTask(1L, "Old Task", "Old Description", false);
        Task updatedTask = createTask(1L, "Updated Title", "Updated Description", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.isCompleted());
    }

    @Test
    void testUpdateTaskNotFound() {
        Task updatedTask = createTask(1L, "Updated Title", "", false);

        doReturn(Optional.empty()).when(taskRepository).findById(1L);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(1L, updatedTask);
        });
    }
    
    @Test
    void testDeleteTask() {
        Task task = createTask(1L, "Task to Delete", "Description", false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }
    
    @Test
    void testGetAllTasks() {
        Task task1 = createTask(1L, "Task 1", "Description 1", false);
        Task task2 = createTask(2L, "Task 2", "Description 2", true);

        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> foundTasks = taskService.getAllTasks();

        assertEquals(2, foundTasks.size());
        assertEquals("Task 1", foundTasks.get(0).getTitle());
        assertTrue(foundTasks.get(1).isCompleted());
    }
}
