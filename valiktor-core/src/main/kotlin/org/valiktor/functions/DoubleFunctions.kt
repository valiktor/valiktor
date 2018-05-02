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

/**
 * Validates if the [Double] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isZero(): Validator<E>.Property<Double?> =
        this.validate(Equals(0.0), { it == null || it == 0.0 })

/**
 * Validates if the [Double] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isNotZero(): Validator<E>.Property<Double?> =
        this.validate(NotEquals(0.0), { it == null || it != 0.0 })

/**
 * Validates if the [Double] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isOne(): Validator<E>.Property<Double?> =
        this.validate(Equals(1.0), { it == null || it == 1.0 })

/**
 * Validates if the [Double] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isNotOne(): Validator<E>.Property<Double?> =
        this.validate(NotEquals(1.0), { it == null || it != 1.0 })

/**
 * Validates if the [Double] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isPositive(): Validator<E>.Property<Double?> =
        this.validate(Greater(0.0), { it == null || it > 0.0 })

/**
 * Validates if the [Double] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isNotPositive(): Validator<E>.Property<Double?> =
        this.validate(LessOrEqual(0.0), { it == null || it <= 0.0 })

/**
 * Validates if the [Double] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isNegative(): Validator<E>.Property<Double?> =
        this.validate(Less(0.0), { it == null || it < 0.0 })

/**
 * Validates if the [Double] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isNotNegative(): Validator<E>.Property<Double?> =
        this.validate(GreaterOrEqual(0.0), { it == null || it >= 0.0 })

/**
 * Validates if the [Double] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isLessThan(value: Double): Validator<E>.Property<Double?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [Double] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isLessThanOrEqualTo(value: Double): Validator<E>.Property<Double?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Double] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isGreaterThan(value: Double): Validator<E>.Property<Double?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Double] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isGreaterThanOrEqualTo(value: Double): Validator<E>.Property<Double?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Double] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isBetween(start: Double, end: Double): Validator<E>.Property<Double?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [Double] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.isNotBetween(start: Double, end: Double): Validator<E>.Property<Double?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

/**
 * Validates if the [Double] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Double?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().removePrefix("-").split(".")[0].length in min.rangeTo(max) })

/**
 * Validates if the [Double] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Double?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Double?> =
        this.validate(DecimalDigits(min, max), { it == null || it.toString().removePrefix("-").split(".")[1].length in min.rangeTo(max) })