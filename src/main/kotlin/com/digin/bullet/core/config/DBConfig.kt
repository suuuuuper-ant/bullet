package com.digin.bullet.core.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.mariadb.r2dbc.MariadbConnectionConfiguration
import org.mariadb.r2dbc.MariadbConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.nio.charset.StandardCharsets


@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
class DBConfig {
    @Value("\${spring.r2dbc.url}")
    private val dbUrl: String = ""

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val config = MariadbConnectionConfiguration.builder()
            .host("")
            .port(3306)
            .username("admin")
            .password("")
            .database("dev-digin")
            .build()
        return MariadbConnectionFactory(config)

    }
}