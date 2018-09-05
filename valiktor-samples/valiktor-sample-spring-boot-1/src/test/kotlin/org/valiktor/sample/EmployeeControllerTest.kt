package org.valiktor.sample

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.valiktor.springframework.web.payload.UnprocessableEntity
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class EmployeeControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun createValidEmployee(id: Int) {
        val entity = HttpEntity(EmployeeControllerFixture.validEmployee(id = id))
        val response = restTemplate.exchange("/employees", HttpMethod.POST, entity, Void::class.java)

        assertEquals(response.statusCode, HttpStatus.CREATED)
        assertTrue(response.headers[HttpHeaders.LOCATION]!![0].endsWith("/employees/$id"))
        assertFalse(response.hasBody())
    }

    @BeforeTest
    fun setUp() {
        Locale.setDefault(Locale.ENGLISH)
    }

    @Test
    fun `should return 422 with default locale`() {
        createValidEmployee(id = 1)

        val headers = HttpHeaders()
        headers[HttpHeaders.CONTENT_TYPE] = APPLICATION_JSON_VALUE
        headers[HttpHeaders.ACCEPT] = APPLICATION_JSON_VALUE

        val entity = HttpEntity(EmployeeControllerFixture.invalidEmployee(id = 1), headers)
        val response = restTemplate.exchange("/employees", HttpMethod.POST, entity, UnprocessableEntity::class.java)

        assertEquals(response.statusCode, HttpStatus.UNPROCESSABLE_ENTITY)
        assertTrue(response.headers[HttpHeaders.CONTENT_TYPE]!![0].startsWith(APPLICATION_JSON_VALUE))
        assertEquals(response.body, EmployeeControllerFixture.unprocessableEntityEn(id = 1))
    }

    @Test
    fun `should return 422 with locale en`() {
        createValidEmployee(id = 2)

        val headers = HttpHeaders()
        headers[HttpHeaders.CONTENT_TYPE] = APPLICATION_JSON_VALUE
        headers[HttpHeaders.ACCEPT] = APPLICATION_JSON_VALUE
        headers[HttpHeaders.ACCEPT_LANGUAGE] = "en"

        val entity = HttpEntity(EmployeeControllerFixture.invalidEmployee(id = 2), headers)
        val response = restTemplate.exchange("/employees", HttpMethod.POST, entity, UnprocessableEntity::class.java)

        assertEquals(response.statusCode, HttpStatus.UNPROCESSABLE_ENTITY)
        assertTrue(response.headers[HttpHeaders.CONTENT_TYPE]!![0].startsWith(APPLICATION_JSON_VALUE))
        assertEquals(response.body, EmployeeControllerFixture.unprocessableEntityEn(id = 2))
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        createValidEmployee(id = 3)

        val headers = HttpHeaders()
        headers[HttpHeaders.CONTENT_TYPE] = APPLICATION_JSON_VALUE
        headers[HttpHeaders.ACCEPT] = APPLICATION_JSON_VALUE
        headers[HttpHeaders.ACCEPT_LANGUAGE] = "pt-BR"

        val entity = HttpEntity(EmployeeControllerFixture.invalidEmployee(id = 3), headers)
        val response = restTemplate.exchange("/employees", HttpMethod.POST, entity, UnprocessableEntity::class.java)

        assertEquals(response.statusCode, HttpStatus.UNPROCESSABLE_ENTITY)
        assertTrue(response.headers[HttpHeaders.CONTENT_TYPE]!![0].startsWith(APPLICATION_JSON_VALUE))
        assertEquals(response.body, EmployeeControllerFixture.unprocessableEntityPtBr(id = 3))
    }
}