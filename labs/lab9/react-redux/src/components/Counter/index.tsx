import { useDispatch, useSelector } from "react-redux"
import { GlobalState } from "../../stores"
import { actionDecrement, actionIncrement } from "../../stores/Counter"

export const Counter = () => {

    const counter = useSelector((state:GlobalState) => state.counter)

    const dispatch = useDispatch()

    return <div>
        {counter}
        <button onClick={() => dispatch(actionIncrement())}>+</button>
        <button onClick={() => dispatch(actionDecrement())}>-</button>
    </div>
}

export default Counter