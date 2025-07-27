import React from 'react';
import './App.css';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Student Management Portal</h1>
        
        {/* Example 1: Student who achieved goal */}
        <CalculateScore 
          name="John Smith"
          school="Springfield High School"
          total={85}
          goal={80}
        />
        
        {/* Example 2: Student who didn't achieve goal */}
        <CalculateScore 
          name="Sarah Johnson"
          school="Riverside Academy"
          total={72}
          goal={85}
        />
        
        {/* Example 3: Another student */}
        <CalculateScore 
          name="Mike Wilson"
          school="Central High School"
          total={92}
          goal={90}
        />
      </header>
    </div>
  );
}

export default App;