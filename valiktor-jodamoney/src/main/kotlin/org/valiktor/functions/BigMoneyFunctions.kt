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

import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.valiktor.Validator
import org.valiktor.asBigDecimal
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
import org.valiktor.safelyEquals
import java.math.BigDecimal.ONE
import java.math.BigDecimal.ZERO

/**
 * Validates if the [BigMoney] number property is equal to another [BigMoney] value
 *
 * @param value specifies the [BigMoney] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isEqualTo(value: BigMoney): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(Equals(value)) { it == null || it.safelyEquals(value) }

/**
 * Validates if the [BigMoney] number property is equal to another [Number] value
 *
 * @param value specifies the [Number] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isEqualTo(value: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Equals(BigMoney.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it.compareTo(BigMoney.of(it.currencyUnit, value.asBigDecimal())) == 0 })

/**
 * Validates if the [BigMoney] number property is equal to another [BigMoney] value
 *
 * @param value specifies the [BigMoney] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotEqualTo(value: BigMoney): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(NotEquals(value)) { it == null || !it.safelyEquals(value) }

/**
 * Validates if the [BigMoney] number property isn't equal to another [Number] value
 *
 * @param value specifies the [Number] value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotEqualTo(value: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { NotEquals(BigMoney.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it.compareTo(BigMoney.of(it.currencyUnit, value.asBigDecimal())) != 0 })

/**
 * Validates if the [BigMoney] number property is equal to one of the [BigMoney] values
 *
 * @param values specifies the array of [BigMoney] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isIn(vararg values: BigMoney): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(In(values.toSet())) { it == null || values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [BigMoney] number property is equal to one of the [Number] values
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isIn(vararg values: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { In(values.map { value -> BigMoney.of(it?.currencyUnit, value.asBigDecimal()) }.toSet()) },
        { it == null || values.map { value -> BigMoney.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [BigMoney] number property is equal to one of the [BigMoney] values
 *
 * @param values specifies the iterable of [BigMoney] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isIn(values: Iterable<BigMoney>): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(In(values)) { it == null || values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [BigMoney] number property is equal to one of the [Number] values
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
@JvmName("isInNumber")
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isIn(values: Iterable<Number>): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { In(values.map { value -> BigMoney.of(it?.currencyUnit, value.asBigDecimal()) }) },
        { it == null || values.map { value -> BigMoney.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [BigMoney] number property isn't equal to any [BigMoney] value
 *
 * @param values specifies the array of [BigMoney] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotIn(vararg values: BigMoney): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [BigMoney] number property isn't equal to any [Number] value
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotIn(vararg values: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { NotIn(values.map { value -> BigMoney.of(it?.currencyUnit, value.asBigDecimal()) }.toSet()) },
        { it == null || !values.map { value -> BigMoney.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [BigMoney] number property isn't equal to any [BigMoney] value
 *
 * @param values specifies the iterable of [BigMoney] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotIn(values: Iterable<BigMoney>): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(NotIn(values)) { it == null || !values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [BigMoney] number property isn't equal to any [Number] value
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
@JvmName("isNotInNumber")
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotIn(values: Iterable<Number>): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { NotIn(values.map { value -> BigMoney.of(it?.currencyUnit, value.asBigDecimal()) }) },
        { it == null || !values.map { value -> BigMoney.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [BigMoney] number property is less than another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isLessThan(value: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Less(BigMoney.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it < BigMoney.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [BigMoney] number property is less than or equal to another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isLessThanOrEqualTo(value: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { LessOrEqual(BigMoney.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it <= BigMoney.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [BigMoney] number property is greater than another [Number]  value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isGreaterThan(value: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Greater(BigMoney.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it > BigMoney.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [BigMoney] number property is greater than or equal to another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isGreaterThanOrEqualTo(value: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { GreaterOrEqual(BigMoney.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it >= BigMoney.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [BigMoney] number property is between two [Number] values
 *
 * @property start (inclusive) specifies [Number] value that should start
 * @property end (inclusive) specifies [Number] value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isBetween(start: Number, end: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Between(BigMoney.of(it?.currencyUnit, start.asBigDecimal()), BigMoney.of(it?.currencyUnit, end.asBigDecimal())) },
        { it == null || it in BigMoney.of(it.currencyUnit, start.asBigDecimal()).rangeTo(BigMoney.of(it.currencyUnit, end.asBigDecimal())) })

/**
 * Validates if the [BigMoney] number property isn't between two [Number] values
 *
 * @property start (inclusive) specifies [Number] value that shouldn't start
 * @property end (inclusive) specifies [Number] value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotBetween(start: Number, end: Number): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { NotBetween(BigMoney.of(it?.currencyUnit, start.asBigDecimal()), BigMoney.of(it?.currencyUnit, end.asBigDecimal())) },
        { it == null || it !in BigMoney.of(it.currencyUnit, start.asBigDecimal()).rangeTo(BigMoney.of(it.currencyUnit, end.asBigDecimal())) })

/**
 * Validates if the [BigMoney] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isZero(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Equals(BigMoney.of(it?.currencyUnit, ZERO)) },
        { it == null || it.isZero })

/**
 * Validates if the [BigMoney] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotZero(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { NotEquals(BigMoney.of(it?.currencyUnit, ZERO)) },
        { it == null || !it.isZero })

/**
 * Validates if the [BigMoney] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isOne(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Equals(BigMoney.of(it?.currencyUnit, ONE)) },
        { it == null || it.compareTo(BigMoney.of(it.currencyUnit, ONE)) == 0 })

/**
 * Validates if the [BigMoney] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNotOne(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { NotEquals(BigMoney.of(it?.currencyUnit, ONE)) },
        { it == null || it.compareTo(BigMoney.of(it.currencyUnit, ONE)) != 0 })

/**
 * Validates if the [BigMoney] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isPositive(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Greater(BigMoney.of(it?.currencyUnit, ZERO)) },
        { it == null || it > BigMoney.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [BigMoney] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isPositiveOrZero(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { GreaterOrEqual(BigMoney.of(it?.currencyUnit, ZERO)) },
        { it == null || it >= BigMoney.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [BigMoney] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNegative(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { Less(BigMoney.of(it?.currencyUnit, ZERO)) },
        { it == null || it < BigMoney.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [BigMoney] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.isNegativeOrZero(): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(
        { LessOrEqual(BigMoney.of(it?.currencyUnit, ZERO)) },
        { it == null || it <= BigMoney.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [BigMoney] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.amount.precision() - it.amount.scale() in min.rangeTo(max) }

/**
 * Validates if the [BigMoney] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(DecimalDigits(min, max)) { it == null || (if (it.amount.scale() < 0) 0 else it.amount.scale()) in min.rangeTo(max) }

/**
 * Validates if the currency unit is equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasCurrencyEqualTo(currency: CurrencyUnit): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(CurrencyEquals(currency)) { it == null || it.currencyUnit == currency }

/**
 * Validates if the currency unit isn't equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasCurrencyNotEqualTo(currency: CurrencyUnit): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(CurrencyNotEquals(currency)) { it == null || it.currencyUnit != currency }

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasCurrencyIn(vararg currencies: CurrencyUnit): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(CurrencyIn(currencies.toSet())) { it == null || currencies.contains(it.currencyUnit) }

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasCurrencyIn(currencies: Iterable<CurrencyUnit>): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(CurrencyIn(currencies)) { it == null || currencies.contains(it.currencyUnit) }

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasCurrencyNotIn(vararg currencies: CurrencyUnit): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(CurrencyNotIn(currencies.toSet())) { it == null || !currencies.contains(it.currencyUnit) }

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, BigMoney?>.hasCurrencyNotIn(currencies: Iterable<CurrencyUnit>): Validator<E>.ReceiverValidator<E, BigMoney?> =
    this.validate(CurrencyNotIn(currencies)) { it == null || !currencies.contains(it.currencyUnit) }
