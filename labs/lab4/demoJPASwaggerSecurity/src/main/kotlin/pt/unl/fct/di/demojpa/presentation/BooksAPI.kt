package pt.unl.fct.di.demojpa.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pt.unl.fct.di.demojpa.config.security.CanAddBooks
import pt.unl.fct.di.demojpa.config.security.CanReadAllBooks
import pt.unl.fct.di.demojpa.config.security.CanReadOneBook

@Schema(name="Book")
data class BookDTO(val id:Long, val name:String, val kind:CategoryInBookDTO)

@Schema(name="ShortCategoryinBook", description = "Short version of the category")
data class CategoryInBookDTO(val id:Long, val name:String)

@Schema(name="Category")
data class CategoryDTO(val id:Long, val name:String, val books:Set<BookInCategoryDTO>)

data class BookInCategoryDTO(val id:Long, val name:String)

@RequestMapping("/api/books")
@Tag(name = "Books", description = "Books API")
interface BooksAPI {

    @GetMapping("")
    @Operation(summary = "Get all books")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "The list of books that are in the system"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @CanReadAllBooks
    fun getAllBooks(@RequestParam(value = "", required = false) filter:String):List<BookDTO>

    @PostMapping("")
    @Operation(summary = "Add a new book")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "The book was successfully added"),
        ApiResponse(responseCode = "400", description = "Invalid book data"),
        ApiResponse(responseCode = "404", description = "Category not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @CanAddBooks
    fun addBook(book:BookDTO)

    @GetMapping("{id}")
    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "The book with the given id"),
        ApiResponse(responseCode = "404", description = "Book not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @CanReadOneBook
    fun getBookById(id:Long):BookDTO
}