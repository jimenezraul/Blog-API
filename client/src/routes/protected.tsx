import { Suspense, lazy } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import BlogDetails from '../pages/BlogDetails';

const Home = lazy(() => import('../pages/Home'));
const Dashboard = lazy(() => import('../pages/Dashboard'));
const AddNewPost = lazy(() => import('../pages/AddNewPost'));
const AboutUs = lazy(() => import('../pages/AboutUs'));
const AllPosts = lazy(() => import('../pages/AllPosts'));
const EditPost = lazy(() => import('../pages/EditPost'));

const Protected = () => {
  return (
    <div className='relative flex flex-1 bg-gray-100 overflow-hidden'>
      <Suspense
        fallback={
          <div className='h-full w-full flex items-center justify-center'></div>
        }
      >
        <Outlet />
      </Suspense>
    </div>
  );
};

export const protectedRoutes = [
  {
    path: '/',
    element: <Protected />,
    children: [
      { path: '/', element: <Home /> },
      { path: 'dashboard', element: <Dashboard /> },
      {
        path: '/blog-details/:id',
        element: <BlogDetails />,
      },
      {
        path: '/about-us',
        element: <AboutUs />,
      },
      {
        path: '/all-posts',
        element: <AllPosts />,
      },
      {
        path: '/edit-post/:id',
        element: <EditPost />,
      },
      { path: '/add-new-post', element: <AddNewPost /> },
      { path: '*', element: <Navigate to='.' /> },
    ],
  },
];
