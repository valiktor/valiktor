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

package org.valiktor.functions

import org.valiktor.Validator
import org.valiktor.constraints.*
import java.math.BigDecimal.ONE
import java.math.BigDecimal.ZERO
import javax.money.CurrencyUnit
import javax.money.MonetaryAmount

/**
 * Validates if the [MonetaryAmount] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ Equals(it?.factory?.setNumber(ZERO)?.create()) }, { it == null || it.isZero })

/**
 * Validates if the [MonetaryAmount] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ NotEquals(it?.factory?.setNumber(ZERO)?.create()) }, { it == null || !it.isZero })

/**
 * Validates if the [MonetaryAmount] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isOne(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ Equals(it?.factory?.setNumber(ONE)?.create()) }, { it == null || it == it.factory.setNumber(ONE).create() })

/**
 * Validates if the [MonetaryAmount] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotOne(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ NotEquals(it?.factory?.setNumber(ONE)?.create()) }, { it == null || it != it.factory.setNumber(ONE).create() })

/**
 * Validates if the [MonetaryAmount] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isPositive(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ Greater(it?.factory?.setNumber(ZERO)?.create()) }, { it == null || it > it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isPositiveOrZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ GreaterOrEqual(it?.factory?.setNumber(ZERO)?.create()) }, { it == null || it >= it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNegative(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ Less(it?.factory?.setNumber(ZERO)?.create()) }, { it == null || it < it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNegativeOrZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate({ LessOrEqual(it?.factory?.setNumber(ZERO)?.create()) }, { it == null || it <= it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<MonetaryAmount?> =
        this.validate(IntegerDigits(min, max), { it == null || it.number.precision - it.number.scale in min.rangeTo(max) })

/**
 * Validates if the [MonetaryAmount] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<MonetaryAmount?> =
        this.validate(DecimalDigits(min, max), { it == null || (if (it.number.scale < 0) 0 else it.number.scale) in min.rangeTo(max) })

/**
 * Validates if the currency unit is equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyEqualTo(currency: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyEquals(currency), { it == null || it.currency == currency })

/**
 * Validates if the currency unit isn't equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyNotEqualTo(currency: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyNotEquals(currency), { it == null || it.currency != currency })

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyIn(vararg currencies: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyIn(currencies.toSet()), { it == null || currencies.contains(it.currency) })

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyIn(currencies: Iterable<CurrencyUnit>): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyIn(currencies), { it == null || currencies.contains(it.currency) })

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyNotIn(vararg currencies: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyNotIn(currencies.toSet()), { it == null || !currencies.contains(it.currency) })

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyNotIn(currencies: Iterable<CurrencyUnit>): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyNotIn(currencies), { it == null || !currencies.contains(it.currency) })