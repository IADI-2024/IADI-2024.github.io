import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { BooksApi, Configuration } from '../../api';

export interface BooksState {
    books:string[],
    loading:boolean
}

const initialState:BooksState = { books: [], loading: false}

const bookSlice = createSlice({
    name: 'books',
    initialState,
    reducers: {
        addBook: (state, action:PayloadAction<string>) => {
            state.books = [...state.books, action.payload]
        },
        setBooks: (state, action:PayloadAction<string[]>) => {
            state.books = action.payload
            state.loading = false
        },
        setLoading: (state, action:PayloadAction<boolean>) => {
            state.loading = action.payload
        }
    },
});

const {setBooks, setLoading}= bookSlice.actions

export const actionAddBook = bookSlice.actions.addBook

// This is only for demo
const config = new Configuration({
    headers: {
      "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgSUFESSAyMDIyLzIwMjMiLCJyb2xlcyI6IlVTRVIsIERSSVZFUiIsImV4cCI6MTczMjA1OTI4NCwiaWF0IjoxNzMyMDU4Njg0LCJ1c2VybmFtZSI6ImFkbWluIn0.iDCaAzqztkLDdegzHHVKH36GMuNdc0Q1urY0zGOONBc",
    },
  });
  
const api = new BooksApi(config);
  
export const actionLoadBooks = (filter:string="") => async (dispatch: any) => {
    dispatch(setLoading(true))
    api.getAllBooks({filter})
    .then( books => dispatch(setBooks(books.map( b => b.name ))))    
}

export default bookSlice.reducer


