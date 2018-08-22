package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test

class NumberFormatterTest {

    @Test
    fun `should format Byte value`() {
        assertThat(Formatters[Byte::class].formatAllSupportedLocales(1)).containsExactly(
            entry(SupportedLocales.DEFAULT, "1"),
            entry(SupportedLocales.EN, "1"),
            entry(SupportedLocales.PT_BR, "1"))
    }

    @Test
    fun `should format Byte negative value`() {
        assertThat(Formatters[Byte::class].formatAllSupportedLocales(-98)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-98"),
            entry(SupportedLocales.EN, "-98"),
            entry(SupportedLocales.PT_BR, "-98"))
    }

    @Test
    fun `should format Short value`() {
        assertThat(Formatters[Short::class].formatAllSupportedLocales(987)).containsExactly(
            entry(SupportedLocales.DEFAULT, "987"),
            entry(SupportedLocales.EN, "987"),
            entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Short negative value`() {
        assertThat(Formatters[Short::class].formatAllSupportedLocales(-987)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987"),
            entry(SupportedLocales.EN, "-987"),
            entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Short value with grouping`() {
        assertThat(Formatters[Short::class].formatAllSupportedLocales(9_876)).containsExactly(
            entry(SupportedLocales.DEFAULT, "9,876"),
            entry(SupportedLocales.EN, "9,876"),
            entry(SupportedLocales.PT_BR, "9.876"))
    }

    @Test
    fun `should format Short negative value with grouping`() {
        assertThat(Formatters[Short::class].formatAllSupportedLocales(-9_876)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-9,876"),
            entry(SupportedLocales.EN, "-9,876"),
            entry(SupportedLocales.PT_BR, "-9.876"))
    }

    @Test
    fun `should format Int value`() {
        assertThat(Formatters[Int::class].formatAllSupportedLocales(987)).containsExactly(
            entry(SupportedLocales.DEFAULT, "987"),
            entry(SupportedLocales.EN, "987"),
            entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Int negative value`() {
        assertThat(Formatters[Int::class].formatAllSupportedLocales(-987)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987"),
            entry(SupportedLocales.EN, "-987"),
            entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Int value with grouping`() {
        assertThat(Formatters[Int::class].formatAllSupportedLocales(987_654_321)).containsExactly(
            entry(SupportedLocales.DEFAULT, "987,654,321"),
            entry(SupportedLocales.EN, "987,654,321"),
            entry(SupportedLocales.PT_BR, "987.654.321"))
    }

    @Test
    fun `should format Int negative value with grouping`() {
        assertThat(Formatters[Int::class].formatAllSupportedLocales(-987_654_321)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987,654,321"),
            entry(SupportedLocales.EN, "-987,654,321"),
            entry(SupportedLocales.PT_BR, "-987.654.321"))
    }

    @Test
    fun `should format Long value`() {
        assertThat(Formatters[Long::class].formatAllSupportedLocales(987L)).containsExactly(
            entry(SupportedLocales.DEFAULT, "987"),
            entry(SupportedLocales.EN, "987"),
            entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Long negative value`() {
        assertThat(Formatters[Long::class].formatAllSupportedLocales(-987L)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987"),
            entry(SupportedLocales.EN, "-987"),
            entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Long value with grouping`() {
        assertThat(Formatters[Long::class].formatAllSupportedLocales(98_765_432_109_876_543L)).containsExactly(
            entry(SupportedLocales.DEFAULT, "98,765,432,109,876,543"),
            entry(SupportedLocales.EN, "98,765,432,109,876,543"),
            entry(SupportedLocales.PT_BR, "98.765.432.109.876.543"))
    }

    @Test
    fun `should format Long negative value with grouping`() {
        assertThat(Formatters[Long::class].formatAllSupportedLocales(-98_765_432_109_876_543L)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-98,765,432,109,876,543"),
            entry(SupportedLocales.EN, "-98,765,432,109,876,543"),
            entry(SupportedLocales.PT_BR, "-98.765.432.109.876.543"))
    }

    @Test
    fun `should format BigInteger value`() {
        assertThat(Formatters[BigInteger::class].formatAllSupportedLocales(123.toBigInteger())).containsExactly(
            entry(SupportedLocales.DEFAULT, "123"),
            entry(SupportedLocales.EN, "123"),
            entry(SupportedLocales.PT_BR, "123"))
    }

    @Test
    fun `should format BigInteger negative value`() {
        assertThat(Formatters[BigInteger::class].formatAllSupportedLocales(123.unaryMinus().toBigInteger())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-123"),
            entry(SupportedLocales.EN, "-123"),
            entry(SupportedLocales.PT_BR, "-123"))
    }

    @Test
    fun `should format BigInteger value with grouping`() {
        assertThat(Formatters[BigInteger::class].formatAllSupportedLocales("987654321012345678910111231451659990".toBigInteger())).containsExactly(
            entry(SupportedLocales.DEFAULT, "987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.EN, "987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.PT_BR, "987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format BigInteger negative value with grouping`() {
        assertThat(Formatters[BigInteger::class].formatAllSupportedLocales("-987654321012345678910111231451659990".toBigInteger())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.EN, "-987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.PT_BR, "-987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format Float value`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(987.0f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "987"),
            entry(SupportedLocales.EN, "987"),
            entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Float negative value`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(-987.0f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987"),
            entry(SupportedLocales.EN, "-987"),
            entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Float value with grouping`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(9_210_123.0f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "9,210,123"),
            entry(SupportedLocales.EN, "9,210,123"),
            entry(SupportedLocales.PT_BR, "9.210.123"))
    }

    @Test
    fun `should format Float negative value with grouping`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(-9_210_123.0f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-9,210,123"),
            entry(SupportedLocales.EN, "-9,210,123"),
            entry(SupportedLocales.PT_BR, "-9.210.123"))
    }

    @Test
    fun `should format Float value with decimal digits`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(0.9876543f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.9876543"),
            entry(SupportedLocales.EN, "0.9876543"),
            entry(SupportedLocales.PT_BR, "0,9876543"))
    }

    @Test
    fun `should format Float value with decimal digits without preserving zeros`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(0.9876543000f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.9876543"),
            entry(SupportedLocales.EN, "0.9876543"),
            entry(SupportedLocales.PT_BR, "0,9876543"))
    }

    @Test
    fun `should format Float negative value with decimal digits`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(-0.9876543f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-0.9876543"),
            entry(SupportedLocales.EN, "-0.9876543"),
            entry(SupportedLocales.PT_BR, "-0,9876543"))
    }

    @Test
    fun `should format Float negative value with decimal digits without preserving zeros`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(-0.98765430f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-0.9876543"),
            entry(SupportedLocales.EN, "-0.9876543"),
            entry(SupportedLocales.PT_BR, "-0,9876543"))
    }

    @Test
    fun `should format Float value with grouping and decimal digits`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(6_543.987f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "6,543.987"),
            entry(SupportedLocales.EN, "6,543.987"),
            entry(SupportedLocales.PT_BR, "6.543,987"))
    }

    @Test
    fun `should format Float value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(6_543.9870f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "6,543.987"),
            entry(SupportedLocales.EN, "6,543.987"),
            entry(SupportedLocales.PT_BR, "6.543,987"))
    }

    @Test
    fun `should format Float negative value with grouping and decimal digits`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(-6_543.987f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-6,543.987"),
            entry(SupportedLocales.EN, "-6,543.987"),
            entry(SupportedLocales.PT_BR, "-6.543,987"))
    }

    @Test
    fun `should format Float negative value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(-6_543.9870f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-6,543.987"),
            entry(SupportedLocales.EN, "-6,543.987"),
            entry(SupportedLocales.PT_BR, "-6.543,987"))
    }

    @Test
    fun `should format Float zero`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(0f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Float zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales(0.0f)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Float zero with decimal digits without preserving zeros`() {
        assertThat(Formatters[Float::class].formatAllSupportedLocales("0.00000".toFloat())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Double value`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(987.toDouble())).containsExactly(
            entry(SupportedLocales.DEFAULT, "987"),
            entry(SupportedLocales.EN, "987"),
            entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Double negative value`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(-987.0)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987"),
            entry(SupportedLocales.EN, "-987"),
            entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Double value with grouping`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(9_876_543_210_123.0)).containsExactly(
            entry(SupportedLocales.DEFAULT, "9,876,543,210,123"),
            entry(SupportedLocales.EN, "9,876,543,210,123"),
            entry(SupportedLocales.PT_BR, "9.876.543.210.123"))
    }

    @Test
    fun `should format Double negative value with grouping`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(-9_876_543_210_123.0)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-9,876,543,210,123"),
            entry(SupportedLocales.EN, "-9,876,543,210,123"),
            entry(SupportedLocales.PT_BR, "-9.876.543.210.123"))
    }

    @Test
    fun `should format Double value with decimal digits`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(0.987654321)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.987654321"),
            entry(SupportedLocales.EN, "0.987654321"),
            entry(SupportedLocales.PT_BR, "0,987654321"))
    }

    @Test
    fun `should format Double value with decimal digits without preserving zeros`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(0.98765432100)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.987654321"),
            entry(SupportedLocales.EN, "0.987654321"),
            entry(SupportedLocales.PT_BR, "0,987654321"))
    }

    @Test
    fun `should format Double negative value with decimal digits`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(-0.987654321)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-0.987654321"),
            entry(SupportedLocales.EN, "-0.987654321"),
            entry(SupportedLocales.PT_BR, "-0,987654321"))
    }

    @Test
    fun `should format Double negative value with decimal digits without preserving zeros`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(-0.987654321000)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-0.987654321"),
            entry(SupportedLocales.EN, "-0.987654321"),
            entry(SupportedLocales.PT_BR, "-0,987654321"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(876_543.987654321)).containsExactly(
            entry(SupportedLocales.DEFAULT, "876,543.987654321"),
            entry(SupportedLocales.EN, "876,543.987654321"),
            entry(SupportedLocales.PT_BR, "876.543,987654321"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(876_543.9876543210)).containsExactly(
            entry(SupportedLocales.DEFAULT, "876,543.987654321"),
            entry(SupportedLocales.EN, "876,543.987654321"),
            entry(SupportedLocales.PT_BR, "876.543,987654321"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(-876_543.987654321)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-876,543.987654321"),
            entry(SupportedLocales.EN, "-876,543.987654321"),
            entry(SupportedLocales.PT_BR, "-876.543,987654321"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits without preserving zeros`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(-876_543.98765432100)).containsExactly(
            entry(SupportedLocales.DEFAULT, "-876,543.987654321"),
            entry(SupportedLocales.EN, "-876,543.987654321"),
            entry(SupportedLocales.PT_BR, "-876.543,987654321"))
    }

    @Test
    fun `should format Double zero`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(0.toDouble())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Double zero with 1 decimal digit without preserving zeros`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales(0.0)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Double zero with decimal digits without preserving zeros`() {
        assertThat(Formatters[Double::class].formatAllSupportedLocales("0.00000".toDouble())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format BigDecimal value`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(987.toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "987"),
            entry(SupportedLocales.EN, "987"),
            entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format BigDecimal negative value`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(987.unaryMinus().toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987"),
            entry(SupportedLocales.EN, "-987"),
            entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format BigDecimal value with grouping`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("987654321012345678910111231451659990".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.EN, "987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.PT_BR, "987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-987654321012345678910111231451659990".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.EN, "-987,654,321,012,345,678,910,111,231,451,659,990"),
            entry(SupportedLocales.PT_BR, "-987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("0.987654321234567891011121314151699786".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.987654321234567891011121314151699786"),
            entry(SupportedLocales.EN, "0.987654321234567891011121314151699786"),
            entry(SupportedLocales.PT_BR, "0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits preserving zeros`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("0.987654321234567891011121314151699786000".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.987654321234567891011121314151699786000"),
            entry(SupportedLocales.EN, "0.987654321234567891011121314151699786000"),
            entry(SupportedLocales.PT_BR, "0,987654321234567891011121314151699786000"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-0.987654321234567891011121314151699786".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-0.987654321234567891011121314151699786"),
            entry(SupportedLocales.EN, "-0.987654321234567891011121314151699786"),
            entry(SupportedLocales.PT_BR, "-0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits preserving zeros`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-0.98765432123456789101112131415169978600".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-0.98765432123456789101112131415169978600"),
            entry(SupportedLocales.EN, "-0.98765432123456789101112131415169978600"),
            entry(SupportedLocales.PT_BR, "-0,98765432123456789101112131415169978600"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("987654321987654321987654321.12345678910111213141516178".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.EN, "987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.PT_BR, "987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits preserving zeros`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("987654321987654321987654321.1234567891011121314151617800".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "987,654,321,987,654,321,987,654,321.1234567891011121314151617800"),
            entry(SupportedLocales.EN, "987,654,321,987,654,321,987,654,321.1234567891011121314151617800"),
            entry(SupportedLocales.PT_BR, "987.654.321.987.654.321.987.654.321,1234567891011121314151617800"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-987654321987654321987654321.12345678910111213141516178".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.EN, "-987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
            entry(SupportedLocales.PT_BR, "-987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits preserving zeros`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-987654321987654321987654321.123456789101112131415161780".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "-987,654,321,987,654,321,987,654,321.123456789101112131415161780"),
            entry(SupportedLocales.EN, "-987,654,321,987,654,321,987,654,321.123456789101112131415161780"),
            entry(SupportedLocales.PT_BR, "-987.654.321.987.654.321.987.654.321,123456789101112131415161780"))
    }

    @Test
    fun `should format BigDecimal zero`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(BigDecimal.ZERO)).containsExactly(
            entry(SupportedLocales.DEFAULT, "0"),
            entry(SupportedLocales.EN, "0"),
            entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format BigDecimal zero with 1 decimal digit preserving zeros`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(0.0.toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.0"),
            entry(SupportedLocales.EN, "0.0"),
            entry(SupportedLocales.PT_BR, "0,0"))
    }

    @Test
    fun `should format BigDecimal zero with decimal digits preserving zeros`() {
        assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("0.00000".toBigDecimal())).containsExactly(
            entry(SupportedLocales.DEFAULT, "0.00000"),
            entry(SupportedLocales.EN, "0.00000"),
            entry(SupportedLocales.PT_BR, "0,00000"))
    }
}