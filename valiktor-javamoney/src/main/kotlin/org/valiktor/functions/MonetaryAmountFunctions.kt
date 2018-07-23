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
 * Validates if the [MonetaryAmount] number property is equal to another [Number] value
 *
 * @param value specifies the [Number] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isEqualTo(value: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Equals(it?.factory?.setNumber(value)?.create()) },
                { it == null || it == it.factory.setNumber(value).create() })

/**
 * Validates if the [MonetaryAmount] number property isn't equal to another [Number] value
 *
 * @param value specifies the [Number] value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotEqualTo(value: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { NotEquals(it?.factory?.setNumber(value)?.create()) },
                { it == null || it != it.factory.setNumber(value).create() })

/**
 * Validates if the [MonetaryAmount] number property is equal to one of the [Number] values
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isIn(vararg values: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { In(values.map { value -> it?.factory?.setNumber(value)?.create() }.toSet()) },
                { it == null || values.map { value -> it.factory.setNumber(value).create() }.contains(it) })

/**
 * Validates if the [MonetaryAmount] number property is equal to one of the [Number] values
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isIn(values: Iterable<Number>): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { In(values.map { value -> it?.factory?.setNumber(value)?.create() }) },
                { it == null || values.map { value -> it.factory.setNumber(value).create() }.contains(it) })

/**
 * Validates if the [MonetaryAmount] number property isn't equal to any [Number] value
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotIn(vararg values: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { NotIn(values.map { value -> it?.factory?.setNumber(value)?.create() }.toSet()) },
                { it == null || !values.map { value -> it.factory.setNumber(value).create() }.contains(it) })

/**
 * Validates if the [MonetaryAmount] number property isn't equal to any [Number] value
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotIn(values: Iterable<Number>): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { NotIn(values.map { value -> it?.factory?.setNumber(value)?.create() }) },
                { it == null || !values.map { value -> it.factory.setNumber(value).create() }.contains(it) })

/**
 * Validates if the [MonetaryAmount] number property is less than another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isLessThan(value: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Less(it?.factory?.setNumber(value)?.create()) },
                { it == null || it < it.factory.setNumber(value).create() })

/**
 * Validates if the [MonetaryAmount] number property is less than or equal to another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isLessThanOrEqualTo(value: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { LessOrEqual(it?.factory?.setNumber(value)?.create()) },
                { it == null || it <= it.factory.setNumber(value).create() })

/**
 * Validates if the [MonetaryAmount] number property is greater than another [Number]  value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isGreaterThan(value: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Greater(it?.factory?.setNumber(value)?.create()) },
                { it == null || it > it.factory.setNumber(value).create() })

/**
 * Validates if the [MonetaryAmount] number property is greater than or equal to another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isGreaterThanOrEqualTo(value: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { GreaterOrEqual(it?.factory?.setNumber(value)?.create()) },
                { it == null || it >= it.factory.setNumber(value).create() })

/**
 * Validates if the [MonetaryAmount] number property is between two [Number] values
 *
 * @property start (inclusive) specifies [Number] value that should start
 * @property end (inclusive) specifies [Number] value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isBetween(start: Number, end: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Between(it?.factory?.setNumber(start)?.create(), it?.factory?.setNumber(end)?.create()) },
                { it == null || it in it.factory.setNumber(start).create().rangeTo(it.factory.setNumber(end).create()) })

/**
 * Validates if the [MonetaryAmount] number property isn't between two [Number] values
 *
 * @property start (inclusive) specifies [Number] value that shouldn't start
 * @property end (inclusive) specifies [Number] value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotBetween(start: Number, end: Number): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { NotBetween(it?.factory?.setNumber(start)?.create(), it?.factory?.setNumber(end)?.create()) },
                { it == null || it !in it.factory.setNumber(start).create().rangeTo(it.factory.setNumber(end).create()) })

/**
 * Validates if the [MonetaryAmount] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Equals(it?.factory?.setNumber(ZERO)?.create()) },
                { it == null || it.isZero })

/**
 * Validates if the [MonetaryAmount] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { NotEquals(it?.factory?.setNumber(ZERO)?.create()) },
                { it == null || !it.isZero })

/**
 * Validates if the [MonetaryAmount] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isOne(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Equals(it?.factory?.setNumber(ONE)?.create()) },
                { it == null || it == it.factory.setNumber(ONE).create() })

/**
 * Validates if the [MonetaryAmount] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNotOne(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { NotEquals(it?.factory?.setNumber(ONE)?.create()) },
                { it == null || it != it.factory.setNumber(ONE).create() })

/**
 * Validates if the [MonetaryAmount] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isPositive(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Greater(it?.factory?.setNumber(ZERO)?.create()) },
                { it == null || it > it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isPositiveOrZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { GreaterOrEqual(it?.factory?.setNumber(ZERO)?.create()) },
                { it == null || it >= it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNegative(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { Less(it?.factory?.setNumber(ZERO)?.create()) },
                { it == null || it < it.factory.setNumber(ZERO).create() })

/**
 * Validates if the [MonetaryAmount] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.isNegativeOrZero(): Validator<E>.Property<MonetaryAmount?> =
        this.validate(
                { LessOrEqual(it?.factory?.setNumber(ZERO)?.create()) },
                { it == null || it <= it.factory.setNumber(ZERO).create() })

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