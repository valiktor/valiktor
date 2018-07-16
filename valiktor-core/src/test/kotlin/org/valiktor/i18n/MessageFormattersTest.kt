package org.valiktor.i18n

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.i18n.FormattersFixture.TestEnum
import org.valiktor.i18n.FormattersFixture.TestFormatter
import org.valiktor.i18n.FormattersFixture.TestObject
import org.valiktor.i18n.FormattersFixture.TestParentL
import org.valiktor.i18n.FormattersFixture.TestParentLFormatter
import org.valiktor.i18n.FormattersFixture.TestParentParentL
import org.valiktor.i18n.FormattersFixture.TestParentParentLFormatter
import org.valiktor.i18n.FormattersFixture.TestParentParentParent
import org.valiktor.i18n.FormattersFixture.TestParentParentParentFormatter
import org.valiktor.i18n.FormattersFixture.TestParentParentR
import org.valiktor.i18n.FormattersFixture.TestParentParentRFormatter
import org.valiktor.i18n.FormattersFixture.TestParentR
import org.valiktor.i18n.FormattersFixture.TestParentRFormatter
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

private object FormattersFixture {

    interface TestParentParentParent
    interface TestParentParentL : TestParentParentParent
    interface TestParentParentR : TestParentParentParent
    interface TestParentL : TestParentParentL, TestParentParentR
    interface TestParentR : TestParentParentL, TestParentParentR
    object TestObject : TestParentL, TestParentR {
        override fun toString(): String = "TestObject"
    }

    enum class TestEnum { E1, E2 }

