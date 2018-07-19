/*
 * Copyright 2018 https://www.valiktor.org
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

import org.valiktor.i18n.Formatter
import org.valiktor.i18n.MessageBundle
import java.math.BigDecimal
import java.text.NumberFormat

/**
 * Represents the formatter for [Number] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object NumberFormatter : Formatter<Number> {

    override fun format(value: Number, messageBundle: MessageBundle): String {
        val bigNum = value as? BigDecimal ?: BigDecimal(value.toString()).stripTrailingZeros()
        val integerDigits = (bigNum.precision() - bigNum.scale()).let { if (it <= 0) 1 else it }
        val fractionDigits = bigNum.scale().let { if (it < 0) 0 else it }

        val numberFormat = NumberFormat.getNumberInstance(messageBundle.locale)
        numberFormat.minimumIntegerDigits = integerDigits
        numberFormat.maximumIntegerDigits = integerDigits
        numberFormat.minimumFractionDigits = fractionDigits
        numberFormat.maximumFractionDigits = fractionDigits

        return numberFormat.format(value)
    }
}