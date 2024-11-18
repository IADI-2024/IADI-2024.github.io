package pt.unl.fct.di.demojpa.config.security

import org.springframework.stereotype.Service
import pt.unl.fct.di.demojpa.data.BookRepository
import java.security.Principal


@Service("securityService")
class SecurityService(val books:BookRepository) {

    fun canReadOneBook(principal:Principal,id:Long) = true
    // Go to the repo and check if the principal is the owner
}