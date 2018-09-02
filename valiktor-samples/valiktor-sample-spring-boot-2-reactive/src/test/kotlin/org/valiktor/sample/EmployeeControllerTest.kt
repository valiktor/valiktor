package org.valiktor.sample

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.valiktor.springframework.web.payload.UnprocessableEntity
import kotlin.test.Test

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class EmployeeControllerTest {

    @Autowired
    private lateinit var env: Environment

    @Autowired
    private lateinit var webClient: WebTestClient

    private fun createValidEmployee(id: Int) {
        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(EmployeeControllerFixture.validEmployee(id = id)))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(HttpHeaders.LOCATION, "http://localhost:${env.getProperty("local.server.port")}/employees/$id")
            .expectBody().isEmpty
    }

    @Test
    fun `should return 422 with locale en`() {
        createValidEmployee(id = 1)

        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee(id = 1)))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody(UnprocessableEntity::class.java).isEqualTo<Nothing>(EmployeeControllerFixture.unprocessableEntityEn(id = 1))
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        createValidEmployee(id = 2)

        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee(id = 2)))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody(UnprocessableEntity::class.java).isEqualTo<Nothing>(EmployeeControllerFixture.unprocessableEntityPtBr(id = 2))
    }
}