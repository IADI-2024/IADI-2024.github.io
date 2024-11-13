import { useEffect, useState } from "react"

export interface ClockInterface {message:string}

export const Clock = ({message}:ClockInterface) => {

    const [date, setDate] = useState(new Date())
  
    const tick = () => { setDate( new Date() ) }
  
    function installTimer() {
      let interval = setInterval(tick, 1000)
      return () => clearInterval(interval)
    }
  
    useEffect(installTimer,[])
  
    return <div>
            <p>{message}</p>
            {date.toLocaleTimeString()}
            </div>
  }
  
  