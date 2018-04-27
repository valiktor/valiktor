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
 * Validates if the [Int] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isZero(): Validator<E>.Property<Int?> =
        this.validate(Equals(0), { it == null || it == 0 })

/**
 * Validates if the [Int] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isNotZero(): Validator<E>.Property<Int?> =
        this.validate(NotEquals(0), { it == null || it != 0 })

/**
 * Validates if the [Int] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isOne(): Validator<E>.Property<Int?> =
        this.validate(Equals(1), { it == null || it == 1 })

/**
 * Validates if the [Int] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isNotOne(): Validator<E>.Property<Int?> =
        this.validate(NotEquals(1), { it == null || it != 1 })

/**
 * Validates if the [Int] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isPositive(): Validator<E>.Property<Int?> =
        this.validate(Greater(0), { it == null || it > 0 })

/**
 * Validates if the [Int] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isNotPositive(): Validator<E>.Property<Int?> =
        this.validate(LessOrEqual(0), { it == null || it <= 0 })

/**
 * Validates if the [Int] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isNegative(): Validator<E>.Property<Int?> =
        this.validate(Less(0), { it == null || it < 0 })

/**
 * Validates if the [Int] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isNotNegative(): Validator<E>.Property<Int?> =
        this.validate(GreaterOrEqual(0), { it == null || it >= 0 })

/**
 * Validates if the [Int] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isLessThan(value: Int): Validator<E>.Property<Int?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [Int] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isLessThanOrEqualTo(value: Int): Validator<E>.Property<Int?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Int] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isGreaterThan(value: Int): Validator<E>.Property<Int?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Int] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isGreaterThanOrEqualTo(value: Int): Validator<E>.Property<Int?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Int] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isBetween(start: Int, end: Int): Validator<E>.Property<Int?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [Int] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.isNotBetween(start: Int, end: Int): Validator<E>.Property<Int?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

/**
 * Validates if the [Int] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Int?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Int?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) })