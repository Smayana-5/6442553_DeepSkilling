import React, { Component } from 'react';

class WelcomeButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      welcomeMessage: ''
    };

    // Binding method to this
    this.handleWelcome = this.handleWelcome.bind(this);
  }

  // Method that takes "welcome" as an argument
  handleWelcome(message) {
    this.setState({
      welcomeMessage: `${message}! Thank you for clicking the button!`
    });

    // Clear message after 3 seconds
    setTimeout(() => {
      this.setState({ welcomeMessage: '' });
    }, 3000);
  }

  render() {
    return (
      <div className="welcome-container">
        <button 
          className="welcome-btn"
          onClick={() => this.handleWelcome('Welcome')}
        >
          Say Welcome
        </button>

        {this.state.welcomeMessage && (
          <div className="welcome-message">
            {this.state.welcomeMessage}
          </div>
        )}
      </div>
    );
  }
}

export default WelcomeButton;