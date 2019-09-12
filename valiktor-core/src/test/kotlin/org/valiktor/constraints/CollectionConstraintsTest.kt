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

class EmptyTest {

    @Test
    fun `should validate messages`() {
        assertThat(Empty.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be empty"),
            entry(SupportedLocales.DE, "Muss leer sein"),
            entry(SupportedLocales.EN, "Must be empty"),
            entry(SupportedLocales.PT_BR, "Deve ser vazio"))
    }
}

class NotEmptyTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEmpty.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be empty"),
            entry(SupportedLocales.DE, "Darf nicht leer sein"),
            entry(SupportedLocales.EN, "Must not be empty"),
            entry(SupportedLocales.PT_BR, "Não deve ser vazio"))
    }
}

class ContainsTest {

    @Test
    fun `should validate messages`() {
        assertThat(Contains("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must contain test"),
            entry(SupportedLocales.DE, "Muss test enthalten"),
            entry(SupportedLocales.EN, "Must contain test"),
            entry(SupportedLocales.PT_BR, "Deve conter test"))
    }
}

class ContainsAllTest {

    @Test
    fun `should validate messages`() {
        assertThat(ContainsAll(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must contain 1, 2, 3"),
            entry(SupportedLocales.DE, "Muss alle Werte in 1, 2, 3 enthalten"),
            entry(SupportedLocales.EN, "Must contain 1, 2, 3"),
            entry(SupportedLocales.PT_BR, "Deve conter 1, 2, 3"))
    }
}

class ContainsAnyTest {

    @Test
    fun `should validate messages`() {
        assertThat(ContainsAny(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must contain 1, 2, 3"),
            entry(SupportedLocales.DE, "Muss einen Wert in 1, 2, 3 enthalten"),
            entry(SupportedLocales.EN, "Must contain 1, 2, 3"),
            entry(SupportedLocales.PT_BR, "Deve conter 1, 2, 3"))
    }
}

class NotContainTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotContain("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not contain test"),
            entry(SupportedLocales.DE, "Darf test nicht enthalten"),
            entry(SupportedLocales.EN, "Must not contain test"),
            entry(SupportedLocales.PT_BR, "Não deve conter test"))
    }
}

class NotContainAllTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotContainAll(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not contain 1, 2, 3"),
            entry(SupportedLocales.DE, "Darf nicht alle Werte in 1, 2, 3 enthalten"),
            entry(SupportedLocales.EN, "Must not contain 1, 2, 3"),
            entry(SupportedLocales.PT_BR, "Não deve conter 1, 2, 3"))
    }
}

class NotContainAnyTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotContainAny(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not contain 1, 2, 3"),
            entry(SupportedLocales.DE, "Darf keinen Wert in 1, 2, 3 enthalten"),
            entry(SupportedLocales.EN, "Must not contain 1, 2, 3"),
            entry(SupportedLocales.PT_BR, "Não deve conter 1, 2, 3"))
    }
}

class SizeTest {

    @Test
    fun `should validate messages with min`() {
        assertThat(Size(min = 5).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Size must be greater than or equal to 5"),
            entry(SupportedLocales.DE, "Größe muss größer oder gleich 5 sein"),
            entry(SupportedLocales.EN, "Size must be greater than or equal to 5"),
            entry(SupportedLocales.PT_BR, "O tamanho deve ser maior ou igual a 5"))
    }

    @Test
    fun `should validate messages with max`() {
        assertThat(Size(max = 10).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Size must be less than or equal to 10"),
            entry(SupportedLocales.DE, "Größe muss kleiner oder gleich 10 sein"),
            entry(SupportedLocales.EN, "Size must be less than or equal to 10"),
            entry(SupportedLocales.PT_BR, "O tamanho deve ser menor ou igual a 10"))
    }

    @Test
    fun `should validate messages with min and max`() {
        assertThat(Size(min = 5, max = 10).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Size must be between 5 and 10"),
            entry(SupportedLocales.DE, "Größe muss zwischen 5 und 10 sein"),
            entry(SupportedLocales.EN, "Size must be between 5 and 10"),
            entry(SupportedLocales.PT_BR, "O tamanho deve estar entre 5 e 10"))
    }
}