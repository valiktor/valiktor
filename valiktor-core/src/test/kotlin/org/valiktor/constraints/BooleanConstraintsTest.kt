package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Locales
import org.valiktor.i18n.interpolatedMessages

class TrueTest {

    @Test
    fun `should validate messages`() {
        assertThat(True.interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be true"),
                entry(Locales.EN, "Must be true"),
                entry(Locales.PT_BR, "Deve ser verdadeiro"))
    }
}

class FalseTest {

    @Test
    fun `should validate messages`() {
        assertThat(False.interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be false"),
                entry(Locales.EN, "Must be false"),
                entry(Locales.PT_BR, "Deve ser falso"))
    }
}