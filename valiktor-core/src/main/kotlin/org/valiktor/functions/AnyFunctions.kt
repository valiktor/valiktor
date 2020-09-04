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
 * Validates the property initializing another DSL function recursively
 *
 * @param block specifies the function DSL
 * @receiver the property to be validated
 * @return the same receiver validator
 */
inline fun <E, T> Validator<E>.ReceiverValidator<E, T?>.validate(block: Validator<T>.(T) -> Unit): Validator<E>.ReceiverValidator<E, T?> {
    val value = value()
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
 * Validates if the property value is null
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isNull(): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(Null) { it == null }

/**
 * Validates if the property value is not null
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isNotNull(): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(NotNull) { it != null }

/**
 * Validates if the property value is equal to another value
 *
 * @param value specifies the value that should be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isEqualTo(value: T): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(Equals(value)) { it == null || it == value }

/**
 * Validates if the property value isn't equal to another value
 *
 * @param value specifies the value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isNotEqualTo(value: T): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(NotEquals(value)) { it == null || it != value }

/**
 * Validates if the property value is equal to one of the values
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isIn(vararg values: T): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(In(values.toSet())) { it == null || values.contains(it) }

/**
 * Validates if the property value is equal to one of the values
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isIn(values: Iterable<T>): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(In(values)) { it == null || values.contains(it) }

/**
 * Validates if the property value isn't equal to any value
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isNotIn(vararg values: T): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(NotIn(values.toSet())) { it == null || !values.contains(it) }

/**
 * Validates if the property value isn't equal to any value
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isNotIn(values: Iterable<T>): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(NotIn(values)) { it == null || !values.contains(it) }

/**
 * Validates if the property is valid by passing a custom function
 *
 * @param validator specifies the validation function
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isValid(validator: (T) -> Boolean): Validator<E>.ReceiverValidator<E, T?> =
    this.validate(Valid) { it == null || validator(it) }

/**
 * Validates if the property is valid by passing a custom suspending function
 *
 * @param validator specifies the validation function
 * @receiver the property to be validated
 * @return the same receiver validator
 */
suspend fun <E, T> Validator<E>.ReceiverValidator<E, T?>.isCoValid(validator: suspend (T) -> Boolean): Validator<E>.ReceiverValidator<E, T?> =
    this.coValidate(Valid) { it == null || validator(it) }
