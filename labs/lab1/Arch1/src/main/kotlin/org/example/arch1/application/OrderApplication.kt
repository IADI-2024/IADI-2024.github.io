package org.example.arch1.application

import org.springframework.stereotype.Component
import java.util.*

data class Client(val number: Int)
data class Order(val number: Int, val client: Client)

@Component
class OrderApplication {

    fun allOrders(): List<Order> {
        TODO("Not implemented yet")
    }

    fun ordersOfClient(client: Client): List<Order> {
        TODO("Not implemented yet")
    }

    fun createOrder(order:Order): Order {
        TODO("Not implemented yet")
    }

    fun updateOrder(order:Order): Order {
        TODO("Not implemented yet")
    }

    fun deleteOrder(order:Order): Order {
        TODO("Not implemented yet")
    }

    fun findClient(client: Int): Optional<Client> {
        TODO("Not yet implemented")
    }
}