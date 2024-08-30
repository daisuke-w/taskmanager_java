package d.com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import d.com.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
