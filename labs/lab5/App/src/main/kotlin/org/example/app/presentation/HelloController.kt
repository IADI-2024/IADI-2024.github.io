package org.example.app.presentation

import org.example.app.service.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hello")
class HelloController(val resources: ResourceAPI) {

    @GetMapping()
    fun hello() = resources.getAll()

    @GetMapping("/{id}")
    fun helloOne(@PathVariable id:Long) = resources.getOne(id)

    @PostMapping()
    fun helloCreate() = resources.createResource(ResourceDTO("Hello, World!"))

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiden(e: ForbiddenException) = e.message

    // Other exception handlers go here...
}

