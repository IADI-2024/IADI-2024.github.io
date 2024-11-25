import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit';

interface ClockState {time: string, timezone:string}
  
const initialState:ClockState = {
  time: new Date().toLocaleString("pt-PT", { timeZone: "America/New_York" }), 
  timezone:"America/New_York"
}

const clockSlice = createSlice({
    name: 'clock',
    initialState,
    reducers: {
      tick: (state) => { state.time = new Date().toLocaleString("pt-PT", { timeZone: state.timezone }) },
      setTimeZone: (state, action: PayloadAction<string>) => {state.timezone = action.payload}
    },
  });
  

const store = configureStore(clockSlice);

export const { tick, setTimeZone } = clockSlice.actions;

export default clockSlice.reducer;


// Demo only

function displayClock() {
  const {time} = store.getState()
  console.log(time)
}

export function demo() {
  displayClock()
  store.dispatch(setTimeZone("Europe/Lisbon"))
  store.dispatch(tick())
  displayClock()
  store.dispatch(setTimeZone("Europe/Paris"))
  store.dispatch(tick())
  displayClock()
  store.dispatch(setTimeZone("Asia/Tokyo"))
  store.dispatch(tick())
  displayClock()
}

