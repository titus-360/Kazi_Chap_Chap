package com.job_portal.jobportal.security

import com.job_portal.jobportal.services.impl.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 *
 * Author: Titus Murithi Bundi
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: CustomUserDetailsService,
    private val authEntryPoint: JwtAuthEntryPoint,
    private val tokenGenerator: JWTGenerator
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .exceptionHandling { it.authenticationEntryPoint(authEntryPoint) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeRequests {
                it.requestMatchers("/api/v1/auth/**").permitAll()
                it.anyRequest().authenticated()
            }
            .httpBasic { }
        http.addFilterBefore(
            jwtAuthenticationFilter(tokenGenerator, userDetailsService),
            UsernamePasswordAuthenticationFilter::class.java
        )
        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtAuthenticationFilter(
        tokenGenerator: JWTGenerator,
        customUserDetailsService: CustomUserDetailsService
    ): JWTAuthenticationFilter {
        return JWTAuthenticationFilter(tokenGenerator, customUserDetailsService)
    }
}

