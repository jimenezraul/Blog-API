import { Suspense, lazy } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import ForgotPassword from '../pages/ForgotPassword';

const Home = lazy(() => import('../pages/Home'));
const AboutUs = lazy(() => import('../pages/AboutUs'));
const Login = lazy(() => import('../pages/Login'));
const Signup = lazy(() => import('../pages/Signup'));
const BlogDetails = lazy(() => import('../pages/BlogDetails'));
const AllPosts = lazy(() => import('../pages/AllPosts'));

const Public = () => {
  return (
    <div className='relative flex flex-1 bg-gray-100'>
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

export const publicRoutes = [
  {
    element: <Public />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
      {
        path: '/login',
        element: <Login />,
      },
      {
        path: '/signup',
        element: <Signup />,
      },
      {
        path: '/forgot-password',
        element: <ForgotPassword />,
      },
      {
        path: '/blog-details/:id',
        element: <BlogDetails />,
      },
      {
        path: '/all-posts',
        element: <AllPosts />,
      },
      {
        path: '/about-us',
        element: <AboutUs />,
      },
      { path: '*', element: <Navigate to='/login' /> },
    ],
  },
];
