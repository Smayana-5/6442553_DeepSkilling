import React, { useState } from 'react';

const BlogDetails = ({ userRole }) => {
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [showComments, setShowComments] = useState({});
  const [likedPosts, setLikedPosts] = useState(new Set());

  const blogPosts = [
    {
      id: 1,
      title: "Getting Started with React Hooks",
      author: "Jane Doe",
      category: "React",
      date: "2024-01-15",
      likes: 45,
      comments: 12,
      published: true,
      premium: false,
      excerpt: "Learn the fundamentals of React Hooks and how they can simplify your component logic."
    },
    {
      id: 2,
      title: "Advanced State Management Patterns",
      author: "John Smith",
      category: "React",
      date: "2024-01-20",
      likes: 78,
      comments: 23,
      published: true,
      premium: true,
      excerpt: "Explore advanced patterns for managing complex state in large React applications."
    },
    {
      id: 3,
      title: "CSS-in-JS vs Traditional CSS",
      author: "Alice Johnson",
      category: "CSS",
      date: "2024-01-25",
      likes: 32,
      comments: 8,
      published: false,
      premium: false,
      excerpt: "A comprehensive comparison between CSS-in-JS solutions and traditional CSS approaches."
    },
    {
      id: 4,
      title: "Building Scalable Node.js Applications",
      author: "Bob Wilson",
      category: "Node.js",
      date: "2024-01-30",
      likes: 56,
      comments: 15,
      published: true,
      premium: true,
      excerpt: "Best practices for building and scaling Node.js applications in production environments."
    }
  ];

  // Method 1: Filter with conditional logic
  const getFilteredPosts = () => {
    let filtered = blogPosts;
    
    // Filter by category
    if (selectedCategory !== 'all') {
      filtered = filtered.filter(post => post.category === selectedCategory);
    }
    
    // Filter by user role permissions
    if (userRole === 'guest') {
      filtered = filtered.filter(post => !post.premium && post.published);
    } else if (userRole === 'user') {
      filtered = filtered.filter(post => post.published);
    }
    // Admin sees all posts
    
    return filtered;
  };

  const toggleComments = (postId) => {
    setShowComments(prev => ({
      ...prev,
      [postId]: !prev[postId]
    }));
  };

  const toggleLike = (postId) => {
    setLikedPosts(prev => {
      const newLiked = new Set(prev);
      if (newLiked.has(postId)) {
        newLiked.delete(postId);
      } else {
        newLiked.add(postId);
      }
      return newLiked;
    });
  };

  const categories = ['all', 'React', 'CSS', 'Node.js'];

  return (
    <div className="blog-details">
      <h2>📝 Blog Details Component</h2>
      
      {/* Method 2: Ternary operator for user greeting */}
      <div className="welcome-message">
        {userRole === 'guest' ? (
          <p>Welcome! <a href="#login">Login</a> to access premium content.</p>
        ) : (
          <p>Welcome back! You have {userRole} access.</p>
        )}
      </div>

      {/* Category Filter */}
      <div className="filter-section">
        <h3>Filter by Category:</h3>
        {categories.map(category => (
          <button
            key={category}
            onClick={() => setSelectedCategory(category)}
            className={selectedCategory === category ? 'active' : ''}
          >
            {category.charAt(0).toUpperCase() + category.slice(1)}
          </button>
        ))}
      </div>

      {/* Method 3: Logical AND for admin panel */}
      {userRole === 'admin' && (
        <div className="admin-blog-panel">
          <h3>📊 Admin Dashboard</h3>
          <div className="admin-stats">
            <span>Total Posts: {blogPosts.length}</span>
            <span>Published: {blogPosts.filter(p => p.published).length}</span>
            <span>Drafts: {blogPosts.filter(p => !p.published).length}</span>
          </div>
          <button className="admin-btn">Create New Post</button>
        </div>
      )}

      <div className="blog-posts">
        {/* Method 4: Complex conditional rendering with map */}
        {getFilteredPosts().length > 0 ? (
          getFilteredPosts().map(post => (
            <div key={post.id} className="blog-post">
              <div className="post-header">
                <h3>{post.title}</h3>
                
                {/* Method 5: Multiple condition checks */}
                <div className="post-badges">
                  {!post.published && <span className="badge draft">Draft</span>}
                  {post.premium && <span className="badge premium">Premium</span>}
                  {post.likes > 50 && <span className="badge popular">Popular</span>}
                </div>
              </div>
              
              <div className="post-meta">
                <span>By {post.author}</span>
                <span>{post.date}</span>
                <span>{post.category}</span>
              </div>
              
              <div className="post-content">
                <p>{post.excerpt}</p>
                
                {/* Method 6: Nested ternary for content access */}
                {post.premium ? (
                  userRole === 'guest' ? (
                    <div className="premium-block">
                      <p>🔒 This is premium content. Login to read more.</p>
                    </div>
                  ) : (
                    <div className="premium-content">
                      <p>✨ Premium content unlocked! Continue reading...</p>
                    </div>
                  )
                ) : (
                  <p>Continue reading this free article...</p>
                )}
              </div>
              
              <div className="post-actions">
                {/* Method 7: Conditional button rendering */}
                {userRole !== 'guest' && (
                  <>
                    <button 
                      onClick={() => toggleLike(post.id)}
                      className={likedPosts.has(post.id) ? 'liked' : ''}
                    >
                      {likedPosts.has(post.id) ? '❤️' : '🤍'} 
                      {post.likes + (likedPosts.has(post.id) ? 1 : 0)}
                    </button>
                    
                    <button onClick={() => toggleComments(post.id)}>
                      💬 {post.comments} Comments
                    </button>
                  </>
                )}
                
                {/* Method 8: Role-based action buttons */}
                {(() => {
                  switch(userRole) {
                    case 'admin':
                      return (
                        <div className="admin-post-actions">
                          <button>Edit</button>
                          <button>Delete</button>
                          <button>{post.published ? 'Unpublish' : 'Publish'}</button>
                        </div>
                      );
                    case 'user':
                      return (
                        <div className="user-post-actions">
                          <button>Share</button>
                          <button>Save</button>
                        </div>
                      );
                    default:
                      return null;
                  }
                })()}
              </div>
              
              {/* Method 9: Comments section with conditional rendering */}
              {showComments[post.id] && (
                <div className="comments-section">
                  <h4>Comments</h4>
                  {userRole === 'guest' ? (
                    <p>Please login to view and post comments.</p>
                  ) : (
                    <div className="comments">
                      <div className="comment">
                        <strong>User1:</strong> Great article! Very helpful.
                      </div>
                      <div className="comment">
                        <strong>User2:</strong> Thanks for sharing this.
                      </div>
                      {userRole !== 'guest' && (
                        <div className="comment-form">
                          <textarea placeholder="Add a comment..."></textarea>
                          <button>Post Comment</button>
                        </div>
                      )}
                    </div>
                  )}
                </div>
              )}
            </div>
          ))
        ) : (
          <div className="no-posts">
            <h3>No posts found</h3>
            <p>
              {selectedCategory === 'all' 
                ? 'No posts available for your access level.'
                : `No posts found in ${selectedCategory} category.`
              }
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default BlogDetails;