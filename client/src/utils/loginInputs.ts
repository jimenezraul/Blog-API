export const menuInput = [
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

export const initialState = {
  username: '',
  password: '',
  error: {
    username: '',
    password: '',
  },
};
