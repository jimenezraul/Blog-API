import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../../app/store';

interface User {
    id: number | null;
}

export const UserSlice = createSlice({
    name: 'user',
    initialState: {
        id: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!).id : null,
    } as User,

  reducers: {
      setUser: (state, action: PayloadAction<User>) => {
          state.id = action.payload.id;
      },
  },
});
export const { setUser } = UserSlice.actions;

export const user = (state: RootState) => state.user.id;

export default UserSlice.reducer;
