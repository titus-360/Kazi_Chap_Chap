package com.job_portal.jobportal.repositories

import com.job_portal.jobportal.models.House
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
@Repository
interface HouseRepository : JpaRepository<House, Long> {
    fun findByHouseTypeId(houseTypeId: Long): List<House>

}