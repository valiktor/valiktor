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
 * Validates if the [Long] value is equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isZero(): Validator<E>.ValueValidator<Long?> =
    this.validate(Equals(0L)) { it == null || it == 0L }

/**
 * Validates if the [Long] value is not equal to zero
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isNotZero(): Validator<E>.ValueValidator<Long?> =
    this.validate(NotEquals(0L)) { it == null || it != 0L }

/**
 * Validates if the [Long] value is equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isOne(): Validator<E>.ValueValidator<Long?> =
    this.validate(Equals(1L)) { it == null || it == 1L }

/**
 * Validates if the [Long] value is not equal to one
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isNotOne(): Validator<E>.ValueValidator<Long?> =
    this.validate(NotEquals(1L)) { it == null || it != 1L }

/**
 * Validates if the [Long] value is positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isPositive(): Validator<E>.ValueValidator<Long?> =
    this.validate(Greater(0L)) { it == null || it > 0L }

/**
 * Validates if the [Long] value isn't negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isPositiveOrZero(): Validator<E>.ValueValidator<Long?> =
    this.validate(GreaterOrEqual(0L)) { it == null || it >= 0L }

/**
 * Validates if the [Long] value is negative
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isNegative(): Validator<E>.ValueValidator<Long?> =
    this.validate(Less(0L)) { it == null || it < 0L }

/**
 * Validates if the [Long] value isn't positive
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.isNegativeOrZero(): Validator<E>.ValueValidator<Long?> =
    this.validate(LessOrEqual(0L)) { it == null || it <= 0L }

/**
 * Validates if the [Long] value digits is within the limits (min and max)
 *
 * @value min specifies the minimum size
 * @value max specifies the maximum size
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Long?>.hasDigits(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.ValueValidator<Long?> =
    this.validate(IntegerDigits(min, max)) { it == null || it.toString().removePrefix("-").length in min.rangeTo(max) }
