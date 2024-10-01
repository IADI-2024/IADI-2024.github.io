package org.example.arch1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

data class Greeting(val greeting: String, val timestamp:Date)

data class GreetingInput(val greeting: String, val name:String)

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello() = Greeting("Hello World!", Date())

    @PostMapping("/hello/{name}")
    fun sayHello(@PathVariable(value = "name") name: String) : Greeting = Greeting( "Hello $name!", timestamp = Date())

    @PostMapping("/greetings")
    fun postGreeting(@RequestBody greeting:GreetingInput): Greeting =
        Greeting("${greeting.greeting} ${greeting.name}!", timestamp = Date())
}