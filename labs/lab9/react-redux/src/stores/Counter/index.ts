import { createSlice } from "@reduxjs/toolkit"

export type CounterState = number

const initialState = 0

const slice = createSlice({
    name: 'counter',
    initialState,
    reducers: {
        increment: state => state + 1,
        decrement: state => state - 1
    }
})

const {increment, decrement} = slice.actions

export const actionIncrement = increment
export const actionDecrement = decrement

export default slice.reducer