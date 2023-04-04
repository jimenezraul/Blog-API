import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useAppDispatch } from '../app/hooks';
import { setAlert } from '../app/features/alertSlice';
import { FetchData } from '../utils/FetchData';
import Alert from '../components/NotificationAlert';

const signupInpit = [
  {
    id: 'name',
    name: 'Name',
    type: 'text',
    placeholder: 'Enter your full name',
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

const initialState = {
  name: '',
  email: '',
  username: '',
  password: '',
  confirmPassword: '',
  error: {
    name: '',
    email: '',
    username: '',
    password: '',
    confirmPassword: '',
  },
};

function Signup() {
  const dispatch = useAppDispatch();
  const [state, setState] = useState<SignUpForm>(initialState);
  const [isLoading, setIsLoading] = useState(false);

  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
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

    const { name, email, username, password, confirmPassword } = state;
    const error = {
      name: name ? '' : 'First name is required',
      email: email ? '' : 'Email is required',
      username: username ? '' : 'Username is required',
      password: password ? '' : 'Password is required',
      confirmPassword: confirmPassword ? '' : 'Confirm password is required',
    };

    setState({
      ...state,
      error,
    });
    if (
      error.name ||
      error.email ||
      error.username ||
      error.password ||
      error.confirmPassword
    ) {
      setIsLoading(false);
      return;
    }

    if (password !== confirmPassword) {
      setState({
        ...state,
        error: {
          ...state.error,
          password: 'Password does not match',
          confirmPassword: 'Password does not match',
        },
      });
      setIsLoading(false);
      return;
    }

    try {
      const res = await FetchData('/api/v1/auth/register', 'POST', {
        name: name.trim(),
        email: email.trim(),
        username: username.trim(),
        password: password.trim(),
      });

      if (!res.ok) {
        if (res.status === 409) {
          const { message } = await res.json();
          dispatch(
            setAlert({
              message,
              type: 'ERROR',
              show: true,
            })
          );
          setIsLoading(false);
          return;
        }
        dispatch(
          setAlert({
            message: 'Something went wrong, please try again later',
            type: 'ERROR',
            show: true,
          })
        );
        setIsLoading(false);
        return;
      }

      dispatch(
        setAlert({
          message:
            'Account created successfully, check your email to verify your account',
          type: 'SUCCESS',
          show: true,
        })
      );
      setIsLoading(false);
      setState(initialState);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div className='flex flex-1 justify-center overflow-hidden'>
      <div className='relative w-full flex justify-center bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
        <Alert />
        <div className='flex items-center justify-center w-full'>
          <div className='bg-white p-8 rounded-lg shadow-md max-w-md w-full'>
            <h2 className='text-2xl font-bold mb-4 text-center'>SignUp</h2>
            <form onSubmit={handleSubmit}>
              {signupInpit.map((input) => (
                <div className='mb-4' key={input.id}>
                  <label
                    htmlFor='password'
                    className='block text-gray-700 font-bold mb-2'
                  >
                    {input.name}
                  </label>
                  <input
                    type={input.type}
                    name={input.id}
                    value={state[input.id as keyof SignUpForm['error']]}
                    onChange={handleOnChange}
                    className='border-gray-200 border-2 p-2 rounded w-full'
                    placeholder={input.placeholder}
                  />

                  <p className='text-red-500'>
                    {state.error[input.id as keyof SignUpForm['error']]}
                  </p>
                </div>
              ))}

              <button
                type='submit'
                disabled={isLoading}
                className={`flex ${
                  isLoading ? 'bg-gray-500' : 'bg-blue-500 hover:bg-blue-700'
                } text-white font-bold py-2 px-4 rounded`}
              >
                {isLoading && (
                  <svg
                    className='animate-spin -ml-1 mr-3 h-5 w-5 text-white'
                    xmlns='http://www.w3.org/2000/svg'
                    fill='none'
                    viewBox='0 0 24 24'
                  >
                    <circle
                      className='opacity-25'
                      cx='12'
                      cy='12'
                      r='10'
                      stroke='currentColor'
                      strokeWidth='4'
                    ></circle>
                    <path
                      className='opacity-75'
                      fill='currentColor'
                      d='M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z'
                    ></path>
                  </svg>
                )}
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
