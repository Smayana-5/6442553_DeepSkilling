import React from 'react';

const Header = ({ isLoggedIn, onLogin, onLogout }) => {
  // Conditional rendering for button
  const authButton = isLoggedIn ? (
    <button 
      onClick={onLogout}
      className="btn btn-logout"
    >
      Logout
    </button>
  ) : (
    <button 
      onClick={onLogin}
      className="btn btn-login"
    >
      Login
    </button>
  );

  // Conditional styling based on user status
  const headerClass = isLoggedIn ? "header header-user" : "header header-guest";

  return (
    <header className={headerClass}>
      <div className="header-container">
        <h1 className="header-title">✈️ Ticket Booking App</h1>
        {authButton}
      </div>
    </header>
  );
};

export default Header;