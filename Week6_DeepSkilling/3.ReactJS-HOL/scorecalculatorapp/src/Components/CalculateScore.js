import React from 'react';
import '../Stylesheets/mystyle.css';

function CalculateScore({ name, school, total, goal }) {
    // Calculate average score (assuming total is out of 100)
    const averageScore = total;
    
    // Determine if goal is achieved
    const goalAchieved = averageScore >= goal;
    
    // Calculate percentage towards goal
    const goalPercentage = ((averageScore / goal) * 100).toFixed(2);

    return (
        <div className="score-container">
            <h2 className="title">Student Score Calculator</h2>
            <div className="student-info">
                <div className="info-row">
                    <span className="label">Student Name:</span>
                    <span className="value">{name}</span>
                </div>
                <div className="info-row">
                    <span className="label">School:</span>
                    <span className="value">{school}</span>
                </div>
                <div className="info-row">
                    <span className="label">Total Score:</span>
                    <span className="value">{total}%</span>
                </div>
                <div className="info-row">
                    <span className="label">Goal:</span>
                    <span className="value">{goal}%</span>
                </div>
            </div>
            
            <div className="results">
                <div className="average-score">
                    <h3>Average Score: {averageScore}%</h3>
                </div>
                
                <div className={`goal-status ${goalAchieved ? 'achieved' : 'not-achieved'}`}>
                    <p>
                        Goal Status: {goalAchieved ? 'ACHIEVED! 🎉' : 'NOT ACHIEVED 📚'}
                    </p>
                    <p>Progress: {goalPercentage}% of goal</p>
                </div>
                
                {!goalAchieved && (
                    <div className="improvement-needed">
                        <p>Need {(goal - averageScore).toFixed(2)} more points to reach your goal!</p>
                    </div>
                )}
            </div>
        </div>
    );
}

export default CalculateScore;