package org.example.app.presentation

import org.example.app.service.ResourceAPI
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController(val resources: ResourceAPI) {

    @GetMapping()
    fun hello() = resources.getAll()

    @GetMapping("/{id}")
    fun helloOne(@PathVariable id:Long) = resources.getOne(id)
}