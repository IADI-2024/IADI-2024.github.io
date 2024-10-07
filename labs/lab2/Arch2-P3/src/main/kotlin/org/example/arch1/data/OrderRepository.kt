package org.example.arch1.data

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.springframework.data.repository.CrudRepository

@Entity
data class ClientDAO(
    @Id val number: Int,
    val name:String)

@Entity
data class OrderDAO(
    @Id val number: Int,
    @ManyToOne val client: ClientDAO)


interface OrderRepository : CrudRepository<OrderDAO,Long>

interface ClientRepository : CrudRepository<ClientDAO, Long>