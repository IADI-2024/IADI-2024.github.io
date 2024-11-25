import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { BooksApi, Configuration } from '../../api';

export interface BooksState {
    books:string[],
    loading:boolean,
    uploading:boolean
}

const initialState:BooksState = { books: [], loading: false, uploading:false}

const slice = createSlice({
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
        },
        setUploading: (state, action:PayloadAction<boolean>) => {
            state.uploading = action.payload
        }
    },
});

const {setBooks, setLoading, setUploading, addBook }= slice.actions

// This is only for demo
const config = new Configuration({
    headers: {
      "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgSUFESSAyMDIyLzIwMjMiLCJyb2xlcyI6IlVTRVIsIERSSVZFUiIsImV4cCI6MTczMjIwMjI2NiwiaWF0IjoxNzMyMjAxNjY2LCJ1c2VybmFtZSI6ImFkbWluIn0.6MtnTekOYGCF0Trvfw-kbAFLPdZ8PnYP8taVuj656UE",
    },
  });
  
const api = new BooksApi(config);
  
export const actionLoadBooks = (filter:string="") => async (dispatch: any) => {
    dispatch(setLoading(true))
    api.getAllBooks({filter})
    .then( books => { dispatch(setBooks(books.map( b => b.name )))})    
}

export const actionAddBook = (name:string) => async (dispatch:any) => {
    dispatch(setUploading(true))
    api.addBook({book:{id:0, name, kind:{id:0, name:'dunno'}}})
    .then( x => dispatch(addBook(name)))
}

export default slice.reducer


