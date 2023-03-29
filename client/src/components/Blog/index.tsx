import { FetchData } from '../../utils/FetchData';
import { useState, useEffect } from 'react';
import { useNavigate, useMatch, Link } from 'react-router-dom';

const Blog = () => {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);
  const isHome = useMatch('/');

  let fetchPost: any;
  if (isHome) {
    fetchPost = async () => {
      const res = await FetchData('/api/v1/posts?size=6', 'GET');

      if (!res) return;

      const newData = res?.map((post: any) => {
        return {
          ...post,
          created_at: Intl.DateTimeFormat().format(new Date(post.created_at)),
        };
      });
      setPosts(newData);
    };
  } else {
    fetchPost = async () => {
      const res = await FetchData('/api/v1/posts', 'GET');
      const newData = res?.map((post: any) => {
        return {
          ...post,
          created_at: Intl.DateTimeFormat().format(new Date(post.created_at)),
        };
      });
      setPosts(newData);
    };
  }

  useEffect(() => {
    fetchPost();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleLoadMore = (link: string) => {
    navigate(link);
  };

  return (
    <div className='flex flex-col w-full flex-1 bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <h1 className='text-3xl font-bold text-blue-900 mb-8'>
        Latest Posts
      </h1>
      <div className='grid gap-8 sm:grid-cols-2 lg:grid-cols-3 sm:max-w-md md:max-w-full sm:mx-auto'>
        {posts.map((post: any) => (
          <div
            key={post.id}
            className='overflow-hidden transition-shadow duration-300 bg-white rounded shadow-lg border'
          >
            {/* <img
              src='https://images.pexels.com/photos/2408666/pexels-photo-2408666.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=2&amp;w=500'
              className='object-cover w-full h-64'
              alt=''
            /> */}
            <div className='p-5'>
              <p className='mb-3 text-xs font-semibold tracking-wide uppercase'>
                By {post.userName}
                <span className='text-gray-600'>— {post.created_at}</span>
              </p>
              <Link
                to={`/blog-details/${post.id}`}
                aria-label='Category'
                title='Visit the East'
                className='inline-block mb-3 text-2xl font-bold leading-5 transition-colors duration-200 hover:text-deep-purple-accent-700 text-blue-900'
              >
                {post.title}
              </Link>
              <div
                dangerouslySetInnerHTML={{
                  __html: post.body.substring(0, 100),
                }}
              />
              <p className='mb-2 text-gray-700'>
                {post.numberOfComments} comments
              </p>
              <button
                onClick={() => handleLoadMore(`/blog-details/${post.id}`)}
                aria-label=''
                className='inline-flex items-center font-semibold transition-colors duration-200 text-deep-purple-accent-400 hover:text-deep-purple-800 text-blue-700 hover:text-blue-900'
              >
                Learn more
              </button>
            </div>
          </div>
        ))}
      </div>
      <div className='flex justify-end w-full p-5'>
        <Link
          to='/all-posts'
          className='inline-flex w-32 items-center bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded'
        >
          View All
          <svg
            className='w-4 h-4 ml-2'
            viewBox='0 0 24 24'
            fill='none'
            stroke='currentColor'
            strokeWidth='2'
            strokeLinecap='round'
            strokeLinejoin='round'
          >
            <path d='M5 12h14'></path>
            <path d='M12 5l7 7-7 7'></path>
          </svg>
        </Link>
      </div>
    </div>
  );
};

export default Blog;
