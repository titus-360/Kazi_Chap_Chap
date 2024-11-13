package com.job_portal.jobportal.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Author: Titus Murithi Bundi
 * Date:11/13/24
 */
@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}