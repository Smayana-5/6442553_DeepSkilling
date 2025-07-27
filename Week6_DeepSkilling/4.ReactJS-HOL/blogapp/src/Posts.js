import React from 'react';
import Post from './Post';

class Posts extends React.Component {
  constructor(props) {
    super(props);
    // Step 5: Initialize component with list of posts in state
    this.state = {
      posts: [],
      loading: true,
      error: null
    };
  }

  // Step 6: Create loadPosts() method using Fetch API
  loadPosts() {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch posts');
        }
        return response.json();
      })
      .then(data => {
        this.setState({
          posts: data.slice(0, 10), // Limit to first 10 posts for better display
          loading: false
        });
      })
      .catch(error => {
        this.setState({
          error: error.message,
          loading: false
        });
      });
  }

  // Step 7: Implement componentDidMount() hook
  componentDidMount() {
    console.log('Component mounted - fetching posts...');
    this.loadPosts();
  }

  // Step 9: Implement componentDidCatch() method for error handling
  componentDidCatch(error, errorInfo) {
    console.error('Error caught by componentDidCatch:', error, errorInfo);
    alert(`An error occurred: ${error.message}`);
    this.setState({
      error: error.message
    });
  }

  // Step 8: Implement render() method
  render() {
    const { posts, loading, error } = this.state;

    if (loading) {
      return (
        <div className="posts-container">
          <h2>Blog Posts</h2>
          <p>Loading posts...</p>
        </div>
      );
    }

    if (error) {
      return (
        <div className="posts-container">
          <h2>Blog Posts</h2>
          <p style={{ color: 'red' }}>Error: {error}</p>
        </div>
      );
    }

    return (
      <div className="posts-container">
        <h2>Blog Posts</h2>
        <div className="posts-list">
          {posts.map(post => (
            <Post key={post.id} post={post} />
          ))}
        </div>
      </div>
    );
  }
}

export default Posts;