package org.example.app.data

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class Init(
    val users:UserRepository,
    val friends:FriendRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val encoder = BCryptPasswordEncoder()

        val user1 = UserDAO("John", encoder.encode("PassJohn"))
        val user2 = UserDAO("Jane", encoder.encode("PassJane"))
        val user3 = UserDAO("Mary", encoder.encode("PassMary"))

        users.saveAll(listOf(user1, user2, user3))

        val friend1 = FriendshipDAO(1, "John", friend = "Jane")
        val friend2 = FriendshipDAO(2, "John", friend = "Mary")

        friends.saveAll(listOf(friend1, friend2))
    }
}