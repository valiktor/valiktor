/*
 * Copyright 2018 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import java.util.Locale
import kotlin.test.BeforeTest
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
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(EmployeeControllerFixture.validEmployee(id = id)))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(HttpHeaders.LOCATION, "http://localhost:${env.getProperty("local.server.port")}/employees/$id")
            .expectBody().isEmpty
    }

    @BeforeTest
    fun setUp() {
        Locale.setDefault(Locale.ENGLISH)
    }

    @Test
    fun `should return 422 with default locale`() {
        createValidEmployee(id = 1)

        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee(id = 1)))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody(UnprocessableEntity::class.java).isEqualTo<Nothing>(EmployeeControllerFixture.unprocessableEntityEn(id = 1))
    }

    @Test
    fun `should return 422 with locale en`() {
        createValidEmployee(id = 2)

        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee(id = 2)))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody(UnprocessableEntity::class.java).isEqualTo<Nothing>(EmployeeControllerFixture.unprocessableEntityEn(id = 2))
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        createValidEmployee(id = 3)

        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee(id = 3)))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody(UnprocessableEntity::class.java).isEqualTo<Nothing>(EmployeeControllerFixture.unprocessableEntityPtBr(id = 3))
    }
}