import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import alertSlice from './features/alertSlice';
import userSlice from './features/userSlice';

export const store = configureStore({
  reducer: {
    alert: alertSlice,
    user: userSlice,
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
