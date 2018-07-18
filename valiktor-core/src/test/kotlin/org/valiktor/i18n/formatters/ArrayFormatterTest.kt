package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales

private object ArrayFormatterFixture {

    enum class TestEnum { E1, E2 }
    object TestObject {
        override fun toString(): String = "TestObject"
    }
}

class ArrayFormatterTest {

    @Test
    fun `should format Array of Any`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(ArrayFormatterFixture.TestObject, ArrayFormatterFixture.TestObject))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "TestObject, TestObject"),
                Assertions.entry(SupportedLocales.EN, "TestObject, TestObject"),
                Assertions.entry(SupportedLocales.PT_BR, "TestObject, TestObject"))
    }

    @Test
    fun `should format Array of Enum`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(ArrayFormatterFixture.TestEnum.E1, ArrayFormatterFixture.TestEnum.E2))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "E1, E2"),
                Assertions.entry(SupportedLocales.EN, "E1, E2"),
                Assertions.entry(SupportedLocales.PT_BR, "E1, E2"))
    }

    @Test
    fun `should format Array of String`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf("test1", "test2"))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "test1, test2"),
                Assertions.entry(SupportedLocales.EN, "test1, test2"),
                Assertions.entry(SupportedLocales.PT_BR, "test1, test2"))
    }

    @Test
    fun `should format Array of Char`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf('A', 'B'))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "A, B"),
                Assertions.entry(SupportedLocales.EN, "A, B"),
                Assertions.entry(SupportedLocales.PT_BR, "A, B"))
    }

    @Test
    fun `should format Array of Boolean`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(true, false))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "true, false"),
                Assertions.entry(SupportedLocales.EN, "true, false"),
                Assertions.entry(SupportedLocales.PT_BR, "true, false"))
    }

    @Test
    fun `should format Array of Byte`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(1.toByte(), 50.toByte(), 100.unaryMinus().toByte()))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "1, 50, -100"),
                Assertions.entry(SupportedLocales.EN, "1, 50, -100"),
                Assertions.entry(SupportedLocales.PT_BR, "1, 50, -100"))
    }

    @Test
    fun `should format Array of Short`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.toShort(), 123.toShort(), 987.unaryMinus().toShort(), 1_234.toShort(), 9_876.unaryMinus().toShort()))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234, -9,876"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234, -9,876"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234, -9.876"))
    }

    @Test
    fun `should format Array of Int`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0, 123, -987, 1_234_567, -9_876_543))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567, -9,876,543"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234,567, -9,876,543"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567, -9.876.543"))
    }

    @Test
    fun `should format Array of Long`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0L, 123L, -987L, 1_234_567_891_235_987_587L, -9_876_543_549_852_546L))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567.891.235.987.587, -9.876.543.549.852.546"))
    }

    @Test
    fun `should format Array of BigInteger`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.toBigInteger(), 123.toBigInteger(), 987.unaryMinus().toBigInteger(),
                "987654321012345678910111231451659990".toBigInteger(), "-845765952346154579884659654872130".toBigInteger()))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 987.654.321.012.345.678.910.111.231.451.659.990, -845.765.952.346.154.579.884.659.654.872.130"))
    }

    @Test
    fun `should format Array of Float`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.0f, 123f, -987f, 1_234.12f, -9_876.789f))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234.12, -9,876.789"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234.12, -9,876.789"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234,12, -9.876,789"))
    }

    @Test
    fun `should format Array of Double`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.0, 123, -987, 132_234.12345678, -129_876.789876))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 132.234,12345678, -129.876,789876"))
    }

    @Test
    fun `should format Array of BigDecimal`() {
        Assertions.assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf("0.00".toBigDecimal(), 123.toBigDecimal(), 987.unaryMinus().toBigDecimal(),
                "7896541236548.78964843546840".toBigDecimal(), "-7895462489785454.258965899".toBigDecimal()))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                Assertions.entry(SupportedLocales.EN, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                Assertions.entry(SupportedLocales.PT_BR, "0,00, 123, -987, 7.896.541.236.548,78964843546840, -7.895.462.489.785.454,258965899"))
    }
}