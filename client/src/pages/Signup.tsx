import { Link } from 'react-router-dom';

const signupInpit = [
  {
    id: 'firstName',
    name: 'First Name',
    type: 'text',
    placeholder: 'Enter your first name',
  },
  {
    id: 'lastName',
    name: 'Last Name',
    type: 'text',
    placeholder: 'Enter your last name',
  },
  {
    id: 'email',
    name: 'Email',
    type: 'email',
    placeholder: 'Enter your email',
  },
  {
    id: 'username',
    name: 'Username',
    type: 'text',
    placeholder: 'Enter your username',
  },
  {
    id: 'password',
    name: 'Password',
    type: 'password',
    placeholder: 'Enter your password',
  },
  {
    id: 'confirmPassword',
    name: 'ConfirmPassword',
    type: 'password',
    placeholder: 'Confirm your password',
  },
];

function Signup() {
  return (
    <div className='flex flex-1 justify-center'>
      <div className='w-full flex justify-center bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
        <div className='flex items-center justify-center w-full'>
          <div className='bg-white p-8 rounded-lg shadow-md max-w-md w-full'>
            <h2 className='text-2xl font-bold mb-4 text-center'>SignUp</h2>
            <form>
              {signupInpit.map((input, index) => (
                <div className='mb-4' key={index}>
                  <label
                    htmlFor='password'
                    className='block text-gray-700 font-bold mb-2'
                  >
                    {input.name}
                  </label>
                  <input
                    type={input.type}
                    name={input.name}
                    className='border-gray-200 border-2 p-2 rounded w-full'
                    placeholder={input.placeholder}
                  />
                </div>
              ))}

              <button
                type='submit'
                className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
              >
                Sign Up
              </button>

              <div className='flex justify-center items-center mt-8'>
                Already have an account?{' '}
                <Link
                  to='/login'
                  className='text-blue-500 hover:underline ml-2'
                >
                  Login
                </Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Signup;
