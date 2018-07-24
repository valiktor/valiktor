package org.valiktor.i18n

import org.valiktor.i18n.formatters.MonetaryAmountFormatter
import javax.money.MonetaryAmount
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaMoneyFormatterSpiTest {

    @Test
    fun `should get MonetaryAmountFormatter`() {
        assertEquals(Formatters[MonetaryAmount::class], MonetaryAmountFormatter)
    }
}