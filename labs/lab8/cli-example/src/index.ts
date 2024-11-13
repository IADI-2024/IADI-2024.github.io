import { BooksApi, Configuration } from "./api";

const config = new Configuration({
    basePath: "http://localhost:8080", // Your API's base URL
    headers: {
      "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgSUFESSAyMDIyLzIwMjMiLCJyb2xlcyI6IlVTRVIsIERSSVZFUiIsImV4cCI6MTczMTQ5MTQ1NSwiaWF0IjoxNzMxNDkwODU1LCJ1c2VybmFtZSI6ImFkbWluIn0.84e-j6mmKB_ymWrtgxOFc2XlqKa26qVi5fBTYz1_D2Q",
    },
  });

const api = new BooksApi(config);

async function showBooks() {
    let books = await api.getAllBooks()
    books.map( b => b.name )
    console.log(books)
}

showBooks()
