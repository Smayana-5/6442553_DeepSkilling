import React, { Component } from 'react';

class CurrencyConverter extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rupees: '',
      euros: '',
      conversionRate: 0.011 // 1 INR = 0.011 EUR (approximate)
    };

    // Binding methods to this
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // Handle input change
  handleInputChange(event) {
    this.setState({
      rupees: event.target.value
    });
  }

  // Handle form submission and conversion
  handleSubmit(event) {
    event.preventDefault(); // Prevent form from submitting normally
    
    const rupeesValue = parseFloat(this.state.rupees);
    
    if (isNaN(rupeesValue) || rupeesValue <= 0) {
      alert('Please enter a valid amount in Rupees');
      return;
    }

    const eurosValue = (rupeesValue * this.state.conversionRate).toFixed(2);
    
    this.setState({
      euros: eurosValue
    });
  }

  render() {
    return (
      <div className="currency-converter-container">
        <h3>Currency Converter (INR to EUR)</h3>
        
        <form onSubmit={this.handleSubmit}>
          <div className="input-group">
            <label htmlFor="rupees">Enter Amount in Rupees:</label>
            <input
              type="number"
              id="rupees"
              value={this.state.rupees}
              onChange={this.handleInputChange}
              placeholder="Enter amount in INR"
              step="0.01"
              min="0"
            />
          </div>

          <button type="submit" className="convert-btn">
            Convert
          </button>
        </form>

        {this.state.euros && (
          <div className="conversion-result">
            <h4>Conversion Result:</h4>
            <p>₹{this.state.rupees} = €{this.state.euros}</p>
            <small>Exchange Rate: 1 INR = {this.state.conversionRate} EUR</small>
          </div>
        )}
      </div>
    );
  }
}

export default CurrencyConverter;