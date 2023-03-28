import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import accessTokenSlice from './features/accessTokenSlice';
import alertSlice from './features/alertSlice';

export const store = configureStore({
  reducer: {
    alert: alertSlice,
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
