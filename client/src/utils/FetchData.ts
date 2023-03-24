import { store } from '../app/store';
import { setAccessToken } from '../app/features/accessTokenSlice';

const BASE_URL = 'http://localhost:8080';

export const FetchData: any = async (
  endpoint: string,
  method: string,
  body?: any,
  options: any = {}
) => {
  try {
    if (store.getState().token.access_token) {
      options.headers = {
        Authorization: `Bearer ${store.getState().token.access_token}`,
      };
    }
    const response = await fetch(BASE_URL + endpoint, {
      method,
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        ...options?.headers,
      },
      body: body && JSON.stringify(body),
    });

    if (response.ok) {
      const data = await response.json();

      return data;
    }

    if (response.status === 401) {
      throw response;
    }
    console.log(response);
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
        localStorage.removeItem('isLogged');
        localStorage.removeItem('isAdmin');
      }
    }

    console.log(error);
  }
};
