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
	
	@Test
	void testCreateTask() {
		Task task = new Task();
		task.setTitle("Test Task");
		task.setDescription("This is a test task.");
		task.setCompleted(false);
		
		Task savedTask = new Task();
		savedTask.setId(1L);
		savedTask.setTitle("Test Task");
		savedTask.setDescription("This is a test task.");
		savedTask.setCompleted(false);
		
		when(taskRepository.save(task)).thenReturn(savedTask);
		
		Task createdTask = taskService.createTask(task);
		
		assertNotNull(createdTask.getId());
		assertEquals("Test Task", createdTask.getTitle());
		assertEquals("This is a test task.", createdTask.getDescription());
		assertFalse(createdTask.isCompleted());
	}
	
	@Test
	void testGetTaskById() {
		Task task = new Task();
		task.setId(1L);
		task.setTitle("Test Task");
		task.setDescription("This is a test task.");
		task.setCompleted(false);
		
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
		Task existingTask = new Task();
		existingTask.setId(1L);
		existingTask.setTitle("Old Task");
		existingTask.setDescription("Old Description");
		existingTask.setCompleted(false);

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.isCompleted());
	}

    @Test
    void testUpdateTaskNotFound() {

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Title");

        doReturn(Optional.empty()).when(taskRepository).findById(1L);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(1L, updatedTask);
        });
    }
    
    @Test
    void testDeleteTask() {

        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }
    
    @Test
    void testGetAllTasks() {

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setCompleted(true);

        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> foundTasks = taskService.getAllTasks();

        assertEquals(2, foundTasks.size());
        assertEquals("Task 1", foundTasks.get(0).getTitle());
        assertTrue(foundTasks.get(1).isCompleted());
    }
}
