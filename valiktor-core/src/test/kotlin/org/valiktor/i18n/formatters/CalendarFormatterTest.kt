package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.util.*

class CalendarFormatterTest {

    @Test
    fun `should format date`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 0, 0, 0)

        assertThat(Formatters[Calendar::class].formatAllSupportedLocales(calendar)).containsExactly(
                entry(SupportedLocales.DEFAULT, "Dec 31, 2018"),
                entry(SupportedLocales.EN, "Dec 31, 2018"),
                entry(SupportedLocales.PT_BR, "31/12/2018"))
    }

    @Test
    fun `should format date and time`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 23, 58, 59)

        assertThat(Formatters[Calendar::class].formatAllSupportedLocales(calendar)).containsExactly(
                entry(SupportedLocales.DEFAULT, "Dec 31, 2018 11:58:59 PM"),
                entry(SupportedLocales.EN, "Dec 31, 2018 11:58:59 PM"),
                entry(SupportedLocales.PT_BR, "31/12/2018 23:58:59"))
    }
}