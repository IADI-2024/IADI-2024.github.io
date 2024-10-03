package org.example.arch1

import com.fasterxml.jackson.core.type.TypeReference
import org.example.arch1.OrdersTestsWoDB.Companion.objectMapper
import org.example.arch1.presentation.OrderDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertNotNull

@SpringBootTest
@AutoConfigureMockMvc
class OrderTestWDB {

    @Autowired lateinit var mvc: MockMvc

    @Test
    fun `Test that checks the number of record in the DB`() {

        val result = mvc
            .perform(get("/orders"))
            .andExpect(status().isOk)
            .andReturn()

        val body = result.response.contentAsString
        val orders:List<OrderDTO> = objectMapper.readValue(body, object : TypeReference<List<OrderDTO>>() {})
        assertNotNull(orders)
        assertEquals(5, orders.size)
    }
}