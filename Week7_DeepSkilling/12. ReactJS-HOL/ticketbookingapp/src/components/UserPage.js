import React, { useState } from 'react';
import Header from './Header';
import { flightData } from '../utils/flightData';

// User Component - Shows flight details with booking capability
const UserPage = ({ onLogout }) => {
  const [bookedFlights, setBookedFlights] = useState([]);

  const handleBookTicket = (flight) => {
    if (!bookedFlights.find(booked => booked.id === flight.id)) {
      setBookedFlights([...bookedFlights, flight]);
      alert(`Ticket booked successfully for ${flight.airline} flight!`);
    } else {
      alert('You have already booked this flight!');
    }
  };

  // Element variable for booked flights section
  let bookedFlightsSection = null;
  if (bookedFlights.length > 0) {
    bookedFlightsSection = (
      <div className="card booked-flights-card">
        <h3 className="flights-title">Your Booked Flights</h3>
        <div className="booked-flights-list">
          {bookedFlights.map(flight => (
            <div key={flight.id} className="booked-flight-item">
              <p className="booked-flight-airline">{flight.airline}</p>
              <p className="booked-flight-details">{flight.from} → {flight.to} at {flight.time}</p>
              <p className="booked-flight-price">{flight.price}</p>
            </div>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f9fafb' }}>
      <Header isLoggedIn={true} onLogout={onLogout} />

      <main className="main-content">
        <div className="card welcome-card">
          <h2 className="welcome-title">Welcome back, User!</h2>
          <p className="welcome-text">You can now book tickets for available flights.</p>
          {/* Conditional rendering for booking status */}
          {bookedFlights.length > 0 && (
            <div className="booking-status">
              <p className="booking-status-text">Booked Flights: {bookedFlights.length}</p>
            </div>
          )}
        </div>

        <div className="card flights-card">
          <h3 className="flights-title">Available Flights</h3>
          <div className="flights-list">
            {flightData.map(flight => {
              const isBooked = bookedFlights.find(booked => booked.id === flight.id);
              return (
                <div key={flight.id} className="flight-card">
                  <div className="flight-card-content">
                    <div className="flight-details">
                      <h4 className="flight-airline">{flight.airline}</h4>
                      <p className="flight-route">{flight.from} → {flight.to}</p>
                      <p className="flight-time">Departure: {flight.time}</p>
                      {/* Conditional rendering for booked badge */}
                      {isBooked && (
                        <span className="booked-badge">✓ Booked</span>
                      )}
                    </div>
                    <div className="flight-pricing">
                      <p className="flight-price">{flight.price}</p>
                      <button 
                        onClick={() => handleBookTicket(flight)}
                        className={`btn ${isBooked ? 'btn-booked' : 'btn-book'}`}
                        disabled={isBooked}
                      >
                        {isBooked ? 'Already Booked' : 'Book Now'}
                      </button>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Conditionally render booked flights section */}
        {bookedFlightsSection}
      </main>
    </div>
  );
};

export default UserPage;