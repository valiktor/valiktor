package org.valiktor.i18n

import org.valiktor.i18n.formatters.LocalDateFormatter
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaTimeFormatterSpiTest {

    @Test
    fun `should get MonetaryAmountFormatter`() {
        assertEquals(Formatters[LocalDate::class], LocalDateFormatter)
    }
}