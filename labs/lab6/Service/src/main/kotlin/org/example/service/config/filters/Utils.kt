package org.example.service.config.filters

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import java.util.*

data class Capability(val username:String, val operation: String)

@Configuration
class JWTUtils(@Value("\${jwt.secret}") val jwtSecret: String,
               @Value("\${jwt.expiration}") val expiration: Long,
               @Value("\${jwt.subject}") val subject: String) {

    val key = Base64.getEncoder().encodeToString(jwtSecret.toByteArray())

    fun addResponseToken(authentication: Authentication, response: HttpServletResponse) {

        val claims = HashMap<String, Any?>()
        claims["username"] = authentication.name
        if (authentication.name == "admin")
            claims["capabilities"] = listOf(Capability("John", "ALL"))
        else
            claims["capabilities"] = emptyList<Capability>()

        val token = Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        response.addHeader("Authorization", "Bearer $token")
    }
}

