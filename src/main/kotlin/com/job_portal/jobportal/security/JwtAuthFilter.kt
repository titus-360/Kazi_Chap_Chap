package com.job_portal.jobportal.security

import com.job_portal.jobportal.dtos.SignUpRequestDto
import com.job_portal.jobportal.services.impl.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


/**
 *
 * @author Titus Murithi Bundi
 */
class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = getJwtFromRequest(request)
        if (token != null && jwtTokenProvider.validateJwtToken(token)) {
            val username = jwtTokenProvider.getUserNameFromJwtToken(token)
            val signUpRequestDto = username?.let {
                SignUpRequestDto(
                    username = it,
                    email = "",
                    phoneNumber = "",
                    password = "",
                    roles = ""
                )
            }
            val userDetails = signUpRequestDto?.let { userDetailsService.existsByUsername(it) }
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails?.authorities)
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) bearerToken.substring(7) else null
    }
}
