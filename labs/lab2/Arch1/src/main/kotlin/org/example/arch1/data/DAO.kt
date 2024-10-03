package org.example.arch1.data

import jakarta.annotation.Generated
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class ClientDAO(@Generated @Id val number: Long, val name:String)

@Entity
data class OrderDAO(@Generated @Id val number: Long, @ManyToOne val client: ClientDAO)

