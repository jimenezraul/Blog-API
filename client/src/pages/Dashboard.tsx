import { useEffect, useState } from 'react';
import { FetchData } from '../utils/FetchData';
import ProfileCard from '../components/ProfileCard';
import BlogPost from '../components/Posts';
import { Link } from 'react-router-dom';
import Auth from '../auth';

const Dashboard = () => {
  const [data, setData] = useState({
    id: '',
    name: '',
    email: '',
    imageUrl: '',
    createdAt: '',
    updatedAt: '',
  });

  const [posts, setPosts] = useState<BlogPostProps[]>([]);

  useEffect(() => {
    const id = Auth.getUserId();
    Promise.all([
      FetchData(`/api/v1/me/${id}`, 'GET'),
      FetchData(`/api/v1/posts/user/${id}`, 'GET'),
    ]).then(([userData, postsData]) => {
      setData({
        ...userData,
        createdAt: Intl.DateTimeFormat().format(new Date(userData.created_at)),
        updatedAt: Intl.DateTimeFormat().format(new Date(userData.updated_at)),
      });
      setPosts(postsData);
    });
  }, []);

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
      </div>
    </div>
  );
};

export default Dashboard;
