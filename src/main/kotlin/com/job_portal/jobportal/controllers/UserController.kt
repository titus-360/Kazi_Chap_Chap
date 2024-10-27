package com.job_portal.jobportal.controllers

import com.job_portal.jobportal.dtos.LoginRequestDto
import com.job_portal.jobportal.dtos.LoginResponseDto
import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.repositories.RoleRepository
import com.job_portal.jobportal.repositories.UserRepository
import com.job_portal.jobportal.security.JWTGenerator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
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
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JWTGenerator
) {


    @PostMapping("login")
    fun login(@RequestBody loginDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.email,
                loginDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtGenerator.generateToken(authentication)
        val user = userRepository.findByEmail(loginDto.email)
            ?: throw UsernameNotFoundException("User not found with username: ${loginDto.email}")

        return ResponseEntity(
            LoginResponseDto(
                token,
                loginDto.email,
                authentication.authorities.map { it.authority },
                user.phoneNumber
            ), HttpStatus.OK
        )
    }

    @PostMapping("register")
    fun register(@RequestBody registerDto: SignUpRequestDto): ResponseEntity<String> {
        return when {
            userRepository.existsByUserName(registerDto.username) -> {
                ResponseEntity("Username is taken!", HttpStatus.BAD_REQUEST)
            }

            userRepository.existsByEmail(registerDto.email) -> {
                ResponseEntity("Email is already in use!", HttpStatus.BAD_REQUEST)
            }

            else -> {
                val roles = registerDto.roles.split(",").map { roleName ->
                    roleRepository.findByName(roleName)
                        ?: throw IllegalArgumentException("Role $roleName not found!")
                }.toSet()

                val user = User(
                    userName = registerDto.username,
                    email = registerDto.email,
                    phoneNumber = registerDto.phoneNumber,
                    userPassword = passwordEncoder.encode(registerDto.password),
                    roles = roles
                )
                userRepository.save(user)
                ResponseEntity("User registered successfully!", HttpStatus.OK)
            }
        }
    }

}
