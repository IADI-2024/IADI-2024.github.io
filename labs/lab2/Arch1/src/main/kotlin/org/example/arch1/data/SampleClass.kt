package org.example.arch1.data

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component



@Component
class SampleClass(
    val ordersRepo: OrderRepository,
    val clientsRepo: ClientRepository) : CommandLineRunner{

    override fun run(vararg args: String?) {
        val client1 = ClientDAO(1, "John Doe")
        val client2 = ClientDAO(1, "Jane Doe")

        clientsRepo.saveAll(listOf(client1, client2))

        val orders = mutableListOf<OrderDAO>(
            OrderDAO(1, client1),
            OrderDAO(2, client2),
            OrderDAO(3, client2),
            OrderDAO(4, client1),
            OrderDAO(5, client2))

        ordersRepo.saveAll(orders)
    }
}