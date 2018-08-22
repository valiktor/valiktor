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