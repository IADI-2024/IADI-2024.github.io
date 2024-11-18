
import { useEffect, useState } from "react";
import { BooksApi, Configuration } from "../api";

const config = new Configuration({
    headers: {
      "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgSUFESSAyMDIyLzIwMjMiLCJyb2xlcyI6IlVTRVIsIERSSVZFUiIsImV4cCI6MTczMTUwMTMyNywiaWF0IjoxNzMxNTAwNzI3LCJ1c2VybmFtZSI6ImFkbWluIn0.34NmEfOG-w1GPqMwSl23jnZCuoZ9EosPekAnYQ-xGlI",
    },
  });
  
const api = new BooksApi(config);
  
async function loadBooks() {
    let books = await api.getAllBooks()
    return books.map( b => b.name )
}

export const BookList = () => {
    const [loading, setLoading] = useState(true)
    const [books, setBooks] = useState([] as string[])
  
    useEffect(()=> { 
      loadBooks().then( bs => {setBooks(bs); setLoading(false)}) 
    }, [])
  
    return <>
      { loading && <p>Loading...</p>}
      { !loading && <ul>{books.map(b=> <li key={b}>{b}</li>)}</ul> }
    </>
  }
  
  