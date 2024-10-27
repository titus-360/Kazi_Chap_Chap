package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.repositories.RoleRepository
import com.job_portal.jobportal.repositories.UserRepository
import com.job_portal.jobportal.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
) : UserService, UserDetailsService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun registerUser(signUpRequestDto: SignUpRequestDto): User {
        if (userRepository.existsByUserName(signUpRequestDto.username)) {
            throw IllegalArgumentException("Username is already taken!")
        }

        val roleName = "${signUpRequestDto.roles.toUpperCase()}"
        logger.info("Searching for role: $roleName")

        val userRole = roleRepository.findByName(roleName) ?: throw RuntimeException("Role not found!")

        val user = User(
            userName = signUpRequestDto.username,
            email = signUpRequestDto.email,
            userPassword = bCryptPasswordEncoder.encode(signUpRequestDto.password),
            roles = setOf(userRole)
        )
        return userRepository.save(user)
    }

    override fun existsByUsername(username: String): UserDetails {
        return loadUserByUsername(username)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUserName(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return org.springframework.security.core.userdetails.User(
            user.userName,
            user.userPassword,
            user.roles.map { SimpleGrantedAuthority(it.name) }
        )
    }


    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}
