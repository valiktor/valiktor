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
import org.valiktor.constraints.Blank
import org.valiktor.constraints.Digit
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.Letter
import org.valiktor.constraints.LetterOrDigit
import org.valiktor.constraints.LowerCase
import org.valiktor.constraints.NotBlank
import org.valiktor.constraints.NotDigit
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotLetter
import org.valiktor.constraints.NotLetterOrDigit
import org.valiktor.constraints.UpperCase

/**
 * Validates if the [Char] property is a whitespace
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isWhitespace(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(Blank) { it == null || it.isWhitespace() }

/**
 * Validates if the [Char] property is not a whitespace
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotWhitespace(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotBlank) { it == null || !it.isWhitespace() }

/**
 * Validates if the [Char] property is a letter
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isLetter(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(Letter) { it == null || it.isLetter() }

/**
 * Validates if the [Char] property is not a letter
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotLetter(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotLetter) { it == null || !it.isLetter() }

/**
 * Validates if the [Char] property is a digit
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isDigit(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(Digit) { it == null || it.isDigit() }

/**
 * Validates if the [Char] property is not a digit
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotDigit(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotDigit) { it == null || !it.isDigit() }

/**
 * Validates if the [Char] property is a letter or a digit
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isLetterOrDigit(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(LetterOrDigit) { it == null || it.isLetterOrDigit() }

/**
 * Validates if the [Char] property is not a letter or a digit
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotLetterOrDigit(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotLetterOrDigit) { it == null || !it.isLetterOrDigit() }

/**
 * Validates if the [Char] property is uppercase
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isUpperCase(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(UpperCase) { it == null || it.isUpperCase() }

/**
 * Validates if the [Char] property is lowercase
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isLowerCase(): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(LowerCase) { it == null || it.isLowerCase() }

/**
 * Validates if the [Char] property is equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isEqualToIgnoringCase(value: Char): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(Equals(value)) { it == null || it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the [Char] property isn't equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotEqualToIgnoringCase(value: Char): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotEquals(value)) { it == null || !it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the [Char] property is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isInIgnoringCase(vararg values: Char): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(In(values.toSet())) { it == null || values.toSet().any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] property is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isInIgnoringCase(values: Iterable<Char>): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(In(values)) { it == null || values.any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] property isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotInIgnoringCase(vararg values: Char): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotIn(values.toSet())) { it == null || values.toSet().none { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] property isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Char?>.isNotInIgnoringCase(values: Iterable<Char>): Validator<E>.ReceiverValidator<E, Char?> =
    this.validate(NotIn(values)) { it == null || values.none { e -> it.equals(other = e, ignoreCase = true) } }
