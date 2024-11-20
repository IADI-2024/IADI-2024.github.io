import { configureStore } from "@reduxjs/toolkit"
import { logger } from 'redux-logger'
import bookReducer, { BooksState, actionLoadBooks } from "./Books"
import counterReducer, { CounterState } from "./Counter"

export interface GlobalState {
    books: BooksState,
    counter: CounterState
}

export const store = configureStore({
    reducer : {
        books: bookReducer,
        counter: counterReducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
})

store.dispatch(actionLoadBooks())