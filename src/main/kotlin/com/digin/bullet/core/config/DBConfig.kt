package com.digin.bullet.core.config

import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
class DBConfig : AbstractR2dbcConfiguration() {
    @Value("\${spring.r2dbc.url}")
    private val dbUrl: String = ""

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val factory = ConnectionFactoryOptions.parse(dbUrl).mutate()
            .option(ConnectionFactoryOptions.DRIVER, "h2")
            .option(ConnectionFactoryOptions.PROTOCOL, "mysql")
            .build()
        println(factory)
        return ConnectionFactories.get(dbUrl)
    }
}