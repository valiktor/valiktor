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

/**
 * Validates if the [Char] property is a whitespace
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isWhitespace(): Validator<E>.Property<Char?> =
        this.validate(Blank) { it == null || it.isWhitespace() }

/**
 * Validates if the [Char] property is not a whitespace
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotWhitespace(): Validator<E>.Property<Char?> =
        this.validate(NotBlank) { it == null || !it.isWhitespace() }

/**
 * Validates if the [Char] property is a letter
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isLetter(): Validator<E>.Property<Char?> =
        this.validate(Letter) { it == null || it.isLetter() }

/**
 * Validates if the [Char] property is not a letter
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotLetter(): Validator<E>.Property<Char?> =
        this.validate(NotLetter) { it == null || !it.isLetter() }

/**
 * Validates if the [Char] property is a digit
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isDigit(): Validator<E>.Property<Char?> =
        this.validate(Digit) { it == null || it.isDigit() }

/**
 * Validates if the [Char] property is not a digit
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotDigit(): Validator<E>.Property<Char?> =
        this.validate(NotDigit) { it == null || !it.isDigit() }

/**
 * Validates if the [Char] property is a letter or a digit
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isLetterOrDigit(): Validator<E>.Property<Char?> =
        this.validate(LetterOrDigit) { it == null || it.isLetterOrDigit() }

/**
 * Validates if the [Char] property is not a letter or a digit
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotLetterOrDigit(): Validator<E>.Property<Char?> =
        this.validate(NotLetterOrDigit) { it == null || !it.isLetterOrDigit() }

/**
 * Validates if the [Char] property is uppercase
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isUpperCase(): Validator<E>.Property<Char?> =
        this.validate(UpperCase) { it == null || it.isUpperCase() }

/**
 * Validates if the [Char] property is lowercase
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isLowerCase(): Validator<E>.Property<Char?> =
        this.validate(LowerCase) { it == null || it.isLowerCase() }

/**
 * Validates if the [Char] property is equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isEqualToIgnoringCase(value: Char): Validator<E>.Property<Char?> =
        this.validate(Equals(value)) { it == null || it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the [Char] property isn't equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotEqualToIgnoringCase(value: Char): Validator<E>.Property<Char?> =
        this.validate(NotEquals(value)) { it == null || !it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the [Char] property is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isInIgnoringCase(vararg values: Char): Validator<E>.Property<Char?> =
        this.validate(In(values.toSet())) { it == null || values.toSet().any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] property is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isInIgnoringCase(values: Iterable<Char>): Validator<E>.Property<Char?> =
        this.validate(In(values)) { it == null || values.any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] property isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotInIgnoringCase(vararg values: Char): Validator<E>.Property<Char?> =
        this.validate(NotIn(values.toSet())) { it == null || values.toSet().none { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] property isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<Char?>.isNotInIgnoringCase(values: Iterable<Char>): Validator<E>.Property<Char?> =
        this.validate(NotIn(values)) { it == null || values.none { e -> it.equals(other = e, ignoreCase = true) } }