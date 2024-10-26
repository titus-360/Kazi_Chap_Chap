package com.job_portal.jobportal.dtos

import com.job_portal.jobportal.enums.Role


/**
 *
 * @author Titus Murithi Bundi
 */
data class SignUpRequestDto(
    val username: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val roles: String
)
