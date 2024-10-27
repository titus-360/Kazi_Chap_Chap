package com.job_portal.jobportal.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


/**
 *
 * @author Titus Murithi Bundi
 */
@Component
class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    @Lazy private val userDetailsService: UserDetailsService  // Inject UserDetailsService
) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtAuthFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        logger.info("Filtering request: ${request.requestURI}")
        val token = getJwtFromRequest(request)
        if (token != null && jwtTokenProvider.validateJwtToken(token)) {
            val username = jwtTokenProvider.getUserNameFromJwtToken(token)
            if (username != null) {
                logger.info("Authenticating user: $username")
                // Fetch UserDetails using UserDetailsService
                val userDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) bearerToken.substring(7) else null
    }
}


