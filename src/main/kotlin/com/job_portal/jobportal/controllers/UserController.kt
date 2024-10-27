package com.job_portal.jobportal.controllers

import com.job_portal.jobportal.dtos.LoginRequestDto
import com.job_portal.jobportal.dtos.LoginResponseDto
import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.security.JwtTokenProvider
import com.job_portal.jobportal.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author Titus Murithi Bundi
 */
@RestController
@RequestMapping("/api/v1/auth")
class UserController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequestDto: SignUpRequestDto): ResponseEntity<User> {
        val user = userService.registerUser(signUpRequestDto)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/signin")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        return try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequestDto.username, loginRequestDto.password)
            )

            // Retrieve user details directly without casting
            val userDetails = userService.existsByUsername(loginRequestDto.username) as User

            // Generate JWT token
            val token = jwtTokenProvider.generateJwtToken(userDetails.username)

            val response = LoginResponseDto(
                token = token,
                username = userDetails.username,
                roles = userDetails.roles.map { it.name },
                phoneNumber = userDetails.phoneNumber
            )

            ResponseEntity.ok(response)
        } catch (e: AuthenticationException) {
            ResponseEntity.status(401).body(LoginResponseDto("", "", emptyList(), ""))
        }
    }


}
