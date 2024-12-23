package pt.unl.fct.di.demojpa.data

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookDAO,Long> {
    @Query("select b from BookDAO b where b.name LIKE %:filter%")
    fun findAllWithFilter(filter:String): Iterable<BookDAO>
}

interface CategoryRepository : CrudRepository<CategoryDAO,Long> {
    @Query("select c from CategoryDAO c inner join fetch c.books b")
    fun findAllWithBooks():Iterable<CategoryDAO>
}