    object TestFormatter : Formatter<TestObject> {
        override fun format(value: TestObject, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentLFormatter : Formatter<TestParentL> {
        override fun format(value: TestParentL, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentRFormatter : Formatter<TestParentR> {
        override fun format(value: TestParentR, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentParentLFormatter : Formatter<TestParentParentL> {
        override fun format(value: TestParentParentL, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentParentRFormatter : Formatter<TestParentParentR> {
        override fun format(value: TestParentParentR, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentParentParentFormatter : Formatter<TestParentParentParent> {
        override fun format(value: TestParentParentParent, messageBundle: MessageBundle): String = value.toString()
    }
}

class FormattersTest {

    @BeforeEach
    fun `remove formatters`() {
        Formatters -= TestObject::class
        Formatters -= TestParentL::class
        Formatters -= TestParentR::class
        Formatters -= TestParentParentL::class
        Formatters -= TestParentParentR::class
        Formatters -= TestParentParentParent::class
    }

    @Test
    fun `should get default formatters`() {
        assertAll(
                { assertEquals(Formatters[Any::class], AnyFormatter) },
                { assertEquals(Formatters[Number::class], NumberFormatter) },
                { assertEquals(Formatters[Date::class], DateFormatter) },
                { assertEquals(Formatters[Calendar::class], CalendarFormatter) },
                { assertEquals(Formatters[Iterable::class], IterableFormatter) },
                { assertEquals(Formatters[Array<Any>::class], ArrayFormatter) }
        )
    }

    @Test
    fun `should get any formatter`() {
        assertEquals(Formatters[TestObject::class], AnyFormatter)
    }

    @Test
    fun `should get object formatter`() {
        Formatters[TestObject::class] = TestFormatter
        Formatters[TestParentL::class] = TestParentLFormatter
        Formatters[TestParentR::class] = TestParentRFormatter

        assertEquals(Formatters[TestObject::class], TestFormatter)
    }

    @Test
    fun `should get left parent formatter`() {
        Formatters[TestParentL::class] = TestParentLFormatter
        Formatters[TestParentR::class] = TestParentRFormatter

        assertEquals(Formatters[TestObject::class], TestParentLFormatter)
    }

    @Test
    fun `should get right parent formatter`() {
        Formatters[TestParentR::class] = TestParentRFormatter
        Formatters[TestParentParentL::class] = TestParentParentLFormatter

        assertEquals(Formatters[TestObject::class], TestParentRFormatter)
    }

    @Test
    fun `should get left parent parent formatter`() {
        Formatters[TestParentParentL::class] = TestParentParentLFormatter
        Formatters[TestParentParentR::class] = TestParentParentRFormatter

        assertEquals(Formatters[TestObject::class], TestParentParentLFormatter)
    }

    @Test
    fun `should get right parent parent formatter`() {
        Formatters[TestParentParentR::class] = TestParentParentRFormatter
        Formatters[TestParentParentParent::class] = TestParentParentParentFormatter

        assertEquals(Formatters[TestObject::class], TestParentParentRFormatter)
    }

    @Test
    fun `should get parent parent parent formatter`() {
        Formatters[TestParentParentParent::class] = TestParentParentParentFormatter

        assertEquals(Formatters[TestObject::class], TestParentParentParentFormatter)
    }
}

class AnyFormatterTest {

    @Test
    fun `should format Any value`() {
        assertThat(Formatters[TestObject::class].formatAllSupportedLocales(TestObject)).containsExactly(
                entry(SupportedLocales.DEFAULT, "TestObject"),
                entry(SupportedLocales.EN, "TestObject"),
                entry(SupportedLocales.PT_BR, "TestObject"))
    }

    @Test
    fun `should format Enum value`() {
        assertThat(Formatters[Enum::class].formatAllSupportedLocales(TestEnum.E1)).containsExactly(
                entry(SupportedLocales.DEFAULT, "E1"),
                entry(SupportedLocales.EN, "E1"),
                entry(SupportedLocales.PT_BR, "E1"))
    }

    @Test
    fun `should format String value`() {
        assertThat(Formatters[String::class].formatAllSupportedLocales("test")).containsExactly(
                entry(SupportedLocales.DEFAULT, "test"),
                entry(SupportedLocales.EN, "test"),
                entry(SupportedLocales.PT_BR, "test"))
    }

    @Test
    fun `should format Char value`() {
        assertThat(Formatters[Char::class].formatAllSupportedLocales('A')).containsExactly(
                entry(SupportedLocales.DEFAULT, "A"),
                entry(SupportedLocales.EN, "A"),
                entry(SupportedLocales.PT_BR, "A"))
    }

    @Test
    fun `should format Boolean value`() {
        assertThat(Formatters[Boolean::class].formatAllSupportedLocales(true)).containsExactly(
                entry(SupportedLocales.DEFAULT, "true"),
                entry(SupportedLocales.EN, "true"),
                entry(SupportedLocales.PT_BR, "true"))
    }
}

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

class DateFormatterTest {

    @Test
    fun `should format date`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 0, 0, 0)

        assertThat(Formatters[Date::class].formatAllSupportedLocales(calendar.time)).containsExactly(
                entry(SupportedLocales.DEFAULT, "12/31/2018"),
                entry(SupportedLocales.EN, "12/31/2018"),
                entry(SupportedLocales.PT_BR, "31/12/2018"))
    }

    @Test
    fun `should format date and time`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 23, 58, 59)

        assertThat(Formatters[Date::class].formatAllSupportedLocales(calendar.time)).containsExactly(
                entry(SupportedLocales.DEFAULT, "12/31/2018 23:58:59"),
                entry(SupportedLocales.EN, "12/31/2018 23:58:59"),
                entry(SupportedLocales.PT_BR, "31/12/2018 23:58:59"))
    }
}

class CalendarFormatterTest {

    @Test
    fun `should format date`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 0, 0, 0)

        assertThat(Formatters[Calendar::class].formatAllSupportedLocales(calendar)).containsExactly(
                entry(SupportedLocales.DEFAULT, "12/31/2018"),
                entry(SupportedLocales.EN, "12/31/2018"),
                entry(SupportedLocales.PT_BR, "31/12/2018"))
    }

    @Test
    fun `should format date and time`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 23, 58, 59)

        assertThat(Formatters[Calendar::class].formatAllSupportedLocales(calendar)).containsExactly(
                entry(SupportedLocales.DEFAULT, "12/31/2018 23:58:59"),
                entry(SupportedLocales.EN, "12/31/2018 23:58:59"),
                entry(SupportedLocales.PT_BR, "31/12/2018 23:58:59"))
    }
}

class IterableFormatterTest {

