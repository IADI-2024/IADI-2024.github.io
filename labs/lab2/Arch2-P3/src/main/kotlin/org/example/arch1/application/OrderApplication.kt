package org.example.arch1.application

import org.example.arch1.data.ClientDAO
import org.example.arch1.data.OrderDAO
import org.example.arch1.data.OrderRepository
import org.springframework.stereotype.Component
import java.util.*


@Component
class OrderApplication(val orderRepo: OrderRepository) {

    fun allOrders(): List<OrderDAO> = orderRepo.findAll().toList()

    fun ordersOfClient(client: ClientDAO): List<OrderDAO> {
        TODO("Not implemented yet")
    }

    fun createOrder(order:OrderDAO): OrderDAO {
        TODO("Not implemented yet")
    }

    fun updateOrder(order:OrderDAO): OrderDAO {
        TODO("Not implemented yet")
    }

    fun deleteOrder(order:OrderDAO): OrderDAO {
        TODO("Not implemented yet")
    }

    fun findClient(client: Int): Optional<ClientDAO> {
        TODO("Not yet implemented")
    }

    fun orderById(id: Long):OrderDAO? {
        return orderRepo.findById(id).orElse(null)
    }
}