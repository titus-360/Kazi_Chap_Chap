package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.models.Role
import com.job_portal.jobportal.models.User
import com.job_portal.jobportal.repositories.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 *
 * @author Titus Murithi Bundi
 */
class CustomUserDetailsServiceTest {

    private val userRepository = mock(UserRepository::class.java)
    private val customUserDetailsService = CustomUserDetailsService(userRepository)

    @Test
    fun loadUserByUsername_UserExists_ReturnsUserDetails() {
        val roles = setOf(Role(1L, "ROLE_USER"))
        val user = User(2L, "test@example.com", "password", roles.toString())
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(user)

        val userDetails = customUserDetailsService.loadUserByUsername("test@example.com")

        assertEquals("test@example.com", userDetails.username)
        assertEquals("password", userDetails.password)
        assertTrue(userDetails.authorities.contains(SimpleGrantedAuthority("ROLE_USER")))
    }

    @Test
    fun loadUserByUsername_UserDoesNotExist_ThrowsUsernameNotFoundException() {
        `when`(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null)

        assertThrows<UsernameNotFoundException> {
            customUserDetailsService.loadUserByUsername("nonexistent@example.com")
        }
    }

    @Test
    fun loadUserByUsername_UserHasMultipleRoles_ReturnsUserDetailsWithAllRoles() {
        val roles = setOf(Role(1L, "ROLE_USER"), Role(1L, "ROLE_ADMIN"))
        val user = User(1L , "admin@example.com", "adminpassword", roles.toString())
        `when`(userRepository.findByEmail("admin@example.com")).thenReturn(user)

        val userDetails = customUserDetailsService.loadUserByUsername("admin@example.com")

        assertEquals("admin@example.com", userDetails.username)
        assertEquals("adminpassword", userDetails.password)
        assertTrue(userDetails.authorities.contains(SimpleGrantedAuthority("ROLE_USER")))
        assertTrue(userDetails.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")))
    }


}
