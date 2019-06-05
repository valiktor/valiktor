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

package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.valiktor.asBigDecimal
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.math.BigDecimal
import kotlin.test.Test

private val BRL = CurrencyUnit.of("BRL")
private val USD = CurrencyUnit.of("USD")

private fun <T : Number> moneyOf(currency: CurrencyUnit, number: T) = Money.of(currency, number.asBigDecimal())

class MoneyFormatterTest {

    @Test
    fun `should format Double value`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 987.toDouble()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 987.00"),
            entry(SupportedLocales.EN, "USD987.00"),
            entry(SupportedLocales.PT_BR, "US$ 987,00"))
    }

    @Test
    fun `should format Double negative value`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, -987.0))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-BRL 987.00"),
            entry(SupportedLocales.EN, "-BRL987.00"),
            entry(SupportedLocales.PT_BR, "-R$ 987,00"))
    }

    @Test
    fun `should format Double value with grouping`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 9_876_543_210_123.0))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 9,876,543,210,123.00"),
            entry(SupportedLocales.EN, "USD9,876,543,210,123.00"),
            entry(SupportedLocales.PT_BR, "US$ 9.876.543.210.123,00"))
    }

    @Test
    fun `should format Double negative value with grouping`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, -9_876_543_210_123.0))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-BRL 9,876,543,210,123.00"),
            entry(SupportedLocales.EN, "-BRL9,876,543,210,123.00"),
            entry(SupportedLocales.PT_BR, "-R$ 9.876.543.210.123,00"))
    }

    @Test
    fun `should format Double value with decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 0.98))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 0.98"),
            entry(SupportedLocales.EN, "USD0.98"),
            entry(SupportedLocales.PT_BR, "US$ 0,98"))
    }

    @Test
    fun `should format Double negative value with decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, -0.98))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-USD 0.98"),
            entry(SupportedLocales.EN, "-USD0.98"),
            entry(SupportedLocales.PT_BR, "-US$ 0,98"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 876_543.98))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 876,543.98"),
            entry(SupportedLocales.EN, "USD876,543.98"),
            entry(SupportedLocales.PT_BR, "US$ 876.543,98"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, -876_543.98))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-USD 876,543.98"),
            entry(SupportedLocales.EN, "-USD876,543.98"),
            entry(SupportedLocales.PT_BR, "-US$ 876.543,98"))
    }

    @Test
    fun `should format Double zero`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 0.toDouble()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }

    @Test
    fun `should format Double zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, 0.0))).containsExactly(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }

    @Test
    fun `should format Double zero with decimal digits without preserving zeros`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, "0.00000".toDouble()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }

    @Test
    fun `should format BigDecimal value`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, 987.toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "BRL 987.00"),
            entry(SupportedLocales.EN, "BRL987.00"),
            entry(SupportedLocales.PT_BR, "R$ 987,00"))
    }

    @Test
    fun `should format BigDecimal negative value`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 987.unaryMinus().toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-USD 987.00"),
            entry(SupportedLocales.EN, "-USD987.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987,00"))
    }

    @Test
    fun `should format BigDecimal value with grouping`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, "987654321012345678910111231451659990".toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "BRL 987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.EN, "BRL987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.PT_BR, "R$ 987.654.321.012.345.678.910.111.231.451.659.990,00"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, "-987654321012345678910111231451659990".toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-USD 987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.EN, "-USD987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987.654.321.012.345.678.910.111.231.451.659.990,00"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, "0.98".toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "BRL 0.98"),
            entry(SupportedLocales.EN, "BRL0.98"),
            entry(SupportedLocales.PT_BR, "R$ 0,98"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, "-0.98".toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-BRL 0.98"),
            entry(SupportedLocales.EN, "-BRL0.98"),
            entry(SupportedLocales.PT_BR, "-R$ 0,98"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, "987654321987654321987654321.12".toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "BRL 987,654,321,987,654,321,987,654,321.12"),
            entry(SupportedLocales.EN, "BRL987,654,321,987,654,321,987,654,321.12"),
            entry(SupportedLocales.PT_BR, "R$ 987.654.321.987.654.321.987.654.321,12"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, "-987654321987654321987654321.12".toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "-BRL 987,654,321,987,654,321,987,654,321.12"),
            entry(SupportedLocales.EN, "-BRL987,654,321,987,654,321,987,654,321.12"),
            entry(SupportedLocales.PT_BR, "-R$ 987.654.321.987.654.321.987.654.321,12"))
    }

    @Test
    fun `should format BigDecimal zero`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(BRL, BigDecimal.ZERO))).containsExactly(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }

    @Test
    fun `should format BigDecimal zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[Money::class].formatAllSupportedLocales(moneyOf(USD, 0.0.toBigDecimal()))).containsExactly(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }
}