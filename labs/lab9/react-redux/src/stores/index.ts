import { configureStore } from "@reduxjs/toolkit"
import { logger } from 'redux-logger'
import bookReducer, { BooksState, actionLoadBooks } from "./Books"
// import counterReducer from "./Counter"

export interface GlobalState {
    books: BooksState
}

export const store = configureStore({
    reducer : {
        books: bookReducer,
//        counter: counterReducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
})

store.dispatch(actionLoadBooks())