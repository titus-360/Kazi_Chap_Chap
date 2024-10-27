package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.models.Role
import com.job_portal.jobportal.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 *
 * @author Titus Murithi Bundi
 */
@Service
class CustomUserDetailsService @Autowired constructor(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User Not Found with username: $email")
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            mapRolesToAuthorities(user.roles)
        )
    }

    private fun mapRolesToAuthorities(roles: Set<Role>): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }
}
