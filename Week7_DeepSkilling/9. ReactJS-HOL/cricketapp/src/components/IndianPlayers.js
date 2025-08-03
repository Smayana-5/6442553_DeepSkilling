import React from 'react';

const IndianPlayers = () => {
  // Array for destructuring demonstration
  const teamPlayers = [
    "Virat Kohli", "Rohit Sharma", "KL Rahul", "Hardik Pandya", 
    "Rishabh Pant", "Ravindra Jadeja", "Jasprit Bumrah", "Mohammed Shami"
  ];

  // Destructuring to separate odd and even positioned players
  const [first, second, third, fourth, fifth, sixth, seventh, eighth] = teamPlayers;
  
  // Creating odd and even team arrays using destructuring
  const oddTeamPlayers = [first, third, fifth, seventh];
  const evenTeamPlayers = [second, fourth, sixth, eighth];

  // T20 players array
  const T20players = [
    "Virat Kohli", "Rohit Sharma", "KL Rahul", "Hardik Pandya", "Rishabh Pant"
  ];

  // Ranji Trophy players array
  const RanjiTrophyPlayers = [
    "Prithvi Shaw", "Devdutt Padikkal", "Sarfaraz Khan", "Rinku Singh"
  ];

  // Merge arrays using ES6 spread operator
  const mergedPlayers = [...T20players, ...RanjiTrophyPlayers];

  return (
    <div className="indian-players">
      <h2>Indian Players</h2>
      
      <div className="section">
        <h3>Team Distribution (Using Destructuring)</h3>
        <div className="team-container">
          <div className="team">
            <h4>Odd Position Team Players</h4>
            <ul>
              {oddTeamPlayers.map((player, index) => (
                <li key={index} className="odd-player">{player}</li>
              ))}
            </ul>
          </div>
          
          <div className="team">
            <h4>Even Position Team Players</h4>
            <ul>
              {evenTeamPlayers.map((player, index) => (
                <li key={index} className="even-player">{player}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>

      <div className="section">
        <h3>Merged Teams (T20 + Ranji Trophy)</h3>
        <div className="merged-section">
          <div className="original-teams">
            <div className="team-list">
              <h4>T20 Players</h4>
              <ul>
                {T20players.map((player, index) => (
                  <li key={index} className="t20-player">{player}</li>
                ))}
              </ul>
            </div>
            
            <div className="team-list">
              <h4>Ranji Trophy Players</h4>
              <ul>
                {RanjiTrophyPlayers.map((player, index) => (
                  <li key={index} className="ranji-player">{player}</li>
                ))}
              </ul>
            </div>
          </div>
          
          <div className="merged-team">
            <h4>All Players (Merged using ES6 Spread Operator)</h4>
            <ul>
              {mergedPlayers.map((player, index) => (
                <li key={index} className="merged-player">{player}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default IndianPlayers;