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
 * Validates if the [Short] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isZero(): Validator<E>.Property<Short?> =
        this.validate(Equals(0.toShort()), { it == null || it == 0.toShort() })

/**
 * Validates if the [Short] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isNotZero(): Validator<E>.Property<Short?> =
        this.validate(NotEquals(0.toShort()), { it == null || it != 0.toShort() })

/**
 * Validates if the [Short] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isOne(): Validator<E>.Property<Short?> =
        this.validate(Equals(1.toShort()), { it == null || it == 1.toShort() })

/**
 * Validates if the [Short] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isNotOne(): Validator<E>.Property<Short?> =
        this.validate(NotEquals(1.toShort()), { it == null || it != 1.toShort() })

/**
 * Validates if the [Short] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isPositive(): Validator<E>.Property<Short?> =
        this.validate(Greater(0.toShort()), { it == null || it > 0.toShort() })

/**
 * Validates if the [Short] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isNotPositive(): Validator<E>.Property<Short?> =
        this.validate(LessOrEqual(0.toShort()), { it == null || it <= 0.toShort() })

/**
 * Validates if the [Short] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isNegative(): Validator<E>.Property<Short?> =
        this.validate(Less(0.toShort()), { it == null || it < 0.toShort() })

/**
 * Validates if the [Short] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isNotNegative(): Validator<E>.Property<Short?> =
        this.validate(GreaterOrEqual(0.toShort()), { it == null || it >= 0.toShort() })

/**
 * Validates if the [Short] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isLessThan(value: Short): Validator<E>.Property<Short?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [Short] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isLessThanOrEqualTo(value: Short): Validator<E>.Property<Short?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Short] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isGreaterThan(value: Short): Validator<E>.Property<Short?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Short] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isGreaterThanOrEqualTo(value: Short): Validator<E>.Property<Short?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Short] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isBetween(start: Short, end: Short): Validator<E>.Property<Short?> =
        this.validate(Between(start, end), { it == null || it in IntRange(start.toInt(), end.toInt()) })

/**
 * Validates if the [Short] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.isNotBetween(start: Short, end: Short): Validator<E>.Property<Short?> =
        this.validate(NotBetween(start, end), { it == null || it !in IntRange(start.toInt(), end.toInt()) })

/**
 * Validates if the [Short] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Short?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Short?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().length in IntRange(min, max) })