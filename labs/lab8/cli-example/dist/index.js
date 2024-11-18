"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
const api_1 = require("./api");
const config = new api_1.Configuration({
    basePath: "http://localhost:8080", // Your API's base URL
    headers: {
        "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKU09OIFdlYiBUb2tlbiBmb3IgSUFESSAyMDIyLzIwMjMiLCJyb2xlcyI6IlVTRVIsIERSSVZFUiIsImV4cCI6MTczMTU5NjM2NSwiaWF0IjoxNzMxNTk1NzY1LCJ1c2VybmFtZSI6ImFkbWluIn0.H0SzzI3BCWHg90v4DKzoALKEWZVegJDgQKqs_kIvkzY",
    },
});
const api = new api_1.BooksApi(config);
function showBooks() {
    return __awaiter(this, void 0, void 0, function* () {
        let books = yield api.getAllBooks();
        console.log(books);
    });
}
// console.log("Before")
// showBooks().then(() => console.log("Arrived"))
// console.log("After")
console.log("Before");
api.getAllBooks().then(bs => console.log(bs));
console.log("After");
