package com.job_portal.jobportal.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

/**
 *
 * @author Titus Murithi Bundi
 */

@Component
class JWTGenerator {
    // private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val currentDate = Date()
        val expireDate = Date(currentDate.time + SecurityConstants.JWT_EXPIRATION)
        val roles = authentication.authorities.map { it.authority }

        val token = Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(expireDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        println("New token :")
        println(token)
        return token
    }

    fun getUsernameFromJWT(token: String?): String {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            throw AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", ex.fillInStackTrace())
        }
    }
}
