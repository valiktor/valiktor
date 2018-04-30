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
import java.math.BigDecimal

/**
 * Validates if the [BigDecimal] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isZero(): Validator<E>.Property<BigDecimal?> =
        this.validate(Equals(BigDecimal.ZERO), { it == null || it.compareTo(BigDecimal.ZERO) == 0 })

/**
 * Validates if the [BigDecimal] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotZero(): Validator<E>.Property<BigDecimal?> =
        this.validate(NotEquals(BigDecimal.ZERO), { it == null || it.compareTo(BigDecimal.ZERO) != 0 })

/**
 * Validates if the [BigDecimal] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isOne(): Validator<E>.Property<BigDecimal?> =
        this.validate(Equals(BigDecimal.ONE), { it == null || it.compareTo(BigDecimal.ONE) == 0 })

/**
 * Validates if the [BigDecimal] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotOne(): Validator<E>.Property<BigDecimal?> =
        this.validate(NotEquals(BigDecimal.ONE), { it == null || it.compareTo(BigDecimal.ONE) != 0 })

/**
 * Validates if the [BigDecimal] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isPositive(): Validator<E>.Property<BigDecimal?> =
        this.validate(Greater(BigDecimal.ZERO), { it == null || it > BigDecimal.ZERO })

/**
 * Validates if the [BigDecimal] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotPositive(): Validator<E>.Property<BigDecimal?> =
        this.validate(LessOrEqual(BigDecimal.ZERO), { it == null || it <= BigDecimal.ZERO })

/**
 * Validates if the [BigDecimal] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNegative(): Validator<E>.Property<BigDecimal?> =
        this.validate(Less(BigDecimal.ZERO), { it == null || it < BigDecimal.ZERO })

/**
 * Validates if the [BigDecimal] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotNegative(): Validator<E>.Property<BigDecimal?> =
        this.validate(GreaterOrEqual(BigDecimal.ZERO), { it == null || it >= BigDecimal.ZERO })

/**
 * Validates if the [BigDecimal] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isLessThan(value: BigDecimal): Validator<E>.Property<BigDecimal?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [BigDecimal] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isLessThanOrEqualTo(value: BigDecimal): Validator<E>.Property<BigDecimal?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [BigDecimal] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isGreaterThan(value: BigDecimal): Validator<E>.Property<BigDecimal?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [BigDecimal] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isGreaterThanOrEqualTo(value: BigDecimal): Validator<E>.Property<BigDecimal?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [BigDecimal] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isBetween(start: BigDecimal, end: BigDecimal): Validator<E>.Property<BigDecimal?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [BigDecimal] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigDecimal?>.isNotBetween(start: BigDecimal, end: BigDecimal): Validator<E>.Property<BigDecimal?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

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
        this.validate(IntegerDigits(min, max), { it == null || it.precision() - it.scale() in min.rangeTo(max) })

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
        this.validate(DecimalDigits(min, max), { it == null || (if (it.scale() < 0) 0 else it.scale()) in min.rangeTo(max) })