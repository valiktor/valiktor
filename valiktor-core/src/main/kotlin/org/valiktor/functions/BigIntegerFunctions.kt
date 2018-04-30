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
import java.math.BigInteger

/**
 * Validates if the [BigInteger] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isZero(): Validator<E>.Property<BigInteger?> =
        this.validate(Equals(BigInteger.ZERO), { it == null || it == BigInteger.ZERO })

/**
 * Validates if the [BigInteger] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isNotZero(): Validator<E>.Property<BigInteger?> =
        this.validate(NotEquals(BigInteger.ZERO), { it == null || it != BigInteger.ZERO })

/**
 * Validates if the [BigInteger] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isOne(): Validator<E>.Property<BigInteger?> =
        this.validate(Equals(BigInteger.ONE), { it == null || it == BigInteger.ONE })

/**
 * Validates if the [BigInteger] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isNotOne(): Validator<E>.Property<BigInteger?> =
        this.validate(NotEquals(BigInteger.ONE), { it == null || it != BigInteger.ONE })

/**
 * Validates if the [BigInteger] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isPositive(): Validator<E>.Property<BigInteger?> =
        this.validate(Greater(BigInteger.ZERO), { it == null || it > BigInteger.ZERO })

/**
 * Validates if the [BigInteger] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isNotPositive(): Validator<E>.Property<BigInteger?> =
        this.validate(LessOrEqual(BigInteger.ZERO), { it == null || it <= BigInteger.ZERO })

/**
 * Validates if the [BigInteger] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isNegative(): Validator<E>.Property<BigInteger?> =
        this.validate(Less(BigInteger.ZERO), { it == null || it < BigInteger.ZERO })

/**
 * Validates if the [BigInteger] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isNotNegative(): Validator<E>.Property<BigInteger?> =
        this.validate(GreaterOrEqual(BigInteger.ZERO), { it == null || it >= BigInteger.ZERO })

/**
 * Validates if the [BigInteger] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isLessThan(value: BigInteger): Validator<E>.Property<BigInteger?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [BigInteger] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isLessThanOrEqualTo(value: BigInteger): Validator<E>.Property<BigInteger?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [BigInteger] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isGreaterThan(value: BigInteger): Validator<E>.Property<BigInteger?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [BigInteger] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isGreaterThanOrEqualTo(value: BigInteger): Validator<E>.Property<BigInteger?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [BigInteger] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isBetween(start: BigInteger, end: BigInteger): Validator<E>.Property<BigInteger?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [BigInteger] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.isNotBetween(start: BigInteger, end: BigInteger): Validator<E>.Property<BigInteger?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })

/**
 * Validates if the [BigInteger] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<BigInteger?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<BigInteger?> =
        this.validate(IntegerDigits(min, max), { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) })