import React, { useState } from 'react';
import GuestPage from './components/GuestPage';
import UserPage from './components/UserPage';
import './styles/App.css';

// Main App Component with Conditional Rendering
const App = () => {
  // Element variable to track login state
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Functions to handle login/logout
  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  // Conditional rendering using element variables
  let currentPage;
  if (isLoggedIn) {
    currentPage = <UserPage onLogout={handleLogout} />;
  } else {
    currentPage = <GuestPage onLogin={handleLogin} />;
  }

  // Prevent rendering of specific components based on conditions
  return (
    <div className="app">
      {currentPage}
    </div>
  );
};

export default App;