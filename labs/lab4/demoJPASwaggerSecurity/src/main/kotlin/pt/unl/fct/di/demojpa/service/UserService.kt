package pt.unl.fct.di.demojpa.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

data class MyUserDetails(val username:String, val password:String, val roles:String)

@Service
class UserService {

    // This is a mock of a service that would store users in the database
    fun findUserById(username: String?): Optional<MyUserDetails> =
//        Optional.of(MyUserDetails("admin","admin","ADMIN"))
         Optional.of(MyUserDetails("admin",BCryptPasswordEncoder().encode("admin"),"ADMIN"))
}