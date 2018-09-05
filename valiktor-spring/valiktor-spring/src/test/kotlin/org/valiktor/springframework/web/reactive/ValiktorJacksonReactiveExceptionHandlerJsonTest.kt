package org.valiktor.springframework.web.reactive

import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.BodyInserters
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.Test

class ValiktorJacksonReactiveExceptionHandlerJsonTest {

    private val json = ValiktorReactiveExceptionHandlerFixture.JSON
    private val webClient = ValiktorReactiveExceptionHandlerFixture.webClient

    @BeforeTest
    fun setUp() {
        Locale.setDefault(Locale.ENGLISH)
    }

    @Test
    fun `should return 201`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromObject(json.payloadEmployeeValid()))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(LOCATION, "/employees/1")
            .expectBody().isEmpty
    }

    @Test
    fun `should return 422 with default locale`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromObject(json.payloadEmployeeNullName()))
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422NullName(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 with locale en`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .header(ACCEPT_LANGUAGE, "en")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromObject(json.payloadEmployeeNullName()))
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422NullName(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .header(ACCEPT_LANGUAGE, "pt-BR")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromObject(json.payloadEmployeeNullName()))
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422NullName(Locale("pt", "BR")))
    }
}