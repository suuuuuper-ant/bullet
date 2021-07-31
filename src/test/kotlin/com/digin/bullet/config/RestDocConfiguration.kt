package com.digin.bullet.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsWebTestClientConfigurationCustomizer

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentationConfigurer


//@TestConfiguration
class RestDocConfiguration {

//    @Bean
//    fun restDocsMockMvcConfigurationCustomizer(): RestDocsMockMvcConfigurationCustomizer? {
//        return RestDocsMockMvcConfigurationCustomizer { configurer: MockMvcRestDocumentationConfigurer ->
//            configurer.operationPreprocessors()
//                .withRequestDefaults(prettyPrint())
//                .withResponseDefaults(prettyPrint())
//        }
//    }
//
//    @Bean
//    fun restDocsWebTestClientConfigurationCustomizer(): RestDocsWebTestClientConfigurationCustomizer? {
//        return RestDocsWebTestClientConfigurationCustomizer { configurer: WebTestClientRestDocumentationConfigurer ->
//            configurer.operationPreprocessors()
//                .withRequestDefaults(prettyPrint())
//                .withResponseDefaults(prettyPrint())
//        }
//    }
}