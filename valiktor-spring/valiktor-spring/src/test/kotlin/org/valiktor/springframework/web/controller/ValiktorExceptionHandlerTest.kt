package org.valiktor.springframework.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
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
import org.valiktor.validate
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.test.Test

data class Employee(val id: Int,
                    val name: String,
                    val email: String,
                    val salary: BigDecimal,
                    val dateOfBirth: Date)

@RestController
@RequestMapping("/")
class ValiktorTestController {

    @PostMapping("/employees", consumes = [APPLICATION_JSON_UTF8_VALUE], produces = [APPLICATION_JSON_UTF8_VALUE])
    fun test(@RequestBody employee: Employee): ResponseEntity<Void> {
        validate(employee) {
            validate(Employee::id).isEqualTo(1)
            validate(Employee::name).hasSize(min = 4)
            validate(Employee::email).isEmail()
            validate(Employee::salary).isBetween(start = "999.99".toBigDecimal(), end = "9999.99".toBigDecimal())
            validate(Employee::dateOfBirth).isEqualTo(Date.from(LocalDate.of(2001, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
        }

        val location = fromCurrentRequestUri().path("/{id}").buildAndExpand(employee.id).toUri()
        return ResponseEntity.created(location).build()
    }
}

class ValiktorExceptionHandlerTest {

    private val objectMapper = ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .registerModule(KotlinModule())

    private val mockMvc: MockMvc = MockMvcBuilders
            .standaloneSetup(ValiktorTestController())
            .setControllerAdvice(ValiktorExceptionHandler(config = ValiktorConfiguration()))
            .setMessageConverters(MappingJackson2HttpMessageConverter().also {
                it.objectMapper = this.objectMapper
            })
            .setLocaleResolver(object : AcceptHeaderLocaleResolver() {
                override fun resolveLocale(req: HttpServletRequest): Locale =
                        Locale.lookup(
                                Locale.LanguageRange.parse(req.getHeader(ACCEPT_LANGUAGE)),
                                listOf(Locale.ENGLISH, Locale("pt", "BR")))
            })
            .build()

    private val validEmployee = Employee(
            id = 1,
            name = "John",
            email = "john@john.com",
            salary = "1000".toBigDecimal(),
            dateOfBirth = Date.from(LocalDate.of(2001, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()))

    private val invalidEmployee = Employee(
            id = 2,
            name = "Jon",
            email = "jon",
            salary = "10000".toBigDecimal(),
            dateOfBirth = Date.from(LocalDate.of(2001, Month.JANUARY, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()))

    @Test
    fun `should return 201`() {
        mockMvc
                .perform(post("/employees")
                        .accept(APPLICATION_JSON)
                        .header(ACCEPT_LANGUAGE, "en")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(validEmployee)))
                .andExpect(status().isCreated)
                .andExpect(header().string(LOCATION, "http://localhost/employees/1"))
                .andExpect(content().bytes(ByteArray(0)))
                .andDo(log())
    }

    @Test
    fun `should return 422 with locale en`() {
        mockMvc
                .perform(post("/employees")
                        .accept(APPLICATION_JSON)
                        .header(ACCEPT_LANGUAGE, "en")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(invalidEmployee)))
                .andExpect(status().isUnprocessableEntity)
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json(this.javaClass.classLoader.getResource("payload/422_en.json").readText()))
                .andDo(log())
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        mockMvc
                .perform(post("/employees")
                        .accept(APPLICATION_JSON)
                        .header(ACCEPT_LANGUAGE, "pt-BR")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(invalidEmployee)))
                .andExpect(status().isUnprocessableEntity)
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json(this.javaClass.classLoader.getResource("payload/422_pt_BR.json").readText()))
                .andDo(log())
    }
}