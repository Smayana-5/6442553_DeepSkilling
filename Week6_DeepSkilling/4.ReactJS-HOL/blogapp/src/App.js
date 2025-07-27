import React from 'react';
import './App.css';
import Posts from './Posts';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>My Blog Application</h1>
      </header>
      <main>
        <Posts />
      </main>
    </div>
  );
}

export default App;