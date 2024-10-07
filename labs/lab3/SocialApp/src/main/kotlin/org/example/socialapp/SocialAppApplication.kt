package org.example.socialapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SocialAppApplication

fun main(args: Array<String>) {
    runApplication<SocialAppApplication>(*args)
}
