import { BooksApi, Configuration } from "./api";

const config = new Configuration({
    basePath: "http://localhost:8080", // Your API's base URL
    headers: {
      "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgSUFESSAyMDIyLzIwMjMiLCJyb2xlcyI6IlVTRVIsIERSSVZFUiIsImV4cCI6MTczMTU5NjM2NSwiaWF0IjoxNzMxNTk1NzY1LCJ1c2VybmFtZSI6ImFkbWluIn0.H0SzzI3BCWHg90v4DKzoALKEWZVegJDgQKqs_kIvkzY",
    },
  });

const api = new BooksApi(config);

async function showBooks() {
    let books = await api.getAllBooks()
    console.log(books)
}

// console.log("Before")
// showBooks().then(() => console.log("Arrived"))
// console.log("After")

console.log("Before")
api.getAllBooks().then(bs => console.log(bs))
console.log("After")
