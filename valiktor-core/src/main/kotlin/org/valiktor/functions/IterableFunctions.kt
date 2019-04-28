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

import org.valiktor.DefaultConstraintViolation
import org.valiktor.Validator
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.Empty
import org.valiktor.constraints.In
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.Size

/**
 * Validates the iterable property initializing another DSL function recursively
 *
 * @param block specifies the function DSL
 * @receiver the property to be validated
 * @return the same receiver property
 */
@JvmName("validateForEachIterable")
fun <E, T> Validator<E>.Property<Iterable<T>?>.validateForEach(block: Validator<T>.(T) -> Unit): Validator<E>.Property<Iterable<T>?> {
    this.property.get(obj)?.forEachIndexed { index, value ->
        this.addConstraintViolations(Validator(value).apply { block(value) }.constraintViolations.map {
            DefaultConstraintViolation(
                property = "${this.property.name}[$index].${it.property}",
                value = it.value,
                constraint = it.constraint)
        })
    }
    return this
}

/**
 * Validates if the [Iterable] property value is equal to one of the values
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.isIn(vararg values: Iterable<T>): Validator<E>.Property<Iterable<T>?> =
    this.validate(In(values.toSet())) { it == null || values.contains(it) }

/**
 * Validates if the [Iterable] property value is equal to one of the values
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.isIn(values: Iterable<Iterable<T>>): Validator<E>.Property<Iterable<T>?> =
    this.validate(In(values)) { it == null || values.contains(it) }

/**
 * Validates if the [Iterable] property value isn't equal to any value
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.isNotIn(vararg values: Iterable<T>): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.contains(it) }

/**
 * Validates if the [Iterable] property value isn't equal to any value
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.isNotIn(values: Iterable<Iterable<T>>): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotIn(values)) { it == null || !values.contains(it) }

/**
 * Validates if the [Iterable] property is empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.isEmpty(): Validator<E>.Property<Iterable<T>?> =
    this.validate(Empty) { it == null || it.count() == 0 }

/**
 * Validates if the [Iterable] property is not empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.isNotEmpty(): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotEmpty) { it == null || it.count() > 0 }

/**
 * Validates if the [Iterable] property size is within the limits (min and max)
 *
 * @param min specifies the minimum size
 * @param max specifies the maximum size
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.hasSize(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<Iterable<T>?> =
    this.validate(Size(min, max)) { it == null || it.count() in min.rangeTo(max) }

/**
 * Validates if the [Iterable] property contains the value
 *
 * @param value specifies the value that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.contains(value: T): Validator<E>.Property<Iterable<T>?> =
    this.validate(Contains(value)) { it == null || it.contains(value) }

/**
 * Validates if the [Iterable] property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.containsAll(vararg values: T): Validator<E>.Property<Iterable<T>?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.toSet().all { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.containsAll(values: Iterable<T>): Validator<E>.Property<Iterable<T>?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.containsAny(vararg values: T): Validator<E>.Property<Iterable<T>?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.toSet().any { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.containsAny(values: Iterable<T>): Validator<E>.Property<Iterable<T>?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property doesn't contain the value
 *
 * @param value specifies the value that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.doesNotContain(value: T): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotContain(value)) { it == null || !it.contains(value) }

/**
 * Validates if the [Iterable] property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.doesNotContainAll(vararg values: T): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.toSet().all { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.doesNotContainAll(values: Iterable<T>): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.doesNotContainAny(vararg values: T): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.toSet().any { e -> it.contains(e) } }

/**
 * Validates if the [Iterable] property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, T> Validator<E>.Property<Iterable<T>?>.doesNotContainAny(values: Iterable<T>): Validator<E>.Property<Iterable<T>?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.contains(e) } }