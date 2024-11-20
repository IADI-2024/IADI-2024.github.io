import { useState } from "react"
import { useDispatch } from "react-redux"
import { actionAddBook } from "../../store/Books"

export const BookForm = () => {

    const [text, setText] = useState("")

    const dispatch:any = useDispatch()

    function handleChange(e:any) {
        setText(e.target.value)
    }

    function addBook() {
        dispatch(actionAddBook(text))
    }

    return <div>
        Title of book: <input value={text} onChange={handleChange}/>
        <button onClick={addBook}>Add</button>
    </div>
}

export default BookForm