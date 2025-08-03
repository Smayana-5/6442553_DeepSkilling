import React, { Component } from 'react';

class SyntheticEventButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      clickMessage: ''
    };

    // Binding method to this
    this.onPress = this.onPress.bind(this);
  }

  // Synthetic event handler
  onPress(event) {
    // Demonstrate synthetic event properties
    console.log('Synthetic Event Object:', event);
    console.log('Event Type:', event.type);
    console.log('Target Element:', event.target);
    console.log('Current Target:', event.currentTarget);

    this.setState({
      clickMessage: 'I was clicked'
    });

    // Clear message after 3 seconds
    setTimeout(() => {
      this.setState({ clickMessage: '' });
    }, 3000);
  }

  render() {
    return (
      <div className="synthetic-event-container">
        <button 
          className="synthetic-btn"
          onClick={this.onPress}
        >
          Click Me (Synthetic Event)
        </button>

        {this.state.clickMessage && (
          <div className="click-message">
            {this.state.clickMessage}
          </div>
        )}
      </div>
    );
  }
}

export default SyntheticEventButton;