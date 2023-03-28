import { store } from '../app/store';
import { setAccessToken } from '../app/features/accessTokenSlice';
import Auth from '../auth';

const BASE_URL = 'http://localhost:8080';

const getAccessToken = () => {
  const token = store.getState().token.access_token;
  return token ? token : null;
};

export const FetchData: any = async (
  endpoint: string,
  method: string,
  body?: any,
  options: any = {}
) => {
  try {
    const token = getAccessToken();
    if (store.getState().token.access_token) {
      options.headers = {
        Authorization: `Bearer ${token}`,
      };
    }
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
          store.dispatch(setAccessToken(data.accessToken));
          options.headers = {
            ...options.headers,
            Authorization: `Bearer ${data.accessToken}`,
          };
          return await FetchData(endpoint, method, body, options);
        }
      } catch (error: any) {
        console.log(error);
        Auth.logout();
        window.location.href = '/login';
      }
    }

    console.log(error);
  }
};
