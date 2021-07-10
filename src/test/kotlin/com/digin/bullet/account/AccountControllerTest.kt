package com.digin.bullet.account

import com.digin.bullet.account.handler.AccountHandler
import com.digin.bullet.account.repository.AccountRepository
import com.digin.bullet.account.router.AccountRouter
import com.digin.bullet.account.router.AuthRouter
import com.digin.bullet.account.service.AccountService
import com.digin.bullet.config.RestDocConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.context.ApplicationContext





//@ActiveProfiles("local")
@ContextConfiguration(classes = [AccountRouter::class, AccountHandler:: class])
@WebFluxTest(excludeAutoConfiguration = [ReactiveSecurityAutoConfiguration::class])
@Import(RestDocConfiguration::class)
@AutoConfigureRestDocs
class AccountControllerTest {
//    @Autowired
//    private lateinit var context: ApplicationContext
//    @Autowired
//    private lateinit var webTestClient: WebTestClient
//
//    @Autowired
//    private lateinit var objectMapper: ObjectMapper
//
//    @BeforeAll
//    fun setUp() {
//        webTestClient = WebTestClient.bindToRouterFunction().build()
//    }
//
//    @Test
//    @DisplayName("showOne1")
//    fun showOne() {
////        val clientRequest = ClientRequest("hi")
//        val response = webTestClient
//            .get()
//            .uri("/accounts")
//            .header("X-AUTH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjQ4IiwibmFtZSI6ImFhIiwicm9sZSI6IlVTRVIiLCJzdWIiOiJzdHJpbmcxMTIxIiwiaWF0IjoxNjI1OTQwNzY1LCJleHAiOjE2MjU5NTA3NjV9.5_n87MxNj1VZHsm0Yl46PZsYZDJy0o1gqETWJ-ZKdxg")
////            .accept(MediaType.APPLICATION_JSON)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody()
//            .consumeWith(
//                document("index")
//            )
////            .consumeWith(
////                document(
////                    "web-test-page",
////                    requestHeaders(
//////                        headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON_VALUE),
////                        headerWithName(HttpHeaders.ACCEPT).description(MediaType.APPLICATION_JSON_VALUE)
////                    ),
////                    requestFields(
//////                        fieldWithPath("name").optional().description("Name of Person")
////                    ),
////                    responseHeaders( // RequestHeader 작성
////                        headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON_VALUE)
////                    ),
////                    responseFields( // ResponseField 작성
////                        fieldWithPath("name").type(JsonFieldType.STRING).description("Name of Person"),
////                    )
////                )
////            )
//            .returnResult()
//            .responseBody
//    }

}