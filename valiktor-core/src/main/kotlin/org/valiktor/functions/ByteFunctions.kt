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
 * Validates if the [Byte] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isZero(): Validator<E>.Property<Byte?> =
        this.validate(Equals<Byte>(0), { it == null || it == 0.toByte() })

/**
 * Validates if the [Byte] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isNotZero(): Validator<E>.Property<Byte?> =
        this.validate(NotEquals<Byte>(0), { it == null || it != 0.toByte() })

/**
 * Validates if the [Byte] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isOne(): Validator<E>.Property<Byte?> =
        this.validate(Equals<Byte>(1), { it == null || it == 1.toByte() })

/**
 * Validates if the [Byte] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isNotOne(): Validator<E>.Property<Byte?> =
        this.validate(NotEquals<Byte>(1), { it == null || it != 1.toByte() })

/**
 * Validates if the [Byte] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isPositive(): Validator<E>.Property<Byte?> =
        this.validate(Greater<Byte>(0), { it == null || it > 0.toByte() })

/**
 * Validates if the [Byte] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isNotPositive(): Validator<E>.Property<Byte?> =
        this.validate(LessOrEqual<Byte>(0), { it == null || it <= 0.toByte() })

/**
 * Validates if the [Byte] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isNegative(): Validator<E>.Property<Byte?> =
        this.validate(Less<Byte>(0), { it == null || it < 0.toByte() })

/**
 * Validates if the [Byte] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isNotNegative(): Validator<E>.Property<Byte?> =
        this.validate(GreaterOrEqual<Byte>(0), { it == null || it >= 0.toByte() })

/**
 * Validates if the [Byte] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isLessThan(value: Byte): Validator<E>.Property<Byte?> =
        this.validate(Less(value), { it == null || it < value })

/**
 * Validates if the [Byte] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isLessThanOrEqualTo(value: Byte): Validator<E>.Property<Byte?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Byte] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isGreaterThan(value: Byte): Validator<E>.Property<Byte?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Byte] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isGreaterThanOrEqualTo(value: Byte): Validator<E>.Property<Byte?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Byte] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isBetween(start: Byte, end: Byte): Validator<E>.Property<Byte?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [Byte] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.isNotBetween(start: Byte, end: Byte): Validator<E>.Property<Byte?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

/**
 * Validates if the [Byte] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Byte?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Byte?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) })