package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales

private object AnyFormatterFixture {

    enum class TestEnum { E1, E2 }
    object TestObject {
        override fun toString(): String = "TestObject"
    }
}

class AnyFormatterTest {

    @Test
    fun `should format Any value`() {
        Assertions.assertThat(Formatters[AnyFormatterFixture.TestObject::class].formatAllSupportedLocales(AnyFormatterFixture.TestObject)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "TestObject"),
                Assertions.entry(SupportedLocales.EN, "TestObject"),
                Assertions.entry(SupportedLocales.PT_BR, "TestObject"))
    }

    @Test
    fun `should format Enum value`() {
        Assertions.assertThat(Formatters[Enum::class].formatAllSupportedLocales(AnyFormatterFixture.TestEnum.E1)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "E1"),
                Assertions.entry(SupportedLocales.EN, "E1"),
                Assertions.entry(SupportedLocales.PT_BR, "E1"))
    }

    @Test
    fun `should format String value`() {
        Assertions.assertThat(Formatters[String::class].formatAllSupportedLocales("test")).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "test"),
                Assertions.entry(SupportedLocales.EN, "test"),
                Assertions.entry(SupportedLocales.PT_BR, "test"))
    }

    @Test
    fun `should format Char value`() {
        Assertions.assertThat(Formatters[Char::class].formatAllSupportedLocales('A')).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "A"),
                Assertions.entry(SupportedLocales.EN, "A"),
                Assertions.entry(SupportedLocales.PT_BR, "A"))
    }

    @Test
    fun `should format Boolean value`() {
        Assertions.assertThat(Formatters[Boolean::class].formatAllSupportedLocales(true)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "true"),
                Assertions.entry(SupportedLocales.EN, "true"),
                Assertions.entry(SupportedLocales.PT_BR, "true"))
    }
}