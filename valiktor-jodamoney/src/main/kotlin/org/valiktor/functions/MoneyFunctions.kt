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

package org.valiktor.functions

import org.joda.money.CurrencyUnit
import org.joda.money.Money
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
 * Validates if the [Money] number property is equal to another [Money] value
 *
 * @param value specifies the [Money] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isEqualTo(value: Money): Validator<E>.Property<Money?> =
    this.validate(Equals(value)) { it == null || it.safelyEquals(value) }

/**
 * Validates if the [Money] number property is equal to another [Number] value
 *
 * @param value specifies the [Number] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isEqualTo(value: Number): Validator<E>.Property<Money?> =
    this.validate(
        { Equals(Money.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it.compareTo(Money.of(it.currencyUnit, value.asBigDecimal())) == 0 })

/**
 * Validates if the [Money] number property is equal to another [Money] value
 *
 * @param value specifies the [Money] value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotEqualTo(value: Money): Validator<E>.Property<Money?> =
    this.validate(NotEquals(value)) { it == null || !it.safelyEquals(value) }

/**
 * Validates if the [Money] number property isn't equal to another [Number] value
 *
 * @param value specifies the [Number] value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotEqualTo(value: Number): Validator<E>.Property<Money?> =
    this.validate(
        { NotEquals(Money.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it.compareTo(Money.of(it.currencyUnit, value.asBigDecimal())) != 0 })

/**
 * Validates if the [Money] number property is equal to one of the [Money] values
 *
 * @param values specifies the array of [Money] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isIn(vararg values: Money): Validator<E>.Property<Money?> =
    this.validate(In(values.toSet())) { it == null || values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [Money] number property is equal to one of the [Number] values
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isIn(vararg values: Number): Validator<E>.Property<Money?> =
    this.validate(
        { In(values.map { value -> Money.of(it?.currencyUnit, value.asBigDecimal()) }.toSet()) },
        { it == null || values.map { value -> Money.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [Money] number property is equal to one of the [Money] values
 *
 * @param values specifies the iterable of [Money] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isIn(values: Iterable<Money>): Validator<E>.Property<Money?> =
    this.validate(In(values)) { it == null || values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [Money] number property is equal to one of the [Number] values
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
@JvmName("isInNumber")
fun <E> Validator<E>.Property<Money?>.isIn(values: Iterable<Number>): Validator<E>.Property<Money?> =
    this.validate(
        { In(values.map { value -> Money.of(it?.currencyUnit, value.asBigDecimal()) }) },
        { it == null || values.map { value -> Money.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [Money] number property isn't equal to any [Money] value
 *
 * @param values specifies the array of [Money] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotIn(vararg values: Money): Validator<E>.Property<Money?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [Money] number property isn't equal to any [Number] value
 *
 * @param values specifies the array of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotIn(vararg values: Number): Validator<E>.Property<Money?> =
    this.validate(
        { NotIn(values.map { value -> Money.of(it?.currencyUnit, value.asBigDecimal()) }.toSet()) },
        { it == null || !values.map { value -> Money.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [Money] number property isn't equal to any [Money] value
 *
 * @param values specifies the iterable of [Money] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotIn(values: Iterable<Money>): Validator<E>.Property<Money?> =
    this.validate(NotIn(values)) { it == null || !values.any { e -> it.safelyEquals(e) } }

/**
 * Validates if the [Money] number property isn't equal to any [Number] value
 *
 * @param values specifies the iterable of [Number] values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
@JvmName("isNotInNumber")
fun <E> Validator<E>.Property<Money?>.isNotIn(values: Iterable<Number>): Validator<E>.Property<Money?> =
    this.validate(
        { NotIn(values.map { value -> Money.of(it?.currencyUnit, value.asBigDecimal()) }) },
        { it == null || !values.map { value -> Money.of(it.currencyUnit, value.asBigDecimal()) }.any { e -> it.compareTo(e) == 0 } })

/**
 * Validates if the [Money] number property is less than another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isLessThan(value: Number): Validator<E>.Property<Money?> =
    this.validate(
        { Less(Money.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it < Money.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [Money] number property is less than or equal to another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isLessThanOrEqualTo(value: Number): Validator<E>.Property<Money?> =
    this.validate(
        { LessOrEqual(Money.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it <= Money.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [Money] number property is greater than another [Number]  value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isGreaterThan(value: Number): Validator<E>.Property<Money?> =
    this.validate(
        { Greater(Money.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it > Money.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [Money] number property is greater than or equal to another [Number] value
 *
 * @property value specifies the [Number] value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isGreaterThanOrEqualTo(value: Number): Validator<E>.Property<Money?> =
    this.validate(
        { GreaterOrEqual(Money.of(it?.currencyUnit, value.asBigDecimal())) },
        { it == null || it >= Money.of(it.currencyUnit, value.asBigDecimal()) })

/**
 * Validates if the [Money] number property is between two [Number] values
 *
 * @property start (inclusive) specifies [Number] value that should start
 * @property end (inclusive) specifies [Number] value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isBetween(start: Number, end: Number): Validator<E>.Property<Money?> =
    this.validate(
        { Between(Money.of(it?.currencyUnit, start.asBigDecimal()), Money.of(it?.currencyUnit, end.asBigDecimal())) },
        { it == null || it in Money.of(it.currencyUnit, start.asBigDecimal()).rangeTo(Money.of(it.currencyUnit, end.asBigDecimal())) })

/**
 * Validates if the [Money] number property isn't between two [Number] values
 *
 * @property start (inclusive) specifies [Number] value that shouldn't start
 * @property end (inclusive) specifies [Number] value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotBetween(start: Number, end: Number): Validator<E>.Property<Money?> =
    this.validate(
        { NotBetween(Money.of(it?.currencyUnit, start.asBigDecimal()), Money.of(it?.currencyUnit, end.asBigDecimal())) },
        { it == null || it !in Money.of(it.currencyUnit, start.asBigDecimal()).rangeTo(Money.of(it.currencyUnit, end.asBigDecimal())) })

/**
 * Validates if the [Money] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isZero(): Validator<E>.Property<Money?> =
    this.validate(
        { Equals(Money.of(it?.currencyUnit, ZERO)) },
        { it == null || it.isZero })

/**
 * Validates if the [Money] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotZero(): Validator<E>.Property<Money?> =
    this.validate(
        { NotEquals(Money.of(it?.currencyUnit, ZERO)) },
        { it == null || !it.isZero })

/**
 * Validates if the [Money] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isOne(): Validator<E>.Property<Money?> =
    this.validate(
        { Equals(Money.of(it?.currencyUnit, ONE)) },
        { it == null || it.compareTo(Money.of(it.currencyUnit, ONE)) == 0 })

/**
 * Validates if the [Money] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNotOne(): Validator<E>.Property<Money?> =
    this.validate(
        { NotEquals(Money.of(it?.currencyUnit, ONE)) },
        { it == null || it.compareTo(Money.of(it.currencyUnit, ONE)) != 0 })

/**
 * Validates if the [Money] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isPositive(): Validator<E>.Property<Money?> =
    this.validate(
        { Greater(Money.of(it?.currencyUnit, ZERO)) },
        { it == null || it > Money.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [Money] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isPositiveOrZero(): Validator<E>.Property<Money?> =
    this.validate(
        { GreaterOrEqual(Money.of(it?.currencyUnit, ZERO)) },
        { it == null || it >= Money.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [Money] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNegative(): Validator<E>.Property<Money?> =
    this.validate(
        { Less(Money.of(it?.currencyUnit, ZERO)) },
        { it == null || it < Money.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [Money] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.isNegativeOrZero(): Validator<E>.Property<Money?> =
    this.validate(
        { LessOrEqual(Money.of(it?.currencyUnit, ZERO)) },
        { it == null || it <= Money.of(it.currencyUnit, ZERO) })

/**
 * Validates if the [Money] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Money?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.amount.precision() - it.amount.scale() in min.rangeTo(max) }

/**
 * Validates if the [Money] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Money?> =
    this.validate(DecimalDigits(min, max)) { it == null || (if (it.amount.scale() < 0) 0 else it.amount.scale()) in min.rangeTo(max) }

/**
 * Validates if the currency unit is equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasCurrencyEqualTo(currency: CurrencyUnit): Validator<E>.Property<Money?> =
    this.validate(CurrencyEquals(currency)) { it == null || it.currencyUnit == currency }

/**
 * Validates if the currency unit isn't equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasCurrencyNotEqualTo(currency: CurrencyUnit): Validator<E>.Property<Money?> =
    this.validate(CurrencyNotEquals(currency)) { it == null || it.currencyUnit != currency }

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasCurrencyIn(vararg currencies: CurrencyUnit): Validator<E>.Property<Money?> =
    this.validate(CurrencyIn(currencies.toSet())) { it == null || currencies.contains(it.currencyUnit) }

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasCurrencyIn(currencies: Iterable<CurrencyUnit>): Validator<E>.Property<Money?> =
    this.validate(CurrencyIn(currencies)) { it == null || currencies.contains(it.currencyUnit) }

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasCurrencyNotIn(vararg currencies: CurrencyUnit): Validator<E>.Property<Money?> =
    this.validate(CurrencyNotIn(currencies.toSet())) { it == null || !currencies.contains(it.currencyUnit) }

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Money?>.hasCurrencyNotIn(currencies: Iterable<CurrencyUnit>): Validator<E>.Property<Money?> =
    this.validate(CurrencyNotIn(currencies)) { it == null || !currencies.contains(it.currencyUnit) }
