package com.digin.bullet.core.config

import com.digin.bullet.core.jwt.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.http.HttpStatus

import reactor.core.publisher.Mono

import org.springframework.security.config.web.server.ServerHttpSecurity.http




@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        jwtAuthenticationManager: ReactiveAuthenticationManager,
        jwtAuthenticationConverter: ServerAuthenticationConverter,
        securityContextRepository: SecurityContextRepository
    ): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(jwtAuthenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)

        return http
            .exceptionHandling()
                .authenticationEntryPoint { swe, e -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED } }
                .accessDeniedHandler { swe, e -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN } }
            .and()
            .csrf()
                .disable()
            .formLogin()
                .disable()
            .httpBasic()
                .disable()
            .authenticationManager(jwtAuthenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/auth/**").permitAll()
                .anyExchange().authenticated()
            .and()
            .build()
//        return http.authorizeExchange()
//            .pathMatchers("/auth/signup")
//                .permitAll()
//            .pathMatchers("/auth/signin")
//                .permitAll()
//            .pathMatchers("/user")
//                .authenticated()
//            .and()
//                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
//            .httpBasic()
//                .disable()
//            .csrf()
//                .disable()
//            .formLogin()
//                .disable()
//            .logout()
//                .disable()
//            .build()
    }
}