package org.valiktor.i18n

import org.valiktor.i18n.formatters.LocalDateFormatter
import org.valiktor.i18n.formatters.LocalDateTimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaTimeFormatterSpiTest {

    @Test
    fun `should get JavaTimeFormatterSpi`() {
        assertEquals(Formatters[LocalDate::class], LocalDateFormatter)
        assertEquals(Formatters[LocalDateTime::class], LocalDateTimeFormatter)
    }
}