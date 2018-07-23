package org.valiktor.i18n

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.valiktor.i18n.formatters.MonetaryAmountFormatter
import javax.money.MonetaryAmount

class JavaMoneyFormatterSpiTest {

    @Test
    fun `should get MonetaryAmountFormatter`() {
        assertEquals(Formatters[MonetaryAmount::class], MonetaryAmountFormatter)
    }
}