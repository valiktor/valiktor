/*
 * Copyright 2018 https://www.valiktor.org
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
import javax.money.Monetary
import kotlin.test.Test

private val REAL = Monetary.getCurrency("BRL")
private val DOLLAR = Monetary.getCurrency("USD")

class CurrencyEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyEquals(REAL).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must be equal to BRL"),
            entry(SupportedLocales.EN, "Currency unit must be equal to BRL"),
            entry(SupportedLocales.PT_BR, "A unidade monetária deve ser igual a BRL"))
    }
}

class CurrencyNotEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyNotEquals(REAL).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must not be equal to BRL"),
            entry(SupportedLocales.EN, "Currency unit must not be equal to BRL"),
            entry(SupportedLocales.PT_BR, "A unidade monetária não deve ser igual a BRL"))
    }
}

class CurrencyInTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyIn(setOf(REAL, DOLLAR)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must be in BRL, USD"),
            entry(SupportedLocales.EN, "Currency unit must be in BRL, USD"),
            entry(SupportedLocales.PT_BR, "A unidade monetária deve ser uma dessas: BRL, USD"))
    }
}

class CurrencyNotInTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyNotIn(setOf(REAL, DOLLAR)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must not be in BRL, USD"),
            entry(SupportedLocales.EN, "Currency unit must not be in BRL, USD"),
            entry(SupportedLocales.PT_BR, "A unidade monetária não deve ser uma dessas: BRL, USD"))
    }
}