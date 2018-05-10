package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Locales
import org.valiktor.i18n.interpolatedMessages

class LessTest {

    @Test
    fun `should validate messages`() {
        assertThat(Less(1).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be less than 1"),
                entry(Locales.EN, "Must be less than 1"),
                entry(Locales.PT_BR, "Deve ser menor que 1"))
    }
}

class LessOrEqualTest {

    @Test
    fun `should validate messages`() {
        assertThat(LessOrEqual(5).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be less than or equal to 5"),
                entry(Locales.EN, "Must be less than or equal to 5"),
                entry(Locales.PT_BR, "Deve ser menor ou igual a 5"))
    }
}

class GreaterTest {

    @Test
    fun `should validate messages`() {
        assertThat(Greater(10).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be greater than 10"),
                entry(Locales.EN, "Must be greater than 10"),
                entry(Locales.PT_BR, "Deve ser maior que 10"))
    }
}

class GreaterOrEqualTest {

    @Test
    fun `should validate messages`() {
        assertThat(GreaterOrEqual(15).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be greater than or equal to 15"),
                entry(Locales.EN, "Must be greater than or equal to 15"),
                entry(Locales.PT_BR, "Deve ser maior ou igual a 15"))
    }
}

class BetweenTest {

    @Test
    fun `should validate messages`() {
        assertThat(Between(start = 1, end = 10).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be between 1 and 10"),
                entry(Locales.EN, "Must be between 1 and 10"),
                entry(Locales.PT_BR, "Deve estar entre 1 e 10"))
    }
}

class NotBetweenTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotBetween(start = 1, end = 10).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be between 1 and 10"),
                entry(Locales.EN, "Must not be between 1 and 10"),
                entry(Locales.PT_BR, "Não deve estar entre 1 e 10"))
    }
}