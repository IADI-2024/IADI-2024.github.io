package org.example.userservice

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(var eurekaClient: EurekaClient) : HelloAPI {
    @Value("\${spring.application.name}")
    var appName: String? = null

    override fun hello(): String {
        return "Hello World! from ${eurekaClient.getApplication(appName).getName()}"
    }
}