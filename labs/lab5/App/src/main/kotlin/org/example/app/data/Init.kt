package org.example.app.data

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class Init(
    val users:UserRepository,
    val resources:ResourceRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val encoder = BCryptPasswordEncoder()

        val user1 = UserDAO("John", encoder.encode("PassJohn"))
        val user2 = UserDAO("Jane", encoder.encode("PassJane"))
        val user3 = UserDAO("Mary", encoder.encode("PassMary"))

        users.saveAll(listOf(user1, user2, user3))

        val resource1 = ResourceDAO(1, user1)
        val resource2 = ResourceDAO(2, user1)
        val resource3 = ResourceDAO(3, user3)
        val resource4 = ResourceDAO(4, user3)
        val resource5 = ResourceDAO(5, user2)

        resources.saveAll(listOf(resource1, resource2, resource3, resource4, resource5))
    }
}