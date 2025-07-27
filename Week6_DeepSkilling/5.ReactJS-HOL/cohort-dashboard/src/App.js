import React from 'react';
import './App.css';
import CohortDetails from './components/CohortDetails';

function App() {
  // Sample cohort data
  const cohorts = [
    {
      id: 'C001',
      name: 'React Development Batch 1',
      technology: 'ReactJS',
      startDate: '2024-01-15',
      endDate: '2024-03-15',
      status: 'ongoing',
      participants: 25
    },
    {
      id: 'C002',
      name: 'Angular Development Batch 2',
      technology: 'Angular',
      startDate: '2023-11-01',
      endDate: '2024-01-01',
      status: 'completed',
      participants: 30
    },
    {
      id: 'C003',
      name: 'Vue.js Development Batch 1',
      technology: 'Vue.js',
      startDate: '2024-02-01',
      endDate: '2024-04-01',
      status: 'ongoing',
      participants: 20
    },
    {
      id: 'C004',
      name: 'Node.js Backend Batch 1',
      technology: 'Node.js',
      startDate: '2023-10-15',
      endDate: '2023-12-15',
      status: 'completed',
      participants: 18
    }
  ];

  return (
    <div className="App">
      <header className="App-header">
        <h1>Cognizant My Academy - Cohort Dashboard</h1>
        <div className="cohort-container">
          {cohorts.map(cohort => (
            <CohortDetails key={cohort.id} cohort={cohort} />
          ))}
        </div>
      </header>
    </div>
  );
}

export default App;
