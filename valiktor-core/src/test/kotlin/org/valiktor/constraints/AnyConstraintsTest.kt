package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages

class NullTest {

    @Test
    fun `should validate messages`() {
        assertThat(Null.interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must be null"),
                entry(SupportedLocales.EN, "Must be null"),
                entry(SupportedLocales.PT_BR, "Deve ser nulo"))
    }
}

class NotNullTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotNull.interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must not be null"),
                entry(SupportedLocales.EN, "Must not be null"),
                entry(SupportedLocales.PT_BR, "Não deve ser nulo"))
    }
}

class EqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(Equals(1).interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must be equal to 1"),
                entry(SupportedLocales.EN, "Must be equal to 1"),
                entry(SupportedLocales.PT_BR, "Deve ser igual a 1"))
    }
}

class NotEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEquals(1).interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must not be equal to 1"),
                entry(SupportedLocales.EN, "Must not be equal to 1"),
                entry(SupportedLocales.PT_BR, "Não deve ser igual a 1"))
    }
}

class InTest {

    @Test
    fun `should validate messages`() {
        assertThat(In(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must be in 1, 2, 3"),
                entry(SupportedLocales.EN, "Must be in 1, 2, 3"),
                entry(SupportedLocales.PT_BR, "Deve ser um desses: 1, 2, 3"))
    }
}

class NotInTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotIn(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must not be in 1, 2, 3"),
                entry(SupportedLocales.EN, "Must not be in 1, 2, 3"),
                entry(SupportedLocales.PT_BR, "Não deve ser um desses: 1, 2, 3"))
    }
}

class ValidTest {

    @Test
    fun `should validate messages`() {
        assertThat(Valid.interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must be valid"),
                entry(SupportedLocales.EN, "Must be valid"),
                entry(SupportedLocales.PT_BR, "Deve ser válido"))
    }
}