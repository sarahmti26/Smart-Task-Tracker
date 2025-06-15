import logo from './logo.svg';
import './App.css';

import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
    const [tasks, setTasks] = useState([]);

  useEffect(() => {
    axios.get('/api/tasks').then(res => setTasks(res.data));
  }, []);


  return (
    <div>
      <h1>My Tasks</h1>
      {tasks.map(t => (
        <div key={t.id}>
          <h3>{t.title}</h3>
          <p>Due: {t.dueDate}</p>
        </div>
      ))}
    </div>
  );
}


export default App;
