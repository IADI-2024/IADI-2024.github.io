package org.example.socialapp.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("app")
interface MainAPI {

    @GetMapping("hello")
    fun hello(): List<String>
}