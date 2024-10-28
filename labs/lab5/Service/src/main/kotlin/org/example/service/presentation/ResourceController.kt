package org.example.service.presentation

import org.springframework.web.bind.annotation.RestController

@RestController
class ResourceController: ResourceAPI {

    override fun getAll(): List<ResourceWIdDTO> =
        listOf(
            ResourceWIdDTO(1,"one"),
            ResourceWIdDTO(2,"two"),
            ResourceWIdDTO(3,"three")
        )

    override fun createResource(resource: ResourceDTO): Long = 4

    override fun getOne(id: Long) = ResourceWIdDTO(1,"one")
}