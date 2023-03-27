import { FetchData } from '../../utils/FetchData';
import { useState, useEffect } from 'react';
import { useNavigate, useMatch } from 'react-router-dom';

const Blog = () => {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);
  const isHomeOrDashboard = useMatch("/");
  
  let fetchPost: any;
  if (isHomeOrDashboard) {
    fetchPost = async () => {
      const res = await FetchData('/api/v1/posts?size=3', 'GET');
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
    <div className='px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <div className='grid gap-8 lg:grid-cols-3 sm:max-w-sm sm:mx-auto lg:max-w-full'>
        {posts.map((post: any) => (
          <div
            key={post.id}
            className='overflow-hidden transition-shadow duration-300 bg-white rounded shadow-lg border'
          >
            <img
              src='https://images.pexels.com/photos/2408666/pexels-photo-2408666.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=2&amp;w=500'
              className='object-cover w-full h-64'
              alt=''
            />
            <div className='p-5'>
              <p className='mb-3 text-xs font-semibold tracking-wide uppercase'>
                By {post.userName}
                <span className='text-gray-600'>— {post.created_at}</span>
              </p>
              <a
                href='/'
                aria-label='Category'
                title='Visit the East'
                className='inline-block mb-3 text-2xl font-bold leading-5 transition-colors duration-200 hover:text-deep-purple-accent-700'
              >
                {post.title}
              </a>
              <p className='mb-2 text-gray-700'>
                {post.body.substring(0, 100)}
              </p>
              <p className='mb-2 text-gray-700'>
                {post.numberOfComments} comments
              </p>
              <button
                onClick={() => handleLoadMore(`/blog-details/${post.id}`)}
                aria-label=''
                className='inline-flex items-center font-semibold transition-colors duration-200 text-deep-purple-accent-400 hover:text-deep-purple-800'
              >
                Learn more
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Blog;
