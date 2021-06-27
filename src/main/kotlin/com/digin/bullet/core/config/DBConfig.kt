package com.digin.bullet.core.config

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.sql.db.mysql.util.URLParser
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import com.github.jasync.sql.db.Configuration as JasyncDBConfiguration

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
@EnableConfigurationProperties(DBProperties::class)
class DBConfig(private val dbProperties: DBProperties) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return JasyncConnectionFactory(createMySqlConnectionFactory())
    }

    private fun createMySqlConnectionFactory(): MySQLConnectionFactory {
        return MySQLConnectionFactory(getConnectionConfiguration())
    }

    private fun getConnectionConfiguration(): JasyncDBConfiguration {
        return URLParser.DEFAULT
            .copy(host = dbProperties.host,
                database = dbProperties.database,
                username = dbProperties.username,
                password = dbProperties.password,
                port = 3306,
                connectionTimeout = dbProperties.connectionTimeout)
    }
}

@ConstructorBinding
@ConfigurationProperties(prefix = "bullet.database")
data class DBProperties(val host: String,
                        val database: String,
                        val username: String,
                        val password: String,
                        val connectionTimeout: Int,
                        val maxConnection: Int)