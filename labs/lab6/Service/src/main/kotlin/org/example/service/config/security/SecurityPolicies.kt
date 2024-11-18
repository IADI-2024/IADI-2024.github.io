package org.example.service.config.security

import org.example.service.config.filters.UserAuthToken
import org.example.service.presentation.ResourceDTO
import org.example.service.presentation.ResourceWIdDTO
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

// TODO: do an enum for operations

@Service
class capabilitiesService {
    fun canReadAll(user: Principal, filter:String?): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities["*"] // to read all, needs to be acting on behalf of any user
        return filter == "public"
            || operation != null && lessOrEqual("READ", operation) // can take into account a filter
    }

    fun canCreate(user: Principal, resourceDTO: ResourceDTO): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities[resourceDTO.owner]
        return operation != null && lessOrEqual("CREATE", operation)
    }

    fun canReadOne(user: Principal, id:Long): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        // TODO: fetch a resource from the database and compare the owner of the resource with the user.name
        val resource = if (id == 1L) ResourceWIdDTO(1, "John", "twenty") else ResourceWIdDTO(2, "Mary", "twenty")

        val operationOne = capabilities[resource.owner]
        val operationAll = capabilities["*"]

        return operationOne != null && lessOrEqual("READ", operationOne)
            || operationAll != null && lessOrEqual("READ", operationAll)
    }

    private fun lessOrEqual(op1:String, op2:String) =
        op1 == op2
                || op1 == "NONE"
                || op2 == "ALL"
                || op1 == "READ" && op2 == "WRITE"
    // others, e.g. "DELETE" and "CREATE" are non-related operations
    // Better with an enum if the relation order is complete
}

@PreAuthorize("@capabilitiesService.canReadAll(principal, #filter)")
annotation class CanReadAllResources

@PreAuthorize("@capabilitiesService.canCreate(principal, #resource)")
annotation class CanCreateResources

@PreAuthorize("@capabilitiesService.canReadOne(principal, #id)")
annotation class CanReadOneResource

