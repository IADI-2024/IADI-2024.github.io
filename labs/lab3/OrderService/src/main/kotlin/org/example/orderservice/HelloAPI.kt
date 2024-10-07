package org.example.orderservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/hello")
interface HelloAPI {

    @GetMapping()
    fun hello(): String
}