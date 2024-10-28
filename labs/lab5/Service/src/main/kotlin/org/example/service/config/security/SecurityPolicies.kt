package org.example.service.config.security

import org.example.service.config.filters.UserAuthToken
import org.example.service.presentation.ResourceDTO
import org.example.service.presentation.ResourceWIdDTO
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.lang.annotation.Documented
import java.security.Principal

// TODO: do an enum for operations

@Service
class capabilitiesService {
    fun canReadAll(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities.get(0) // 0 means * because we assume that ids begin in 1
        return operation != null && lessOrEqual("READ", operation)
    }

    fun canCreate(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities.get(0)
        return operation != null && lessOrEqual("CREATE", operation)
    }

    fun canReadOne(user: Principal, id:Long): Boolean {
        val capabilities = (user as UserAuthToken).capabilities

        val operationOne = capabilities.get(id)
        val operationAll = capabilities.get(0L)

        return operationOne != null && lessOrEqual("READ", operationOne) ||
               operationAll != null && lessOrEqual("READ", operationAll)
    }

    // In a post authorize
    // TODO: test this
    fun canReadMultipleTrap(user:Principal, resources:List<ResourceWIdDTO>): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        resources.forEach {
            val operation = capabilities.get(it.id)
            if( operation == null || !lessOrEqual("READ", operation))
                return false
        }
        return true
    }

    // In a post filter
    // TODO: complete and test this
    fun canReadMultipleFilter(user:Principal, resources:List<ResourceWIdDTO>): List<ResourceWIdDTO> {
        val capabilities = (user as UserAuthToken).capabilities
        return resources.map {
            val operation = capabilities.get(it.id)
            if( operation != null && lessOrEqual("READ", operation)) it else null
        }.mapNotNull { it }
    }

    private fun lessOrEqual(op1:String, op2:String) =
        op1 == op2
                || op1 == "NONE"
                || op2 == "ALL"
                || op1 == "READ" && op2 == "WRITE"
    // others, e.g. "DELETE" and "CREATE" are non-related operations
    // Better with an enum if the relation order is complete
}

@PreAuthorize("@capabilitiesService.canReadAll(principal)")
annotation class CanReadAllResources

@PreAuthorize("@capabilitiesService.canCreate(principal)")
annotation class CanCreateResources

@PreAuthorize("@capabilitiesService.canReadOne(principal, #id)")
annotation class CanReadOneResource