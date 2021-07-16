import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    id("org.asciidoctor.convert") version "1.5.9.2"

}

group = "com.digin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    // logger
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.8")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda")

    // r2dbc jasync-sql
    implementation("com.github.jasync-sql:jasync-r2dbc-mysql:1.1.6")

    // arrow kt
    implementation("io.arrow-kt:arrow-core:0.13.2")
    implementation("io.arrow-kt:arrow-fx-coroutines:0.13.2")
    implementation("io.arrow-kt:arrow-optics:0.13.2")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.1")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.1")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.1")

    // swagger
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.5.9")
    implementation("org.springdoc:springdoc-openapi-webflux-core:1.5.9")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.9")

    // restdoc
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
//    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc") // mock
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient") // webtestclient
    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("io.projectreactor:reactor-test")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.r2dbc:r2dbc-h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks {
    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(test)

        doLast {
            copy {
                from("build/asciidoc/html5")
                into("build/resources/main/static/docs")
            }
        }
    }

    bootJar {
        dependsOn("asciidoctor")
        from ("${asciidoctor.get().outputDir}/html5") {
            into("src/docs/asciidoc")
        }
    }
}