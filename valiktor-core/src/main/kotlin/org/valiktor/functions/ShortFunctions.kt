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
 * Validates if the [Short] value is equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isZero(): Validator<E>.ValueValidator<Short?> =
    this.validate(Equals<Short>(0)) { it == null || it == 0.toShort() }

/**
 * Validates if the [Short] value is not equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isNotZero(): Validator<E>.ValueValidator<Short?> =
    this.validate(NotEquals<Short>(0)) { it == null || it != 0.toShort() }

/**
 * Validates if the [Short] value is equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isOne(): Validator<E>.ValueValidator<Short?> =
    this.validate(Equals<Short>(1)) { it == null || it == 1.toShort() }

/**
 * Validates if the [Short] value is not equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isNotOne(): Validator<E>.ValueValidator<Short?> =
    this.validate(NotEquals<Short>(1)) { it == null || it != 1.toShort() }

/**
 * Validates if the [Short] value is positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isPositive(): Validator<E>.ValueValidator<Short?> =
    this.validate(Greater<Short>(0)) { it == null || it > 0.toShort() }

/**
 * Validates if the [Short] value isn't negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isPositiveOrZero(): Validator<E>.ValueValidator<Short?> =
    this.validate(GreaterOrEqual<Short>(0)) { it == null || it >= 0.toShort() }

/**
 * Validates if the [Short] value is negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isNegative(): Validator<E>.ValueValidator<Short?> =
    this.validate(Less<Short>(0)) { it == null || it < 0.toShort() }

/**
 * Validates if the [Short] value isn't positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.isNegativeOrZero(): Validator<E>.ValueValidator<Short?> =
    this.validate(LessOrEqual<Short>(0)) { it == null || it <= 0.toShort() }

/**
 * Validates if the [Short] value digits is within the limits (min and max)
 *
 * @value min specifies the minimum size
 * @value max specifies the maximum size
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Short?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ValueValidator<Short?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) }
