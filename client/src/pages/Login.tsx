import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { FetchData } from '../utils/FetchData';
import { useAppDispatch } from '../app/hooks';
import { setAccessToken } from '../app/features/accessTokenSlice';
const menuInput = [
  {
    name: 'username',
    type: 'text',
    placeholder: 'Enter your username',
  },
  {
    name: 'password',
    type: 'password',
    placeholder: 'Enter your password',
  },
];

const initialState = {
  username: '',
  password: '',
  error: {
    username: '',
    password: '',
  },
};

function LoginForm() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [state, setState] = useState(initialState);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setState({
      ...state,
      [name]: value,
      error: {
        ...state.error,
        [name]: '',
      },
    });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const { username, password } = state;
    const error = {
      username: username ? '' : 'Username is required',
      password: password ? '' : 'Password is required',
    };
    setState({
      ...state,
      error,
    });
    if (error.username || error.password) return;
    try {
      const res = await FetchData('/api/v1/auth/login', 'POST', {
        username: username,
        password: password,
      });
   
      if (res.isLogged) {
        localStorage.setItem('isLogged', 'true');
        if (res.isAdmin) {
          localStorage.setItem('isAdmin', 'true');
        }
        dispatch(setAccessToken(res.accessToken));
        setIsLoading(false);
      }
      navigate('/dashboard');
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className='flex flex-1 justify-center items-center bg-gray-100'>
      <div className='bg-white p-8 rounded-lg shadow-md max-w-md w-full'>
        <h2 className='text-2xl font-bold mb-4 text-center'>Login</h2>
        <form onSubmit={handleSubmit}>
          {menuInput.map((input, index) => (
            <div className='mb-4' key={index}>
              <label
                htmlFor={input.name}
                className='block text-gray-700 font-bold mb-2 capitalize'
              >
                {input.name}
              </label>
              <input
                type={input.type}
                name={input.name}
                onChange={handleChange}
                className='border-gray-200 border-2 p-2 rounded w-full'
                placeholder={input.placeholder}
                value={state[input.name as keyof LoginForm['error']]}
              />
              <div className='text-red-500 text-sm'>
                {state.error[input.name as keyof LoginForm['error']]}
              </div>
            </div>
          ))}
          <div className='flex justify-end items-center mb-4'>
            <Link
              to='/forgot-password'
              className='text-blue-500 hover:underline'
            >
              Forgot password?
            </Link>
          </div>
          <button
            disabled={isLoading}
            type='submit'
            className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
          >
            Sign In
          </button>

          <div className='flex justify-center items-center mt-8'>
            Don't have an account?{' '}
            <Link to='/signup' className='text-blue-500 hover:underline ml-2'>
              Sign Up
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}

export default LoginForm;
