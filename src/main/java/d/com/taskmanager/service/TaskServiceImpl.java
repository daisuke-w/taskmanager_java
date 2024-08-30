package d.com.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import d.com.taskmanager.entity.Task;
import d.com.taskmanager.repository.TaskRepository;

public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public List<Task> getAllTask() {
		return taskRepository.findAll();
	}

	@Override
	public Task getTaskById(Long id) {
		Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			return task.get();
		} else {
			throw new RuntimeException("Task not found with id" + id);
		}
	}

	@Override
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task updateTask(Long id, Task taskDetails) {
		Task task = getTaskById(id);
		task.setTitle(taskDetails.getTitle());
		task.setDescription(taskDetails.getDescription());
		task.setCompleted(taskDetails.isCompleted());
		return taskRepository.save(task);
	}

	@Override
	public void deleteTask(Long id) {
		Task task = getTaskById(id);
		taskRepository.delete(task);
	}
}
