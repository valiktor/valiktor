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
 * Validates if the [Int] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isZero(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(Equals(0)) { it == null || it == 0 }

/**
 * Validates if the [Int] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isNotZero(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(NotEquals(0)) { it == null || it != 0 }

/**
 * Validates if the [Int] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isOne(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(Equals(1)) { it == null || it == 1 }

/**
 * Validates if the [Int] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isNotOne(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(NotEquals(1)) { it == null || it != 1 }

/**
 * Validates if the [Int] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isPositive(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(Greater(0)) { it == null || it > 0 }

/**
 * Validates if the [Int] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isPositiveOrZero(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(GreaterOrEqual(0)) { it == null || it >= 0 }

/**
 * Validates if the [Int] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isNegative(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(Less(0)) { it == null || it < 0 }

/**
 * Validates if the [Int] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.isNegativeOrZero(): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(LessOrEqual(0)) { it == null || it <= 0 }

/**
 * Validates if the [Int] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Int?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ReceiverValidator<E, Int?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) }
