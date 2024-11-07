package org.example.service.config.filters

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.util.*

data class UserAuthToken(
    private val login:String,
    private val authorities:List<GrantedAuthority>,
    val capabilities: LinkedHashMap<String, String>
) : Authentication {

    override fun getAuthorities() = authorities

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getName() = login

    override fun getCredentials() = null

    override fun getPrincipal() = this

    override fun isAuthenticated() = true

    override fun getDetails() = login
}

class JWTAuthenticationFilter(val utils:JWTUtils): GenericFilterBean() {

    val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    override fun doFilter(request: ServletRequest?,
                          response: ServletResponse?,
                          chain: FilterChain?) {

        val authHeader = (request as HttpServletRequest).getHeader("Authorization")

        if( authHeader != null && authHeader.startsWith("Bearer ") ) {
            val token = authHeader.substring(7) // Skip 7 characters for "Bearer "
            try {
                val claims = Jwts.parser().setSigningKey(utils.key).parseClaimsJws(token).body

                val capabilities = LinkedHashMap<String,String>()
                (claims["capabilities"] as ArrayList<LinkedHashMap<String, *>>).forEach {
                    val key = it["username"] as String
                    val operation = it["operation"] as String
                    capabilities[key] = operation
                }
                logger.info("Registered capabilities ${capabilities.toString()}")

                val authentication = UserAuthToken(
                    claims["username"] as String,
                    listOf(SimpleGrantedAuthority("ROLE_USER")),
                    capabilities
                )

                SecurityContextHolder.getContext().authentication = authentication

                utils.addResponseToken(authentication, response as HttpServletResponse)

                chain!!.doFilter(request, response)
            }
            catch( e: ExpiredJwtException) {
                (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
            }
        } else {
            chain!!.doFilter(request, response)
        }
    }
}