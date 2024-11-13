package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.models.HouseType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
@Repository
interface HouseTypeRepository : JpaRepository<HouseType, Long> {
    fun findByCategory(category: String): HouseType?
}