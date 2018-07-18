package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.util.*

class DateFormatterTest {

    @Test
    fun `should format date`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 0, 0, 0)

        Assertions.assertThat(Formatters[Date::class].formatAllSupportedLocales(calendar.time)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "12/31/2018"),
                Assertions.entry(SupportedLocales.EN, "12/31/2018"),
                Assertions.entry(SupportedLocales.PT_BR, "31/12/2018"))
    }

    @Test
    fun `should format date and time`() {
        val calendar = Calendar.getInstance()
        calendar.set(2018, Calendar.DECEMBER, 31, 23, 58, 59)

        Assertions.assertThat(Formatters[Date::class].formatAllSupportedLocales(calendar.time)).containsExactly(
                Assertions.entry(SupportedLocales.DEFAULT, "12/31/2018 23:58:59"),
                Assertions.entry(SupportedLocales.EN, "12/31/2018 23:58:59"),
                Assertions.entry(SupportedLocales.PT_BR, "31/12/2018 23:58:59"))
    }
}