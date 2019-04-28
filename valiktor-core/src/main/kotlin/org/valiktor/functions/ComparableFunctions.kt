/*
 * Copyright 2018-2019 https://www.valiktor.org
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
 * Validates if the [Comparable] property is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T : Comparable<T>> Validator<E>.Property<T?>.isLessThan(value: T): Validator<E>.Property<T?> =
    this.validate(Less(value)) { it == null || it < value }

/**
 * Validates if the [Comparable] property is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T : Comparable<T>> Validator<E>.Property<T?>.isLessThanOrEqualTo(value: T): Validator<E>.Property<T?> =
    this.validate(LessOrEqual(value)) { it == null || it <= value }

/**
 * Validates if the [Comparable] property is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T : Comparable<T>> Validator<E>.Property<T?>.isGreaterThan(value: T): Validator<E>.Property<T?> =
    this.validate(Greater(value)) { it == null || it > value }

/**
 * Validates if the [Comparable] property is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T : Comparable<T>> Validator<E>.Property<T?>.isGreaterThanOrEqualTo(value: T): Validator<E>.Property<T?> =
    this.validate(GreaterOrEqual(value)) { it == null || it >= value }

/**
 * Validates if the [Comparable] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T : Comparable<T>> Validator<E>.Property<T?>.isBetween(start: T, end: T): Validator<E>.Property<T?> =
    this.validate(Between(start, end)) { it == null || it in start.rangeTo(end) }

/**
 * Validates if the [Comparable] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T : Comparable<T>> Validator<E>.Property<T?>.isNotBetween(start: T, end: T): Validator<E>.Property<T?> =
    this.validate(NotBetween(start, end)) { it == null || it !in start.rangeTo(end) }