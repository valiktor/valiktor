/*
 * Copyright 2018-2020 https://www.valiktor.org
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

package org.valiktor.springframework.web.reactive

import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.MediaType.APPLICATION_JSON
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.Test

class ReactiveInvalidFormatExceptionHandlerJsonTest {

    private val json = ReactiveExceptionHandlerFixture.JSON
    private val webClient = ReactiveExceptionHandlerFixture.webClient

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
            .bodyValue(json.payloadEmployeeValid())
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(LOCATION, "/employees/1")
            .expectBody().isEmpty
    }

    @Test
    fun `should return 422 (In) with default locale`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .bodyValue(json.payloadEmployeeInvalidStatus())
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422InvalidStatus(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 (In) with locale en`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .header(ACCEPT_LANGUAGE, "en")
            .contentType(APPLICATION_JSON)
            .bodyValue(json.payloadEmployeeInvalidStatus())
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422InvalidStatus(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 (In) with locale pt_BR`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .header(ACCEPT_LANGUAGE, "pt-BR")
            .contentType(APPLICATION_JSON)
            .bodyValue(json.payloadEmployeeInvalidStatus())
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422InvalidStatus(Locale("pt", "BR")))
    }

    @Test
    fun `should return 422 (Valid) with default locale`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .bodyValue(json.payloadEmployeeInvalidSalary())
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422InvalidSalary(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 (Valid) with locale en`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .header(ACCEPT_LANGUAGE, "en")
            .contentType(APPLICATION_JSON)
            .bodyValue(json.payloadEmployeeInvalidSalary())
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422InvalidSalary(Locale.ENGLISH))
    }

    @Test
    fun `should return 422 (Valid) with locale pt_BR`() {
        webClient
            .post()
            .uri("/employees")
            .accept(APPLICATION_JSON)
            .header(ACCEPT_LANGUAGE, "pt-BR")
            .contentType(APPLICATION_JSON)
            .bodyValue(json.payloadEmployeeInvalidSalary())
            .exchange()
            .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
            .expectHeader().contentTypeCompatibleWith(APPLICATION_JSON)
            .expectBody().json(json.payload422InvalidSalary(Locale("pt", "BR")))
    }
}
