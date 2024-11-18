import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';
import { BookList } from './BookList';
import { Timer } from './Timer';
import { Clock } from './Clock';
import { TodoList } from './ToDoList';

function App() {
  return (
    <div className="App">
      <h1>React Demo</h1>
      <h2>
        Timer and Clock
      </h2>
      <div>
        <Timer message="Time elapsed:"/><Clock message="The time is:"/>
      </div>
      <h2>To Do List</h2>
      <TodoList/>
      <h2>Book List</h2> 
      <BookList/>
    </div>
  );
}

export default App;
