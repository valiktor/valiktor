package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.time.LocalDateTime
import java.time.Month
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.Test

class OffsetDateTimeFormatterTest {

    @Test
    fun `should format dateTime`() {
        assertThat(Formatters[OffsetDateTime::class].formatAllSupportedLocales(OffsetDateTime.of(LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 58, 59), ZoneOffset.UTC))).containsExactly(
                entry(SupportedLocales.DEFAULT, "Dec 31, 2018 11:58:59 PM"),
                entry(SupportedLocales.EN, "Dec 31, 2018 11:58:59 PM"),
                entry(SupportedLocales.PT_BR, "31/12/2018 23:58:59"))
    }
}