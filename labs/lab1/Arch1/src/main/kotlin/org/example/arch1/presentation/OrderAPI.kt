package org.example.arch1.presentation

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class OrderDTO(var number: Int, var client:Int)

@RequestMapping("/orders")
interface OrderAPI {

    @GetMapping("")
    fun getOrders(): List<OrderDTO>

    @PostMapping("")
    fun createOrder(@RequestBody order: OrderDTO): OrderDTO

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable("id") id: Int): OrderDTO

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable("id") id: Int, @RequestBody order: OrderDTO): OrderDTO

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable("id") id: Int)
}
