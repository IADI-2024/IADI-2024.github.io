import { useSelector } from "react-redux"
import { GlobalState } from "../../store"

export const BooksList = () => {

    const {books, loading} = useSelector((state:GlobalState) => state.books)

    return <>
        {loading && <p>Books Loading...</p> }
        {!loading && <ul>{books.map(b=><li key={b}>{b}</li>)}</ul>}
        </>
}

export const BookCounter = () => {

    const n = useSelector((state:GlobalState) => state.books.books.length)

    return <p>I have {n} Books</p>
}

export * from './SearchBox'

export default BooksList