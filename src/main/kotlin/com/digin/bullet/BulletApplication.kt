package com.digin.bullet

import com.digin.bullet.account.repository.AccountRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.kotlin.coroutine.EnableCoroutine

@SpringBootApplication
class BulletApplication {

//    @Bean
//    fun run(accountRepository: AccountRepository): ApplicationRunner {
//        return ApplicationRunner {
//            accountRepository.findAll().subscribe { println(it) }
//        }
//    }
}

fun main(args: Array<String>) {
    runApplication<BulletApplication>(*args)
}
