package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.math.BigDecimal
import java.math.BigInteger

class NumberFormatterTest {

    @Test
    fun `should format Byte value`() {
        Assertions.assertThat(Formatters[Byte::class].formatAllSupportedLocales(1)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "1"),
                Assertions.entry(SupportedLocales.EN, "1"),
                Assertions.entry(SupportedLocales.PT_BR, "1"))
    }

    @Test
    fun `should format Byte negative value`() {
        Assertions.assertThat(Formatters[Byte::class].formatAllSupportedLocales(-98)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-98"),
                Assertions.entry(SupportedLocales.EN, "-98"),
                Assertions.entry(SupportedLocales.PT_BR, "-98"))
    }

    @Test
    fun `should format Short value`() {
        Assertions.assertThat(Formatters[Short::class].formatAllSupportedLocales(987)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987"),
                Assertions.entry(SupportedLocales.EN, "987"),
                Assertions.entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Short negative value`() {
        Assertions.assertThat(Formatters[Short::class].formatAllSupportedLocales(-987)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987"),
                Assertions.entry(SupportedLocales.EN, "-987"),
                Assertions.entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Short value with grouping`() {
        Assertions.assertThat(Formatters[Short::class].formatAllSupportedLocales(9_876)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "9,876"),
                Assertions.entry(SupportedLocales.EN, "9,876"),
                Assertions.entry(SupportedLocales.PT_BR, "9.876"))
    }

    @Test
    fun `should format Short negative value with grouping`() {
        Assertions.assertThat(Formatters[Short::class].formatAllSupportedLocales(-9_876)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-9,876"),
                Assertions.entry(SupportedLocales.EN, "-9,876"),
                Assertions.entry(SupportedLocales.PT_BR, "-9.876"))
    }

    @Test
    fun `should format Int value`() {
        Assertions.assertThat(Formatters[Int::class].formatAllSupportedLocales(987)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987"),
                Assertions.entry(SupportedLocales.EN, "987"),
                Assertions.entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Int negative value`() {
        Assertions.assertThat(Formatters[Int::class].formatAllSupportedLocales(-987)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987"),
                Assertions.entry(SupportedLocales.EN, "-987"),
                Assertions.entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Int value with grouping`() {
        Assertions.assertThat(Formatters[Int::class].formatAllSupportedLocales(987_654_321)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987,654,321"),
                Assertions.entry(SupportedLocales.EN, "987,654,321"),
                Assertions.entry(SupportedLocales.PT_BR, "987.654.321"))
    }

    @Test
    fun `should format Int negative value with grouping`() {
        Assertions.assertThat(Formatters[Int::class].formatAllSupportedLocales(-987_654_321)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987,654,321"),
                Assertions.entry(SupportedLocales.EN, "-987,654,321"),
                Assertions.entry(SupportedLocales.PT_BR, "-987.654.321"))
    }

    @Test
    fun `should format Long value`() {
        Assertions.assertThat(Formatters[Long::class].formatAllSupportedLocales(987L)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987"),
                Assertions.entry(SupportedLocales.EN, "987"),
                Assertions.entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Long negative value`() {
        Assertions.assertThat(Formatters[Long::class].formatAllSupportedLocales(-987L)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987"),
                Assertions.entry(SupportedLocales.EN, "-987"),
                Assertions.entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Long value with grouping`() {
        Assertions.assertThat(Formatters[Long::class].formatAllSupportedLocales(98_765_432_109_876_543L)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "98,765,432,109,876,543"),
                Assertions.entry(SupportedLocales.EN, "98,765,432,109,876,543"),
                Assertions.entry(SupportedLocales.PT_BR, "98.765.432.109.876.543"))
    }

    @Test
    fun `should format Long negative value with grouping`() {
        Assertions.assertThat(Formatters[Long::class].formatAllSupportedLocales(-98_765_432_109_876_543L)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-98,765,432,109,876,543"),
                Assertions.entry(SupportedLocales.EN, "-98,765,432,109,876,543"),
                Assertions.entry(SupportedLocales.PT_BR, "-98.765.432.109.876.543"))
    }

    @Test
    fun `should format BigInteger value`() {
        Assertions.assertThat(Formatters[BigInteger::class].formatAllSupportedLocales(123.toBigInteger())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "123"),
                Assertions.entry(SupportedLocales.EN, "123"),
                Assertions.entry(SupportedLocales.PT_BR, "123"))
    }

    @Test
    fun `should format BigInteger negative value`() {
        Assertions.assertThat(Formatters[BigInteger::class].formatAllSupportedLocales(123.unaryMinus().toBigInteger())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-123"),
                Assertions.entry(SupportedLocales.EN, "-123"),
                Assertions.entry(SupportedLocales.PT_BR, "-123"))
    }

    @Test
    fun `should format BigInteger value with grouping`() {
        Assertions.assertThat(Formatters[BigInteger::class].formatAllSupportedLocales("987654321012345678910111231451659990".toBigInteger())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.EN, "987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.PT_BR, "987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format BigInteger negative value with grouping`() {
        Assertions.assertThat(Formatters[BigInteger::class].formatAllSupportedLocales("-987654321012345678910111231451659990".toBigInteger())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.EN, "-987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.PT_BR, "-987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format Float value`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(987.0f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987"),
                Assertions.entry(SupportedLocales.EN, "987"),
                Assertions.entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Float negative value`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(-987.0f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987"),
                Assertions.entry(SupportedLocales.EN, "-987"),
                Assertions.entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Float value with grouping`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(9_210_123.0f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "9,210,123"),
                Assertions.entry(SupportedLocales.EN, "9,210,123"),
                Assertions.entry(SupportedLocales.PT_BR, "9.210.123"))
    }

    @Test
    fun `should format Float negative value with grouping`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(-9_210_123.0f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-9,210,123"),
                Assertions.entry(SupportedLocales.EN, "-9,210,123"),
                Assertions.entry(SupportedLocales.PT_BR, "-9.210.123"))
    }

    @Test
    fun `should format Float value with decimal digits`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(0.9876543f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.9876543"),
                Assertions.entry(SupportedLocales.EN, "0.9876543"),
                Assertions.entry(SupportedLocales.PT_BR, "0,9876543"))
    }

    @Test
    fun `should format Float value with decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(0.9876543000f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.9876543"),
                Assertions.entry(SupportedLocales.EN, "0.9876543"),
                Assertions.entry(SupportedLocales.PT_BR, "0,9876543"))
    }

    @Test
    fun `should format Float negative value with decimal digits`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(-0.9876543f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-0.9876543"),
                Assertions.entry(SupportedLocales.EN, "-0.9876543"),
                Assertions.entry(SupportedLocales.PT_BR, "-0,9876543"))
    }

    @Test
    fun `should format Float negative value with decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(-0.98765430f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-0.9876543"),
                Assertions.entry(SupportedLocales.EN, "-0.9876543"),
                Assertions.entry(SupportedLocales.PT_BR, "-0,9876543"))
    }

    @Test
    fun `should format Float value with grouping and decimal digits`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(6_543.987f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "6,543.987"),
                Assertions.entry(SupportedLocales.EN, "6,543.987"),
                Assertions.entry(SupportedLocales.PT_BR, "6.543,987"))
    }

    @Test
    fun `should format Float value with grouping and decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(6_543.9870f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "6,543.987"),
                Assertions.entry(SupportedLocales.EN, "6,543.987"),
                Assertions.entry(SupportedLocales.PT_BR, "6.543,987"))
    }

    @Test
    fun `should format Float negative value with grouping and decimal digits`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(-6_543.987f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-6,543.987"),
                Assertions.entry(SupportedLocales.EN, "-6,543.987"),
                Assertions.entry(SupportedLocales.PT_BR, "-6.543,987"))
    }

    @Test
    fun `should format Float negative value with grouping and decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(-6_543.9870f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-6,543.987"),
                Assertions.entry(SupportedLocales.EN, "-6,543.987"),
                Assertions.entry(SupportedLocales.PT_BR, "-6.543,987"))
    }

    @Test
    fun `should format Float zero`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(0f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Float zero with 1 decimal digit without preserving zeros`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales(0.0f)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Float zero with decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Float::class].formatAllSupportedLocales("0.00000".toFloat())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Double value`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(987.toDouble())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987"),
                Assertions.entry(SupportedLocales.EN, "987"),
                Assertions.entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format Double negative value`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(-987.0)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987"),
                Assertions.entry(SupportedLocales.EN, "-987"),
                Assertions.entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format Double value with grouping`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(9_876_543_210_123.0)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "9,876,543,210,123"),
                Assertions.entry(SupportedLocales.EN, "9,876,543,210,123"),
                Assertions.entry(SupportedLocales.PT_BR, "9.876.543.210.123"))
    }

    @Test
    fun `should format Double negative value with grouping`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(-9_876_543_210_123.0)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-9,876,543,210,123"),
                Assertions.entry(SupportedLocales.EN, "-9,876,543,210,123"),
                Assertions.entry(SupportedLocales.PT_BR, "-9.876.543.210.123"))
    }

    @Test
    fun `should format Double value with decimal digits`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(0.987654321)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.987654321"),
                Assertions.entry(SupportedLocales.EN, "0.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "0,987654321"))
    }

    @Test
    fun `should format Double value with decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(0.98765432100)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.987654321"),
                Assertions.entry(SupportedLocales.EN, "0.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "0,987654321"))
    }

    @Test
    fun `should format Double negative value with decimal digits`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(-0.987654321)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-0.987654321"),
                Assertions.entry(SupportedLocales.EN, "-0.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "-0,987654321"))
    }

    @Test
    fun `should format Double negative value with decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(-0.987654321000)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-0.987654321"),
                Assertions.entry(SupportedLocales.EN, "-0.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "-0,987654321"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(876_543.987654321)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "876,543.987654321"),
                Assertions.entry(SupportedLocales.EN, "876,543.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "876.543,987654321"))
    }

    @Test
    fun `should format Double value with grouping and decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(876_543.9876543210)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "876,543.987654321"),
                Assertions.entry(SupportedLocales.EN, "876,543.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "876.543,987654321"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(-876_543.987654321)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-876,543.987654321"),
                Assertions.entry(SupportedLocales.EN, "-876,543.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "-876.543,987654321"))
    }

    @Test
    fun `should format Double negative value with grouping and decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(-876_543.98765432100)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-876,543.987654321"),
                Assertions.entry(SupportedLocales.EN, "-876,543.987654321"),
                Assertions.entry(SupportedLocales.PT_BR, "-876.543,987654321"))
    }

    @Test
    fun `should format Double zero`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(0.toDouble())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Double zero with 1 decimal digit without preserving zeros`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales(0.0)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format Double zero with decimal digits without preserving zeros`() {
        Assertions.assertThat(Formatters[Double::class].formatAllSupportedLocales("0.00000".toDouble())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format BigDecimal value`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(987.toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987"),
                Assertions.entry(SupportedLocales.EN, "987"),
                Assertions.entry(SupportedLocales.PT_BR, "987"))
    }

    @Test
    fun `should format BigDecimal negative value`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(987.unaryMinus().toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987"),
                Assertions.entry(SupportedLocales.EN, "-987"),
                Assertions.entry(SupportedLocales.PT_BR, "-987"))
    }

    @Test
    fun `should format BigDecimal value with grouping`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("987654321012345678910111231451659990".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.EN, "987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.PT_BR, "987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-987654321012345678910111231451659990".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.EN, "-987,654,321,012,345,678,910,111,231,451,659,990"),
                Assertions.entry(SupportedLocales.PT_BR, "-987.654.321.012.345.678.910.111.231.451.659.990"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("0.987654321234567891011121314151699786".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.987654321234567891011121314151699786"),
                Assertions.entry(SupportedLocales.EN, "0.987654321234567891011121314151699786"),
                Assertions.entry(SupportedLocales.PT_BR, "0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal value with decimal digits preserving zeros`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("0.987654321234567891011121314151699786000".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.987654321234567891011121314151699786000"),
                Assertions.entry(SupportedLocales.EN, "0.987654321234567891011121314151699786000"),
                Assertions.entry(SupportedLocales.PT_BR, "0,987654321234567891011121314151699786000"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-0.987654321234567891011121314151699786".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-0.987654321234567891011121314151699786"),
                Assertions.entry(SupportedLocales.EN, "-0.987654321234567891011121314151699786"),
                Assertions.entry(SupportedLocales.PT_BR, "-0,987654321234567891011121314151699786"))
    }

    @Test
    fun `should format BigDecimal negative value with decimal digits preserving zeros`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-0.98765432123456789101112131415169978600".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-0.98765432123456789101112131415169978600"),
                Assertions.entry(SupportedLocales.EN, "-0.98765432123456789101112131415169978600"),
                Assertions.entry(SupportedLocales.PT_BR, "-0,98765432123456789101112131415169978600"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("987654321987654321987654321.12345678910111213141516178".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
                Assertions.entry(SupportedLocales.EN, "987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
                Assertions.entry(SupportedLocales.PT_BR, "987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal value with grouping and decimal digits preserving zeros`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("987654321987654321987654321.1234567891011121314151617800".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "987,654,321,987,654,321,987,654,321.1234567891011121314151617800"),
                Assertions.entry(SupportedLocales.EN, "987,654,321,987,654,321,987,654,321.1234567891011121314151617800"),
                Assertions.entry(SupportedLocales.PT_BR, "987.654.321.987.654.321.987.654.321,1234567891011121314151617800"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-987654321987654321987654321.12345678910111213141516178".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
                Assertions.entry(SupportedLocales.EN, "-987,654,321,987,654,321,987,654,321.12345678910111213141516178"),
                Assertions.entry(SupportedLocales.PT_BR, "-987.654.321.987.654.321.987.654.321,12345678910111213141516178"))
    }

    @Test
    fun `should format BigDecimal negative value with grouping and decimal digits preserving zeros`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("-987654321987654321987654321.123456789101112131415161780".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "-987,654,321,987,654,321,987,654,321.123456789101112131415161780"),
                Assertions.entry(SupportedLocales.EN, "-987,654,321,987,654,321,987,654,321.123456789101112131415161780"),
                Assertions.entry(SupportedLocales.PT_BR, "-987.654.321.987.654.321.987.654.321,123456789101112131415161780"))
    }

    @Test
    fun `should format BigDecimal zero`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(BigDecimal.ZERO)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0"),
                Assertions.entry(SupportedLocales.EN, "0"),
                Assertions.entry(SupportedLocales.PT_BR, "0"))
    }

    @Test
    fun `should format BigDecimal zero with 1 decimal digit preserving zeros`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales(0.0.toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.0"),
                Assertions.entry(SupportedLocales.EN, "0.0"),
                Assertions.entry(SupportedLocales.PT_BR, "0,0"))
    }

    @Test
    fun `should format BigDecimal zero with decimal digits preserving zeros`() {
        Assertions.assertThat(Formatters[BigDecimal::class].formatAllSupportedLocales("0.00000".toBigDecimal())).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.00000"),
                Assertions.entry(SupportedLocales.EN, "0.00000"),
                Assertions.entry(SupportedLocales.PT_BR, "0,00000"))
    }
}