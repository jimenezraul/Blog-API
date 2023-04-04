import { FetchData } from '../../utils/FetchData';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAppDispatch } from '../../app/hooks';
import { setAlert } from '../../app/features/alertSlice';
import Alert from '../NotificationAlert';
import { useState, useEffect } from 'react';

const initialState = {
  title: '',
  content: '',
  tags: '',
  error: {
    title: '',
    content: '',
    tags: '',
  },
};

const NewPostForm = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const currentPage = parseInt(
    new URLSearchParams(location.search).get('page') || '1'
  );
  const currentPost = parseInt(
    new URLSearchParams(location.search).get('post') || '0'
  );
  const [state, setState] = useState(initialState);

  useEffect(() => {
    const fetchPost = async () => {
      const res = await FetchData(`/api/v1/posts/${currentPost}`, 'GET');
      if (res) {
        setState({
          ...state,
          title: res.title.trim(),
          content: res.content.trim(),
          tags: res.tags.join(' '),
        });
      }
    };
    if (currentPost) {
      fetchPost();
    }
  }, [currentPost]);

  const handleChange = (e: any) => {
    const { name, value } = e.target;
    setState({
      ...state,
      [name]: value,
      error: {
        ...state.error,
        [name]: '',
      },
    });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const { title, content, tags } = state;
    const error = {
      title: title ? '' : 'Title is required',
      content: content ? '' : 'Content is required',
      tags: tags ? '' : 'Tags are required',
    };

    setState({
      ...state,
      error,
    });

    if (error.title || error.content || error.tags) {
      setAlert({
        message: 'Please fill in all fields',
        type: 'WARNING',
        show: true,
      });
    }

    const tagsArray = tags.split(' ').map((tag: string) => tag.trim());

    try {
      if (content.length < 10) {
        throw new Error('Body must be at least 10 characters long');
      }

      if (currentPost) {
        await FetchData(`/api/v1/posts/${currentPost}`, 'PUT', {
          title,
          content,
          tags: tagsArray,
        });
        navigate(`/dashboard?page=${currentPage}`);
        return;
      }

      await FetchData('/api/v1/posts', 'POST', {
        title,
        content,
        tags: tagsArray,
      });

      navigate(`/dashboard?page=${currentPage}`);
    } catch (error: any) {
      dispatch(
        setAlert({
          message: error.message,
          type: 'WARNING',
          show: true,
        })
      );
    }
  };

  return (
    <div className='relative w-full bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <Alert />
      <button
        className='transition duration-300 px-3 py-2 mb-5 bg-blue-400 text-white rounded shadow-md hover:bg-blue-600 focus:outline-none'
        aria-label='Go back'
        onClick={() => window.history.back()}
      >
        Back
      </button>
      <div className='flex flex-col items-center justify-center'>
        <div className='flex flex-col items-center justify-center w-full max-w-3xl bg-slate-50 shadow-lg p-5 rounded-lg'>
          <h1 className='text-3xl font-bold text-gray-900'>
            {currentPost ? 'Edit Post' : 'Add new post'}
          </h1>
          <form onSubmit={handleSubmit} className='w-full max-w-3xl mt-5'>
            <div className='flex flex-wrap -mx-3 mb-6'>
              <div className='w-full px-3'>
                <label
                  className='block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2'
                  htmlFor='grid-title'
                >
                  Title
                </label>
                <input
                  className='w-full bg-white shadow text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3'
                  name='title'
                  type='text'
                  placeholder='Title'
                  value={state.title}
                  onChange={handleChange}
                />
                {state.error.title && (
                  <p className='text-red-600 text-xs italic'>
                    Add a title for your post
                  </p>
                )}
              </div>
            </div>
            <div className='flex flex-wrap -mx-3 mb-6'>
              <div className='w-full px-3'>
                <label
                  className='block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2'
                  htmlFor='grid-body'
                >
                  Content
                </label>
                <textarea
                  className='w-full p-2 border border-gray-300 rounded shadow'
                  name='content'
                  placeholder='Content'
                  value={state.content}
                  onChange={handleChange}
                />
                {state.error.content && (
                  <p className='text-red-600 text-xs italic'>
                    Add a content for your post
                  </p>
                )}
              </div>
            </div>
            <div className='flex flex-wrap -mx-3 mb-6'>
              <div className='w-full px-3'>
                <label
                  className='block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2'
                  htmlFor='grid-body'
                >
                  Tags
                </label>
                <textarea
                  className='w-full p-2 border border-gray-300 rounded shadow'
                  name='tags'
                  placeholder='Tags e.g. #javascript #react #typescript'
                  value={state.tags}
                  onChange={handleChange}
                />
                {state.error.tags && (
                  <p className='text-red-600 text-xs italic'>
                    Add your tags for your post
                  </p>
                )}
              </div>
            </div>
            <button
              className='mb-8 w-full px-4 py-2 font-bold text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none focus:shadow-outline'
              type='submit'
            >
              {currentPost ? 'Update Post' : 'Add Post'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default NewPostForm;
