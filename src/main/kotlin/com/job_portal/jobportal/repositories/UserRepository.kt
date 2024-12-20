package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Titus Murithi Bundi
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByUserName(userName: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
