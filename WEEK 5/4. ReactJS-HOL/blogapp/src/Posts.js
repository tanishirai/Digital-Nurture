import React from 'react';
import Post from './Post';

class Posts extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            posts: [
                new Post(1, 'Default Title', 'Default body content.')
            ]
        };
    }

    loadPosts() {
        fetch('https://jsonplaceholder.typicode.com/posts')
            .then(response => response.json())
            .then(data => {
                const loaded = data.map(p => new Post(p.id, p.title, p.body));
                this.setState({ posts: loaded });
            });
    }

    componentDidMount() {
        this.loadPosts();
    }

    componentDidCatch(error, info) {
        alert('Error: ' + error);
    }

    render() {
        return (
            <div>
                {this.state.posts.map(post => (
                    <div key={post.id}>
                        <h2>{post.title}</h2>
                        <p>{post.body}</p>
                    </div>
                ))}
            </div>
        );
    }
}

export default Posts;