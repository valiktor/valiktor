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
 * Validates if the [Long] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isZero(): Validator<E>.Property<Long?> =
        this.validate(Equals(0.toLong()), { it == null || it == 0.toLong() })

/**
 * Validates if the [Long] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isNotZero(): Validator<E>.Property<Long?> =
        this.validate(NotEquals(0.toLong()), { it == null || it != 0.toLong() })

/**
 * Validates if the [Long] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isOne(): Validator<E>.Property<Long?> =
        this.validate(Equals(1.toLong()), { it == null || it == 1.toLong() })

/**
 * Validates if the [Long] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isNotOne(): Validator<E>.Property<Long?> =
        this.validate(NotEquals(1.toLong()), { it == null || it != 1.toLong() })

/**
 * Validates if the [Long] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isPositive(): Validator<E>.Property<Long?> =
        this.validate(Greater(0.toLong()), { it == null || it > 0.toLong() })

/**
 * Validates if the [Long] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isNotPositive(): Validator<E>.Property<Long?> =
        this.validate(LessOrEqual(0.toLong()), { it == null || it <= 0.toLong() })

/**
 * Validates if the [Long] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isNegative(): Validator<E>.Property<Long?> =
        this.validate(Less(0.toLong()), { it == null || it < 0.toLong() })

/**
 * Validates if the [Long] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isNotNegative(): Validator<E>.Property<Long?> =
        this.validate(GreaterOrEqual(0.toLong()), { it == null || it >= 0.toLong() })

/**
 * Validates if the [Long] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isLessThan(value: Long): Validator<E>.Property<Long?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [Long] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isLessThanOrEqualTo(value: Long): Validator<E>.Property<Long?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Long] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isGreaterThan(value: Long): Validator<E>.Property<Long?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Long] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isGreaterThanOrEqualTo(value: Long): Validator<E>.Property<Long?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Long] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isBetween(start: Long, end: Long): Validator<E>.Property<Long?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [Long] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.isNotBetween(start: Long, end: Long): Validator<E>.Property<Long?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

/**
 * Validates if the [Long] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Long?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Long?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().length in min.rangeTo(max) })