/*
 * Copyright 2018-2020 https://www.valiktor.org
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

package org.valiktor.i18n

import org.joda.money.BigMoney
import org.joda.money.Money
import org.valiktor.i18n.formatters.BigMoneyFormatter
import org.valiktor.i18n.formatters.MoneyFormatter

/**
 * Represents the implementation for service provider interface for JodaMoney formatters
 *
 * @property formatters specifies the [Set] of JodaMoney formatters
 *
 * @author Rodolpho S. Couto
 * @since 0.6.0
 */
class JodaMoneyFormatterSpi : FormatterSpi {

    override val formatters = setOf(
        BigMoney::class to BigMoneyFormatter,
        Money::class to MoneyFormatter
    )
}
