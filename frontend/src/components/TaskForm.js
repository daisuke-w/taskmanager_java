import React, { useState, useEffect } from "react";
import { getTaskById, createTask, updateTask } from "../services/api";
import { useParams, useNavigate } from "react-router-dom";
import Button from './Button';

import './TaskForm.css';

const TaskForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [task, setTask] = useState({ title: '', description: '', deadline: '', completed: false });

  useEffect(() => {
    if (id) {
      const fetchTask = async () => {
        try {
          const response = await getTaskById(id);
          setTask(response.data);
        } catch (error) {
          console.error('Error fetching task:', error);
        }
      };

      fetchTask();
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTask({ ...task, [name]: value });
};

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (id) {
        await updateTask(id, task);
      } else {
        await createTask(task);
      }
      navigate('/');
    } catch (error) {
      console.error('Error saving task:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="task-form-container">
      <h2>{id ? 'Edit Task' : 'Create Task'}</h2>
      <label>
        Title:
        <input type="text" name="title" value={task.title} onChange={handleChange} required />
      </label>
      <br />
      <label>
        Description:
        <textarea name="description" value={task.description} onChange={handleChange} />
      </label>
      <br />
      <label>
        Deadline:
        <input type="date" name="deadline" max="9999-12-31" value={task.deadline} onChange={handleChange}/>
      </label>
      <br />
      <Button as="a" href="/" className="task-list-button">Task List</Button>
      <Button type="submit">Save</Button>
    </form>
  );
};

export default TaskForm;
