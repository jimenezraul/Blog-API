/// <reference types="react-scripts" />

interface LoginForm {
  username: string;
  password: string;
  error: {
    username: string;
    password: string;
  };
}

interface BlogPostProps {
  id: string;
  title: string;
  body: string;
  author: string;
  createdAt: string;
  commentsCount: number;
}

interface CommentProps {
  comment: {
    created_at: string;
    id: string;
    postId: string;
    text: string;
    updated_at: string;
    user: string;
    userId: string;
  };
}
