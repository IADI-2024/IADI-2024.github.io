package pt.unl.fct.di.demojpa

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.security.test.context.support.WithMockUser


@SpringBootTest
@AutoConfigureMockMvc
class DemoSecurityTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `Test with no access`() {
        mvc.get("/api/books")
            .andExpect { status { isUnauthorized() } }
    }

    @Test
    @WithMockUser(username="admin", roles = ["USER"])
    fun `Test with access`() {
        mvc.get("/api/books")
            .andExpect { status { isOk() } }
    }

    @Test
    @WithMockUser(username="admin", roles = ["ADMIN"])
    fun `Test with wrong access`() {
        mvc.get("/api/books")
            .andExpect { status { isForbidden() } }
    }

}