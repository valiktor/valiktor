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
import org.joda.money.CurrencyUnit
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages
import kotlin.test.Test

private val BRL = CurrencyUnit.of("BRL")
private val USD = CurrencyUnit.of("USD")

class CurrencyEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyEquals(BRL).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must be equal to BRL"),
            entry(SupportedLocales.CA, "La unitat monetària ha de ser igual a BRL"),
            entry(SupportedLocales.DE, "Die Währungseinheit muss gleich BRL sein"),
            entry(SupportedLocales.EN, "Currency unit must be equal to BRL"),
            entry(SupportedLocales.ES, "La unidad monetaria tiene que ser igual a BRL"),
            entry(SupportedLocales.JA, "BRL 通貨単位と同じである必要があります"),
            entry(SupportedLocales.PT_BR, "A unidade monetária deve ser igual a BRL")
        )
    }
}

class CurrencyNotEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyNotEquals(BRL).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must not be equal to BRL"),
            entry(SupportedLocales.CA, "La unitat monetària no pot ser igual a BRL"),
            entry(SupportedLocales.DE, "Die Währungseinheit darf nicht gleich BRL sein"),
            entry(SupportedLocales.EN, "Currency unit must not be equal to BRL"),
            entry(SupportedLocales.ES, "La unidad monetaria no pueder ser igual a BRL"),
            entry(SupportedLocales.JA, "BRL 通貨単位以外である必要があります"),
            entry(SupportedLocales.PT_BR, "A unidade monetária não deve ser igual a BRL")
        )
    }
}

class CurrencyInTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyIn(setOf(BRL, USD)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must be in BRL, USD"),
            entry(SupportedLocales.CA, "La unitat monetària ha de ser una de les següents: BRL, USD"),
            entry(SupportedLocales.DE, "Die Währungseinheit muss in BRL, USD sein"),
            entry(SupportedLocales.EN, "Currency unit must be in BRL, USD"),
            entry(SupportedLocales.ES, "La unidad monetaria tiene que ser una de esas: BRL, USD"),
            entry(SupportedLocales.JA, "BRL, USD 通貨単位が含まれている必要があります"),
            entry(SupportedLocales.PT_BR, "A unidade monetária deve ser uma dessas: BRL, USD")
        )
    }
}

class CurrencyNotInTest {

    @Test
    fun `should validate messages`() {
        assertThat(CurrencyNotIn(setOf(BRL, USD)).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Currency unit must not be in BRL, USD"),
            entry(SupportedLocales.CA, "La unitat monetària no pot ser una de les següents: BRL, USD"),
            entry(SupportedLocales.DE, "Die Währungseinheit darf nicht in BRL, USD sein"),
            entry(SupportedLocales.EN, "Currency unit must not be in BRL, USD"),
            entry(SupportedLocales.ES, "La unidad monetaria no pueder ser una de esas: BRL, USD"),
            entry(SupportedLocales.JA, "BRL, USD 通貨単位以外が含まれている必要があります"),
            entry(SupportedLocales.PT_BR, "A unidade monetária não deve ser uma dessas: BRL, USD")
        )
    }
}
