package com.job_portal.jobportal.services.impl

import com.job_portal.jobportal.dtos.HouseDto
import com.job_portal.jobportal.models.House
import com.job_portal.jobportal.repositories.HouseRepository
import com.job_portal.jobportal.services.HouseService
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
@Service("houseService")
class HouseServiceImpl(private val houseRepository: HouseRepository, private val modelMapper: ModelMapper) :
    HouseService {


    override fun saveNewHouse(houseDto: HouseDto) {
        val house: House = modelMapper.map(houseDto, House::class.java)
        houseRepository.save(house)

    }


}