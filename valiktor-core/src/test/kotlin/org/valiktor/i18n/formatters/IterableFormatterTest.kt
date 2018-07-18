package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales

private object IterableFormatterFixture {

    enum class TestEnum { E1, E2 }
    object TestObject {
        override fun toString(): String = "TestObject"
    }
}

class IterableFormatterTest {

    @Test
    fun `should format Iterable of Any`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(IterableFormatterFixture.TestObject, IterableFormatterFixture.TestObject))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "TestObject, TestObject"),
                Assertions.entry(SupportedLocales.EN, "TestObject, TestObject"),
                Assertions.entry(SupportedLocales.PT_BR, "TestObject, TestObject"))
    }

    @Test
    fun `should format Iterable of Enum`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(IterableFormatterFixture.TestEnum.E1, IterableFormatterFixture.TestEnum.E2))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "E1, E2"),
                Assertions.entry(SupportedLocales.EN, "E1, E2"),
                Assertions.entry(SupportedLocales.PT_BR, "E1, E2"))
    }

    @Test
    fun `should format Iterable of String`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf("test1", "test2"))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "test1, test2"),
                Assertions.entry(SupportedLocales.EN, "test1, test2"),
                Assertions.entry(SupportedLocales.PT_BR, "test1, test2"))
    }

    @Test
    fun `should format Iterable of Char`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf('A', 'B'))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "A, B"),
                Assertions.entry(SupportedLocales.EN, "A, B"),
                Assertions.entry(SupportedLocales.PT_BR, "A, B"))
    }

    @Test
    fun `should format Iterable of Boolean`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(true, false))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "true, false"),
                Assertions.entry(SupportedLocales.EN, "true, false"),
                Assertions.entry(SupportedLocales.PT_BR, "true, false"))
    }

    @Test
    fun `should format Iterable of Byte`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf<Byte>(1, 50, -100))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "1, 50, -100"),
                Assertions.entry(SupportedLocales.EN, "1, 50, -100"),
                Assertions.entry(SupportedLocales.PT_BR, "1, 50, -100"))
    }

    @Test
    fun `should format Iterable of Short`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf<Short>(0, 123, -987, 1_234, -9_876))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234, -9,876"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234, -9,876"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234, -9.876"))
    }

    @Test
    fun `should format Iterable of Int`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0, 123, -987, 1_234_567, -9_876_543))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567, -9,876,543"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234,567, -9,876,543"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567, -9.876.543"))
    }

    @Test
    fun `should format Iterable of Long`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0L, 123L, -987L, 1_234_567_891_235_987_587L, -9_876_543_549_852_546L))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567.891.235.987.587, -9.876.543.549.852.546"))
    }

    @Test
    fun `should format Iterable of BigInteger`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0.toBigInteger(), 123.toBigInteger(), 987.unaryMinus().toBigInteger(),
                "987654321012345678910111231451659990".toBigInteger(), "-845765952346154579884659654872130".toBigInteger()))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 987.654.321.012.345.678.910.111.231.451.659.990, -845.765.952.346.154.579.884.659.654.872.130"))
    }

    @Test
    fun `should format Iterable of Float`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0.0f, 123f, -987f, 1_234.12f, -9_876.789f))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234.12, -9,876.789"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 1,234.12, -9,876.789"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234,12, -9.876,789"))
    }

    @Test
    fun `should format Iterable of Double`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0.0, 123, -987, 132_234.12345678, -129_876.789876))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                Assertions.entry(SupportedLocales.EN, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                Assertions.entry(SupportedLocales.PT_BR, "0, 123, -987, 132.234,12345678, -129.876,789876"))
    }

    @Test
    fun `should format Iterable of BigDecimal`() {
        Assertions.assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf("0.00".toBigDecimal(), 123.toBigDecimal(), 987.unaryMinus().toBigDecimal(),
                "7896541236548.78964843546840".toBigDecimal(), "-7895462489785454.258965899".toBigDecimal()))).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                Assertions.entry(SupportedLocales.EN, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                Assertions.entry(SupportedLocales.PT_BR, "0,00, 123, -987, 7.896.541.236.548,78964843546840, -7.895.462.489.785.454,258965899"))
    }
}