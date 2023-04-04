import { useState, useEffect } from 'react';
import Posts from '../components/Posts';
import { FetchData } from '../utils/FetchData';
import { useLocation } from 'react-router-dom';

const AllPosts = () => {
  const [posts, setPosts] = useState<PostProps[]>([]);
  const [loading, setLoading] = useState(true);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(1);
  const location = useLocation();
  const searchParams = parseInt(
    new URLSearchParams(location.search).get('page') || '1'
  );
  const currentPage = searchParams > 0 ? searchParams : 1;
  const size = 10;

  useEffect(() => {
    const fetchPosts = async () => {
      Promise.all([
        FetchData(`/api/v1/posts/count`, 'GET'),
        FetchData(`/api/v1/posts?page=${currentPage - 1}&size=${size}`, 'GET'),
      ]).then(([postCount, postsData]) => {
        setTotalPages(Math.ceil(postCount / size));
        setPosts(postsData);
        setPage(currentPage);
        setLoading(false);
      });
    };
    fetchPosts();
  }, []);

  const handlePageChange = (newPage: number) => {
    setPage(newPage);
    window.scrollTo(0, 0);
  };

  if (loading) {
    return (
      <div className='flex flex-col w-full flex-1 bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
        <div className='flex flex-col w-full'>Loading ...</div>
      </div>
    );
  }

  return (
    <div className='flex flex-col justify-center items-center w-full flex-1 bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
      <div className='w-full md:w-1/2 pt-10'>
        <h1 className='text-3xl font-bold text-center text-slate-900'>
          All Posts
        </h1>
        {posts.map((post: any) => (
          <Posts
            key={post.id}
            title={post.title}
            id={post.id}
            content={post.content}
            author={post.author}
            tags={post.tags}
            created_at={Intl.DateTimeFormat().format(new Date(post.created_at))}
            commentsCount={post.numberOfComments}
            setPosts={setPosts}
            setTotalPages={setTotalPages}
            page={page}
            postsLength={posts.length}
            userId={post.userId}
          />
        ))}
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

export default AllPosts;
