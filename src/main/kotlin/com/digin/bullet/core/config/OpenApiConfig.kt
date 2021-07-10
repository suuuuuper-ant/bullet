package com.digin.bullet.core.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig: OpenAPI() {
    @Value("\${spring.profiles.active}")
    private val profile: String? = null

    @Bean
    fun openAPI(): OpenAPI {
        val SECURITY_SCHEME_KEY = "auth"
        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.APIKEY).bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER).name("X-AUTH-TOKEN")

        val securityRequirement: SecurityRequirement = SecurityRequirement().addList(SECURITY_SCHEME_KEY)

        return OpenAPI()
            .components(Components().addSecuritySchemes(SECURITY_SCHEME_KEY, securityScheme))
            .security(listOf(securityRequirement))
            .addServersItem(Server().url(if (profile.equals("local")) "http://localhost:8080" else "http://3.35.143.195"))
            .info(
                Info()
                    .title("DIGIN")
                    .description("DIGIN REST API")
                    .version("1.0")
            )
    }
}