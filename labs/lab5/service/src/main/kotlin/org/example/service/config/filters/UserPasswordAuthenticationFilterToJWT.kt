/**
Copyright 2023 JCS

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.example.service.config.filters

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.nio.charset.StandardCharsets


data class UserLogin(var username:String, var password:String) {
    constructor() : this("","")
}

class UserPasswordAuthenticationFilterToJWT (
    defaultFilterProcessesUrl: String?,
    private val anAuthenticationManager: AuthenticationManager?
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(request: HttpServletRequest?,
                                       response: HttpServletResponse?): Authentication? {

        val body = String(request!!.inputStream.readAllBytes(), StandardCharsets.UTF_8)

        //getting user from request body
        val user = ObjectMapper().readValue(body, UserLogin::class.java)

        // perform the "normal" authentication
        val auth = anAuthenticationManager?.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))

        return if (auth?.isAuthenticated == true) { // this covers the null case, its a call to equals
            // Proceed with an authenticated user
            SecurityContextHolder.getContext().authentication = auth
            auth
        } else
            null
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          filterChain: FilterChain?,
                                          auth: Authentication) {

        // When returning from the Filter loop, add the token to the response
        addResponseToken(auth, response)
    }
}

