package com.job_portal.jobportal.security

import com.job_portal.jobportal.services.impl.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


/**
 *
 * @author Titus Murithi Bundi
 */
@Component
class JWTAuthenticationFilter @Autowired constructor(
    private val tokenGenerator: JWTGenerator, private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val token = getJWTFromRequest(request)
        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
            val username = tokenGenerator.getUsernameFromJWT(token)
            val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(username)
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(tokenGenerator.key)
                .build()
                .parseClaimsJws(token)
                .body
            val authorities = (claims["roles"] as List<*>).map { SimpleGrantedAuthority(it as String) }

            val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, authorities)
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}




