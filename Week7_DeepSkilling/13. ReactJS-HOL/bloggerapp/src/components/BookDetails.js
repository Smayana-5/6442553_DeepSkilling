import React, { useState } from 'react';

const BookDetails = ({ userRole }) => {
  const [selectedBook, setSelectedBook] = useState(null);
  const [showDescription, setShowDescription] = useState(false);

  const books = [
    {
      id: 1,
      title: "React: The Complete Guide",
      author: "Maximilian Schwarzmüller",
      price: 49.99,
      rating: 4.8,
      description: "Master React with this comprehensive guide covering hooks, context, and modern patterns.",
      available: true,
      category: "Programming"
    },
    {
      id: 2,
      title: "JavaScript: The Good Parts",
      author: "Douglas Crockford",
      price: 29.99,
      rating: 4.5,
      description: "Discover the elegant, lightweight, and highly expressive language at the heart of JavaScript.",
      available: false,
      category: "Programming"
    },
    {
      id: 3,
      title: "Clean Code",
      author: "Robert C. Martin",
      price: 39.99,
      rating: 4.9,
      description: "A handbook of agile software craftsmanship with practical advice on writing clean code.",
      available: true,
      category: "Software Engineering"
    }
  ];

  // Method 1: If-else conditional rendering
  const renderBookStatus = (book) => {
    if (book.available) {
      return <span className="status available">✅ Available</span>;
    } else {
      return <span className="status unavailable">❌ Out of Stock</span>;
    }
  };

  // Method 2: Ternary operator for price display
  const renderPrice = (book) => {
    return userRole === 'guest' ? (
      <span className="price-hidden">Login to see price</span>
    ) : (
      <span className="price">${book.price}</span>
    );
  };

  return (
    <div className="book-details">
      <h2>📚 Book Details Component</h2>
      
      {/* Method 3: Logical AND for admin features */}
      {userRole === 'admin' && (
        <div className="admin-controls">
          <button className="admin-btn">Add New Book</button>
          <button className="admin-btn">Edit Inventory</button>
        </div>
      )}

      <div className="books-grid">
        {books.map(book => (
          <div key={book.id} className="book-card">
            <h3>{book.title}</h3>
            <p className="author">by {book.author}</p>
            
            {/* Method 4: Multiple ternary operators */}
            <div className="book-meta">
              <span className="category">{book.category}</span>
              <span className="rating">
                {book.rating >= 4.8 ? '⭐⭐⭐⭐⭐' : 
                 book.rating >= 4.0 ? '⭐⭐⭐⭐' : '⭐⭐⭐'}
              </span>
            </div>
            
            {/* Method 1: Function call for status */}
            {renderBookStatus(book)}
            
            {/* Method 2: Ternary for price */}
            <div className="price-section">
              {renderPrice(book)}
            </div>
            
            {/* Method 5: Short-circuit evaluation */}
            {book.available && userRole !== 'guest' && (
              <button 
                className="select-btn"
                onClick={() => setSelectedBook(book)}
              >
                Select Book
              </button>
            )}
            
            <button 
              className="desc-btn"
              onClick={() => setShowDescription(showDescription === book.id ? null : book.id)}
            >
              {showDescription === book.id ? 'Hide' : 'Show'} Description
            </button>
            
            {/* Method 6: Conditional content rendering */}
            {showDescription === book.id && (
              <div className="description">
                <p>{book.description}</p>
              </div>
            )}
          </div>
        ))}
      </div>
      
      {/* Method 7: Complex conditional with multiple conditions */}
      {selectedBook && (
        <div className="selected-book">
          <h3>Selected Book</h3>
          <p>You selected: <strong>{selectedBook.title}</strong></p>
          {userRole === 'admin' ? (
            <div className="admin-actions">
              <button>Edit Book</button>
              <button>Delete Book</button>
              <button>Update Stock</button>
            </div>
          ) : userRole === 'user' ? (
            <div className="user-actions">
              <button>Add to Cart</button>
              <button>Add to Wishlist</button>
            </div>
          ) : (
            <p>Please login to perform actions</p>
          )}
        </div>
      )}
    </div>
  );
};

export default BookDetails;
