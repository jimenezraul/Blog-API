import Alert from '../components/NotificationAlert';
import { useState } from 'react';
import { useAppDispatch } from '../app/hooks';
import { setAlert } from '../app/features/alertSlice';
import { FetchData } from '../utils/FetchData';
import AlertType from '../enum/alert';

interface ForgotPasswordForm {
  email: string;
  error: {
    email: string;
  };
}

const initialState = {
  email: '',
  error: {
    email: '',
  },
};

const ForgotPassword = () => {
  const dispatch = useAppDispatch();
  const [state, setState] = useState<ForgotPasswordForm>(initialState);

  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const { email } = state;
    const error = {
      email: email ? '' : 'Email is required',
    };

    setState({
      ...state,
      error,
    });

    console.log(state);

    if (error.email) {
      setIsLoading(false);
      dispatch(
        setAlert({
          message: error.email,
          type: AlertType.ERROR,
          show: true,
        })
      );
    }
    if (!error.email) {
      try {
        const res = {
          message: 'Email sent successfully',
        };
        if (res) {
          setIsLoading(false);
          dispatch(
            setAlert({
              message: res.message,
              type: AlertType.SUCCESS,
              show: true,
            })
          );
        }
        setState(initialState);
      } catch (err: any) {
        setIsLoading(false);
        dispatch(
          setAlert({
            message: err.message,
            type: AlertType.ERROR,
            show: true,
          })
        );
      }
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setState({
      ...state,
      [name]: value,
    });
  };

  return (
    <div className='flex flex-1 justify-center overflow-hidden'>
      <div className='relative w-full flex justify-center bg-slate-200 px-4 py-16 mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8 lg:py-20'>
        <Alert />
        <div className='flex items-center justify-center w-full'>
          <div className='bg-white p-8 rounded-lg shadow-md max-w-md w-full'>
            <h2 className='text-2xl font-bold mb-4 text-center'>
              Forgot Password
            </h2>
            <form onSubmit={handleSubmit}>
              <div className='mb-4'>
                <label
                  htmlFor='email'
                  className='block text-gray-700 font-bold mb-2 capitalize'
                >
                  Email
                </label>
                <input
                  type='email'
                  name='email'
                  onChange={handleChange}
                  className='border-gray-200 border-2 p-2 rounded w-full'
                  placeholder='Email'
                  value={state['email']}
                />
                <div className='text-red-500 text-sm'>
                  {state.error['email']}
                </div>
              </div>
              <button
                disabled={isLoading}
                type='submit'
                className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
