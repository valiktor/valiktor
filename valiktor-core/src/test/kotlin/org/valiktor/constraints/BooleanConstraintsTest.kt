package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages

class TrueTest {

    @Test
    fun `should validate messages`() {
        assertThat(True.interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must be true"),
                entry(SupportedLocales.EN, "Must be true"),
                entry(SupportedLocales.PT_BR, "Deve ser verdadeiro"))
    }
}

class FalseTest {

    @Test
    fun `should validate messages`() {
        assertThat(False.interpolatedMessages()).containsExactly(
                entry(SupportedLocales.DEFAULT, "Must be false"),
                entry(SupportedLocales.EN, "Must be false"),
                entry(SupportedLocales.PT_BR, "Deve ser falso"))
    }
}