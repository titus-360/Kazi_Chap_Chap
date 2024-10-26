package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.repositories.RoleRepository
import com.job_portal.jobportal.repositories.UserRepository
import com.job_portal.jobportal.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


/**
 *
 * @author Titus Murithi Bundi
 */
@Service("userService")
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun registerUser(signUpRequestDto: SignUpRequestDto): User {
        if (userRepository.existsByUsername(signUpRequestDto.username)) {
            throw IllegalArgumentException("Username is already taken!")
        }

        val roleName = "${signUpRequestDto.roles.toUpperCase()}"
        logger.info("Searching for role: $roleName")

        val userRole = roleRepository.findByName(roleName) ?: throw RuntimeException("Role not found!")

        val user = User(
            username = signUpRequestDto.username,
            email = signUpRequestDto.email,
            password = bCryptPasswordEncoder.encode(signUpRequestDto.password),
            roles = setOf(userRole)
        )
        return userRepository.save(user)
    }

    override fun existsByUsername(signUpRequestDto: SignUpRequestDto): org.springframework.security.core.userdetails.User {
        TODO("Not yet implemented")
    }


    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}
