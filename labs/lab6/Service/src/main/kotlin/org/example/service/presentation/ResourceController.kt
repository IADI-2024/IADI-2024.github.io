package org.example.service.presentation

import org.springframework.web.bind.annotation.RestController

@RestController
class ResourceController: ResourceAPI {

    override fun getAll(filter:String?): List<ResourceWIdDTO> =
        listOf(
            ResourceWIdDTO(1,"John", "one"),
            ResourceWIdDTO(2,"Jane", "two"),
            ResourceWIdDTO(3,"Mary", "three")
        )

    override fun createResource(resource: ResourceDTO): Long = 4

    override fun getOne(id: Long) = ResourceWIdDTO(id,"John", "one")
}