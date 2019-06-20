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

package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages
import kotlin.test.Test

class IntegerDigitsTest {

    @Test
    fun `should validate messages with min`() {
        assertThat(IntegerDigits(min = 1).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Integer digits must be greater than or equal to 1"),
            entry(SupportedLocales.DE_DE, "Ganzzahlstellen müssen größer oder gleich 1 sein"),
            entry(SupportedLocales.EN, "Integer digits must be greater than or equal to 1"),
            entry(SupportedLocales.PT_BR, "A quantidade de dígitos inteiros deve ser maior ou igual a 1"))
    }

    @Test
    fun `should validate messages with max`() {
        assertThat(IntegerDigits(max = 5).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Integer digits must be less than or equal to 5"),
            entry(SupportedLocales.DE_DE, "Ganzzahlstellen müssen kleiner oder gleich 5 sein"),
            entry(SupportedLocales.EN, "Integer digits must be less than or equal to 5"),
            entry(SupportedLocales.PT_BR, "A quantidade de dígitos inteiros deve ser menor ou igual a 5"))
    }

    @Test
    fun `should validate messages with min and max`() {
        assertThat(IntegerDigits(min = 1, max = 5).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Integer digits must be between 1 and 5"),
            entry(SupportedLocales.DE_DE, "Ganzzahlstellen müssen zwischen 1 und 5 sein"),
            entry(SupportedLocales.EN, "Integer digits must be between 1 and 5"),
            entry(SupportedLocales.PT_BR, "A quantidade de dígitos inteiros deve estar entre 1 e 5"))
    }
}

class DecimalDigitsTest {

    @Test
    fun `should validate messages with min`() {
        assertThat(DecimalDigits(min = 1).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Decimal digits must be greater than or equal to 1"),
            entry(SupportedLocales.DE_DE, "Dezimalstellen müssen größer oder gleich 1 sein"),
            entry(SupportedLocales.EN, "Decimal digits must be greater than or equal to 1"),
            entry(SupportedLocales.PT_BR, "A quantidade de casas decimais deve ser maior ou igual a 1"))
    }

    @Test
    fun `should validate messages with max`() {
        assertThat(DecimalDigits(max = 5).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Decimal digits must be less than or equal to 5"),
            entry(SupportedLocales.DE_DE, "Dezimalstellen müssen kleiner oder gleich 5 sein"),
            entry(SupportedLocales.EN, "Decimal digits must be less than or equal to 5"),
            entry(SupportedLocales.PT_BR, "A quantidade de casas decimais deve ser menor ou igual a 5"))
    }

    @Test
    fun `should validate messages with min and max`() {
        assertThat(DecimalDigits(min = 1, max = 5).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Decimal digits must be between 1 and 5"),
            entry(SupportedLocales.DE_DE, "Dezimalstellen müssen zwischen 1 und 5 sein"),
            entry(SupportedLocales.EN, "Decimal digits must be between 1 and 5"),
            entry(SupportedLocales.PT_BR, "A quantidade de casas decimais deve estar entre 1 e 5"))
    }
}