import { useEffect, useState } from 'react';
import { FetchData } from '../utils/FetchData';
import ProfileCard from '../components/ProfileCard';
import BlogPost from '../components/Posts';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import Auth from '../auth';

const Dashboard = () => {
  const [data, setData] = useState({
    createdAt: '',
    email: '',
    id: '',
    imageUrl: '',
    name: '',
    updatedAt: '',
  });
  const [page, setPage] = useState(1);
  const [posts, setPosts] = useState<BlogPostProps[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

  const location = useLocation();
  const searchParams = parseInt(
    new URLSearchParams(location.search).get('page') || '1'
  );
  const currentPage = searchParams > 0 ? searchParams : 1;
  const size = 10;

  useEffect(() => {
    const id = Auth.getUserId();
    Promise.all([
      FetchData(`/api/v1/me/${id}`, 'GET'),
      FetchData(`/api/v1/posts/user/${id}/count`, 'GET'),
      FetchData(
        `/api/v1/posts/user/${id}?page=${currentPage - 1}&size=${size}`,
        'GET'
      ),
    ]).then(([userData, postCount, postsData]) => {
      setData({
        ...userData,
        createdAt: Intl.DateTimeFormat().format(new Date(userData.created_at)),
        updatedAt: Intl.DateTimeFormat().format(new Date(userData.updated_at)),
      });
      setTotalPages(Math.ceil(postCount / size));
      setPosts(postsData);
      setPage(currentPage);
    });
  }, [currentPage, size]);

  function handlePageChange(newPage: number) {
    setPage(newPage);
    navigate(`${location.pathname}?page=${newPage}`);
    window.scrollTo(0, 0);
  }

  return (
    <div className='flex flex-wrap w-full bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <div className='w-full md:w-1/2 pt-28'>
        <ProfileCard {...data} />
      </div>
      <div className='w-full md:w-1/2 pt-10'>
        {/* add a new post button */}
        <div className='flex justify-center mb-7'>
          <Link
            to={`/add-new-post?page=${currentPage}`}
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
              setTotalPages={setTotalPages}
              page={page}
              postsLength={posts.length}
            />
          );
        })}
        {totalPages > 1 && (
          <div className='flex flex-col items-center'>
            <div className='flex mt-4'>
              <button
                className={`${
                  page === 1 ? 'cursor-not-allowed opacity-50' : ''
                } px-3 py-2 ml-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-l-lg hover:bg-gray-100 hover:text-gray-700`}
                onClick={() => handlePageChange(page - 1)}
                disabled={page === 1}
              >
                Prev
              </button>
              {Array.from({ length: totalPages }).map((_, i) => {
                // calculate the current group of pages being displayed
                const currentPageGroup = Math.ceil(page / 10);
                const pageGroupStart = (currentPageGroup - 1) * 10 + 1;
                const pageGroupEnd = Math.min(
                  currentPageGroup * 10,
                  totalPages
                );

                // display the buttons only for the current group of pages
                if (i + 1 >= pageGroupStart && i + 1 <= pageGroupEnd) {
                  return (
                    <button
                      key={i}
                      className={`${
                        page === i + 1
                          ? 'text-blue-600 border border-gray-300 bg-blue-50 hover:bg-blue-100 hover:text-blue-700 '
                          : 'leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700'
                      } px-3 py-2`}
                      onClick={() => handlePageChange(i + 1)}
                    >
                      {i + 1}
                    </button>
                  );
                } else {
                  return null;
                }
              })}
              <button
                className={`${
                  page === totalPages ? 'cursor-not-allowed opacity-50' : ''
                } px-3 py-2 leading-tight text-gray-500 bg-white border border-gray-300 rounded-r-lg hover:bg-gray-100 hover:text-gray-700`}
                onClick={() => handlePageChange(page + 1)}
                disabled={page === totalPages}
              >
                Next
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
