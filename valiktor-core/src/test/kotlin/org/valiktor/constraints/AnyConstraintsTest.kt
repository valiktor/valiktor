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

class NullTest {

    @Test
    fun `should validate messages`() {
        assertThat(Null.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be null"),
            entry(SupportedLocales.EN, "Must be null"),
            entry(SupportedLocales.PT_BR, "Deve ser nulo"))
    }
}

class NotNullTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotNull.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be null"),
            entry(SupportedLocales.EN, "Must not be null"),
            entry(SupportedLocales.PT_BR, "Não deve ser nulo"))
    }
}

class EqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(Equals(1).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be equal to 1"),
            entry(SupportedLocales.EN, "Must be equal to 1"),
            entry(SupportedLocales.PT_BR, "Deve ser igual a 1"))
    }
}

class NotEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEquals(1).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be equal to 1"),
            entry(SupportedLocales.EN, "Must not be equal to 1"),
            entry(SupportedLocales.PT_BR, "Não deve ser igual a 1"))
    }
}

class InTest {

    @Test
    fun `should validate messages`() {
        assertThat(In(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be in 1, 2, 3"),
            entry(SupportedLocales.EN, "Must be in 1, 2, 3"),
            entry(SupportedLocales.PT_BR, "Deve ser um desses: 1, 2, 3"))
    }
}

class NotInTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotIn(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be in 1, 2, 3"),
            entry(SupportedLocales.EN, "Must not be in 1, 2, 3"),
            entry(SupportedLocales.PT_BR, "Não deve ser um desses: 1, 2, 3"))
    }
}

class ValidTest {

    @Test
    fun `should validate messages`() {
        assertThat(Valid.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be valid"),
            entry(SupportedLocales.EN, "Must be valid"),
            entry(SupportedLocales.PT_BR, "Deve ser válido"))
    }
}