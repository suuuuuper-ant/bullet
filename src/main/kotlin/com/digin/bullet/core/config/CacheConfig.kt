package com.digin.bullet.core.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.cache.ehcache.EhCacheFactoryBean
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.kotlin.coroutine.EnableCoroutine
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableCaching
@EnableCoroutine
@EnableScheduling
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager = EhCacheCacheManager(ehCacheManager().`object`!!)


    @Bean
    fun ehCacheManager(): EhCacheManagerFactoryBean {
        return EhCacheManagerFactoryBean().apply {
            setConfigLocation(ClassPathResource("ehcache.xml"))
            setShared(true)
        }
    }
}