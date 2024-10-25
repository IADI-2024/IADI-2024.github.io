package pt.unl.fct.di.demojpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Service
import pt.unl.fct.di.demojpa.service.UserService


// check: https://docs.spring.io/spring-security/reference/servlet/getting-started.html

@EnableWebSecurity
@Configuration
open class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<BasicAuthenticationFilter>(
                UserPasswordAuthenticationFilterToJWT(
                    "/login",
                    authenticationManager
                )
            )
            addFilterBefore<BasicAuthenticationFilter>(JWTAuthenticationFilter())

            //formLogin { }
            httpBasic { } // ONLY FOR TESTING, removing will remove the BasicAuthenticationFilter from the chain, see addFilter above
        }
        return http.build()
    }

    // To declare a temporary version with a single user.
//    @Bean
//    fun userDetailsService(): UserDetailsService {
//        val userDetails = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("password")
//            .roles("USER")
//            .build()
//        return InMemoryUserDetailsManager(userDetails)
//    }

    @Bean
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}

@Service
class MyUserDetailsService(val users: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails? =
        users.findUserById(username).map {
            User.builder()
                .username(it.username)
                .password(it.password)
                .roles(it.roles)
                .build()
        }.orElse(null)
}