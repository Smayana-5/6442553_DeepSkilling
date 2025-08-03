import React from 'react';
import './App.css';
import Counter from './components/Counter';
import WelcomeButton from './components/WelcomeButton';
import SyntheticEventButton from './components/SyntheticEventButton';
import CurrencyConverter from './components/CurrencyConverter';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>React Event Examples App</h1>
        
        {/* Counter Component */}
        <div className="component-section">
          <h2>Counter Example</h2>
          <Counter />
        </div>

        {/* Welcome Button Component */}
        <div className="component-section">
          <h2>Welcome Button Example</h2>
          <WelcomeButton />
        </div>

        {/* Synthetic Event Button Component */}
        <div className="component-section">
          <h2>Synthetic Event Example</h2>
          <SyntheticEventButton />
        </div>

        {/* Currency Converter Component */}
        <div className="component-section">
          <h2>Currency Converter Example</h2>
          <CurrencyConverter />
        </div>
      </header>
    </div>
  );
}

export default App;