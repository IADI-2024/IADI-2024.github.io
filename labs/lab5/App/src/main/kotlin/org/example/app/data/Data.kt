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
data class ResourceDAO(@Id val id:Long, @ManyToOne val owner: UserDAO) {
    constructor() : this(0, UserDAO("",""))
}

interface UserRepository : CrudRepository<UserDAO, Long>

interface ResourceRepository : CrudRepository<ResourceDAO, Long> {

    @Query("select r from ResourceDAO r where r.owner.username = :username")
    fun findByOwner(username: String): List<ResourceDAO>
}