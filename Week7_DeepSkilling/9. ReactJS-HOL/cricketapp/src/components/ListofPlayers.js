import React from 'react';

const ListofPlayers = () => {
  // Declare an array with 11 players using ES6 features
  const players = [
    { name: "Virat Kohli", score: 85 },
    { name: "Rohit Sharma", score: 92 },
    { name: "KL Rahul", score: 65 },
    { name: "Hardik Pandya", score: 78 },
    { name: "Rishabh Pant", score: 45 },
    { name: "Ravindra Jadeja", score: 55 },
    { name: "Jasprit Bumrah", score: 25 },
    { name: "Mohammed Shami", score: 15 },
    { name: "Yuzvendra Chahal", score: 35 },
    { name: "Bhuvneshwar Kumar", score: 40 },
    { name: "Shikhar Dhawan", score: 88 }
  ];

  // Filter players with scores below 70 using arrow functions
  const lowScorePlayers = players.filter(player => player.score < 70);

  return (
    <div className="list-of-players">
      <h2>List of Players</h2>
      
      <div className="section">
        <h3>All Players</h3>
        <div className="players-grid">
          {players.map((player, index) => (
            <div key={index} className="player-card">
              <h4>{player.name}</h4>
              <p>Score: {player.score}</p>
            </div>
          ))}
        </div>
      </div>

      <div className="section">
        <h3>Players with Scores Below 70</h3>
        <div className="players-grid">
          {lowScorePlayers.map((player, index) => (
            <div key={index} className="player-card low-score">
              <h4>{player.name}</h4>
              <p>Score: {player.score}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ListofPlayers;
