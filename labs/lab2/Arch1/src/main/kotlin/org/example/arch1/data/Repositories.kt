package org.example.arch1.data

import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<OrderDAO, Long>
interface ClientRepository : CrudRepository<ClientDAO, Long>
