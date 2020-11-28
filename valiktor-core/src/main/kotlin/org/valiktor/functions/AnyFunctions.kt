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
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.constraints.Valid

/**
 * Validates the value initializing another DSL function recursively
 *
 * @param block specifies the function DSL
 * @receiver the value to be validated
 * @return the same receiver value
 */
inline fun <E, T : Any> Validator<E>.ValueValidator<T?>.validate(block: Validator<T>.(T) -> Unit): Validator<E>.ValueValidator<T?> {
    val value = this.value()
    if (value != null) {
        this.addConstraintViolations(
            Validator(value).apply { block(value) }.constraintViolations.map {
                DefaultConstraintViolation(
                    property = "${this.name()}.${it.property}",
                    value = it.value,
                    constraint = it.constraint
                )
            }
        )
    }
    return this
}

/**
 * Validates if the value value is null
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isNull(): Validator<E>.ValueValidator<T?> =
    this.validate(Null) { it == null }

/**
 * Validates if the value value is not null
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isNotNull(): Validator<E>.ValueValidator<T?> =
    this.validate(NotNull) { it != null }

/**
 * Validates if the value value is equal to another value
 *
 * @param value specifies the value that should be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isEqualTo(value: T): Validator<E>.ValueValidator<T?> =
    this.validate(Equals(value)) { it == null || it == value }

/**
 * Validates if the value value isn't equal to another value
 *
 * @param value specifies the value that should not be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isNotEqualTo(value: T): Validator<E>.ValueValidator<T?> =
    this.validate(NotEquals(value)) { it == null || it != value }

/**
 * Validates if the value value is equal to one of the values
 *
 * @param values specifies the array of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isIn(vararg values: T): Validator<E>.ValueValidator<T?> =
    this.validate(In(values.toSet())) { it == null || values.contains(it) }

/**
 * Validates if the value value is equal to one of the values
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isIn(values: Iterable<T>): Validator<E>.ValueValidator<T?> =
    this.validate(In(values)) { it == null || values.contains(it) }

/**
 * Validates if the value value isn't equal to any value
 *
 * @param values specifies the array of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isNotIn(vararg values: T): Validator<E>.ValueValidator<T?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.contains(it) }

/**
 * Validates if the value value isn't equal to any value
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isNotIn(values: Iterable<T>): Validator<E>.ValueValidator<T?> =
    this.validate(NotIn(values)) { it == null || !values.contains(it) }

/**
 * Validates if the value is valid by passing a custom function
 *
 * @param validator specifies the validation function
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E, T> Validator<E>.ValueValidator<T?>.isValid(validator: (T) -> Boolean): Validator<E>.ValueValidator<T?> =
    this.validate(Valid) { it == null || validator(it) }

/**
 * Validates if the value is valid by passing a custom suspending function
 *
 * @param validator specifies the validation function
 * @receiver the value to be validated
 * @return the same receiver value
 */
suspend fun <E, T> Validator<E>.ValueValidator<T?>.isCoValid(validator: suspend (T) -> Boolean): Validator<E>.ValueValidator<T?> =
    this.coValidate(Valid) { it == null || validator(it) }
