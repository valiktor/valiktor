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

package org.valiktor.springframework.web.reactive

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.codec.support.DefaultServerCodecConfigurer
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.functions.isEmail
import org.valiktor.functions.isEqualTo
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.validate
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.util.Date
import java.util.Locale

enum class Status { ACTIVE, INACTIVE }

data class Employee(
    val id: Int,
    val name: String,
    val email: String,
    val salary: BigDecimal,
    val dateOfBirth: Date,
    val status: Status
)

@RestController
@RequestMapping("/")
class ValiktorTestController {

    @PostMapping("/employees", consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun test(req: ServerHttpRequest, @RequestBody employee: Mono<Employee>): Mono<ResponseEntity<Void>> =
        employee
            .map {
                validate(it) {
                    validate(Employee::id).isEqualTo(1)
                    validate(Employee::name).hasSize(min = 4)
                    validate(Employee::email).isEmail()
                    validate(Employee::salary).isBetween(start = "999.99".toBigDecimal(), end = "9999.99".toBigDecimal())
                    validate(Employee::dateOfBirth).isEqualTo(Date.from(LocalDate.of(2001, Month.JANUARY, 1)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))
                }
            }
            .map {
                ResponseEntity.created(UriComponentsBuilder.fromHttpRequest(req).path("/{id}").build(it.id)).build<Void>()
            }
}

object ReactiveExceptionHandlerFixture {

    private val jsonMapper: ObjectMapper = ObjectMapper()
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd"))
        .registerModule(KotlinModule())

    private val reactiveConstraintViolationExceptionHandler = ReactiveConstraintViolationExceptionHandler(
        config = ValiktorConfiguration(),
        codecConfigurer = DefaultServerCodecConfigurer().apply {
            this.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(jsonMapper, MediaType.APPLICATION_JSON))
            this.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(jsonMapper, MediaType.APPLICATION_JSON))
        }
    )

    private val reactiveMissingKotlinParameterExceptionHandler = ReactiveMissingKotlinParameterExceptionHandler()
    private val reactiveInvalidFormatExceptionHandler = ReactiveInvalidFormatExceptionHandler()

    val webClient: WebTestClient = WebTestClient
        .bindToController(ValiktorTestController())
        .controllerAdvice(
            reactiveConstraintViolationExceptionHandler,
            reactiveInvalidFormatExceptionHandler,
            reactiveMissingKotlinParameterExceptionHandler
        )
        .httpMessageCodecs {
            it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(jsonMapper, MediaType.APPLICATION_JSON))
            it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(jsonMapper, MediaType.APPLICATION_JSON))
        }
        .build()

    object JSON {

        fun payloadEmployeeValid() = this.javaClass.classLoader.getResource("payload/request/json/employee_valid.json").readText()
        fun payloadEmployeeInvalid() = this.javaClass.classLoader.getResource("payload/request/json/employee_invalid.json").readText()
        fun payloadEmployeeNullName() = this.javaClass.classLoader.getResource("payload/request/json/employee_null_name.json").readText()
        fun payloadEmployeeInvalidStatus() = this.javaClass.classLoader.getResource("payload/request/json/employee_invalid_status.json").readText()
        fun payloadEmployeeInvalidSalary() = this.javaClass.classLoader.getResource("payload/request/json/employee_invalid_salary.json").readText()

        fun payload422(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/json/$locale/422.json").readText()
        fun payload422NullName(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/json/$locale/422_null_name.json").readText()
        fun payload422InvalidStatus(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/json/$locale/422_invalid_status.json").readText()
        fun payload422InvalidSalary(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/json/$locale/422_invalid_salary.json").readText()
    }
}
