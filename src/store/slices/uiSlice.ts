import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface UIState {
  isLoading: boolean;
  currentSection: string;
}

const initialState: UIState = {
  isLoading: false,
  currentSection: 'landing',
};

const uiSlice = createSlice({
  name: 'ui',
  initialState,
  reducers: {
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.isLoading = action.payload;
    },
    setCurrentSection: (state, action: PayloadAction<string>) => {
      state.currentSection = action.payload;
    },
  },
});

export const { setLoading, setCurrentSection } = uiSlice.actions;
export default uiSlice.reducer;
