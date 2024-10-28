package com.job_portal.jobportal.controllers

import com.job_portal.jobportal.dtos.LoginRequestDto
import com.job_portal.jobportal.dtos.LoginResponseDto
import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.models.VerificationToken
import com.job_portal.jobportal.notifications.OtpEmailVerification
import com.job_portal.jobportal.repositories.RoleRepository
import com.job_portal.jobportal.repositories.UserRepository
import com.job_portal.jobportal.repositories.VerificationTokenRepository
import com.job_portal.jobportal.security.JWTGenerator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

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
    private val jwtGenerator: JWTGenerator,
    private val verificationTokenRepository: VerificationTokenRepository,
    private val emailVerificationService: OtpEmailVerification
) {


    @PostMapping("login")
    fun login(@RequestBody loginDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.email, loginDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtGenerator.generateToken(authentication)
        val user = userRepository.findByEmail(loginDto.email)
            ?: throw UsernameNotFoundException("User not found with username: ${loginDto.email}")

        return ResponseEntity(
            LoginResponseDto(
                token, loginDto.email, authentication.authorities.map { it.authority }, user.phoneNumber
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
                    roleRepository.findByName(roleName) ?: throw IllegalArgumentException("Role $roleName not found!")
                }.toSet()

                val user = User(
                    userName = registerDto.username,
                    email = registerDto.email,
                    phoneNumber = registerDto.phoneNumber,
                    userPassword = passwordEncoder.encode(registerDto.password),
                    roles = roles,
                    enabled = false // Set enabled to false initially
                )
                userRepository.save(user)

                // Generate verification token
                val otp = generateOtp(user.phoneNumber)
                val expiryDate = System.currentTimeMillis() + 5 * 60 * 1000 // 5 minutes in milliseconds
                val verificationToken = VerificationToken(token = otp, expiryDate = expiryDate, user = user)
                verificationTokenRepository.save(verificationToken)

                // Send verification email (implement email sending logic)
                emailVerificationService.sendVerificationEmail(user.email, otp)

                ResponseEntity(
                    "User registered successfully! Please check your email to verify your account.", HttpStatus.OK
                )
            }
        }
    }

    fun generateOtp(phoneNumber: String): String {
        val otp = (100000..999999).random().toString()
        // Send OTP to phoneNumber (implement OTP sending logic)
        return otp
    }


    @PostMapping("verify")
    fun verifyAccount(@RequestParam("otp") otp: String): ResponseEntity<String> {
        val verificationToken = verificationTokenRepository.findByToken(otp) ?: return ResponseEntity(
            "Invalid OTP!", HttpStatus.BAD_REQUEST
        )

        val user = verificationToken.user
        if (verificationToken.expiryDate < System.currentTimeMillis()) {
            return ResponseEntity("OTP expired!", HttpStatus.BAD_REQUEST)
        }

        user.enabled = true
        userRepository.save(user)
        verificationTokenRepository.delete(verificationToken)

        return ResponseEntity("Account verified successfully!", HttpStatus.OK)
    }
}
