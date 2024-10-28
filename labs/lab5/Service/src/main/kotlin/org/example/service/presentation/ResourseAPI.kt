package org.example.service.presentation

import org.example.service.config.security.CanCreateResources
import org.example.service.config.security.CanReadAllResources
import org.example.service.config.security.CanReadOneResource
import org.springframework.security.access.prepost.PostFilter
import org.springframework.web.bind.annotation.*

data class ResourceDTO(val data:String)

data class ResourceWIdDTO(val id:Long, val data:String)

@RequestMapping("/resources")
interface ResourceAPI {

    @GetMapping()
    @CanReadAllResources()
    fun getAll():List<ResourceWIdDTO>

    @PostMapping()
    @CanCreateResources()
    fun createResource(@RequestBody resource: ResourceDTO):Long

    @GetMapping("/{id}")
    @CanReadOneResource()
    fun getOne(@PathVariable id:Long): ResourceWIdDTO
}

