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

import org.valiktor.DefaultConstraintViolation
import org.valiktor.Validator
import org.valiktor.constraints.*

/**
 * Validates the array property initializing another DSL function recursively
 *
 * @param block specifies the function DSL
 * @receiver the property to be validated
 * @return the same receiver property
 */
@JvmName("validateForEachArray")
fun <E, T> Validator<E>.Property<Array<T>?>.validateForEach(block: Validator<T>.() -> Unit): Validator<E>.Property<Array<T>?> {
    this.property.get(obj)?.forEachIndexed { index, value ->
        this.addConstraintViolations(Validator(value).apply(block).constraintViolations.map {
            DefaultConstraintViolation(
                    property = "${this.property.name}[$index].${it.property}",
                    value = it.value,
                    constraint = it.constraint)
        })
    }
    return this
}

/**
 * Validates if the array property is empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.isEmpty(): Validator<E>.Property<Array<T>?> =
        this.validate(Empty(), { it == null || it.count() == 0 })

/**
 * Validates if the array property is not empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.isNotEmpty(): Validator<E>.Property<Array<T>?> =
        this.validate(NotEmpty(), { it == null || it.count() > 0 })

/**
 * Validates if the array property size is within the limits (min and max)
 *
 * @param min specifies the minimum size
 * @param max specifies the maximum size
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.hasSize(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Array<T>?> =
        this.validate(Size(min, max), { it == null || it.count() in IntRange(min, max) })

/**
 * Validates if the array property contains the value
 *
 * @param value specifies the value that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.contains(value: T): Validator<E>.Property<Array<T>?> =
        this.validate(Contains(value), { it == null || it.contains(value) })

/**
 * Validates if the array property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.containsAll(vararg values: T): Validator<E>.Property<Array<T>?> =
        this.validate(ContainsAll(values.toSet()), { it == null || values.toSet().all { e -> it.contains(e) } })

/**
 * Validates if the array property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.containsAll(values: Iterable<T>): Validator<E>.Property<Array<T>?> =
        this.validate(ContainsAll(values), { it == null || values.all { e -> it.contains(e) } })

/**
 * Validates if the array property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.containsAny(vararg values: T): Validator<E>.Property<Array<T>?> =
        this.validate(ContainsAny(values.toSet()), { it == null || values.toSet().any { e -> it.contains(e) } })

/**
 * Validates if the array property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.containsAny(values: Iterable<T>): Validator<E>.Property<Array<T>?> =
        this.validate(ContainsAny(values), { it == null || values.any { e -> it.contains(e) } })

/**
 * Validates if the array property doesn't contain the value
 *
 * @param value specifies the value that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.doesNotContain(value: T): Validator<E>.Property<Array<T>?> =
        this.validate(NotContain(value), { it == null || !it.contains(value) })

/**
 * Validates if the array property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.doesNotContainAll(vararg values: T): Validator<E>.Property<Array<T>?> =
        this.validate(NotContainAll(values.toSet()), { it == null || !values.toSet().all { e -> it.contains(e) } })

/**
 * Validates if the array property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.doesNotContainAll(values: Iterable<T>): Validator<E>.Property<Array<T>?> =
        this.validate(NotContainAll(values), { it == null || !values.all { e -> it.contains(e) } })

/**
 * Validates if the array property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.doesNotContainAny(vararg values: T): Validator<E>.Property<Array<T>?> =
        this.validate(NotContainAny(values.toSet()), { it == null || !values.toSet().any { e -> it.contains(e) } })

/**
 * Validates if the array property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Array<T>?>.doesNotContainAny(values: Iterable<T>): Validator<E>.Property<Array<T>?> =
        this.validate(NotContainAny(values), { it == null || !values.any { e -> it.contains(e) } })