    @Test
    fun `should format Iterable of Any`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(TestObject, TestObject))).containsExactly(
                entry(SupportedLocales.DEFAULT, "TestObject, TestObject"),
                entry(SupportedLocales.EN, "TestObject, TestObject"),
                entry(SupportedLocales.PT_BR, "TestObject, TestObject"))
    }

    @Test
    fun `should format Iterable of Enum`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(TestEnum.E1, TestEnum.E2))).containsExactly(
                entry(SupportedLocales.DEFAULT, "E1, E2"),
                entry(SupportedLocales.EN, "E1, E2"),
                entry(SupportedLocales.PT_BR, "E1, E2"))
    }

    @Test
    fun `should format Iterable of String`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf("test1", "test2"))).containsExactly(
                entry(SupportedLocales.DEFAULT, "test1, test2"),
                entry(SupportedLocales.EN, "test1, test2"),
                entry(SupportedLocales.PT_BR, "test1, test2"))
    }

    @Test
    fun `should format Iterable of Char`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf('A', 'B'))).containsExactly(
                entry(SupportedLocales.DEFAULT, "A, B"),
                entry(SupportedLocales.EN, "A, B"),
                entry(SupportedLocales.PT_BR, "A, B"))
    }

    @Test
    fun `should format Iterable of Boolean`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(true, false))).containsExactly(
                entry(SupportedLocales.DEFAULT, "true, false"),
                entry(SupportedLocales.EN, "true, false"),
                entry(SupportedLocales.PT_BR, "true, false"))
    }

    @Test
    fun `should format Iterable of Byte`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf<Byte>(1, 50, -100))).containsExactly(
                entry(SupportedLocales.DEFAULT, "1, 50, -100"),
                entry(SupportedLocales.EN, "1, 50, -100"),
                entry(SupportedLocales.PT_BR, "1, 50, -100"))
    }

    @Test
    fun `should format Iterable of Short`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf<Short>(0, 123, -987, 1_234, -9_876))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234, -9,876"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234, -9,876"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234, -9.876"))
    }

    @Test
    fun `should format Iterable of Int`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0, 123, -987, 1_234_567, -9_876_543))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567, -9,876,543"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234,567, -9,876,543"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567, -9.876.543"))
    }

    @Test
    fun `should format Iterable of Long`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0L, 123L, -987L, 1_234_567_891_235_987_587L, -9_876_543_549_852_546L))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567.891.235.987.587, -9.876.543.549.852.546"))
    }

    @Test
    fun `should format Iterable of BigInteger`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0.toBigInteger(), 123.toBigInteger(), 987.unaryMinus().toBigInteger(),
                "987654321012345678910111231451659990".toBigInteger(), "-845765952346154579884659654872130".toBigInteger()))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                entry(SupportedLocales.EN, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 987.654.321.012.345.678.910.111.231.451.659.990, -845.765.952.346.154.579.884.659.654.872.130"))
    }

    @Test
    fun `should format Iterable of Float`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0.0f, 123f, -987f, 1_234.12f, -9_876.789f))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234.12, -9,876.789"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234.12, -9,876.789"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234,12, -9.876,789"))
    }

    @Test
    fun `should format Iterable of Double`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf(0.0, 123, -987, 132_234.12345678, -129_876.789876))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                entry(SupportedLocales.EN, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 132.234,12345678, -129.876,789876"))
    }

    @Test
    fun `should format Iterable of BigDecimal`() {
        assertThat(Formatters[Iterable::class].formatAllSupportedLocales(listOf("0.00".toBigDecimal(), 123.toBigDecimal(), 987.unaryMinus().toBigDecimal(),
                "7896541236548.78964843546840".toBigDecimal(), "-7895462489785454.258965899".toBigDecimal()))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                entry(SupportedLocales.EN, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                entry(SupportedLocales.PT_BR, "0,00, 123, -987, 7.896.541.236.548,78964843546840, -7.895.462.489.785.454,258965899"))
    }
}

class ArrayFormatterTest {

