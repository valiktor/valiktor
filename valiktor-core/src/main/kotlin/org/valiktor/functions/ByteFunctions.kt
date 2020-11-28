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
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotEquals

/**
 * Validates if the [Byte] value is equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isZero(): Validator<E>.ValueValidator<Byte?> =
    this.validate(Equals<Byte>(0)) { it == null || it == 0.toByte() }

/**
 * Validates if the [Byte] value is not equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isNotZero(): Validator<E>.ValueValidator<Byte?> =
    this.validate(NotEquals<Byte>(0)) { it == null || it != 0.toByte() }

/**
 * Validates if the [Byte] value is equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isOne(): Validator<E>.ValueValidator<Byte?> =
    this.validate(Equals<Byte>(1)) { it == null || it == 1.toByte() }

/**
 * Validates if the [Byte] value is not equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isNotOne(): Validator<E>.ValueValidator<Byte?> =
    this.validate(NotEquals<Byte>(1)) { it == null || it != 1.toByte() }

/**
 * Validates if the [Byte] value is positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isPositive(): Validator<E>.ValueValidator<Byte?> =
    this.validate(Greater<Byte>(0)) { it == null || it > 0.toByte() }

/**
 * Validates if the [Byte] value isn't negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isPositiveOrZero(): Validator<E>.ValueValidator<Byte?> =
    this.validate(GreaterOrEqual<Byte>(0)) { it == null || it >= 0.toByte() }

/**
 * Validates if the [Byte] value is negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isNegative(): Validator<E>.ValueValidator<Byte?> =
    this.validate(Less<Byte>(0)) { it == null || it < 0.toByte() }

/**
 * Validates if the [Byte] value isn't positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.isNegativeOrZero(): Validator<E>.ValueValidator<Byte?> =
    this.validate(LessOrEqual<Byte>(0)) { it == null || it <= 0.toByte() }

/**
 * Validates if the [Byte] value digits is within the limits (min and max)
 *
 * @value min specifies the minimum size
 * @value max specifies the maximum size
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Byte?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ValueValidator<Byte?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) }
