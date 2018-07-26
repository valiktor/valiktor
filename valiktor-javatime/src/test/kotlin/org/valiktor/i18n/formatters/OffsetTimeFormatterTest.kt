package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneOffset
import kotlin.test.Test

class OffsetTimeFormatterTest {

    @Test
    fun `should format time`() {
        assertThat(Formatters[OffsetTime::class].formatAllSupportedLocales(OffsetTime.of(LocalTime.of(23, 58, 59), ZoneOffset.UTC))).containsExactly(
                entry(SupportedLocales.DEFAULT, "11:58:59 PM"),
                entry(SupportedLocales.EN, "11:58:59 PM"),
                entry(SupportedLocales.PT_BR, "23:58:59"))
    }
}