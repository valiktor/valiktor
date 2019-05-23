/*
 * Copyright 2018-2019 https://www.valiktor.org
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
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
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

    @BeforeTest
    fun setUp() {
        Locale.setDefault(Locale.ENGLISH)
    }

    @Test
    fun `should return 201 with location`() {
        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(EmployeeControllerFixture.validEmployee))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(HttpHeaders.LOCATION, "http://localhost:${env.getProperty("local.server.port")}/employees/111.111.111-11")
            .expectBody().isEmpty
    }

    @Test
    fun `should return 422 with default locale`() {
        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody<UnprocessableEntity>().isEqualTo(EmployeeControllerFixture.unprocessableEntity.getValue(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 with locale en`() {
        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody<UnprocessableEntity>().isEqualTo(EmployeeControllerFixture.unprocessableEntity.getValue(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        webClient
            .post()
            .uri("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
            .body(BodyInserters.fromObject(EmployeeControllerFixture.invalidEmployee))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            .expectBody<UnprocessableEntity>().isEqualTo(EmployeeControllerFixture.unprocessableEntity.getValue(Locale("pt", "BR")))
    }
}