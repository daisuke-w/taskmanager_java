package d.com.taskmanager.service;

import java.util.List;

import d.com.taskmanager.entity.Task;

public interface TaskService {
	List<Task> getAllTask();
	Task getTaskById(Long id);
	Task createTask(Task task);
	Task updateTask(Long id, Task taskDetails);
	void deleteTask(Long id);
}
