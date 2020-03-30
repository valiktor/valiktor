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

package org.valiktor.springframework.web.controller

import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.http.HttpHeaders.LOCATION
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.Test

class InvalidFormatExceptionHandlerXmlTest {

    private val mockMvc = ExceptionHandlerFixture.mockMvc
    private val xml = ExceptionHandlerFixture.XML

    @BeforeTest
    fun setUp() {
        Locale.setDefault(Locale.ENGLISH)
    }

    @Test
    fun `should return 201`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeValid()))
            .andExpect(status().isCreated)
            .andExpect(header().string(LOCATION, "http://localhost/employees/1"))
            .andExpect(content().bytes(ByteArray(0)))
            .andDo(log())
    }

    @Test
    fun `should return 422 (In) with default locale`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeInvalidStatus()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML))
            .andExpect(content().xml(xml.payload422InvalidStatus(Locale.ENGLISH)))
            .andDo(log())
    }

    @Test
    fun `should return 422 (In) with locale en`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .header(ACCEPT_LANGUAGE, "en")
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeInvalidStatus()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML))
            .andExpect(content().xml(xml.payload422InvalidStatus(Locale.ENGLISH)))
            .andDo(log())
    }

    @Test
    fun `should return 422 (In) with locale pt_BR`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .header(ACCEPT_LANGUAGE, "pt-BR")
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeInvalidStatus()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML))
            .andExpect(content().xml(xml.payload422InvalidStatus(Locale("pt", "BR"))))
            .andDo(log())
    }

    @Test
    fun `should return 422 (Valid) with default locale`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeInvalidSalary()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML))
            .andExpect(content().xml(xml.payload422InvalidSalary(Locale.ENGLISH)))
            .andDo(log())
    }

    @Test
    fun `should return 422 (Valid) with locale en`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .header(ACCEPT_LANGUAGE, "en")
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeInvalidSalary()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML))
            .andExpect(content().xml(xml.payload422InvalidSalary(Locale.ENGLISH)))
            .andDo(log())
    }

    @Test
    fun `should return 422 (Valid) with locale pt_BR`() {
        mockMvc
            .perform(post("/employees")
                .accept(APPLICATION_XML)
                .header(ACCEPT_LANGUAGE, "pt-BR")
                .contentType(APPLICATION_XML)
                .content(xml.payloadEmployeeInvalidSalary()))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML))
            .andExpect(content().xml(xml.payload422InvalidSalary(Locale("pt", "BR"))))
            .andDo(log())
    }
}
