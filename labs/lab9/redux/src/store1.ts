import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit';

type CounterState = number
  
const initialState = 0

const counterSlice = createSlice({
    name: 'counter',
    initialState,
    reducers: {
      increment: (state) => state + 1,
      decrement: (state) => state - 1
    },
  });
  
export const { increment, decrement } = counterSlice.actions;

const store = configureStore(counterSlice);

export default counterSlice.reducer
  
// Demo only

export function demo() {
  console.log(store.getState())
  store.dispatch(increment())
  console.log(store.getState())
  store.dispatch(increment())
  console.log(store.getState())
  store.dispatch(decrement())
  console.log(store.getState())
  store.dispatch(decrement())
  console.log(store.getState())  
}
