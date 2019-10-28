/*
 * Copyright 2018-2019 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.valiktor.i18n.formatters

import org.joda.money.Money
import org.valiktor.i18n.Formatter
import org.valiktor.i18n.MessageBundle
import java.text.NumberFormat
import java.util.Currency

/**
 * Represents the formatter for [Money] values
 *
 * @author Rodolpho S. Couto
 * @since 0.6.0
 */
object MoneyFormatter : Formatter<Money> {

    override fun format(value: Money, messageBundle: MessageBundle): String {
        val bigNum = value.amount.stripTrailingZeros()
        val integerDigits = (bigNum.precision() - bigNum.scale()).let { if (it <= 0) 1 else it }
        val fractionDigits = bigNum.scale()
            .let { if (it < 0) 0 else it }
            .let { if (it < value.currencyUnit.decimalPlaces) value.currencyUnit.decimalPlaces else it }

        val numberFormat = NumberFormat.getCurrencyInstance(messageBundle.locale)
        numberFormat.currency = Currency.getInstance(value.currencyUnit.code)
        numberFormat.minimumIntegerDigits = integerDigits
        numberFormat.maximumIntegerDigits = integerDigits
        numberFormat.minimumFractionDigits = fractionDigits
        numberFormat.maximumFractionDigits = fractionDigits

        return numberFormat.format(bigNum)
    }
}
