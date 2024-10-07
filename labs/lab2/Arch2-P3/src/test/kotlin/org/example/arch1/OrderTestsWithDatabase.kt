package org.example.arch1

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.example.arch1.data.OrderDAO
import org.example.arch1.presentation.OrderDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class OrderTestsWithDatabase {

    @Autowired lateinit var mvc:MockMvc

    companion object {
        val objectMapper = jacksonObjectMapper()
    }

    @Test
    fun `Just to see if the controller exists`() {
        mvc.perform(get("/orders"))
        .andExpect(status().isOk)
    }

    @Test
    fun `Just to see if 404 is real`() {
        mvc.perform(get("/order"))
        .andExpect(status().isNotFound)
    }

    @Test
    fun `Just to see if the database has 5 elements`() {
        val body = mvc.perform(get("/orders"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        println(body)

        val orders = objectMapper.readValue(body, object : TypeReference<List<OrderDTO>>() {})

        assertEquals(5, orders.size)
    }
}