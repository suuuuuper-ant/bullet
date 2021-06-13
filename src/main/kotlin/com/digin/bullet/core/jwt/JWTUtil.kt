package com.digin.bullet.core.jwt

import com.digin.bullet.account.model.Account
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import io.jsonwebtoken.Jwts

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.SignatureAlgorithm
import java.lang.Exception
import java.security.SignatureException
import java.util.*
import javax.annotation.PostConstruct


@Component
class JWTUtil {

    private val secret: String = "aasdfasdfasdfasdfasdfasdfasdfasdf"

    private val expirationTime: String = "10000"

    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray())

//    private lateinit var key: Key

//    @PostConstruct
//    fun init() {
//        key = Keys.hmacShaKeyFor(secret.toByteArray())
//    }


    fun getAllClaimsFromToken(token: String = ""): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getAllClaimsFromToken(token).expiration
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(account: Account): String {
        val claims: MutableMap<String, List<String>> = HashMap()
        claims["role"] = account.roles
        return doGenerateToken(claims, account.name)
    }

    private fun doGenerateToken(claims: Map<String, List<String>>, username: String): String {
        val expirationTimeLong = expirationTime.toLong() //in second
        val createdDate = Date()
        val expirationDate = Date(createdDate.time + expirationTimeLong * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            !isTokenExpired(token)
        } catch (e: Exception) {
            false
        }
    }
}