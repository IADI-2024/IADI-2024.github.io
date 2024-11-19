import { useState } from "react"
import { useDispatch } from "react-redux"
import { actionLoadBooks } from "../../stores/Books"

export const SearchBox = () => {

    const [text, setText] = useState("")

    const dispatch:any = useDispatch()

    function handleChange(e:any) {
        const value = e.target.value || ""
        setText(value)
        dispatch(actionLoadBooks(value))
    }

    return <div>
        <input value={text} onChange={handleChange}/>
        </div>
}