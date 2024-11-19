import { configureStore } from '@reduxjs/toolkit';
import counterReducer, { increment } from './store1'
import clockReducer, { tick, setTimeZone } from './store2'


const store = configureStore({reducer: {counter: counterReducer, clock:clockReducer}});
  
export function demo() {
  console.log(store.getState())
  store.dispatch(increment())
  console.log(store.getState())
  store.dispatch(setTimeZone("Europe/Paris"))
  store.dispatch(tick())
  console.log(store.getState())
}
