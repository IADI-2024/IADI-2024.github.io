package org.example.arch1

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
import java.util.Optional
import kotlin.test.assertNotNull

@SpringBootTest
@AutoConfigureMockMvc
class OrdersTestsWoDB {
    @Autowired lateinit var mvc: MockMvc
    @MockBean  lateinit var orders: OrderRepository

    companion object {
        val client = ClientDAO(10,"John Doe")
        val order1 = OrderDAO(10, client)
        val order1DTO = OrderDTO( order1.number, order1.client.number )
        val order2 = OrderDAO(20, client)
        val order3 = OrderDAO(30, client)
        val order4 = OrderDAO(40, client)
        val sampleOrders = listOf(order1, order2, order3, order4)

        //val dtos = listOf<OrderDTO>();
        val dtos = sampleOrders.map { OrderDTO(it.number, it.client.number)}

        val objectMapper = jacksonObjectMapper()
    }

    @Test
    fun `Test that gets and prints a collection`() {
        Mockito.`when`(orders.findAll()).thenReturn(sampleOrders)

        val result = mvc
        .perform(get("/orders"))
        .andExpect(status().isOk)
        .andReturn()

        println(result.response.contentAsString)
    }

    @Test
    fun `Test that compares the collection`() {
        Mockito.`when`(orders.findAll()).thenReturn(sampleOrders)

        val result = mvc
            .perform(get("/orders"))
            .andExpect(status().isOk)
            .andReturn()

        val body = result.response.contentAsString
        val orders:List<OrderDTO> = objectMapper.readValue(body, object : TypeReference<List<OrderDTO>>() {})
        assertNotNull(orders)
        assertEquals(sampleOrders.size, orders.size)

        assertEquals( orders, dtos)
    }

    @Test
    fun `A test that reads parameters`() {
        val key = 1L

        Mockito.`when`(orders.findById(any<Long>())).then {
            assertEquals(it.getArgument<Long>(0) , key )
            return@then order1DTO
        }

        val result = mvc
            .perform(get("/orders/${key}"))
            .andExpect(status().isOk)
            .andReturn()

        val body = result.response.contentAsString
        val order1:OrderDTO = objectMapper.readValue(body, OrderDTO::class.java)
        assertNotNull(order1)
        assertEquals(order1DTO, order1)
    }

    @Test
    fun `A 404 is issued`() {
        Mockito.`when`(orders.findById(any<Long>())).then {
            return@then Optional.empty<OrderDAO>()
        }

        mvc
            .perform(get("/orders/1"))
            .andExpect(status().isNotFound)
            .andReturn()
    }

    @Test
    fun `A test that checks adding an order`() {

    }
}