package com.job_portal.jobportal.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 *
 * Author: Titus Murithi Bundi
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: UserDetailsService) {

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        return JwtTokenProvider()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtAuthFilter: JwtAuthFilter): SecurityFilterChain {
        http.cors { it.disable() }.csrf { it.disable() }.sessionManagement { sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.authorizeHttpRequests { authorizeRequests ->
            authorizeRequests.requestMatchers("/api/v1/auth/**").permitAll().requestMatchers("/api/v1/user/**")
                .hasRole("USER").requestMatchers("/api/v1/employer/**").hasRole("EMPLOYER")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN").anyRequest().authenticated()
        }
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}

