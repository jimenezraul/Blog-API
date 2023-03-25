import { useEffect, useState } from 'react';
import Comments from '../components/Comments';
import { FetchData } from '../utils/FetchData';

const postInitialValues = {
  id: '',
  title: '',
  body: '',
  userId: '',
  userName: '',
  numberOfComments: '',
  created_at: '',
  updated_at: '',
};

const commentInitialValues = {
  created_at: '',
  id: '',
  postId: '',
  text: '',
  updated_at: '',
  user: '',
  userId: '',
};

const BlogDetails = () => {
  const id = window.location.pathname.split('/')[2];
  const [data, setData] = useState(postInitialValues);
  const [comments, setComments] = useState([commentInitialValues]);

  useEffect(() => {
    const fetchData = async () => {
      const res = await FetchData(`/api/v1/posts/${id}`, 'GET');
      setData(res);
    };
    const fetchComments = async () => {
      const res = await FetchData(`/api/v1/posts/${id}/comments`, 'GET');

      const newData = res?.map((comment: any) => {
        return {
          ...comment,
          created_at: Intl.DateTimeFormat().format(
            new Date(comment.created_at)
          ),
        };
      });

      setComments(newData);
    };

    fetchComments();
    fetchData();
  }, [id]);

  const today = new Date();
  const postDate = new Date(data?.created_at);
  const isThisWeek = postDate > new Date(today.setDate(today.getDate() - 7));

  return (
    <div className='px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      {/* button to go back  */}
      <button
        className='transition duration-300 px-3 py-2 mb-5 bg-blue-400 text-white rounded shadow-md hover:bg-blue-600 focus:outline-none'
        aria-label='Go back'
        onClick={() => window.history.back()}
      >
        Back
      </button>

      <div className='mx-auto sm:text-center lg:max-w-2xl'>
        <div className='max-w-xl mb-10 md:mx-auto sm:text-center lg:max-w-2xl md:mb-12'>
          {isThisWeek && (
            <div>
              <p className='inline-block px-3 py-px mb-4 text-xs font-semibold tracking-wider text-teal-900 uppercase rounded-full bg-teal-accent-400'>
                Brand new
              </p>
            </div>
          )}
          <h2 className='max-w-lg mb-6 font-sans text-3xl font-bold leading-none tracking-tight text-gray-900 sm:text-4xl md:mx-auto'>
            <span className='relative inline-block'>
              <svg
                viewBox='0 0 52 24'
                fill='currentColor'
                className='absolute top-0 left-0 z-0 hidden w-32 -mt-8 -ml-20 text-blue-gray-100 lg:w-32 lg:-ml-28 lg:-mt-10 sm:block'
              >
                <defs>
                  <pattern
                    id='5dc90b42-5ed4-45a6-8e63-2d78ca9d3d95'
                    x='0'
                    y='0'
                    width='.135'
                    height='.30'
                  >
                    <circle cx='1' cy='1' r='.7' />
                  </pattern>
                </defs>
                <rect
                  fill='url(#5dc90b42-5ed4-45a6-8e63-2d78ca9d3d95)'
                  width='52'
                  height='24'
                />
              </svg>
              <span className='relative'>{data.title}</span>
            </span>
          </h2>
        </div>
        <div className='mb-4 transition-shadow duration-300 hover:shadow-xl lg:mb-6'>
          <img
            className='object-cover w-full h-56 rounded shadow-lg sm:h-64 md:h-80 lg:h-96'
            src='https://images.pexels.com/photos/3727459/pexels-photo-3727459.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=2&amp;h=750&amp;w=1260'
            alt=''
          />
        </div>
        <div dangerouslySetInnerHTML={{ __html: data.body }} />
      </div>
      <div className='flex flex-col items-center justify-center mt-8'>
        <h3 className='mt-4 text-xl font-bold text-gray-900 mb-5'>Comments</h3>
        <hr className='mb-5' />
        {comments.length ? (
          comments.map((comment: any) => (
            <Comments key={comment.id} comment={comment} />
          ))
        ) : (
          <p>No comments yet</p>
        )}
      </div>
    </div>
  );
};

export default BlogDetails;
