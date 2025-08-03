import React from 'react';
import Header from './Header';
import { flightData } from '../utils/flightData';

// Guest Component - Shows flight details for browsing
const GuestPage = ({ onLogin }) => {
  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb' }}>
      <Header isLoggedIn={false} onLogin={onLogin} />

      <main className="main-content">
        <div className="card welcome-card">
          <h2 className="welcome-title">Welcome, Guest!</h2>
          <p className="welcome-text">Browse available flights below. Please login to book tickets.</p>
        </div>

        <div className="card flights-card">
          <h3 className="flights-title">Available Flights</h3>
          <div className="flights-list">
            {flightData.map(flight => (
              <div key={flight.id} className="flight-card">
                <div className="flight-card-content">
                  <div className="flight-details">
                    <h4 className="flight-airline">{flight.airline}</h4>
                    <p className="flight-route">{flight.from} → {flight.to}</p>
                    <p className="flight-time">Departure: {flight.time}</p>
                  </div>
                  <div className="flight-pricing">
                    <p className="flight-price">{flight.price}</p>
                    {/* Prevent booking component from rendering for guests */}
                    <button className="btn btn-disabled">
                      Login to Book
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </main>
    </div>
  );
};

export default GuestPage;