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
 * Validates if the [Char] value is a whitespace
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isWhitespace(): Validator<E>.ValueValidator<Char?> =
    this.validate(Blank) { it == null || it.isWhitespace() }

/**
 * Validates if the [Char] value is not a whitespace
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotWhitespace(): Validator<E>.ValueValidator<Char?> =
    this.validate(NotBlank) { it == null || !it.isWhitespace() }

/**
 * Validates if the [Char] value is a letter
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isLetter(): Validator<E>.ValueValidator<Char?> =
    this.validate(Letter) { it == null || it.isLetter() }

/**
 * Validates if the [Char] value is not a letter
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotLetter(): Validator<E>.ValueValidator<Char?> =
    this.validate(NotLetter) { it == null || !it.isLetter() }

/**
 * Validates if the [Char] value is a digit
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isDigit(): Validator<E>.ValueValidator<Char?> =
    this.validate(Digit) { it == null || it.isDigit() }

/**
 * Validates if the [Char] value is not a digit
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotDigit(): Validator<E>.ValueValidator<Char?> =
    this.validate(NotDigit) { it == null || !it.isDigit() }

/**
 * Validates if the [Char] value is a letter or a digit
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isLetterOrDigit(): Validator<E>.ValueValidator<Char?> =
    this.validate(LetterOrDigit) { it == null || it.isLetterOrDigit() }

/**
 * Validates if the [Char] value is not a letter or a digit
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotLetterOrDigit(): Validator<E>.ValueValidator<Char?> =
    this.validate(NotLetterOrDigit) { it == null || !it.isLetterOrDigit() }

/**
 * Validates if the [Char] value is uppercase
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isUpperCase(): Validator<E>.ValueValidator<Char?> =
    this.validate(UpperCase) { it == null || it.isUpperCase() }

/**
 * Validates if the [Char] value is lowercase
 *
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isLowerCase(): Validator<E>.ValueValidator<Char?> =
    this.validate(LowerCase) { it == null || it.isLowerCase() }

/**
 * Validates if the [Char] value is equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isEqualToIgnoringCase(value: Char): Validator<E>.ValueValidator<Char?> =
    this.validate(Equals(value)) { it == null || it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the [Char] value isn't equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should not be equal
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotEqualToIgnoringCase(value: Char): Validator<E>.ValueValidator<Char?> =
    this.validate(NotEquals(value)) { it == null || !it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the [Char] value is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isInIgnoringCase(vararg values: Char): Validator<E>.ValueValidator<Char?> =
    this.validate(In(values.toSet())) { it == null || values.toSet().any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] value is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isInIgnoringCase(values: Iterable<Char>): Validator<E>.ValueValidator<Char?> =
    this.validate(In(values)) { it == null || values.any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] value isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotInIgnoringCase(vararg values: Char): Validator<E>.ValueValidator<Char?> =
    this.validate(NotIn(values.toSet())) { it == null || values.toSet().none { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [Char] value isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the value to be validated
 * @return the same receiver value
 */
fun <E> Validator<E>.ValueValidator<Char?>.isNotInIgnoringCase(values: Iterable<Char>): Validator<E>.ValueValidator<Char?> =
    this.validate(NotIn(values)) { it == null || values.none { e -> it.equals(other = e, ignoreCase = true) } }
