package com.digin.bullet.core.jwt

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors
import io.jsonwebtoken.Claims
import mu.KotlinLogging
import java.util.*


@Component
class JwtAuthenticationManager(private val jwtUtil: JWTUtil) : ReactiveAuthenticationManager {
    private val logger = KotlinLogging.logger {}

//    override fun authenticate(authentication: Authentication): Mono<Authentication> {
//        return Mono.just(authentication)
//            .map { jwtSigner.validateJwt(it.credentials as String) }
//            .onErrorResume { Mono.empty() }
//            .map { jws ->
//                UsernamePasswordAuthenticationToken(
//                    jws.body.subject,
//                    authentication.credentials as String,
//                    mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
//                )
//            }
//    }

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()

        return Mono.just(jwtUtil.validateToken(authToken))
            .filter { valid -> valid }
            .switchIfEmpty(Mono.empty())
            .map {
                val claims = jwtUtil.getAllClaimsFromToken(authToken)
                val role: String = claims["role"] as String
                val id = claims["id"] as String
                val email = jwtUtil.getEmailFromToken(authToken)
                logger.info { "authenticate $claims" }
                UsernamePasswordAuthenticationToken(
                    id,
//                    mapOf("email" to email, "id" to id),
                    authToken,
                    listOf(SimpleGrantedAuthority(role))
                )
            }
    }
}