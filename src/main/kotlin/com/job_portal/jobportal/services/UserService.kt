package com.job_portal.jobportal.services

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User

/**
 *
 * @author Titus Murithi Bundi
 */

interface UserService {

    fun registerUser(signUpRequestDto: SignUpRequestDto): User
    fun existsByUsername(signUpRequestDto: SignUpRequestDto): org.springframework.security.core.userdetails.User
    fun existsByEmail(email: String): Boolean
}
