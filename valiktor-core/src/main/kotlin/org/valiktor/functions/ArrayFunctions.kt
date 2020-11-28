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

import org.valiktor.DefaultConstraintViolation
import org.valiktor.Validator
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.Empty
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.Size

/**
 * Validates the array value initializing another DSL function recursively
 *
 * @param block specifies the function DSL
 * @receiver the value to be validated
 * @return the same receiver value
 */
@JvmName("validateForEachArray")
inline fun <E, T> Validator<E>.ValueValidator<Array<T>?>.validateForEach(
    block: Validator<T>.(T) -> Unit
): Validator<E>.ValueValidator<Array<T>?> {
    this.value()?.forEachIndexed { index, value ->
        this.addConstraintViolations(
            Validator(value).apply { block(value) }.constraintViolations.map {
                DefaultConstraintViolation(
                    property = "${this.name()}[$index].${it.property}",
                    value = it.value,
                    constraint = it.constraint
                )
            }
        )
    }
    return this
}

/**
 * Validates the array value initializing another DSL function recursively with index
 *
 * @param block specifies the function DSL
 * @receiver the value to be validated
 * @return the same receiver value
 */
@JvmName("validateForEachIndexedArray")
inline fun <E, T> Validator<E>.ValueValidator<Array<T>?>.validateForEachIndexed(
    block: Validator<T>.(Int, T) -> Unit
): Validator<E>.ValueValidator<Array<T>?> {
    this.value()?.forEachIndexed { index, value ->
        this.addConstraintViolations(
            Validator(value).apply { block(index, value) }.constraintViolations.map {
                DefaultConstraintViolation(
                    property = "${this.name()}[$index].${it.property}",
                    value = it.value,
                    constraint = it.constraint
                )
            }
        )
    }
    return this
}

/**
 * Validates if the array value value is equal to another value
 *
 * @param value specifies the value that should be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isEqualTo(value: Array<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(Equals(value)) { it == null || it contentDeepEquals value }

/**
 * Validates if the array value value isn't equal to another value
 *
 * @param value specifies the value that should not be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isNotEqualTo(value: Array<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotEquals(value)) { it == null || !(it contentDeepEquals value) }

/**
 * Validates if the array value value is equal to one of the values
 *
 * @param values specifies the array of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isIn(vararg values: Array<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(In(values.toSet())) { it == null || values.any { e -> it contentDeepEquals e } }

/**
 * Validates if the array value value is equal to one of the values
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isIn(values: Iterable<Array<T>>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(In(values)) { it == null || values.any { e -> it contentDeepEquals e } }

/**
 * Validates if the array value value isn't equal to any value
 *
 * @param values specifies the array of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isNotIn(vararg values: Array<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.any { e -> it contentDeepEquals e } }

/**
 * Validates if the array value value isn't equal to any value
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isNotIn(values: Iterable<Array<T>>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotIn(values)) { it == null || !values.any { e -> it contentDeepEquals e } }

/**
 * Validates if the array value is empty
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isEmpty(): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(Empty) { it == null || it.count() == 0 }

/**
 * Validates if the array value is not empty
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.isNotEmpty(): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotEmpty) { it == null || it.count() > 0 }

/**
 * Validates if the array value size is within the limits (min and max)
 *
 * @param min specifies the minimum size
 * @param max specifies the maximum size
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.hasSize(
    min: Int = Int.MIN_VALUE,
    max: Int = Int.MAX_VALUE
): Validator<E>.ValueValidator<Array<T>?> = this.validate(Size(min, max)) { it == null || it.count() in min.rangeTo(max) }

/**
 * Validates if the array value contains the value
 *
 * @param value specifies the value that should contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.contains(value: T): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(Contains(value)) { it == null || it.contains(value) }

/**
 * Validates if the array value contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.containsAll(vararg values: T): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.toSet().all { e -> it.contains(e) } }

/**
 * Validates if the array value contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.containsAll(values: Iterable<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.contains(e) } }

/**
 * Validates if the array value contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.containsAny(vararg values: T): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.toSet().any { e -> it.contains(e) } }

/**
 * Validates if the array value contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.containsAny(values: Iterable<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.contains(e) } }

/**
 * Validates if the array value doesn't contain the value
 *
 * @param value specifies the value that shouldn't contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.doesNotContain(value: T): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotContain(value)) { it == null || !it.contains(value) }

/**
 * Validates if the array value doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.doesNotContainAll(vararg values: T): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.toSet().all { e -> it.contains(e) } }

/**
 * Validates if the array value doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.doesNotContainAll(values: Iterable<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.contains(e) } }

/**
 * Validates if the array value doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.doesNotContainAny(vararg values: T): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.toSet().any { e -> it.contains(e) } }

/**
 * Validates if the array value doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<Array<T>?>.doesNotContainAny(values: Iterable<T>): Validator<E>.ValueValidator<Array<T>?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.contains(e) } }
