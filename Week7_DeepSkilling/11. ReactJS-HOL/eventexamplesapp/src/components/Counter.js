import React, { Component } from 'react';

class Counter extends Component {
  constructor(props) {
    super(props);
    this.state = {
      count: 0,
      message: ''
    };

    // Binding methods to this
    this.incrementValue = this.incrementValue.bind(this);
    this.decrementValue = this.decrementValue.bind(this);
    this.sayHello = this.sayHello.bind(this);
    this.handleIncrement = this.handleIncrement.bind(this);
  }

  // Method to increment the value
  incrementValue() {
    this.setState({
      count: this.state.count + 1
    });
  }

  // Method to decrement the value
  decrementValue() {
    this.setState({
      count: this.state.count - 1
    });
  }

  // Method to say hello with static message
  sayHello() {
    this.setState({
      message: 'Hello! Counter has been incremented!'
    });
    
    // Clear message after 3 seconds
    setTimeout(() => {
      this.setState({ message: '' });
    }, 3000);
  }

  // Method that invokes multiple methods (for Increment button)
  handleIncrement() {
    this.incrementValue();
    this.sayHello();
  }

  render() {
    return (
      <div className="counter-container">
        <h3>Counter Value: {this.state.count}</h3>
        
        <div className="button-group">
          <button 
            className="increment-btn" 
            onClick={this.handleIncrement}
          >
            Increment
          </button>
          
          <button 
            className="decrement-btn" 
            onClick={this.decrementValue}
          >
            Decrement
          </button>
        </div>

        {this.state.message && (
          <div className="message">
            {this.state.message}
          </div>
        )}
      </div>
    );
  }
}

export default Counter;