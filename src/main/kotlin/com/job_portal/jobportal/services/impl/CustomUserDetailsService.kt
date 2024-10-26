package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.repositories.UserRepository
import com.job_portal.jobportal.services.UserService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 *
 * @author Titus Murithi Bundi
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserService {


    override fun existsByUsername(username : String): User {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: ${username}")

        return user
    }

    override fun registerUser(signUpRequestDto: SignUpRequestDto): User {
        TODO("Not yet implemented")
    }


    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}
