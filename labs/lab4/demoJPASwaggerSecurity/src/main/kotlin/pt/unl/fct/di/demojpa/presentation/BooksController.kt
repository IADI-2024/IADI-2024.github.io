package pt.unl.fct.di.demojpa.presentation

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pt.unl.fct.di.demojpa.application.BooksApplication

@RestController
class BooksController(val app: BooksApplication) : BooksAPI {

    override fun getAllBooks(filter:String): List<BookDTO> =
        app.getAllBooks(filter).map {
            BookDTO(it.id, it.name, CategoryInBookDTO(it.kind.id, it.kind.name))
        }

    override fun addBook(@RequestBody book: BookDTO) {
        TODO("Not yet implemented")
    }

    override fun getBookById(@PathVariable id: Long): BookDTO {
        TODO("Not yet implemented")
    }
}