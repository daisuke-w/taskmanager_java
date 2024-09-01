import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<TaskList />} />
                <Route path="/task/new" element={<TaskForm />} />
                <Route path="/task/edit/:id" element={<TaskForm />} />
            </Routes>
        </Router>
    );
};

export default App;
