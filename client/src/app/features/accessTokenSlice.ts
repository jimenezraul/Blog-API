import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../../app/store';

interface TokenState {
  access_token: String | null;
}

export const AccessTokenSlice = createSlice({
  name: 'accessToken',
  initialState: {
    access_token: null,
  } as TokenState,

  reducers: {
    setAccessToken: (state, action: PayloadAction<String>) => {
      state.access_token = action.payload;
    },
  },
});
export const { setAccessToken } = AccessTokenSlice.actions;

export const access_token = (state: RootState) => state.token.access_token;

export default AccessTokenSlice.reducer;
