package com.job_portal.jobportal.services

import com.job_portal.jobportal.dtos.HouseDto

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
interface HouseService {

    fun saveNewHouse(houseDto: HouseDto)
}