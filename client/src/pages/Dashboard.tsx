import { useEffect, useState } from 'react';
import { FetchData } from '../utils/FetchData';
import ProfileCard from '../components/ProfileCard';
import BlogPost from '../components/Posts';
import { Link } from 'react-router-dom';

const Dashboard = () => {
  const [data, setData] = useState({
    id: '',
    name: '',
    email: '',
    imageUrl: '',
    createdAt: '',
    updatedAt: '',
  });

  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const id = localStorage.getItem('id');

    async function getData() {
      const response = await FetchData(`/api/v1/me/${id}`, 'GET');
      setData({
        ...response,
        createdAt: Intl.DateTimeFormat().format(new Date(response.created_at)),
        updatedAt: Intl.DateTimeFormat().format(new Date(response.updated_at)),
      });
      const postsData = await FetchData(`/api/v1/posts/user/${id}`, 'GET');
  
      setPosts(postsData);
    }
    getData();
  }, []);

  return (
    <div className='mt-10 container mx-auto'>
      <div className='flex flex-wrap justify-center p-5'>
        <div className='w-full md:w-1/2'>
          <ProfileCard {...data} />
        </div>
        <div className='w-full md:w-1/2'>
          {/* add a new post button */}
          <div className='flex justify-center mb-8'>
            <Link to="/add-new-post" className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>
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
                />
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
