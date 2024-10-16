package org.example.socialapp.presentation

import org.example.socialapp.application.MainApp
import org.example.socialapp.service.OrderService
import org.example.socialapp.service.ProductService
import org.example.socialapp.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(val app:MainApp) : MainAPI {

    override fun hello() = app.hello()
}


