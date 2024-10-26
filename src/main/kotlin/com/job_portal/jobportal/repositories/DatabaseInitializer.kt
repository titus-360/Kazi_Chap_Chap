package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.models.Role
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

/**
 *
 * @author Titus Murithi Bundi
 */
@Component
class DatabaseInitializer(private val roleRepository: RoleRepository) {

    @PostConstruct
    fun init() {
        val roles = listOf("ROLE_USER", "ROLE_ADMIN", "ROLE_EMPLOYER")
        roles.forEach { roleName ->
            if (roleRepository.findByName(roleName) == null) {
                val role = Role(name = roleName)
                roleRepository.save(role)
            }
        }
    }
}
