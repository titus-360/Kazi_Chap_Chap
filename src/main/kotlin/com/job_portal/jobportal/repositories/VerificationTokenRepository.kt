package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.models.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author Titus Murithi Bundi
 */
@Repository
interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): VerificationToken?
    fun deleteByToken(token: String): Boolean
}
