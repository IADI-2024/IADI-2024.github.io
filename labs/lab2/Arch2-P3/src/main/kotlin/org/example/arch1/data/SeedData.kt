package org.example.arch1.data

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SeedData(val orderRepo: OrderRepository,
                val clientRepo: ClientRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val client1 = ClientDAO(1, "John Doe")
        val client2 = ClientDAO(2, "Jane Doe")
        clientRepo.save(client1)
        clientRepo.save(client2)

        val orders = mutableListOf<OrderDAO>(
            OrderDAO(1, client1),
            OrderDAO(2, client2),
            OrderDAO(3, client1),
            OrderDAO(4, client2),
            OrderDAO(5, client1))

        orderRepo.saveAll(orders)
    }
}