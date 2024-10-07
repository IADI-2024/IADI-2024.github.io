package org.example.socialapp.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("OrderService")
interface OrderService {
    @GetMapping("/hello")
    fun hello(): String
}

