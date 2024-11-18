package org.example.app.service

import feign.Logger
import feign.RequestInterceptor
import feign.Response
import feign.codec.ErrorDecoder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.example.app.data.FriendRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*


data class ResourceDTO(val owner:String, val data:String)

data class ResourceWIdDTO(val id:Long, val owner:String, val data:String)

// This is a dedicated client, with a dedicated configuration class
//
//, fallbackFactory = ResourceAPIFallbackFactory::class
@FeignClient(name = "service",
             configuration = [ResourceAPIConfig::class])
interface ResourceAPI {

    @GetMapping("/resources")
    fun getAll():List<ResourceWIdDTO>

    @PostMapping("/resources")
    fun createResource(@RequestBody resource: ResourceDTO):Long

    @GetMapping("/resources/{id}")
    fun getOne(@PathVariable id:Long): ResourceWIdDTO
}



data class Capability(val username:String, val operation: String)

@Configuration
class ResourceAPIConfig(
    @Value("\${jwt.secret}") val jwtSecret: String,
    @Value("\${jwt.expiration}") val expiration: Long,
    @Value("\${jwt.subject}") val subject: String,
    val friends: FriendRepository) {

    val logger: org.slf4j.Logger = LoggerFactory.getLogger(ResourceAPIConfig::class.java)

    @Bean
    fun resourceAPIInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            val resourceToken = getResourceToken()
            template.header("Authorization", "Bearer ${resourceToken}")
        }
    }

    // This application works like the authorization server, it knows the capabilities to pass to others

    private fun getCapabilities(username:String) : List<Capability> {
        val capabilities = mutableListOf<Capability>()

        capabilities.add(Capability(username,"ALL"))
        friends.findByUsername(username).forEach {
            capabilities.add(Capability(it.friend, "READ"))
        }
        logger.info("Adding capabilities: ${capabilities.toString()}")
        return capabilities
    }

    private fun getResourceToken():String {
        val claims = HashMap<String, Any?>()

        // If needed include the username
        //val authentication = SecurityContextHolder.getContext().authentication
        //val username = authentication.username

        claims["username"] = "John"
        claims["capabilities"] = getCapabilities("John")

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

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return CustomErrorDecoder()
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL // Options: NONE, BASIC, HEADERS, FULL
    }

}

// Error handling config

class NotFoundException: Exception()
class BadRequestException: Exception()
class ForbiddenException: Exception()
class UnauthorizedException: Exception()

class CustomErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String?, response: Response): Exception {
        return when (response.status()) {
            400 -> Exception("Bad Request", BadRequestException())
            401 -> Exception("Unauthorized", UnauthorizedException())
            403 -> Exception("Forbidden", ForbiddenException())
            404 -> Exception("Not Found", NotFoundException())
            500 -> Exception("Server Error")
            else -> Exception("Dunno")
        }
    }
}

// Alternative methods include a fallback factory that need to be enabled in the configuration

@Component
class ResourceFallbackFactory : FallbackFactory<ResourceAPI> {
    override fun create(cause: Throwable?): ResourceAPI {
        return object : ResourceAPI {
            override fun getAll(): List<ResourceWIdDTO> {
                throw cause!!
            }

            override fun createResource(resource: ResourceDTO): Long {
                throw cause!!
            }

            override fun getOne(id: Long): ResourceWIdDTO {
                println(cause!!.message)
                throw cause!!
            }
        }
    }
}


