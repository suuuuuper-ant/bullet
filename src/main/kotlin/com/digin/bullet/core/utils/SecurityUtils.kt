package com.digin.bullet.core.utils

import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

val ENCODER: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

fun passwordEncode(password: String): String {
    return ENCODER.encode(password)
}

fun passwordMatch(rawPassword: String, encodedPassword: String): Boolean {
    return ENCODER.matches(rawPassword, encodedPassword)
}