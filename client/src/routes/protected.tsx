import { Suspense, lazy } from 'react';
import { Outlet, Navigate } from 'react-router-dom';

const Home = lazy(() => import('../pages/Home'));
const Dashboard = lazy(() => import('../pages/Dashboard'));

const Protected = () => {
  return (
    <div className='flex flex-1'>
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
      { path: '*', element: <Navigate to='.' /> },
    ],
  },
];
