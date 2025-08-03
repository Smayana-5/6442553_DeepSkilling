import React, { useState } from 'react';
import ListofPlayers from './components/ListofPlayers';
import IndianPlayers from './components/IndianPlayers';
import './App.css';

function App() {
  const [flag, setFlag] = useState(true);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Cricket App</h1>
        <button onClick={() => setFlag(!flag)} className="toggle-btn">
          Toggle View (Current: {flag ? 'List of Players' : 'Indian Players'})
        </button>
        
        {flag ? <ListofPlayers /> : <IndianPlayers />}
      </header>
    </div>
  );
}
export default App;