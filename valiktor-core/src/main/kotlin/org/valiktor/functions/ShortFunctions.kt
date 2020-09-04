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
 * Validates if the [Short] property is equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isZero(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(Equals<Short>(0)) { it == null || it == 0.toShort() }

/**
 * Validates if the [Short] property is not equal to zero
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isNotZero(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(NotEquals<Short>(0)) { it == null || it != 0.toShort() }

/**
 * Validates if the [Short] property is equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isOne(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(Equals<Short>(1)) { it == null || it == 1.toShort() }

/**
 * Validates if the [Short] property is not equal to one
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isNotOne(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(NotEquals<Short>(1)) { it == null || it != 1.toShort() }

/**
 * Validates if the [Short] property is positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isPositive(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(Greater<Short>(0)) { it == null || it > 0.toShort() }

/**
 * Validates if the [Short] property isn't negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isPositiveOrZero(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(GreaterOrEqual<Short>(0)) { it == null || it >= 0.toShort() }

/**
 * Validates if the [Short] property is negative
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isNegative(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(Less<Short>(0)) { it == null || it < 0.toShort() }

/**
 * Validates if the [Short] property isn't positive
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.isNegativeOrZero(): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(LessOrEqual<Short>(0)) { it == null || it <= 0.toShort() }

/**
 * Validates if the [Short] property digits is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Short?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ReceiverValidator<E, Short?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) }
