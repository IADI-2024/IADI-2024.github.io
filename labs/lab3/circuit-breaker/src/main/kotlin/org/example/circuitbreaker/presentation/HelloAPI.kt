package org.example.circuitbreaker.presentation

import org.springframework.web.bind.annotation.RequestMapping

interface HelloAPI {
    @RequestMapping("/hello")
    fun hello() : String
}