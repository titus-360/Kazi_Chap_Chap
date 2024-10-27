package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.enums.Role
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import com.job_portal.jobportal.models.Role as RoleEntity

/**
 *
 * Author: Titus Murithi Bundi
 */
@Component
class DatabaseInitializer(private val roleRepository: RoleRepository) {

    @PostConstruct
    fun init() {
        Role.values().forEach { roleEnum ->
            if (roleRepository.findByName(roleEnum.name) == null) {
                val role = RoleEntity(name = roleEnum.name)
                roleRepository.save(role)
            }
        }
    }
}
