package org.example.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.service.config.filters.UserAuthToken
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = UserWithCapabilitiesFactory::class)
annotation class WithMockUserAndCapabilities(val username: String, val capabilities: String, val role:String)

class UserWithCapabilitiesFactory : WithSecurityContextFactory<WithMockUserAndCapabilities> {
    override fun createSecurityContext(annotation: WithMockUserAndCapabilities?): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        if (annotation != null ) {
            val authorities = listOf(SimpleGrantedAuthority("ROLE_${annotation.role}"))
            val capabilities = ObjectMapper().readValue(
                annotation.capabilities,
                object : TypeReference<LinkedHashMap<String, String>>() {})
            val principal = UserAuthToken(annotation.username, authorities, capabilities)
            context.authentication = UserAuthToken(annotation.username, authorities, capabilities)
        }
        return context
    }
}


@SpringBootTest
@AutoConfigureMockMvc
class ServiceApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
    }

    @Test
    @WithMockUser(username = "user", authorities = ["ROLE_ADMIN"])
    fun `test with mock user`() {
        val auth = SecurityContextHolder.getContext().authentication
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"John\":\"READ\"}",
    )
    fun `test the simple READ Capability`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/1"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"John\":\"READ\"}",
    )
    fun `test the simple READ Capability on the wrong resource`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/2"))
            .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"*\":\"READ\"}",
    )
    fun `test the simple READ Capability on the *`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/2"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"John\":\"NONE\"}",
    )
    fun `test the NONE Capability`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/1"))
            .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"John\":\"ALL\",\"*\":\"CREATE\"}",
    )
    fun `test ALL Capability`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/1"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"*\":\"ALL\"}",
    )
    fun `test * resource matcher`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/1"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"*\":\"ALL\"}",
    )
    fun `test 0 resource matcher on another resource`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/2"))
            .andExpect(status().isOk)
    }

   @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"*\":\"ALL\"}",
    )
    fun `test 0 resource matcher on all resources`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"John\":\"ALL\"}",
    )
    fun `test resource matcher of one resource on all resources`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources"))
            .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"*\":\"DELETE\"}",
    )
    fun `test reading with wrong capability`() {
        val auth = SecurityContextHolder.getContext().authentication
        mockMvc
            .perform(get("/resources/1"))
            .andExpect(status().isForbidden)
    }



}

