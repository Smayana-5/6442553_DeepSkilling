import React from 'react';
import './App.css';

function App() {
  // Create an object of office to display details
  const office = {
    name: "Premium Business Center",
    rent: 45000,
    address: "123 Business District, Downtown",
    image: "https://images.unsplash.com/photo-1497366216548-37526070297c?w=400&h=250&fit=crop&auto=format"
  };

  // Create a list of office objects
  const officeSpaces = [
    {
      id: 1,
      name: "Executive Office Suite",
      rent: 75000,
      address: "456 Corporate Plaza, Business Bay",
      image: "https://images.unsplash.com/photo-1497366811353-6870744d04b2?w=400&h=250&fit=crop&auto=format"
    },
    {
      id: 2,
      name: "Modern Co-working Space",
      rent: 35000,
      address: "789 Innovation Hub, Tech District",
      image: "https://images.unsplash.com/photo-1541746972996-4e0b0f93e586?w=400&h=250&fit=crop&auto=format"
    },
    {
      id: 3,
      name: "Luxury Corner Office",
      rent: 95000,
      address: "321 Prestige Tower, Financial Center",
      image: "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=400&h=250&fit=crop&auto=format"
    },
    {
      id: 4,
      name: "Startup Friendly Space",
      rent: 25000,
      address: "654 Entrepreneur Street, Creative Quarter",
      image: "https://images.unsplash.com/photo-1524758631624-e2822e304c36?w=400&h=250&fit=crop&auto=format"
    },
    {
      id: 5,
      name: "Premium Conference Room",
      rent: 55000,
      address: "987 Meeting Plaza, Corporate Zone",
      image: "https://images.unsplash.com/photo-1560472354-b33ff0c44a43?w=400&h=250&fit=crop&auto=format"
    }
  ];

  // Function to determine rent color based on amount
  const getRentColor = (rent) => {
    return rent < 60000 ? 'red' : 'green';
  };

  return (
    <div className="App">
      {/* Create an element to display the heading of the page */}
      <header style={{
        backgroundColor: '#f8f9fa',
        padding: '20px',
        marginBottom: '30px',
        textAlign: 'center',
        borderBottom: '3px solid #007bff'
      }}>
        <h1 style={{
          color: '#333',
          fontSize: '2.5rem',
          margin: '0',
          fontWeight: 'bold'
        }}>
          Office Space Rental App
        </h1>
        <p style={{
          color: '#666',
          fontSize: '1.2rem',
          margin: '10px 0 0 0'
        }}>
          Find Your Perfect Workspace
        </p>
      </header>

      <main style={{ padding: '0 20px', maxWidth: '1200px', margin: '0 auto' }}>
        {/* Display single office object */}
        <section style={{ marginBottom: '40px' }}>
          <h2 style={{ 
            color: '#333', 
            borderBottom: '2px solid #007bff', 
            paddingBottom: '10px',
            marginBottom: '20px'
          }}>
            Featured Office Space
          </h2>
          
          <div style={{
            border: '1px solid #ddd',
            borderRadius: '8px',
            padding: '20px',
            backgroundColor: '#fff',
            boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
          }}>
            {/* Attribute to display the image of the office space */}
            <img 
              src={office.image} 
              alt={office.name}
              style={{
                width: '100%',
                maxWidth: '400px',
                height: '250px',
                objectFit: 'cover',
                borderRadius: '8px',
                marginBottom: '15px'
              }}
              onError={(e) => {
                e.target.src = 'https://via.placeholder.com/400x250/007bff/white?text=Office+Space';
              }}
            />
            
            <h3 style={{ color: '#333', margin: '0 0 10px 0' }}>
              {office.name}
            </h3>
            
            <p style={{ 
              color: getRentColor(office.rent), 
              fontSize: '1.5rem', 
              fontWeight: 'bold',
              margin: '10px 0'
            }}>
              Rent: ₹{office.rent.toLocaleString()}/month
            </p>
            
            <p style={{ color: '#666', margin: '5px 0' }}>
              <strong>Address:</strong> {office.address}
            </p>
          </div>
        </section>

        {/* Loop through office space items to display more data */}
        <section>
          <h2 style={{ 
            color: '#333', 
            borderBottom: '2px solid #007bff', 
            paddingBottom: '10px',
            marginBottom: '20px'
          }}>
            Available Office Spaces
          </h2>
          
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))',
            gap: '20px'
          }}>
            {/* JavaScript expression in JSX to loop through office spaces */}
            {officeSpaces.map((officeItem) => (
              <div 
                key={officeItem.id}
                style={{
                  border: '1px solid #ddd',
                  borderRadius: '8px',
                  padding: '20px',
                  backgroundColor: '#fff',
                  boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                  transition: 'transform 0.2s ease',
                  cursor: 'pointer'
                }}
                onMouseEnter={(e) => {
                  e.target.style.transform = 'translateY(-5px)';
                }}
                onMouseLeave={(e) => {
                  e.target.style.transform = 'translateY(0)';
                }}
              >
                {/* Display office image with error handling */}
                <img 
                  src={officeItem.image} 
                  alt={officeItem.name}
                  style={{
                    width: '100%',
                    height: '200px',
                    objectFit: 'cover',
                    borderRadius: '8px',
                    marginBottom: '15px'
                  }}
                  onError={(e) => {
                    e.target.src = 'https://via.placeholder.com/350x200/007bff/white?text=Office+Space';
                  }}
                />
                
                <h3 style={{ 
                  color: '#333', 
                  margin: '0 0 10px 0',
                  fontSize: '1.3rem'
                }}>
                  {officeItem.name}
                </h3>
                
                {/* Apply CSS: Display rent color based on condition */}
                <p style={{ 
                  color: getRentColor(officeItem.rent), 
                  fontSize: '1.4rem', 
                  fontWeight: 'bold',
                  margin: '10px 0'
                }}>
                  Rent: ₹{officeItem.rent.toLocaleString()}/month
                </p>
                
                <p style={{ 
                  color: '#666', 
                  margin: '5px 0',
                  lineHeight: '1.4'
                }}>
                  <strong>Address:</strong> {officeItem.address}
                </p>
                
                {/* Additional styling based on rent amount */}
                <div style={{
                  marginTop: '15px',
                  padding: '8px 12px',
                  backgroundColor: officeItem.rent < 60000 ? '#ffebee' : '#e8f5e8',
                  borderRadius: '4px',
                  fontSize: '0.9rem',
                  fontWeight: 'bold',
                  color: officeItem.rent < 60000 ? '#c62828' : '#2e7d32'
                }}>
                  {officeItem.rent < 60000 ? '🔥 Budget Friendly' : '⭐ Premium Space'}
                </div>
              </div>
            ))}
          </div>
        </section>
      </main>

      {/* Footer */}
      <footer style={{
        marginTop: '50px',
        padding: '20px',
        backgroundColor: '#f8f9fa',
        textAlign: 'center',
        borderTop: '1px solid #ddd'
      }}>
        <p style={{ color: '#666', margin: '0' }}>
          © 2024 Office Space Rental App - Built with React & JSX
        </p>
      </footer>
    </div>
  );
}

export default App;