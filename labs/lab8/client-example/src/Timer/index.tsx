import { useEffect, useState } from "react"

export interface TimerInterface {message:string}

export const Timer = ({message}:TimerInterface) => {

  const [seconds, setSeconds] = useState(0)

  const tick = () => { setSeconds( s => s + 1 ) }

  function installTimer() {
    let interval = setInterval(tick, 1000)
    return () => clearInterval(interval)
  }

  useEffect(installTimer,[])

  return <div>
          <p>{message}</p>
          {seconds}
          </div>
}
