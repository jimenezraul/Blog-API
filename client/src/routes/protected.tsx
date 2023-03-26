import { Suspense, lazy } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import BlogDetails from '../pages/BlogDetails';

const Home = lazy(() => import('../pages/Home'));
const Dashboard = lazy(() => import('../pages/Dashboard'));
const AddNewPost = lazy(() => import('../pages/AddNewPost'));

const Protected = () => {
  return (
    <div className='flex flex-1 bg-gray-100'>
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
      {path: '/add-new-post', element: <AddNewPost />},
      { path: '*', element: <Navigate to='.' /> },
    ],
  },
];
