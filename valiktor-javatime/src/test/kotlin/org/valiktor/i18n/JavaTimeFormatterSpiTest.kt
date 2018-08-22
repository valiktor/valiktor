package org.valiktor.i18n

import org.valiktor.i18n.formatters.LocalDateFormatter
import org.valiktor.i18n.formatters.LocalDateTimeFormatter
import org.valiktor.i18n.formatters.LocalTimeFormatter
import org.valiktor.i18n.formatters.OffsetDateTimeFormatter
import org.valiktor.i18n.formatters.OffsetTimeFormatter
import org.valiktor.i18n.formatters.ZonedDateTimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaTimeFormatterSpiTest {

    @Test
    fun `should get JavaTimeFormatterSpi`() {
        assertEquals(Formatters[LocalDate::class], LocalDateFormatter)
        assertEquals(Formatters[LocalDateTime::class], LocalDateTimeFormatter)
        assertEquals(Formatters[LocalTime::class], LocalTimeFormatter)
        assertEquals(Formatters[OffsetDateTime::class], OffsetDateTimeFormatter)
        assertEquals(Formatters[OffsetTime::class], OffsetTimeFormatter)
        assertEquals(Formatters[ZonedDateTime::class], ZonedDateTimeFormatter)
    }
}