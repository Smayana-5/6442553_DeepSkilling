import React, { useState } from 'react';
import BookDetails from './components/BookDetails';
import BlogDetails from './components/BlogDetails';
import CourseDetails from './components/CourseDetails';
import './App.css';

function App() {
  const [selectedComponent, setSelectedComponent] = useState('book');
  const [showContent, setShowContent] = useState(true);
  const [userRole, setUserRole] = useState('user'); // admin, user, guest

  // Method 1: If-else conditional rendering
  const renderSelectedComponent = () => {
    if (selectedComponent === 'book') {
      return <BookDetails userRole={userRole} />;
    } else if (selectedComponent === 'blog') {
      return <BlogDetails userRole={userRole} />;
    } else if (selectedComponent === 'course') {
      return <CourseDetails userRole={userRole} />;
    } else {
      return <div>No component selected</div>;
    }
  };

  return (
    <div className="App">
      <header className="app-header">
        <h1>Blogger App - Conditional Rendering Demo</h1>
        
        {/* Control Panel */}
        <div className="control-panel">
          <div className="button-group">
            <button 
              onClick={() => setSelectedComponent('book')}
              className={selectedComponent === 'book' ? 'active' : ''}
            >
              Book Details
            </button>
            <button 
              onClick={() => setSelectedComponent('blog')}
              className={selectedComponent === 'blog' ? 'active' : ''}
            >
              Blog Details
            </button>
            <button 
              onClick={() => setSelectedComponent('course')}
              className={selectedComponent === 'course' ? 'active' : ''}
            >
              Course Details
            </button>
          </div>
          
          <div className="toggle-controls">
            <button onClick={() => setShowContent(!showContent)}>
              {showContent ? 'Hide Content' : 'Show Content'}
            </button>
            
            <select 
              value={userRole} 
              onChange={(e) => setUserRole(e.target.value)}
              className="role-selector"
            >
              <option value="guest">Guest</option>
              <option value="user">User</option>
              <option value="admin">Admin</option>
            </select>
          </div>
        </div>
      </header>

      <main className="main-content">
        {/* Method 2: Ternary operator conditional rendering */}
        {showContent ? (
          <div className="content-wrapper">
            {/* Method 3: Logical AND (&&) operator */}
            {userRole === 'admin' && (
              <div className="admin-panel">
                <h3>🔧 Admin Panel</h3>
                <p>You have administrative privileges</p>
              </div>
            )}
            
            {/* Method 1: If-else function call */}
            {renderSelectedComponent()}
            
            {/* Method 4: Switch case using object mapping */}
            <div className="component-info">
              {(() => {
                const componentInfo = {
                  book: "📚 Displaying Book Details Component",
                  blog: "📝 Displaying Blog Details Component", 
                  course: "🎓 Displaying Course Details Component"
                };
                return <p>{componentInfo[selectedComponent]}</p>;
              })()}
            </div>
          </div>
        ) : (
          <div className="hidden-message">
            <h2>Content is hidden</h2>
            <p>Click "Show Content" to display the components</p>
          </div>
        )}
        
        {/* Method 5: Multiple conditions with ternary */}
        <div className="status-bar">
          Status: {userRole === 'admin' ? '🔑 Administrator' : 
                   userRole === 'user' ? '👤 Regular User' : 
                   '👋 Guest User'}
        </div>
      </main>
    </div>
  );
}

export default App;