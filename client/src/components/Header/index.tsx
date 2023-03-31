import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <div className='overflow-hidden bg-gray-900'>
      <div className='px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
        <div className='flex flex-col items-center justify-between xl:flex-row'>
          <div className='w-full max-w-xl mb-12 xl:pr-16 xl:mb-0 xl:w-7/12'>
            <h2 className='max-w-lg mb-6 font-sans text-3xl font-bold tracking-tight sm:text-4xl sm:leading-none text-red-500'>
              TechTalk X <br className='block' />
              <span className="text-blue-500" ><span className="text-green-500">Mastering</span><span className='text-yellow-400'> the Art</span> of Coding.</span>
            </h2>
            <p className='max-w-xl mb-4 text-base text-gray-300 md:text-lg'>
            TechTalk X is a tech blog dedicated to exploring the world of
              coding, programming, and software development. Our expert team of
              writers and developers provide insights, tips, and tricks on how
              to master the craft of coding, improve your skills, and stay
              up-to-date with the latest programming trends.
            </p>
            <Link
              to='/'
              aria-label=''
              className='inline-flex items-center font-semibold tracking-wider transition-colors duration-200 text-blue-500 hover:text-blue-700'
            >
              Learn more
              <svg
                className='inline-block w-3 ml-2'
                fill='currentColor'
                viewBox='0 0 12 12'
              >
                <path d='M9.707,5.293l-5-5A1,1,0,0,0,3.293,1.707L7.586,6,3.293,10.293a1,1,0,1,0,1.414,1.414l5-5A1,1,0,0,0,9.707,5.293Z' />
              </svg>
            </Link>
          </div>
          <div className='w-full max-w-xl xl:px-8 xl:w-5/12'>
            <img
              className='object-cover w-full h-64 rounded-lg shadow-lg sm:h-96'
              src='https://images.unsplash.com/photo-1555066931-4365d14bab8c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2670&q=80'
              alt=''
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Header;
