package pt.unl.fct.di.demojpa.application

import org.springframework.stereotype.Service
import pt.unl.fct.di.demojpa.data.BookDAO
import pt.unl.fct.di.demojpa.data.BookRepository

@Service
class BooksApplication(val books: BookRepository) {

    fun getAllBooks(filter:String):List<BookDAO> =
        books.findAllWithFilter(filter).toList()
}