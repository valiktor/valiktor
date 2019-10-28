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
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.math.BigDecimal
import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount
import kotlin.test.Test

private val BRL = Monetary.getCurrency("BRL")
private val USD = Monetary.getCurrency("USD")

private fun <T : Number> moneyOf(currency: CurrencyUnit, number: T) =
    Monetary.getDefaultAmountFactory().setCurrency(currency).setNumber(number).create()

class MonetaryAmountFormatterTest {

    @Test
    fun `should format Byte value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 1.toByte()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 1.00"),
            entry(SupportedLocales.DE, "BRL 1,00"),
            entry(SupportedLocales.EN, "BRL1.00"),
            entry(SupportedLocales.PT_BR, "R$ 1,00"))
    }

    @Test
    fun `should format Byte negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 98.unaryMinus().toByte()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 98.00"),
            entry(SupportedLocales.DE, "-USD 98,00"),
            entry(SupportedLocales.EN, "-USD98.00"),
            entry(SupportedLocales.PT_BR, "-US$ 98,00"))
    }

    @Test
    fun `should format Short value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 987.toShort()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987.00"),
            entry(SupportedLocales.DE, "BRL 987,00"),
            entry(SupportedLocales.EN, "BRL987.00"),
            entry(SupportedLocales.PT_BR, "R$ 987,00"))
    }

    @Test
    fun `should format Short negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 987.unaryMinus().toShort()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987.00"),
            entry(SupportedLocales.DE, "-USD 987,00"),
            entry(SupportedLocales.EN, "-USD987.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987,00"))
    }

    @Test
    fun `should format Short value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 9_876.toShort()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 9,876.00"),
            entry(SupportedLocales.DE, "BRL 9.876,00"),
            entry(SupportedLocales.EN, "BRL9,876.00"),
            entry(SupportedLocales.PT_BR, "R$ 9.876,00"))
    }

    @Test
    fun `should format Short negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 9_876.unaryMinus().toShort()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 9,876.00"),
            entry(SupportedLocales.DE, "-USD 9.876,00"),
            entry(SupportedLocales.EN, "-USD9,876.00"),
            entry(SupportedLocales.PT_BR, "-US$ 9.876,00"))
    }

    @Test
    fun `should format Int value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 987))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987.00"),
            entry(SupportedLocales.DE, "BRL 987,00"),
            entry(SupportedLocales.EN, "BRL987.00"),
            entry(SupportedLocales.PT_BR, "R$ 987,00"))
    }

    @Test
    fun `should format Int negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -987))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987.00"),
            entry(SupportedLocales.DE, "-USD 987,00"),
            entry(SupportedLocales.EN, "-USD987.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987,00"))
    }

    @Test
    fun `should format Int value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 987_654_321))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987,654,321.00"),
            entry(SupportedLocales.DE, "BRL 987.654.321,00"),
            entry(SupportedLocales.EN, "BRL987,654,321.00"),
            entry(SupportedLocales.PT_BR, "R$ 987.654.321,00"))
    }

    @Test
    fun `should format Int negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -987_654_321))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987,654,321.00"),
            entry(SupportedLocales.DE, "-USD 987.654.321,00"),
            entry(SupportedLocales.EN, "-USD987,654,321.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987.654.321,00"))
    }

    @Test
    fun `should format Long value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 987L))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987.00"),
            entry(SupportedLocales.DE, "BRL 987,00"),
            entry(SupportedLocales.EN, "BRL987.00"),
            entry(SupportedLocales.PT_BR, "R$ 987,00"))
    }

    @Test
    fun `should format Long negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -987L))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987.00"),
            entry(SupportedLocales.DE, "-USD 987,00"),
            entry(SupportedLocales.EN, "-USD987.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987,00"))
    }

    @Test
    fun `should format Long value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 98_765_432_109_876_543L))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 98,765,432,109,876,543.00"),
            entry(SupportedLocales.DE, "BRL 98.765.432.109.876.543,00"),
            entry(SupportedLocales.EN, "BRL98,765,432,109,876,543.00"),
            entry(SupportedLocales.PT_BR, "R$ 98.765.432.109.876.543,00"))
    }

    @Test
    fun `should format Long negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -98_765_432_109_876_543L))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 98,765,432,109,876,543.00"),
            entry(SupportedLocales.DE, "-USD 98.765.432.109.876.543,00"),
            entry(SupportedLocales.EN, "-USD98,765,432,109,876,543.00"),
            entry(SupportedLocales.PT_BR, "-US$ 98.765.432.109.876.543,00"))
    }

    @Test
    fun `should format BigInteger value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 123.toBigInteger()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 123.00"),
            entry(SupportedLocales.DE, "BRL 123,00"),
            entry(SupportedLocales.EN, "BRL123.00"),
            entry(SupportedLocales.PT_BR, "R$ 123,00"))
    }

    @Test
    fun `should format BigInteger negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 123.unaryMinus().toBigInteger()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 123.00"),
            entry(SupportedLocales.DE, "-USD 123,00"),
            entry(SupportedLocales.EN, "-USD123.00"),
            entry(SupportedLocales.PT_BR, "-US$ 123,00"))
    }

    @Test
    fun `should format BigInteger value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "987654321012345678910111231451659990".toBigInteger()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.DE, "BRL 987.654.321.012.345.678.910.111.231.451.659.990,00"),
            entry(SupportedLocales.EN, "BRL987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.PT_BR, "R$ 987.654.321.012.345.678.910.111.231.451.659.990,00"))
    }

    @Test
    fun `should format BigInteger negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "-987654321012345678910111231451659990".toBigInteger()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.DE, "-USD 987.654.321.012.345.678.910.111.231.451.659.990,00"),
            entry(SupportedLocales.EN, "-USD987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987.654.321.012.345.678.910.111.231.451.659.990,00"))
    }

    @Test
    fun `should format Float value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 987.0f))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987.00"),
            entry(SupportedLocales.DE, "BRL 987,00"),
            entry(SupportedLocales.EN, "BRL987.00"),
            entry(SupportedLocales.PT_BR, "R$ 987,00"))
    }

    @Test
    fun `should format Float negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -987.0f))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987.00"),
            entry(SupportedLocales.DE, "-USD 987,00"),
            entry(SupportedLocales.EN, "-USD987.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987,00"))
    }

    @Test
    fun `should format Float value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 9_210_123.0f))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 9,210,123.00"),
            entry(SupportedLocales.DE, "BRL 9.210.123,00"),
            entry(SupportedLocales.EN, "BRL9,210,123.00"),
            entry(SupportedLocales.PT_BR, "R$ 9.210.123,00"))
    }

    @Test
    fun `should format Float negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -9_210_123.0f))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 9,210,123.00"),
            entry(SupportedLocales.DE, "-USD 9.210.123,00"),
            entry(SupportedLocales.EN, "-USD9,210,123.00"),
            entry(SupportedLocales.PT_BR, "-US$ 9.210.123,00"))
    }

    @Test
    fun `should format Float value with decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 0.9876543f))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.9876543"),
            entry(SupportedLocales.DE, "BRL 0,9876543"),
            entry(SupportedLocales.EN, "BRL0.9876543"),
            entry(SupportedLocales.PT_BR, "R$ 0,9876543"))
    }

    @Test
    fun `should format Float value with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 0.9876543000f))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.9876543"),
            entry(SupportedLocales.DE, "USD 0,9876543"),
            entry(SupportedLocales.EN, "USD0.9876543"),
            entry(SupportedLocales.PT_BR, "US$ 0,9876543"))
    }

    @Test
    fun `should format Float negative value with decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, -0.9876543f))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 0.9876543"),
            entry(SupportedLocales.DE, "-BRL 0,9876543"),
            entry(SupportedLocales.EN, "-BRL0.9876543"),
            entry(SupportedLocales.PT_BR, "-R$ 0,9876543"))
    }

    @Test
    fun `should format Float negative value with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -0.98765430f))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 0.9876543"),
            entry(SupportedLocales.DE, "-USD 0,9876543"),
            entry(SupportedLocales.EN, "-USD0.9876543"),
            entry(SupportedLocales.PT_BR, "-US$ 0,9876543"))
    }

    @Test
    fun `should format Float value with grouping and decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 6_543.987f))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 6,543.987"),
            entry(SupportedLocales.DE, "BRL 6.543,987"),
            entry(SupportedLocales.EN, "BRL6,543.987"),
            entry(SupportedLocales.PT_BR, "R$ 6.543,987"))
    }

    @Test
    fun `should format Float value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 6_543.9870f))).contains(
            entry(SupportedLocales.DEFAULT, "USD 6,543.987"),
            entry(SupportedLocales.DE, "USD 6.543,987"),
            entry(SupportedLocales.EN, "USD6,543.987"),
            entry(SupportedLocales.PT_BR, "US$ 6.543,987"))
    }

    @Test
    fun `should format Float negative value with grouping and decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, -6_543.987f))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 6,543.987"),
            entry(SupportedLocales.DE, "-BRL 6.543,987"),
            entry(SupportedLocales.EN, "-BRL6,543.987"),
            entry(SupportedLocales.PT_BR, "-R$ 6.543,987"))
    }

    @Test
    fun `should format Float negative value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -6_543.9870f))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 6,543.987"),
            entry(SupportedLocales.DE, "-USD 6.543,987"),
            entry(SupportedLocales.EN, "-USD6,543.987"),
            entry(SupportedLocales.PT_BR, "-US$ 6.543,987"))
    }

    @Test
    fun `should format Float zero`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 0f))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.DE, "BRL 0,00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }

    @Test
    fun `should format Float zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 0.0f))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.DE, "USD 0,00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }

    @Test
    fun `should format Float zero with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "0.00000".toFloat()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.DE, "BRL 0,00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }

    @Test
    fun `should format Double value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 987.toDouble()))).contains(
            entry(SupportedLocales.DEFAULT, "USD 987.00"),
            entry(SupportedLocales.DE, "USD 987,00"),
            entry(SupportedLocales.EN, "USD987.00"),
            entry(SupportedLocales.PT_BR, "US$ 987,00"))
    }

    @Test
    fun `should format Double negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, -987.0))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 987.00"),
            entry(SupportedLocales.DE, "-BRL 987,00"),
            entry(SupportedLocales.EN, "-BRL987.00"),
            entry(SupportedLocales.PT_BR, "-R$ 987,00"))
    }

    @Test
    fun `should format Double value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 9_876_543_210_123.0))).contains(
            entry(SupportedLocales.DEFAULT, "USD 9,876,543,210,123.00"),
            entry(SupportedLocales.DE, "USD 9.876.543.210.123,00"),
            entry(SupportedLocales.EN, "USD9,876,543,210,123.00"),
            entry(SupportedLocales.PT_BR, "US$ 9.876.543.210.123,00"))
    }

    @Test
    fun `should format Double negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, -9_876_543_210_123.0))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 9,876,543,210,123.00"),
            entry(SupportedLocales.DE, "-BRL 9.876.543.210.123,00"),
            entry(SupportedLocales.EN, "-BRL9,876,543,210,123.00"),
            entry(SupportedLocales.PT_BR, "-R$ 9.876.543.210.123,00"))
    }

    @Test
    fun `should format Double value with decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 0.987654321))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.987654321"),
            entry(SupportedLocales.DE, "USD 0,987654321"),
            entry(SupportedLocales.EN, "USD0.987654321"),
            entry(SupportedLocales.PT_BR, "US$ 0,987654321"))
    }

    @Test
    fun `should format Double value with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 0.98765432100))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.987654321"),
            entry(SupportedLocales.DE, "BRL 0,987654321"),
            entry(SupportedLocales.EN, "BRL0.987654321"),
            entry(SupportedLocales.PT_BR, "R$ 0,987654321"))
    }

    @Test
    fun `should format Double negative value with decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -0.987654321))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 0.987654321"),
            entry(SupportedLocales.DE, "-USD 0,987654321"),
            entry(SupportedLocales.EN, "-USD0.987654321"),
            entry(SupportedLocales.PT_BR, "-US$ 0,987654321"))
    }

    @Test
    fun `should format Double negative value with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, -0.987654321000))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 0.987654321"),
            entry(SupportedLocales.DE, "-BRL 0,987654321"),
            entry(SupportedLocales.EN, "-BRL0.987654321"),
            entry(SupportedLocales.PT_BR, "-R$ 0,987654321"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 876_543.987654321))).contains(
            entry(SupportedLocales.DEFAULT, "USD 876,543.987654321"),
            entry(SupportedLocales.DE, "USD 876.543,987654321"),
            entry(SupportedLocales.EN, "USD876,543.987654321"),
            entry(SupportedLocales.PT_BR, "US$ 876.543,987654321"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 876_543.9876543210))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 876,543.987654321"),
            entry(SupportedLocales.DE, "BRL 876.543,987654321"),
            entry(SupportedLocales.EN, "BRL876,543.987654321"),
            entry(SupportedLocales.PT_BR, "R$ 876.543,987654321"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, -876_543.987654321))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 876,543.987654321"),
            entry(SupportedLocales.DE, "-USD 876.543,987654321"),
            entry(SupportedLocales.EN, "-USD876,543.987654321"),
            entry(SupportedLocales.PT_BR, "-US$ 876.543,987654321"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, -876_543.98765432100))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 876,543.987654321"),
            entry(SupportedLocales.DE, "-BRL 876.543,987654321"),
            entry(SupportedLocales.EN, "-BRL876,543.987654321"),
            entry(SupportedLocales.PT_BR, "-R$ 876.543,987654321"))
    }

    @Test
    fun `should format Double zero`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 0.0))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.DE, "USD 0,00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }

    @Test
    fun `should format Double zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 0.0))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.DE, "BRL 0,00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }

    @Test
    fun `should format Double zero with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "0.00000".toDouble()))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.DE, "USD 0,00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }

    @Test
    fun `should format BigDecimal value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, 987.toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987.00"),
            entry(SupportedLocales.DE, "BRL 987,00"),
            entry(SupportedLocales.EN, "BRL987.00"),
            entry(SupportedLocales.PT_BR, "R$ 987,00"))
    }

    @Test
    fun `should format BigDecimal negative value`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 987.unaryMinus().toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987.00"),
            entry(SupportedLocales.DE, "-USD 987,00"),
            entry(SupportedLocales.EN, "-USD987.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987,00"))
    }

    @Test
    fun `should format BigDecimal value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "987654321012345678910111231451659990".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.DE, "BRL 987.654.321.012.345.678.910.111.231.451.659.990,00"),
            entry(SupportedLocales.EN, "BRL987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.PT_BR, "R$ 987.654.321.012.345.678.910.111.231.451.659.990,00"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "-987654321012345678910111231451659990".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.DE, "-USD 987.654.321.012.345.678.910.111.231.451.659.990,00"),
            entry(SupportedLocales.EN, "-USD987,654,321,012,345,678,910,111,231,451,659,990.00"),
            entry(SupportedLocales.PT_BR, "-US$ 987.654.321.012.345.678.910.111.231.451.659.990,00"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "0.987654321234567891011121314151699786".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.987654321234567891011121314151699786"),
            entry(SupportedLocales.DE, "BRL 0,987654321234567891011121314151699786"),
            entry(SupportedLocales.EN, "BRL0.987654321234567891011121314151699786"),
            entry(SupportedLocales.PT_BR, "R$ 0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "0.987654321234567891011121314151699786000".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.987654321234567891011121314151699786"),
            entry(SupportedLocales.DE, "USD 0,987654321234567891011121314151699786"),
            entry(SupportedLocales.EN, "USD0.987654321234567891011121314151699786"),
            entry(SupportedLocales.PT_BR, "US$ 0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "-0.987654321234567891011121314151699786".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 0.987654321234567891011121314151699786"),
            entry(SupportedLocales.DE, "-BRL 0,987654321234567891011121314151699786"),
            entry(SupportedLocales.EN, "-BRL0.987654321234567891011121314151699786"),
            entry(SupportedLocales.PT_BR, "-R$ 0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "-0.98765432123456789101112131415169978600".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 0.987654321234567891011121314151699786"),
            entry(SupportedLocales.DE, "-USD 0,987654321234567891011121314151699786"),
            entry(SupportedLocales.EN, "-USD0.987654321234567891011121314151699786"),
            entry(SupportedLocales.PT_BR, "-US$ 0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "987654321987654321987654321.12345678910111213141516178".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.DE, "BRL 987.654.321.987.654.321.987.654.321,12345678910111213141516178"),
            entry(SupportedLocales.EN, "BRL987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.PT_BR, "R$ 987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "987654321987654321987654321.1234567891011121314151617800".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "USD 987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.DE, "USD 987.654.321.987.654.321.987.654.321,12345678910111213141516178"),
            entry(SupportedLocales.EN, "USD987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.PT_BR, "US$ 987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "-987654321987654321987654321.12345678910111213141516178".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "-BRL 987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.DE, "-BRL 987.654.321.987.654.321.987.654.321,12345678910111213141516178"),
            entry(SupportedLocales.EN, "-BRL987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.PT_BR, "-R$ 987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, "-987654321987654321987654321.123456789101112131415161780".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "-USD 987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.DE, "-USD 987.654.321.987.654.321.987.654.321,12345678910111213141516178"),
            entry(SupportedLocales.EN, "-USD987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.PT_BR, "-US$ 987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal zero`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, BigDecimal.ZERO))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.DE, "BRL 0,00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }

    @Test
    fun `should format BigDecimal zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(USD, 0.0.toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "USD 0.00"),
            entry(SupportedLocales.DE, "USD 0,00"),
            entry(SupportedLocales.EN, "USD0.00"),
            entry(SupportedLocales.PT_BR, "US$ 0,00"))
    }

    @Test
    fun `should format BigDecimal zero with decimal digits without preserving zeros`() {
        assertThat(Formatters[MonetaryAmount::class].formatAllSupportedLocales(moneyOf(BRL, "0.00000".toBigDecimal()))).contains(
            entry(SupportedLocales.DEFAULT, "BRL 0.00"),
            entry(SupportedLocales.DE, "BRL 0,00"),
            entry(SupportedLocales.EN, "BRL0.00"),
            entry(SupportedLocales.PT_BR, "R$ 0,00"))
    }
}
