package org.example.arch1

import com.fasterxml.jackson.core.type.TypeReference
import org.example.arch1.OrderTestsWithDatabase.Companion.objectMapper
import org.example.arch1.data.ClientDAO
import org.example.arch1.data.OrderDAO
import org.example.arch1.data.OrderRepository
import org.example.arch1.presentation.OrderDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class OrderTestsWithoutADataBase {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var orders: OrderRepository

    companion object {
        val client1 = ClientDAO(99, "Jane Doe")
        val client2 = ClientDAO(55, "Jane Smith")
        val dbOrders = listOf( OrderDAO(1, client1), OrderDAO(2, client1), OrderDAO(3, client2))
        val dbOrder = OrderDAO(1, client2)
        val apiOrder = OrderDTO(dbOrder.number, dbOrder.client.number)
        val apiOrders = dbOrders.map { OrderDTO( it.number, it.client.number )}
    }

    @Test
    fun `Just to see if the database can be faked or mocked`() {

        Mockito.`when`(orders.findAll()).thenReturn(dbOrders)

        val body = mvc.perform(get("/orders"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val orders = objectMapper.readValue(body, object : TypeReference<List<OrderDTO>>() {})

        assertEquals(apiOrders.size, orders.size)
        assertEquals(apiOrders, orders)
    }

    @Test
    fun `testing the getting of an order`() {

        val key = 3L

        Mockito.`when`(orders.findById(any())).then {
            assertEquals(key, it.getArgument<Long>(0)); Optional.of(dbOrder)
        }

        val body = mvc.perform(get("/orders/${key}"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val order = objectMapper.readValue(body, OrderDTO::class.java)

        assertEquals(apiOrder, order)


    }







}