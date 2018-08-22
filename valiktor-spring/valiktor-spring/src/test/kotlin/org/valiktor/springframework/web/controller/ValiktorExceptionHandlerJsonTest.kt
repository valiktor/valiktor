package org.valiktor.springframework.web.controller

import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Locale
import kotlin.test.Test

class ValiktorExceptionHandlerJsonTest {

    private val mockMvc = ValiktorExceptionHandlerFixture.mockMvc
    private val json = ValiktorExceptionHandlerFixture.JSON

    @Test
    fun `should return 201`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_JSON)
                .header(ACCEPT_LANGUAGE, "en")
                .contentType(APPLICATION_JSON)
                .content(json.payloadEmployeeValid()))
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
                .content(json.payloadEmployeeInvalid()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
            .andExpect(content().json(json.payload422(Locale.ENGLISH)))
            .andDo(log())
    }

    @Test
    fun `should return 422 with locale pt_BR`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_JSON)
                .header(ACCEPT_LANGUAGE, "pt-BR")
                .contentType(APPLICATION_JSON)
                .content(json.payloadEmployeeInvalid()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
            .andExpect(content().json(json.payload422(Locale("pt", "BR"))))
            .andDo(log())
    }

    @Test
    fun `should return 422 with null name and locale en`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_JSON)
                .header(ACCEPT_LANGUAGE, "en")
                .contentType(APPLICATION_JSON)
                .content(json.payloadEmployeeNullName()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
            .andExpect(content().json(json.payload422NullName(Locale.ENGLISH)))
            .andDo(log())
    }

    @Test
    fun `should return 422 with null name and locale pt_BR`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_JSON)
                .header(ACCEPT_LANGUAGE, "pt-BR")
                .contentType(APPLICATION_JSON)
                .content(json.payloadEmployeeNullName()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
            .andExpect(content().json(json.payload422NullName(Locale("pt", "BR"))))
            .andDo(log())
    }
}