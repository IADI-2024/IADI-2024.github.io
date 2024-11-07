package org.example.app

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.app.service.ResourceAPI
import org.example.app.service.ResourceWIdDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var resourceAPI: ResourceAPI

    @Test
    fun contextLoads() {
    }

    @Test
    fun `greeting test`() {
        val o = ResourceWIdDTO(1,"one")

        Mockito.`when`(resourceAPI.getOne(1L)).thenReturn(o)
        val result = mockMvc
            .perform(get("/hello/1"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        assertEquals(result, ObjectMapper().writeValueAsString(o))
    }

    @Test
    fun `greeting test with list`() {
        val o1 = ResourceWIdDTO(1,"one")
        val o2 = ResourceWIdDTO(1,"two")
        val os = listOf(o1,o2)

        Mockito.`when`(resourceAPI.getAll()).thenReturn(os)
        val result = mockMvc
            .perform(get("/hello"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        assertEquals(result, ObjectMapper().writeValueAsString(os))
    }


}
