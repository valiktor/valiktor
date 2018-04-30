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
 * Validates if the [Float] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isZero(): Validator<E>.Property<Float?> =
        this.validate(Equals(0.toFloat()), { it == null || it == 0.toFloat() })

/**
 * Validates if the [Float] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isNotZero(): Validator<E>.Property<Float?> =
        this.validate(NotEquals(0.toFloat()), { it == null || it != 0.toFloat() })

/**
 * Validates if the [Float] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isOne(): Validator<E>.Property<Float?> =
        this.validate(Equals(1.toFloat()), { it == null || it == 1.toFloat() })

/**
 * Validates if the [Float] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isNotOne(): Validator<E>.Property<Float?> =
        this.validate(NotEquals(1.toFloat()), { it == null || it != 1.toFloat() })

/**
 * Validates if the [Float] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isPositive(): Validator<E>.Property<Float?> =
        this.validate(Greater(0.toFloat()), { it == null || it > 0.toFloat() })

/**
 * Validates if the [Float] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isNotPositive(): Validator<E>.Property<Float?> =
        this.validate(LessOrEqual(0.toFloat()), { it == null || it <= 0.toFloat() })

/**
 * Validates if the [Float] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isNegative(): Validator<E>.Property<Float?> =
        this.validate(Less(0.toFloat()), { it == null || it < 0.toFloat() })

/**
 * Validates if the [Float] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isNotNegative(): Validator<E>.Property<Float?> =
        this.validate(GreaterOrEqual(0.toFloat()), { it == null || it >= 0.toFloat() })

/**
 * Validates if the [Float] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isLessThan(value: Float): Validator<E>.Property<Float?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [Float] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isLessThanOrEqualTo(value: Float): Validator<E>.Property<Float?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Float] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isGreaterThan(value: Float): Validator<E>.Property<Float?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Float] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isGreaterThanOrEqualTo(value: Float): Validator<E>.Property<Float?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Float] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isBetween(start: Float, end: Float): Validator<E>.Property<Float?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [Float] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.isNotBetween(start: Float, end: Float): Validator<E>.Property<Float?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

/**
 * Validates if the [Float] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Float?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().removePrefix("-").split(".")[0].length in min.rangeTo(max) })

/**
 * Validates if the [Float] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Float?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Float?> =
        this.validate(DecimalDigits(min, max), { it == null || it.toString().removePrefix("-").split(".")[1].length in min.rangeTo(max) })