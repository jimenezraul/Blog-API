import { useEffect, useState } from 'react';
import { FetchData } from '../utils/FetchData';
import ProfileCard from '../components/ProfileCard';
import BlogPost from '../components/Posts';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import Auth from '../auth';

const Dashboard = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search).get('page') || '1';
  const currentPage = parseInt(searchParams);
  const size = 10;
  const [data, setData] = useState({
    id: '',
    name: '',
    email: '',
    imageUrl: '',
    createdAt: '',
    updatedAt: '',
  });

  const [posts, setPosts] = useState<BlogPostProps[]>([]);
  const [page, setPage] = useState(currentPage);
  const [totalPages, setTotalPages] = useState(0);

  function handlePageChange(newPage: number) {
    setPage(newPage);
    navigate(`${location.pathname}?page=${newPage}`);
  }

  useEffect(() => {
    const id = Auth.getUserId();
    Promise.all([
      FetchData(`/api/v1/posts/user/${id}/count`, 'GET'),
      FetchData(`/api/v1/me/${id}`, 'GET'),
      FetchData(
        `/api/v1/posts/user/${id}?page=${page - 1}&size=${size}`,
        'GET'
      ),
    ]).then(([postCount, userData, postsData]) => {
      setTotalPages(Math.ceil(postCount / size));
      setData({
        ...userData,
        createdAt: Intl.DateTimeFormat().format(new Date(userData.created_at)),
        updatedAt: Intl.DateTimeFormat().format(new Date(userData.updated_at)),
      });
      setPosts(postsData);
    });
  }, [page]);

  return (
    <div className='flex flex-wrap w-full bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <div className='w-full md:w-1/2 pt-28'>
        <ProfileCard {...data} />
      </div>
      <div className='w-full md:w-1/2 pt-10'>
        {/* add a new post button */}
        <div className='flex justify-center mb-7'>
          <Link
            to='/add-new-post'
            className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
          >
            Add a new post
          </Link>
        </div>
        {posts.map((post: any) => {
          return (
            <BlogPost
              key={post.id}
              title={post.title}
              id={post.id}
              body={post.body}
              author={post.userName}
              createdAt={Intl.DateTimeFormat().format(
                new Date(post.created_at)
              )}
              commentsCount={post.numberOfComments}
              setPosts={setPosts}
            />
          );
        })}
        <div className='flex flex-col items-center'>
          <div className='flex mt-4'>
            <button
              className={`${
                page === 1 ? 'cursor-not-allowed opacity-50' : ''
              } border border-gray-500 px-2 py-1 rounded-l`}
              onClick={() => handlePageChange(page - 1)}
              disabled={page === 1}
            >
              Prev
            </button>
            {Array.from({ length: totalPages }).map((_, i) => {
              return (
                <button
                  key={i}
                  className={`${
                    page === i + 1
                      ? 'bg-blue-500 text-white'
                      : 'bg-white text-gray-700 hover:bg-gray-200'
                  } border border-gray-500 px-2 py-1`}
                  onClick={() => handlePageChange(i + 1)}
                >
                  {i + 1}
                </button>
              );
            })}
            <button
              className={`${
                page === totalPages ? 'cursor-not-allowed opacity-50' : ''
              } border border-gray-500 px-2 py-1 rounded-r`}
              onClick={() => handlePageChange(page + 1)}
              disabled={page === totalPages}
            >
              Next
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
