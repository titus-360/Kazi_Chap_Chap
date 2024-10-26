package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.repositories.UserRepository
import com.job_portal.jobportal.services.UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 *
 * @author Titus Murithi Bundi
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserService {


    override fun existsByUsername(signUpRequestDto: SignUpRequestDto): org.springframework.security.core.userdetails.User {
        val user = userRepository.findByUsername(signUpRequestDto.username)
            ?: throw UsernameNotFoundException("User not found with username: ${signUpRequestDto.username}")

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            user.roles.map { SimpleGrantedAuthority(it.name) }
        )

    }

    override fun registerUser(signUpRequestDto: SignUpRequestDto): User {
        TODO("Not yet implemented")
    }


    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}
