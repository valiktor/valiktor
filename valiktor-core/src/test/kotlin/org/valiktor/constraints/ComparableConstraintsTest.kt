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

package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages
import kotlin.test.Test

class LessTest {

    @Test
    fun `should validate messages`() {
        assertThat(Less(1).interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be less than 1"),
            entry(SupportedLocales.CA, "Ha de ser més petit que 1"),
            entry(SupportedLocales.DE, "Muss kleiner 1 sein"),
            entry(SupportedLocales.EN, "Must be less than 1"),
            entry(SupportedLocales.ES, "Tiene que ser menor que 1"),
            entry(SupportedLocales.JA, "1 未満の値である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser menor que 1")
        )
    }
}

class LessOrEqualTest {

    @Test
    fun `should validate messages`() {
        assertThat(LessOrEqual(5).interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be less than or equal to 5"),
            entry(SupportedLocales.CA, "Ha de ser més petit o igual a 5"),
            entry(SupportedLocales.DE, "Muss kleiner oder gleich 5 sein"),
            entry(SupportedLocales.EN, "Must be less than or equal to 5"),
            entry(SupportedLocales.ES, "Tiene que ser menor o igual que 5"),
            entry(SupportedLocales.JA, "5 以下の値である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser menor ou igual a 5")
        )
    }
}

class GreaterTest {

    @Test
    fun `should validate messages`() {
        assertThat(Greater(10).interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be greater than 10"),
            entry(SupportedLocales.CA, "Ha de ser més gran que 10"),
            entry(SupportedLocales.DE, "Muss größer 10 sein"),
            entry(SupportedLocales.EN, "Must be greater than 10"),
            entry(SupportedLocales.ES, "Tiene que ser mayor que 10"),
            entry(SupportedLocales.JA, "10 を超える値である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser maior que 10")
        )
    }
}

class GreaterOrEqualTest {

    @Test
    fun `should validate messages`() {
        assertThat(GreaterOrEqual(15).interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be greater than or equal to 15"),
            entry(SupportedLocales.CA, "Ha de ser més gran o igual a 15"),
            entry(SupportedLocales.DE, "Muss größer oder gleich 15 sein"),
            entry(SupportedLocales.EN, "Must be greater than or equal to 15"),
            entry(SupportedLocales.ES, "Tiene que ser mayor o igual que 15"),
            entry(SupportedLocales.JA, "15 以上の値である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser maior ou igual a 15")
        )
    }
}

class BetweenTest {

    @Test
    fun `should validate messages`() {
        assertThat(Between(start = 1, end = 10).interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be between 1 and 10"),
            entry(SupportedLocales.CA, "Ha d'estar entre 1 i 10"),
            entry(SupportedLocales.DE, "Muss zwischen 1 und 10 sein"),
            entry(SupportedLocales.EN, "Must be between 1 and 10"),
            entry(SupportedLocales.ES, "Tiene que estar entre 1 y 10"),
            entry(SupportedLocales.JA, "1 から 10 の範囲である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve estar entre 1 e 10")
        )
    }
}

class NotBetweenTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotBetween(start = 1, end = 10).interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must not be between 1 and 10"),
            entry(SupportedLocales.CA, "No pot estar entre 1 i 10"),
            entry(SupportedLocales.DE, "Darf nicht zwischen 1 und 10 sein"),
            entry(SupportedLocales.EN, "Must not be between 1 and 10"),
            entry(SupportedLocales.ES, "No puede estar entre 1 y 10"),
            entry(SupportedLocales.JA, "1 から 10 の範囲外である必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve estar entre 1 e 10")
        )
    }
}
