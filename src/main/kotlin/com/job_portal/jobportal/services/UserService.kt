package com.job_portal.jobportal.services

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 * @author Titus Murithi Bundi
 */

interface UserService {

    fun registerUser(signUpRequestDto: SignUpRequestDto): User
    fun existsByUsername(username: String): UserDetails
    fun existsByEmail(email: String): Boolean
}
