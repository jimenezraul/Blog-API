import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import accessTokenSlice from './features/accessTokenSlice';

export const store = configureStore({
  reducer: {
    token: accessTokenSlice,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
