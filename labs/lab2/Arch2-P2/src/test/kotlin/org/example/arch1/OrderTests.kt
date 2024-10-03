package org.example.arch1

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.example.arch1.data.ClientDAO
import org.example.arch1.data.OrderDAO
import org.example.arch1.data.OrderRepository
import org.example.arch1.presentation.OrderDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class OrderTests {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var orders: OrderRepository

    companion object {
        val objectMapper = jacksonObjectMapper()

        val client= ClientDAO(1, "John Doe")
        val order = OrderDAO(1, client)
        val sampleOrders = listOf<OrderDAO>(order, order, order, order, order)
    }

    @Test
    fun `Just to check if we have 5 orders in the mock database`() {

        Mockito.`when`(orders.findAll()).thenReturn(sampleOrders)

        val result = mvc.perform(get("/orders"))
            .andExpect(status().isOk)
            .andReturn()

        var body = result.response.contentAsString
        val orders =
            objectMapper.readValue<List<OrderDTO>>(body, object : TypeReference<List<OrderDTO>>() {})
        assertEquals(5, orders.size)
    }
}