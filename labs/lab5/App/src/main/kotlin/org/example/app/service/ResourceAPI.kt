package org.example.app.service

import feign.RequestInterceptor
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*
import java.util.*

data class ResourceDTO(val data:String)

data class ResourceWIdDTO(val id:Long, val data:String)

@FeignClient(name = "service", configuration = [ResourceAPIConfig::class])
interface ResourceAPI {

    @GetMapping("/resources")
    fun getAll():List<ResourceWIdDTO>

    @PostMapping("/resources")
    fun createResource(@RequestBody resource: ResourceDTO):Long

    @GetMapping("/resources/{id}")
    fun getOne(@PathVariable id:Long): ResourceWIdDTO
}

@Configuration
class ResourceAPIConfig(
    @Value("\${jwt.secret}") val jwtSecret: String,
    @Value("\${jwt.expiration}") val expiration: Long,
    @Value("\${jwt.subject}") val subject: String) {

    @Bean
    fun resourceAPIInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            val resourceToken = getResourceToken()
            template.header("Authorization", "Bearer ${resourceToken}")
        }
    }

    private fun getResourceToken():String {
        val claims = HashMap<String, Any?>()
        claims["username"] = "admin"

        val key = Base64.getEncoder().encodeToString(jwtSecret.toByteArray())
        val token = Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        return token
    }
}