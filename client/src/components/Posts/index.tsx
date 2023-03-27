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
    <>
      <div className='sm:max-w-sm sm:mx-auto lg:max-w-full pb-3'>
          <div
            key={id}
            className='overflow-hidden transition-shadow duration-300 bg-white rounded-lg shadow-lg border border-slate-300'
          >
            <img
              src='https://images.pexels.com/photos/2408666/pexels-photo-2408666.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=2&amp;w=500'
              className='object-cover w-full h-64'
              alt=''
            />
            <div className='p-5'>
              <p className='mb-3 text-xs font-semibold tracking-wide uppercase'>
                By {author}
                <span className='text-gray-600'>— {createdAt}</span>
              </p>
              <a
                href='/'
                aria-label='Category'
                title='Visit the East'
                className='inline-block mb-3 text-2xl font-bold leading-5 transition-colors duration-200 hover:text-deep-purple-accent-700'
              >
                {title}
              </a>
              <p className='mb-2 text-gray-700'>
                {body.substring(0, 100)}
              </p>
              <p className='mb-2 text-gray-700'>
                {commentsCount} comments
              </p>
              <button
                onClick={handleReadMore}
                aria-label=''
                className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
              >
                Learn more
              </button>
            </div>
          </div>
      </div>
    </>
  );
};

export default Post;
