package org.valiktor.i18n

import org.valiktor.i18n.formatters.*
import java.time.*
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
    }
}