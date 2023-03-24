import React from 'react';

interface BlogPostProps {
  id: string;
  title: string;
  body: string;
  author: string;
  createdAt: string;
  commentsCount: number;
}

const BlogPost = ({
  id,
  title,
  body,
  author,
  createdAt,
  commentsCount,
}: BlogPostProps) => {
  return (
    <div key={id} className='max-w-2xl mx-auto px-4 py-8'>
      <h1 className='text-3xl font-bold mb-4'>{title}</h1>
      <p className='text-gray-600 mb-4'>
        By {author} on {createdAt}
          </p>
          <div dangerouslySetInnerHTML={{ __html: body }} />
      <p className='text-gray-600 mb-4'>Comments: {commentsCount}</p>
    </div>
  );
};

export default BlogPost;
