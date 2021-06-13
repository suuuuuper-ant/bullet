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





@Component
class JwtAuthenticationManager(private val jwtSigner: JwtSigner, private val jwtUtil: JWTUtil) : ReactiveAuthenticationManager {
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
        val username = jwtUtil.getUsernameFromToken(authToken)
        return Mono.just(jwtUtil.validateToken(authToken))
            .filter { valid -> valid }
            .switchIfEmpty(Mono.empty())
            .map {
                val claims = jwtUtil.getAllClaimsFromToken(authToken)
                val rolesMap: List<String> = claims["role"] as List<String>
                UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    rolesMap.map { role -> SimpleGrantedAuthority(role) }
                )
            }
    }
}