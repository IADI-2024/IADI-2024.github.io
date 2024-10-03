package org.example.arch1.application

import org.springframework.stereotype.Component
import java.util.*

data class Client(val number: Int, val name:String)
data class Order(val number: Int, val client: Client)

// THIS SHOULD NOT BE HERE, WE ARE STILL MISSING THE LINK TO THE DB
val client = Client(1, "John Doe")
val orders = mutableListOf<Order>(
    Order(1, client),
    Order(2, client),
    Order(3, client),
    Order(4, client))

@Component
class OrderApplication {

    fun allOrders(): List<Order> = orders

    fun ordersOfClient(client: Client): List<Order> {
        TODO("Not implemented yet")
    }

    fun createOrder(order:Order): Order {
        orders.add(order)
        return order
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

    fun orderById(id: Int):Order? {
        return orders.find { it.client.number == id }
    }
}