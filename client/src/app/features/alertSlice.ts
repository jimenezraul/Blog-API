import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../../app/store';

interface Alert {
  type: 'SUCCESS' | 'ERROR' | 'INFO' | 'WARNING';
  message: string;
  show: boolean;
}

export const AlertSlice = createSlice({
  name: 'alert',
  initialState: {
    type: 'SUCCESS',
    message: '',
    show: false,
  } as Alert,

  reducers: {
    setAlert: (state, action: PayloadAction<Alert>) => {
      state.type = action.payload.type;
      state.message = action.payload.message;
      state.show = action.payload.show;
    },
    setShow: (state, action: PayloadAction<boolean>) => {
      state.show = action.payload;
    },
  },
});
export const { setAlert, setShow } = AlertSlice.actions;

export const alert = (state: RootState) => state.alert;

export default AlertSlice.reducer;
