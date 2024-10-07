package org.example.arch1

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertNotNull

@SpringBootTest
class Arch1ApplicationTests {

    @Autowired
    lateinit var ctx: ApplicationContext

    @Test
    fun contextLoads() {
        assertNotNull(ctx)
    }

}
