/// <reference types="react-scripts" />

interface LoginForm {
  username: string;
  password: string;
  error: {
    username: string;
    password: string;
  };
}

interface PostProps {
  id: string;
  title: string;
  content: string;
  author: string;
  created_at: string;
  commentsCount: number;
  tags: string[];
}

interface CommentProps {
  comment: {
    created_at: string;
    id: string;
    postId: string;
    text: string;
    updated_at: string;
    user: string;
    userId: number;
  };
}
