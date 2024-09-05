import React, { useState, useEffect } from "react";
import { getTasks, deleteTask } from "../services/api";
import Button from './Button';

import './TaskList.css';

const TaskList = () => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await getTasks();
        setTasks(response.data);
      } catch(error) {
        console.error('Error fetching tasks:', error);
      }
    };

    fetchTasks();
  }, []);

  const handleDelete = async (id) => {
    try {
      await deleteTask(id);
      setTasks(tasks.filter(task => task.id !== id));
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  const handleCompleteToggle = (id) => {
    const updatedTasks = tasks.map(task => 
      task.id === id ? { ...task, completed: !task.completed } : task
    );
    setTasks(updatedTasks);
  };

  return (
    <div className="task-list-container">
      <h2 className="task-list-title">Task List</h2>
      <Button as="a" href="/task/new" className="new-task-button">New Task</Button>
      <table className="task-table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Completed</th>
            <th>Edit</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map(task => (
            <tr key={task.id} className="task-item">
              <td>{task.title}</td>
              <td>
                <input 
                  type="checkbox" 
                  checked={task.completed} 
                  onChange={() => handleCompleteToggle(task.id)} 
                />
              </td>
              <td>
                <Button as="a" href={`/task/edit/${task.id}`} className="edit-task-button">Edit</Button>
              </td>
              <td>
                <Button onClick={() => handleDelete(task.id)}>Delete</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TaskList;
