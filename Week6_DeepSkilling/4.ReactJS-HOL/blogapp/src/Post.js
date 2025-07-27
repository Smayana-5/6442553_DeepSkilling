import React from 'react';

class Post extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { post } = this.props;
    return (
      <div className="post-item">
        <h3>{post.title}</h3>
        <p>{post.body}</p>
        <hr />
      </div>
    );
  }
}

export default Post;