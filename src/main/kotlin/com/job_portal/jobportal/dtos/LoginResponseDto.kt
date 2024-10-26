package com.job_portal.jobportal.dtos

/**
 *
 * @author Titus Murithi Bundi
 */
data class LoginResponseDto(
    val token: String,
    val username: String,
    val roles: List<String>,
    val phoneNumber: String?
)
