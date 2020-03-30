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

package org.valiktor.constraints

import org.joda.money.CurrencyUnit
import org.valiktor.Constraint

private const val BASE_BUNDLE_NAME = "org.valiktor/jodaMoneyMessages"

/**
 * Represents a constraint that validate if the currency unit is equal to another value
 *
 * @property currency specifies the currency unit to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.6.0
 */
data class CurrencyEquals(val currency: CurrencyUnit) : Constraint {
    override val messageBundle: String = BASE_BUNDLE_NAME
}

/**
 * Represents a constraint that validate if the currency unit isn't equal to another value
 *
 * @property currency specifies the currency unit to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.6.0
 */
data class CurrencyNotEquals(val currency: CurrencyUnit) : Constraint {
    override val messageBundle: String = BASE_BUNDLE_NAME
}

/**
 * Represents a constraint that validate if the currency unit is equal to one of the values
 *
 * @property currencies specifies the currencies to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.6.0
 */
data class CurrencyIn(val currencies: Iterable<CurrencyUnit>) : Constraint {
    override val messageBundle: String = BASE_BUNDLE_NAME
}

/**
 * Represents a constraint that validate if the currency unit isn't equal to any value
 *
 * @property currencies specifies the currencies to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.6.0
 */
data class CurrencyNotIn(val currencies: Iterable<CurrencyUnit>) : Constraint {
    override val messageBundle: String = BASE_BUNDLE_NAME
}
