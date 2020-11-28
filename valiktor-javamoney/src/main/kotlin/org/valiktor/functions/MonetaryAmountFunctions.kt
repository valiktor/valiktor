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

package org.valiktor.functions

import org.valiktor.Validator
import org.valiktor.constraints.Between
import org.valiktor.constraints.CurrencyEquals
import org.valiktor.constraints.CurrencyIn
import org.valiktor.constraints.CurrencyNotEquals
import org.valiktor.constraints.CurrencyNotIn
import org.valiktor.constraints.DecimalDigits
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.In
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import java.math.BigDecimal.ONE
import java.math.BigDecimal.ZERO
import javax.money.CurrencyUnit
import javax.money.MonetaryAmount

/**
 * Validates if the [MonetaryAmount] number value is equal to another [Number] value
 *
 * @param value specifies the [Number] value that should be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isEqualTo(value: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Equals(it?.factory?.setNumber(value)?.create()) },
        { it == null || it == it.factory.setNumber(value).create() }
    )

/**
 * Validates if the [MonetaryAmount] number value isn't equal to another [Number] value
 *
 * @param value specifies the [Number] value that should not be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNotEqualTo(value: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { NotEquals(it?.factory?.setNumber(value)?.create()) },
        { it == null || it != it.factory.setNumber(value).create() }
    )

/**
 * Validates if the [MonetaryAmount] number value is equal to one of the [Number] values
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isIn(vararg values: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { In(values.map { value -> it?.factory?.setNumber(value)?.create() }.toSet()) },
        { it == null || values.map { value -> it.factory.setNumber(value).create() }.contains(it) }
    )

/**
 * Validates if the [MonetaryAmount] number value is equal to one of the [Number] values
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isIn(values: Iterable<Number>): Validator<E>.ValueValidator<T?> =
    this.validate(
        { In(values.map { value -> it?.factory?.setNumber(value)?.create() }) },
        { it == null || values.map { value -> it.factory.setNumber(value).create() }.contains(it) }
    )

/**
 * Validates if the [MonetaryAmount] number value isn't equal to any [Number] value
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNotIn(vararg values: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { NotIn(values.map { value -> it?.factory?.setNumber(value)?.create() }.toSet()) },
        { it == null || !values.map { value -> it.factory.setNumber(value).create() }.contains(it) }
    )

/**
 * Validates if the [MonetaryAmount] number value isn't equal to any [Number] value
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNotIn(values: Iterable<Number>): Validator<E>.ValueValidator<T?> =
    this.validate(
        { NotIn(values.map { value -> it?.factory?.setNumber(value)?.create() }) },
        { it == null || !values.map { value -> it.factory.setNumber(value).create() }.contains(it) }
    )

/**
 * Validates if the [MonetaryAmount] number value is less than another [Number] value
 *
 * @value value specifies the [Number] value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isLessThan(value: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Less(it?.factory?.setNumber(value)?.create()) },
        { it == null || it < it.factory.setNumber(value).create() }
    )

/**
 * Validates if the [MonetaryAmount] number value is less than or equal to another [Number] value
 *
 * @value value specifies the [Number] value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isLessThanOrEqualTo(value: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { LessOrEqual(it?.factory?.setNumber(value)?.create()) },
        { it == null || it <= it.factory.setNumber(value).create() }
    )

/**
 * Validates if the [MonetaryAmount] number value is greater than another [Number]  value
 *
 * @value value specifies the [Number] value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isGreaterThan(value: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Greater(it?.factory?.setNumber(value)?.create()) },
        { it == null || it > it.factory.setNumber(value).create() }
    )

/**
 * Validates if the [MonetaryAmount] number value is greater than or equal to another [Number] value
 *
 * @value value specifies the [Number] value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isGreaterThanOrEqualTo(value: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { GreaterOrEqual(it?.factory?.setNumber(value)?.create()) },
        { it == null || it >= it.factory.setNumber(value).create() }
    )

/**
 * Validates if the [MonetaryAmount] number value is between two [Number] values
 *
 * @value start (inclusive) specifies [Number] value that should start
 * @value end (inclusive) specifies [Number] value that should end
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isBetween(start: Number, end: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Between(it?.factory?.setNumber(start)?.create(), it?.factory?.setNumber(end)?.create()) },
        { it == null || it in it.factory.setNumber(start).create().rangeTo(it.factory.setNumber(end).create()) }
    )

/**
 * Validates if the [MonetaryAmount] number value isn't between two [Number] values
 *
 * @value start (inclusive) specifies [Number] value that shouldn't start
 * @value end (inclusive) specifies [Number] value that shouldn't end
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNotBetween(start: Number, end: Number): Validator<E>.ValueValidator<T?> =
    this.validate(
        { NotBetween(it?.factory?.setNumber(start)?.create(), it?.factory?.setNumber(end)?.create()) },
        { it == null || it !in it.factory.setNumber(start).create().rangeTo(it.factory.setNumber(end).create()) }
    )

/**
 * Validates if the [MonetaryAmount] value is equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isZero(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Equals(it?.factory?.setNumber(ZERO)?.create()) },
        { it == null || it.isZero }
    )

/**
 * Validates if the [MonetaryAmount] value is not equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNotZero(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { NotEquals(it?.factory?.setNumber(ZERO)?.create()) },
        { it == null || !it.isZero }
    )

/**
 * Validates if the [MonetaryAmount] value is equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isOne(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Equals(it?.factory?.setNumber(ONE)?.create()) },
        { it == null || it == it.factory.setNumber(ONE).create() }
    )

/**
 * Validates if the [MonetaryAmount] value is not equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNotOne(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { NotEquals(it?.factory?.setNumber(ONE)?.create()) },
        { it == null || it != it.factory.setNumber(ONE).create() }
    )

/**
 * Validates if the [MonetaryAmount] value is positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isPositive(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Greater(it?.factory?.setNumber(ZERO)?.create()) },
        { it == null || it > it.factory.setNumber(ZERO).create() }
    )

/**
 * Validates if the [MonetaryAmount] value isn't negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isPositiveOrZero(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { GreaterOrEqual(it?.factory?.setNumber(ZERO)?.create()) },
        { it == null || it >= it.factory.setNumber(ZERO).create() }
    )

/**
 * Validates if the [MonetaryAmount] value is negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNegative(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { Less(it?.factory?.setNumber(ZERO)?.create()) },
        { it == null || it < it.factory.setNumber(ZERO).create() }
    )

/**
 * Validates if the [MonetaryAmount] value isn't positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.isNegativeOrZero(): Validator<E>.ValueValidator<T?> =
    this.validate(
        { LessOrEqual(it?.factory?.setNumber(ZERO)?.create()) },
        { it == null || it <= it.factory.setNumber(ZERO).create() }
    )

/**
 * Validates if the [MonetaryAmount] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @value min specifies the minimum size
 * @value max specifies the maximum size
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ValueValidator<T?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.number.precision - it.number.scale in min.rangeTo(max) }

/**
 * Validates if the [MonetaryAmount] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @value min specifies the minimum size
 * @value max specifies the maximum size
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ValueValidator<T?> =
    this.validate(DecimalDigits(min, max)) { it == null || (if (it.number.scale < 0) 0 else it.number.scale) in min.rangeTo(max) }

/**
 * Validates if the currency unit is equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasCurrencyEqualTo(currency: CurrencyUnit): Validator<E>.ValueValidator<T?> =
    this.validate(CurrencyEquals(currency)) { it == null || it.currency == currency }

/**
 * Validates if the currency unit isn't equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasCurrencyNotEqualTo(currency: CurrencyUnit): Validator<E>.ValueValidator<T?> =
    this.validate(CurrencyNotEquals(currency)) { it == null || it.currency != currency }

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasCurrencyIn(vararg currencies: CurrencyUnit): Validator<E>.ValueValidator<T?> =
    this.validate(CurrencyIn(currencies.toSet())) { it == null || currencies.contains(it.currency) }

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasCurrencyIn(currencies: Iterable<CurrencyUnit>): Validator<E>.ValueValidator<T?> =
    this.validate(CurrencyIn(currencies)) { it == null || currencies.contains(it.currency) }

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasCurrencyNotIn(vararg currencies: CurrencyUnit): Validator<E>.ValueValidator<T?> =
    this.validate(CurrencyNotIn(currencies.toSet())) { it == null || !currencies.contains(it.currency) }

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : MonetaryAmount> Validator<E>.ValueValidator<T?>.hasCurrencyNotIn(currencies: Iterable<CurrencyUnit>): Validator<E>.ValueValidator<T?> =
    this.validate(CurrencyNotIn(currencies)) { it == null || !currencies.contains(it.currency) }
