import React from 'react';
import './App.css';
import Home from './components/Home';
import About from './components/About';
import Contact from './components/Contact';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Student Management Portal</h1>
        <Home />
        <About />
        <Contact />
      </header>
    </div>
  );
}

export default App;