    @Test
    fun `should format Array of Any`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(TestObject, TestObject))).containsExactly(
                entry(SupportedLocales.DEFAULT, "TestObject, TestObject"),
                entry(SupportedLocales.EN, "TestObject, TestObject"),
                entry(SupportedLocales.PT_BR, "TestObject, TestObject"))
    }

    @Test
    fun `should format Array of Enum`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(TestEnum.E1, TestEnum.E2))).containsExactly(
                entry(SupportedLocales.DEFAULT, "E1, E2"),
                entry(SupportedLocales.EN, "E1, E2"),
                entry(SupportedLocales.PT_BR, "E1, E2"))
    }

    @Test
    fun `should format Array of String`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf("test1", "test2"))).containsExactly(
                entry(SupportedLocales.DEFAULT, "test1, test2"),
                entry(SupportedLocales.EN, "test1, test2"),
                entry(SupportedLocales.PT_BR, "test1, test2"))
    }

    @Test
    fun `should format Array of Char`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf('A', 'B'))).containsExactly(
                entry(SupportedLocales.DEFAULT, "A, B"),
                entry(SupportedLocales.EN, "A, B"),
                entry(SupportedLocales.PT_BR, "A, B"))
    }

    @Test
    fun `should format Array of Boolean`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(true, false))).containsExactly(
                entry(SupportedLocales.DEFAULT, "true, false"),
                entry(SupportedLocales.EN, "true, false"),
                entry(SupportedLocales.PT_BR, "true, false"))
    }

    @Test
    fun `should format Array of Byte`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(1.toByte(), 50.toByte(), 100.unaryMinus().toByte()))).containsExactly(
                entry(SupportedLocales.DEFAULT, "1, 50, -100"),
                entry(SupportedLocales.EN, "1, 50, -100"),
                entry(SupportedLocales.PT_BR, "1, 50, -100"))
    }

    @Test
    fun `should format Array of Short`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.toShort(), 123.toShort(), 987.unaryMinus().toShort(), 1_234.toShort(), 9_876.unaryMinus().toShort()))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234, -9,876"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234, -9,876"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234, -9.876"))
    }

    @Test
    fun `should format Array of Int`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0, 123, -987, 1_234_567, -9_876_543))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567, -9,876,543"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234,567, -9,876,543"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567, -9.876.543"))
    }

    @Test
    fun `should format Array of Long`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0L, 123L, -987L, 1_234_567_891_235_987_587L, -9_876_543_549_852_546L))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234,567,891,235,987,587, -9,876,543,549,852,546"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234.567.891.235.987.587, -9.876.543.549.852.546"))
    }

    @Test
    fun `should format Array of BigInteger`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.toBigInteger(), 123.toBigInteger(), 987.unaryMinus().toBigInteger(),
                "987654321012345678910111231451659990".toBigInteger(), "-845765952346154579884659654872130".toBigInteger()))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                entry(SupportedLocales.EN, "0, 123, -987, 987,654,321,012,345,678,910,111,231,451,659,990, -845,765,952,346,154,579,884,659,654,872,130"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 987.654.321.012.345.678.910.111.231.451.659.990, -845.765.952.346.154.579.884.659.654.872.130"))
    }

    @Test
    fun `should format Array of Float`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.0f, 123f, -987f, 1_234.12f, -9_876.789f))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 1,234.12, -9,876.789"),
                entry(SupportedLocales.EN, "0, 123, -987, 1,234.12, -9,876.789"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 1.234,12, -9.876,789"))
    }

    @Test
    fun `should format Array of Double`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf(0.0, 123, -987, 132_234.12345678, -129_876.789876))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                entry(SupportedLocales.EN, "0, 123, -987, 132,234.12345678, -129,876.789876"),
                entry(SupportedLocales.PT_BR, "0, 123, -987, 132.234,12345678, -129.876,789876"))
    }

    @Test
    fun `should format Array of BigDecimal`() {
        assertThat(Formatters[Array<Any>::class].formatAllSupportedLocales(arrayOf("0.00".toBigDecimal(), 123.toBigDecimal(), 987.unaryMinus().toBigDecimal(),
                "7896541236548.78964843546840".toBigDecimal(), "-7895462489785454.258965899".toBigDecimal()))).containsExactly(
                entry(SupportedLocales.DEFAULT, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                entry(SupportedLocales.EN, "0.00, 123, -987, 7,896,541,236,548.78964843546840, -7,895,462,489,785,454.258965899"),
                entry(SupportedLocales.PT_BR, "0,00, 123, -987, 7.896.541.236.548,78964843546840, -7.895.462.489.785.454,258965899"))
    }
}