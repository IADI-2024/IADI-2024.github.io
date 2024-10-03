package org.example.arch1.presentation

import org.example.arch1.application.Client
import org.example.arch1.application.Order
import org.example.arch1.application.OrderApplication
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@ResponseStatus(HttpStatus.NOT_FOUND)
class ClientNotFoundException() : RuntimeException("Client not found")

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrderNotFoundException() : RuntimeException("Order not found")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidOrderDTO() : RuntimeException("Invalid order")

@RestController
class OrderController(val app: OrderApplication) : OrderAPI {

    override fun getOrders() =
        app.allOrders()
        .map { OrderDTO(it.number, it.client.number) }

    override fun createOrder(order: OrderDTO): OrderDTO {
        val client = app.findClient(order.client).orElseThrow{ ClientNotFoundException() }

        val orderResult = app
            .createOrder(Order(order.number, client))
            .let { OrderDTO(it.number, it.client.number) }

        return orderResult
    }

    override fun updateOrder(id: Int, order: OrderDTO): OrderDTO {
        if (id != order.number) {
            throw InvalidOrderDTO()
        } else {
            val client = app.findClient(order.client).orElseThrow{ ClientNotFoundException() }

            val orderResult = app
                .updateOrder(Order(order.number, client))
                .let { OrderDTO(it.number, it.client.number) }

            return orderResult
        }
    }

    override fun getOrderById(id: Int): OrderDTO =
        app.orderById(id)
        ?.let { OrderDTO(it.number, it.client.number) }
        ?: throw OrderNotFoundException()


    override fun deleteOrder(id: Int) {
        TODO("Not yet implemented")
    }
}

