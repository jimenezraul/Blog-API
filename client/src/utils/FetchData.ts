import { store } from '../app/store';
import Auth from '../auth';
import { setAlert } from '../app/features/alertSlice';

const BASE_URL = 'http://localhost:8080';

export const FetchData: any = async (
  endpoint: string,
  method: string,
  body?: any,
  options: any = {}
) => {
  try {
    const requestOptions = {
      method,
      credentials: 'include' as const,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      body: body && JSON.stringify(body),
    };

    const response = await fetch(BASE_URL + endpoint, requestOptions);

    if (response.ok) {
      if (endpoint === '/api/v1/auth/logout') {
        return response;
      }
      if (method === 'DELETE') {
        return response;
      }

      if (response.status === 201) {
        return response;
      }

      const data = await response.json();
      return data;
    }

    if (response.status === 401) {
      throw response;
    }

    return response;
  } catch (error: any) {
    if (error.status === 401) {
      try {
        const response = await fetch(BASE_URL + '/api/v1/auth/refresh', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
        });

        if (!response.ok) {
          throw response;
        }
        const data = await response.json();

        if (data.accessToken) {
          return await FetchData(endpoint, method, body, options);
        }
      } catch (error: any) {
        console.log(error);
        Auth.logout();
        window.location.href = '/login';
      }
    }
    store.dispatch(
      setAlert({
        message: 'Something went wrong. Please try again later.',
        type: 'ERROR',
        show: true,
      })
    );
    console.log(error);
  }
};
