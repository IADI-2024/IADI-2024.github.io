package org.example.circuitbreaker.service

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service

@Service
class HelloService {

    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "reliableHello")
    fun sayHello(): String {
        throw RuntimeException("Service Failure!");
    }

    fun reliableHello() : String {
        return "Default Response";
    }
}
