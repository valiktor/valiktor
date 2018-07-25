package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.time.LocalDate
import java.time.Month
import kotlin.test.Test

class LocalDateFormatterTest {

    @Test
    fun `should format date`() {
        assertThat(Formatters[LocalDate::class].formatAllSupportedLocales(LocalDate.of(2018, Month.DECEMBER, 31))).containsExactly(
                entry(SupportedLocales.DEFAULT, "Dec 31, 2018"),
                entry(SupportedLocales.EN, "Dec 31, 2018"),
                entry(SupportedLocales.PT_BR, "31/12/2018"))
    }
}