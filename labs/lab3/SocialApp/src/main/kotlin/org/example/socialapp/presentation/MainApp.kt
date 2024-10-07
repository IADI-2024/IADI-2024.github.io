package org.example.socialapp.presentation

import org.example.socialapp.service.OrderService
import org.example.socialapp.service.ProductService
import org.example.socialapp.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class MainApp(
    val userClient: UserService,
    val orderService: OrderService,
    val productService: ProductService
) : MainAPI {

    override fun hello() =
        listOf(userClient.hello(),
               orderService.hello(),
               productService.hello())
}


