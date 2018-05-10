package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Locales
import org.valiktor.i18n.interpolatedMessages

class NullTest {

    @Test
    fun `should validate messages`() {
        assertThat(Null.interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be null"),
                entry(Locales.EN, "Must be null"),
                entry(Locales.PT_BR, "Deve ser nulo"))
    }
}

class NotNullTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotNull.interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be null"),
                entry(Locales.EN, "Must not be null"),
                entry(Locales.PT_BR, "Não deve ser nulo"))
    }
}

class EqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(Equals(1).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be equal to 1"),
                entry(Locales.EN, "Must be equal to 1"),
                entry(Locales.PT_BR, "Deve ser igual a 1"))
    }
}

class NotEqualsTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEquals(1).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be equal to 1"),
                entry(Locales.EN, "Must not be equal to 1"),
                entry(Locales.PT_BR, "Não deve ser igual a 1"))
    }
}

class InTest {

    @Test
    fun `should validate messages`() {
        assertThat(In(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be in 1, 2, 3"),
                entry(Locales.EN, "Must be in 1, 2, 3"),
                entry(Locales.PT_BR, "Deve ser um desses: 1, 2, 3"))
    }
}

class NotInTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotIn(setOf(1, 2, 3)).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be in 1, 2, 3"),
                entry(Locales.EN, "Must not be in 1, 2, 3"),
                entry(Locales.PT_BR, "Não deve ser um desses: 1, 2, 3"))
    }
}

class ValidTest {

    @Test
    fun `should validate messages`() {
        assertThat(Valid.interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be valid"),
                entry(Locales.EN, "Must be valid"),
                entry(Locales.PT_BR, "Deve ser válido"))
    }
}