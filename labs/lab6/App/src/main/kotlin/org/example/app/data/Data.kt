package org.example.app.data

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

@Entity
data class UserDAO(@Id val username: String, val password: String) {
    constructor() : this("", "")
}

@Entity
data class FriendshipDAO(@Id val id:Long, val username: String, val friend:String) {
    constructor() : this(0, "", "")
}

interface UserRepository : CrudRepository<UserDAO, Long>

interface FriendRepository : CrudRepository<FriendshipDAO, Long> {
    fun findByUsername(username: String): List<FriendshipDAO>
}
