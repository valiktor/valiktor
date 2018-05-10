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
import java.util.*

/**
 * Validates if the [Date] property is today
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isToday(): Validator<E>.Property<Date?> {
    val start = Calendar.getInstance()
    start.set(Calendar.HOUR_OF_DAY, 0)
    start.set(Calendar.MINUTE, 0)
    start.set(Calendar.SECOND, 0)
    start.set(Calendar.MILLISECOND, 0)

    val end = Calendar.getInstance()
    end.set(Calendar.HOUR_OF_DAY, 23)
    end.set(Calendar.MINUTE, 59)
    end.set(Calendar.SECOND, 59)
    end.set(Calendar.MILLISECOND, 999)

    return this.validate(Today, { it == null || it in start.time.rangeTo(end.time) })
}

/**
 * Validates if the [Date] property is before another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isBefore(value: Date): Validator<E>.Property<Date?> =
        this.validate(Less(value), { it == null || it < value })


/**
 * Validates if the [Date] property is before or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isBeforeOrEqualTo(value: Date): Validator<E>.Property<Date?> =
        this.validate(LessOrEqual(value), { it == null || it <= value })

/**
 * Validates if the [Date] property is after another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isAfter(value: Date): Validator<E>.Property<Date?> =
        this.validate(Greater(value), { it == null || it > value })

/**
 * Validates if the [Date] property is after or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isAfterOrEqualTo(value: Date): Validator<E>.Property<Date?> =
        this.validate(GreaterOrEqual(value), { it == null || it >= value })

/**
 * Validates if the [Date] property is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isBetween(start: Date, end: Date): Validator<E>.Property<Date?> =
        this.validate(Between(start, end), { it == null || it in start.rangeTo(end) })

/**
 * Validates if the [Date] property isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Date?>.isNotBetween(start: Date, end: Date): Validator<E>.Property<Date?> =
        this.validate(NotBetween(start, end), { it == null || it !in start.rangeTo(end) })