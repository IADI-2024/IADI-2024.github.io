package org.example.arch1.data

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SampleData(
    val orderRepo: OrderRepository,
    val clientRepo:ClientRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val client1 = ClientDAO(1, "John Doe")
        val client2 = ClientDAO(2, "Jane Doe")

        clientRepo.saveAll(listOf(client1, client2))

        val orders = mutableListOf<OrderDAO>(
            OrderDAO(1, client1),
            OrderDAO(2, client2),
            OrderDAO(3, client2),
            OrderDAO(4, client1),
            OrderDAO(5, client2))

        orderRepo.saveAll(orders)

    }
}