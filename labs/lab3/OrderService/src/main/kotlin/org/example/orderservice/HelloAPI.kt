package org.example.orderservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

interface HelloAPI {

    @GetMapping("/hello")
    fun hello(): String
}