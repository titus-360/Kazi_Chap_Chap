package com.job_portal.jobportal.controllers

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.services.UserService
import org.springframework.http.ResponseEntity
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
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequestDto: SignUpRequestDto): ResponseEntity<User> {
        val user = userService.registerUser(signUpRequestDto)
        return ResponseEntity.ok(user)
    }
}
