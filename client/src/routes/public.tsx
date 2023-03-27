import { Suspense, lazy } from 'react';
import { Outlet, Navigate } from 'react-router-dom';

const Home = lazy(() => import("../pages/Home"));
const About = lazy(() => import("../pages/About"));
const Login = lazy(() => import("../pages/Login"));
const Signup = lazy(() => import("../pages/Signup"));
const BlogDetails = lazy(() => import("../pages/BlogDetails"));

const Public = () => {
    return (
      <div className="flex flex-1 bg-gray-100">
        <Suspense fallback={<div className="h-full w-full flex items-center justify-center"></div>}>
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
          path: '/blog-details/:id',
          element: <BlogDetails />,
        },
        {
          path: '/about',
          element: <About />,
        },
        { path: '*', element: <Navigate to="/login" /> },
      ],
    },
  ];
  