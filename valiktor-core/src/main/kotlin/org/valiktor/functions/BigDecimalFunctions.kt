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
import org.valiktor.constraints.DecimalDigits
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.In
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import java.math.BigDecimal

/**
 * Validates if the [BigDecimal] property value is equal to another value
 *
 * @param value specifies the value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isEqualTo(value: BigDecimal): Validator<E>.Property<BigDecimal?> =
    this.validate(Equals(value)) { it == null || it.compareTo(value) == 0 }

/**
 * Validates if the [BigDecimal] property value isn't equal to another value
 *
 * @param value specifies the value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotEqualTo(value: BigDecimal): Validator<E>.Property<BigDecimal?> =
    this.validate(NotEquals(value)) { it == null || it.compareTo(value) != 0 }

/**
 * Validates if the [BigDecimal] property value is equal to one of the values
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isIn(vararg values: BigDecimal): Validator<E>.Property<BigDecimal?> =
    this.validate(In(values.toSet())) { it == null || values.any { e -> it.compareTo(e) == 0 } }

/**
 * Validates if the [BigDecimal] property value is equal to one of the values
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isIn(values: Iterable<BigDecimal>): Validator<E>.Property<BigDecimal?> =
    this.validate(In(values)) { it == null || values.any { e -> it.compareTo(e) == 0 } }

/**
 * Validates if the [BigDecimal] property value isn't equal to any value
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotIn(vararg values: BigDecimal): Validator<E>.Property<BigDecimal?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.any { e -> it.compareTo(e) == 0 } }

/**
 * Validates if the [BigDecimal] property value isn't equal to any value
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotIn(values: Iterable<BigDecimal>): Validator<E>.Property<BigDecimal?> =
    this.validate(NotIn(values)) { it == null || !values.any { e -> it.compareTo(e) == 0 } }

/**
 * Validates if the [BigDecimal] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isZero(): Validator<E>.Property<BigDecimal?> =
    this.validate(Equals(BigDecimal.ZERO)) { it == null || it.compareTo(BigDecimal.ZERO) == 0 }

/**
 * Validates if the [BigDecimal] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotZero(): Validator<E>.Property<BigDecimal?> =
    this.validate(NotEquals(BigDecimal.ZERO)) { it == null || it.compareTo(BigDecimal.ZERO) != 0 }

/**
 * Validates if the [BigDecimal] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isOne(): Validator<E>.Property<BigDecimal?> =
    this.validate(Equals(BigDecimal.ONE)) { it == null || it.compareTo(BigDecimal.ONE) == 0 }

/**
 * Validates if the [BigDecimal] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotOne(): Validator<E>.Property<BigDecimal?> =
    this.validate(NotEquals(BigDecimal.ONE)) { it == null || it.compareTo(BigDecimal.ONE) != 0 }

/**
 * Validates if the [BigDecimal] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isPositive(): Validator<E>.Property<BigDecimal?> =
    this.validate(Greater(BigDecimal.ZERO)) { it == null || it > BigDecimal.ZERO }

/**
 * Validates if the [BigDecimal] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isPositiveOrZero(): Validator<E>.Property<BigDecimal?> =
    this.validate(GreaterOrEqual(BigDecimal.ZERO)) { it == null || it >= BigDecimal.ZERO }

/**
 * Validates if the [BigDecimal] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNegative(): Validator<E>.Property<BigDecimal?> =
    this.validate(Less(BigDecimal.ZERO)) { it == null || it < BigDecimal.ZERO }

/**
 * Validates if the [BigDecimal] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNegativeOrZero(): Validator<E>.Property<BigDecimal?> =
    this.validate(LessOrEqual(BigDecimal.ZERO)) { it == null || it <= BigDecimal.ZERO }

/**
 * Validates if the [BigDecimal] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<BigDecimal?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.precision() - it.scale() in min.rangeTo(max) }

/**
 * Validates if the [BigDecimal] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<BigDecimal?> =
    this.validate(DecimalDigits(min, max)) { it == null || (if (it.scale() < 0) 0 else it.scale()) in min.rangeTo(max) }
