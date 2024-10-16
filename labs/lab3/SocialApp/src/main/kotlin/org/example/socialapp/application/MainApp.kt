package org.example.socialapp.application

import org.example.socialapp.service.OrderService
import org.example.socialapp.service.ProductService
import org.example.socialapp.service.UserService
import org.springframework.stereotype.Component

@Component
class MainApp(
    val userService: UserService,
    val orderService: OrderService,
    val productService: ProductService
) {

    fun hello() =
        listOf(userService.hello(),
        orderService.hello(),
        productService.hello())

}