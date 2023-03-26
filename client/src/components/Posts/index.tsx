import { useNavigate } from 'react-router-dom';

const Post = ({
  id,
  title,
  body,
  author,
  createdAt,
  commentsCount,
}: BlogPostProps) => {
  const navigate = useNavigate();

  const handleReadMore = () => {
    navigate(`/blog-details/${id}`);
  };
  
  return (
    <div key={id} className='mb-4 bg-white shadow-lg rounded-lg max-w-2xl mx-auto px-4 py-8'>
      <h1 className='text-3xl font-bold mb-4'>{title}</h1>
      <p className='text-gray-600 mb-4'>
        By {author} on {createdAt}
      </p>
      <div dangerouslySetInnerHTML={{ __html: body }} />
      <p className='text-gray-600 mb-4'>Comments: {commentsCount}</p>
      <div className='flex justify-end'>
      <button
        onClick={handleReadMore}
        className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
      >
        Read More
        </button>
        </div>
    </div>
  );
};

export default Post;
