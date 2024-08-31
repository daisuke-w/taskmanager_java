package d.com.taskmanager;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import d.com.taskmanager.controller.TaskController;
import d.com.taskmanager.entity.Task;
import d.com.taskmanager.service.TaskService;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task createTestTask(Long id, String title, String description, boolean completed) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        return task;
    }

    @BeforeEach
    void setUp() {
        // 必要に応じて初期化処理を実装
    }

    @Test
    void testCreateTask() throws Exception {
        Task task = createTestTask(null, "Test Task", "This is a test task.", false);

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Task\",\"description\":\"This is a test task.\",\"completed\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("This is a test task."))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void testGetTaskById() throws Exception {
        Task task = createTestTask(1L, "Test Task", "This is a test task.", false);

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/task/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("This is a test task."))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void testUpdateTask() throws Exception {
        Task updatedTask = createTestTask(1L, "Updated Title", "Updated Description", true);

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/task/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"completed\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/task/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
