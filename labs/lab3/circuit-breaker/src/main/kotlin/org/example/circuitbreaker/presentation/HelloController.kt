package org.example.circuitbreaker.presentation

import org.example.circuitbreaker.service.HelloService
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(val helloService: HelloService) : HelloAPI {
    override fun hello() = helloService.sayHello()
}