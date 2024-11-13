package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.models.Payment
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findByTenantId(tenantId: Long): List<Payment>
}