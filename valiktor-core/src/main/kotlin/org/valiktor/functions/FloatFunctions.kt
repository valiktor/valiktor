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
import org.valiktor.constraints.DecimalDigits
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotEquals

/**
 * Validates if the [Float] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isZero(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(Equals(0f)) { it == null || it == 0f }

/**
 * Validates if the [Float] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isNotZero(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(NotEquals(0f)) { it == null || it != 0f }

/**
 * Validates if the [Float] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isOne(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(Equals(1f)) { it == null || it == 1f }

/**
 * Validates if the [Float] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isNotOne(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(NotEquals(1f)) { it == null || it != 1f }

/**
 * Validates if the [Float] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isPositive(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(Greater(0f)) { it == null || it > 0f }

/**
 * Validates if the [Float] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isPositiveOrZero(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(GreaterOrEqual(0f)) { it == null || it >= 0f }

/**
 * Validates if the [Float] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isNegative(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(Less(0f)) { it == null || it < 0f }

/**
 * Validates if the [Float] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.isNegativeOrZero(): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(LessOrEqual(0f)) { it == null || it <= 0f }

/**
 * Validates if the [Float] integer digits (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.hasIntegerDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.toString().removePrefix("-").split(".")[0].length in min.rangeTo(max) }

/**
 * Validates if the [Float] decimal digits (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Float?>.hasDecimalDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ReceiverValidator<E, Float?> =
    this.validate(DecimalDigits(min, max)) { it == null || it.toString().removePrefix("-").split(".")[1].length in min.rangeTo(max) }
