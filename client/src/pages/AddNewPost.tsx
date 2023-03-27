import { FetchData } from '../utils/FetchData';
import { useNavigate } from 'react-router-dom';

const AddNewPost = () => {
  const navigate = useNavigate();
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const target = e.target as typeof e.target & {
      title: { value: string };
      body: { value: string };
    };
    const title = target?.title?.value;
    const body = target?.body?.value;

    try {
      if (title === '' || body === '') {
        throw new Error('Please fill in all fields');
      }
      if (body.length < 10) {
        throw new Error('Body must be at least 10 characters long');
      }

      await FetchData('/api/v1/posts', 'POST', {
        title,
        body,
      });

      navigate('/dashboard');
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className='bg-slate-200 w-full lg:w-2/4  px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <button
        className='transition duration-300 px-3 py-2 mb-5 bg-blue-400 text-white rounded shadow-md hover:bg-blue-600 focus:outline-none'
        aria-label='Go back'
        onClick={() => window.history.back()}
      >
        Back
      </button>
      <div className='flex flex-col items-center justify-center w-full bg-slate-50 shadow-lg p-5 rounded-lg'>
        <h1 className='text-3xl font-bold text-gray-900'>Add new post</h1>
        <form onSubmit={handleSubmit} className='w-full max-w-lg mt-5'>
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
              />
              <p className='text-gray-600 text-xs italic'>
                Add a title for your post
              </p>
            </div>
          </div>
          <div className='flex flex-wrap -mx-3 mb-6'>
            <div className='w-full px-3'>
              <label
                className='block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2'
                htmlFor='grid-body'
              >
                Body
              </label>
              <textarea
                className='w-full p-2 border border-gray-300 rounded shadow'
                name='body'
                placeholder='Body'
              />
              <p className='text-gray-600 text-xs italic'>
                Add a body for your post
              </p>
            </div>
          </div>
          <button
            className='mb-8 w-full px-4 py-2 font-bold text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none focus:shadow-outline'
            type='submit'
          >
            Add Post
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddNewPost;