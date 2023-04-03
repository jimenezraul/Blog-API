import { useNavigate } from 'react-router-dom';
import { FetchData } from '../../utils/FetchData';

interface Props extends PostProps {
  setPosts: React.Dispatch<React.SetStateAction<PostProps[]>>;
  setTotalPages: React.Dispatch<React.SetStateAction<number>>;
  page: number;
  postsLength: number;
}

const Post = ({
  id,
  title,
  content,
  author,
  created_at,
  commentsCount,
  setPosts,
  setTotalPages,
  page,
  postsLength,
}: Props) => {
  const navigate = useNavigate();
  const handleReadMore = () => {
    navigate(`/blog-details/${id}`);
  };

  const handlePostDelete = async () => {
    try {
      const response = await FetchData(`/api/v1/posts/${id}`, 'DELETE');
      if (response.status === 200) {
        setPosts((prevPosts) => {
          return prevPosts.filter((post) => post.id !== id);
        });
        if (postsLength === 1) {
          setTotalPages((prevTotalPages) => prevTotalPages - 1);
          navigate(`/dashboard?page=${page - 1}`);
        }
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div className='sm:max-w-sm sm:mx-auto lg:max-w-full p-3'>
        <div
          key={id}
          className='relative overflow-hidden transition-shadow duration-300 bg-white rounded-lg shadow-lg border border-slate-300'
        >
          <div className='absolute top-0 right-0 p-3'>
            <i onClick={()=> navigate(`/edit-post/${id}`)} className='transition ease-in-out duration-300 hover:bg-slate-100 rounded-lg p-3 text-blue-500 hover:text-blue-600 cursor-pointer text-xl fa-solid fa-pen-to-square'></i>
            <i
              onClick={handlePostDelete}
              className='transition ease-in-out duration-300 hover:bg-slate-100 rounded-lg p-3 text-red-500 hover:text-red-600 cursor-pointer text-xl fa-solid fa-trash'
            ></i>
          </div>
          <img
            src='https://images.pexels.com/photos/2408666/pexels-photo-2408666.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=2&amp;w=500'
            className='object-cover w-full h-64'
            alt=''
          />
          <div className='p-5'>
            <p className='mb-3 text-xs font-semibold tracking-wide uppercase'>
              By {author}
              <span className='text-gray-600'>— {created_at}</span>
            </p>
            <a
              href='/'
              aria-label='Category'
              title='Visit the East'
              className='inline-block mb-3 text-2xl font-bold leading-5 transition-colors duration-200 hover:text-deep-purple-accent-700 text-blue-900'
            >
              {title}
            </a>
            <div
              dangerouslySetInnerHTML={{ __html: content.substring(0, 100) }}
            />
            <p className='mb-2 text-gray-700'>{commentsCount} comments</p>
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
