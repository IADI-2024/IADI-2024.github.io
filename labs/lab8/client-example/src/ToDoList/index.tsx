import { useState } from "react"

const TaskList = ({tasks}:{tasks:String[]}) => {
    return <div>
      <p>Tasks</p>
        <ul>
          { tasks.map(t=><li>{t}</li>) }
        </ul>
      </div>
  }
  
  const InputBox = ({add}:{add:(s:string) => void}) => {
    const [text, setText] = useState("")
  
    const handleChange = (e:any) => {
      setText(e.target.value)
    }
  
    const handleClick = () => { 
      add(text) 
      setText("")
    }
  
    return <div>
        <input type='text' value={text} onChange={handleChange}/>
        <button onClick={handleClick}>Add</button>
      </div>
  }
  
export const TodoList = () => {
    const [tasks, setTasks] = useState([] as string[])
  
    const addTask = (s:string) => {setTasks([...tasks, s])}
  
    return <><TaskList tasks={tasks}/><InputBox add={addTask}/></>
  }
  
  