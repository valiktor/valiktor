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

package org.valiktor.springframework.http.webmvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri
import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.functions.isEmail
import org.valiktor.functions.isEqualTo
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.http.DefaultValiktorExceptionHandler
import org.valiktor.springframework.http.UnprocessableEntity
import org.valiktor.springframework.http.ValiktorExceptionHandler
import org.valiktor.validate
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import javax.servlet.http.HttpServletRequest

private enum class Status { ACTIVE, INACTIVE }

private data class Employee(
    val id: Int,
    val name: String,
    val email: String,
    val salary: BigDecimal,
    val dateOfBirth: Date,
    val status: Status
)

@RestController
@RequestMapping("/")
private class ValiktorTestController {

    @PostMapping("/employees", consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun test(@RequestBody employee: Employee): ResponseEntity<Void> {
        validate(employee) {
            validate(Employee::id).isEqualTo(1)
            validate(Employee::name).hasSize(min = 4)
            validate(Employee::email).isEmail()
            validate(Employee::salary).isBetween(start = "999.99".toBigDecimal(), end = "9999.99".toBigDecimal())
            validate(Employee::dateOfBirth).isEqualTo(Date.from(LocalDate.of(2001, Month.JANUARY, 1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()))
        }

        val location = fromCurrentRequestUri().path("/{id}").buildAndExpand(employee.id).toUri()
        return ResponseEntity.created(location).build()
    }
}

object ExceptionHandlerFixture {

    private val jsonMapper: ObjectMapper = ObjectMapper()
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd"))
        .registerModule(KotlinModule())

    private val xmlMapper: ObjectMapper = XmlMapper()
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd"))
        .registerModule(KotlinModule())

    private val valiktorExceptionHandler: ValiktorExceptionHandler<UnprocessableEntity> =
        DefaultValiktorExceptionHandler(config = ValiktorConfiguration())

    private val constraintViolationExceptionHandler = ConstraintViolationExceptionHandler(
        handler = valiktorExceptionHandler
    )
    private val invalidFormatExceptionHandler = InvalidFormatExceptionHandler(
        constraintViolationExceptionHandler = constraintViolationExceptionHandler
    )
    private val missingKotlinParameterExceptionHandler = MissingKotlinParameterExceptionHandler(
        constraintViolationExceptionHandler = constraintViolationExceptionHandler
    )

    val mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(ValiktorTestController())
        .setControllerAdvice(
            constraintViolationExceptionHandler,
            invalidFormatExceptionHandler,
            missingKotlinParameterExceptionHandler
        )
        .setMessageConverters(
            MappingJackson2HttpMessageConverter().also { it.objectMapper = this.jsonMapper },
            MappingJackson2XmlHttpMessageConverter().also { it.objectMapper = this.xmlMapper })
        .setLocaleResolver(object : AcceptHeaderLocaleResolver() {
            override fun resolveLocale(req: HttpServletRequest): Locale? =
                Locale.lookup(
                    Locale.LanguageRange.parse(req.getHeader(ACCEPT_LANGUAGE) ?: "en"),
                    listOf(Locale.ENGLISH, Locale("pt", "BR")))
        })
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

    object XML {

        fun payloadEmployeeValid() = this.javaClass.classLoader.getResource("payload/request/xml/employee_valid.xml").readText()
        fun payloadEmployeeInvalid() = this.javaClass.classLoader.getResource("payload/request/xml/employee_invalid.xml").readText()
        fun payloadEmployeeNullName() = this.javaClass.classLoader.getResource("payload/request/xml/employee_null_name.xml").readText()
        fun payloadEmployeeInvalidStatus() = this.javaClass.classLoader.getResource("payload/request/xml/employee_invalid_status.xml").readText()
        fun payloadEmployeeInvalidSalary() = this.javaClass.classLoader.getResource("payload/request/xml/employee_invalid_salary.xml").readText()

        fun payload422(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/xml/$locale/422.xml").readText()
        fun payload422NullName(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/xml/$locale/422_null_name.xml").readText()
        fun payload422InvalidStatus(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/xml/$locale/422_invalid_status.xml").readText()
        fun payload422InvalidSalary(locale: Locale) = this.javaClass.classLoader.getResource("payload/response/xml/$locale/422_invalid_salary.xml").readText()
    }
}
