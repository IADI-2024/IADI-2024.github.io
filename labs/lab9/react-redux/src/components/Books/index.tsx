import { useSelector } from "react-redux"
import { GlobalState } from "../../stores"

export const BooksList = () => {

    const {books, loading} = useSelector((state:GlobalState) => state.books)

    return <>
        {loading && <p>Books Loading</p> }
        {!loading && <ul>{books.map(b=><li key={b}>{b}</li>)}</ul>}
        </>
}

export default BooksList