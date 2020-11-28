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
import org.valiktor.constraints.Between
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween

/**
 * Validates if the [Comparable] value is less than another value
 *
 * @value value specifies the value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : Comparable<T>> Validator<E>.ValueValidator<T?>.isLessThan(value: T): Validator<E>.ValueValidator<T?> =
    this.validate(Less(value)) { it == null || it < value }

/**
 * Validates if the [Comparable] value is less than or equal to another value
 *
 * @value value specifies the value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : Comparable<T>> Validator<E>.ValueValidator<T?>.isLessThanOrEqualTo(value: T): Validator<E>.ValueValidator<T?> =
    this.validate(LessOrEqual(value)) { it == null || it <= value }

/**
 * Validates if the [Comparable] value is greater than another value
 *
 * @value value specifies the value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : Comparable<T>> Validator<E>.ValueValidator<T?>.isGreaterThan(value: T): Validator<E>.ValueValidator<T?> =
    this.validate(Greater(value)) { it == null || it > value }

/**
 * Validates if the [Comparable] value is greater than or equal to another value
 *
 * @value value specifies the value that should be validated
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : Comparable<T>> Validator<E>.ValueValidator<T?>.isGreaterThanOrEqualTo(value: T): Validator<E>.ValueValidator<T?> =
    this.validate(GreaterOrEqual(value)) { it == null || it >= value }

/**
 * Validates if the [Comparable] value is between two values
 *
 * @value start (inclusive) specifies value that should start
 * @value end (inclusive) specifies value that should end
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : Comparable<T>> Validator<E>.ValueValidator<T?>.isBetween(start: T, end: T): Validator<E>.ValueValidator<T?> =
    this.validate(Between(start, end)) { it == null || it in start.rangeTo(end) }

/**
 * Validates if the [Comparable] value isn't between two values
 *
 * @value start (inclusive) specifies value that shouldn't start
 * @value end (inclusive) specifies value that shouldn't end
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T : Comparable<T>> Validator<E>.ValueValidator<T?>.isNotBetween(start: T, end: T): Validator<E>.ValueValidator<T?> =
    this.validate(NotBetween(start, end)) { it == null || it !in start.rangeTo(end) }
