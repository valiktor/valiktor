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
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.ContainsRegex
import org.valiktor.constraints.Email
import org.valiktor.constraints.Empty
import org.valiktor.constraints.EndsWith
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.Matches
import org.valiktor.constraints.NotBlank
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotContainRegex
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotEndWith
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotMatch
import org.valiktor.constraints.NotStartWith
import org.valiktor.constraints.Size
import org.valiktor.constraints.StartsWith
import org.valiktor.constraints.Website

/**
 * Validates if the [String] property is empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isEmpty(): Validator<E>.Property<String?> =
    this.validate(Empty) { it == null || it.isEmpty() }

/**
 * Validates if the [String] property is not empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isNotEmpty(): Validator<E>.Property<String?> =
    this.validate(NotEmpty) { it == null || it.isNotEmpty() }

/**
 * Validates if the [String] property is blank
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isBlank(): Validator<E>.Property<String?> =
    this.validate(Blank) { it == null || it.isBlank() }

/**
 * Validates if the [String] property is not blank
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isNotBlank(): Validator<E>.Property<String?> =
    this.validate(NotBlank) { it == null || it.isNotBlank() }

/**
 * Validates if the property value is equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isEqualToIgnoringCase(value: String): Validator<E>.Property<String?> =
    this.validate(Equals(value)) { it == null || it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the property value isn't equal to another value ignoring case sensitive
 *
 * @param value specifies the value that should not be equal
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isNotEqualToIgnoringCase(value: String): Validator<E>.Property<String?> =
    this.validate(NotEquals(value)) { it == null || !it.equals(other = value, ignoreCase = true) }

/**
 * Validates if the property value is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isInIgnoringCase(vararg values: String): Validator<E>.Property<String?> =
    this.validate(In(values.toSet())) { it == null || values.toSet().any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the property value is equal to one of the values ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isInIgnoringCase(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(In(values)) { it == null || values.any { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the property value isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isNotInIgnoringCase(vararg values: String): Validator<E>.Property<String?> =
    this.validate(NotIn(values.toSet())) { it == null || values.toSet().none { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the property value isn't equal to any value ignoring case sensitive
 *
 * @param values specifies the iterable of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isNotInIgnoringCase(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(NotIn(values)) { it == null || values.none { e -> it.equals(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property length is within the limits (min and max)
 *
 * @param min specifies the minimum size
 * @param max specifies the maximum size
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.hasSize(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Validator<E>.Property<String?> =
    this.validate(Size(min, max)) { it == null || it.length in min.rangeTo(max) }

/**
 * Validates if the [String] property contains the value
 *
 * @param value specifies the value that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.contains(value: String): Validator<E>.Property<String?> =
    this.validate(Contains(value)) { it == null || it.contains(value) }

/**
 * Validates if the [String] property contains the value ignoring case sensitive
 *
 * @param value specifies the value that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsIgnoringCase(value: String): Validator<E>.Property<String?> =
    this.validate(Contains(value)) { it == null || it.contains(other = value, ignoreCase = true) }

/**
 * Validates if the [String] property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAll(vararg values: String): Validator<E>.Property<String?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.toSet().all { e -> it.contains(e) } }

/**
 * Validates if the [String] property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAll(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.contains(e) } }

/**
 * Validates if the [String] property contains all values ignoring case sensitive
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAllIgnoringCase(vararg values: String): Validator<E>.Property<String?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.toSet().all { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property contains all values ignoring case sensitive
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAllIgnoringCase(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAny(vararg values: String): Validator<E>.Property<String?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.toSet().any { e -> it.contains(e) } }

/**
 * Validates if the [String] property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAny(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.contains(e) } }

/**
 * Validates if the [String] property contains any value ignoring case sensitive
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAnyIgnoringCase(vararg values: String): Validator<E>.Property<String?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.toSet().any { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property contains any value ignoring case sensitive
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.containsAnyIgnoringCase(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property doesn't contain the value
 *
 * @param value specifies the value that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContain(value: String): Validator<E>.Property<String?> =
    this.validate(NotContain(value)) { it == null || !it.contains(value) }

/**
 * Validates if the [String] property doesn't contain the value ignoring case sensitive
 *
 * @param value specifies the value that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainIgnoringCase(value: String): Validator<E>.Property<String?> =
    this.validate(NotContain(value)) { it == null || !it.contains(other = value, ignoreCase = true) }

/**
 * Validates if the [String] property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAll(vararg values: String): Validator<E>.Property<String?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.toSet().all { e -> it.contains(e) } }

/**
 * Validates if the [String] property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAll(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.contains(e) } }

/**
 * Validates if the [String] property doesn't contain all values ignoring case sensitive
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAllIgnoringCase(vararg values: String): Validator<E>.Property<String?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.toSet().all { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property doesn't contain all values ignoring case sensitive
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAllIgnoringCase(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAny(vararg values: String): Validator<E>.Property<String?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.toSet().any { e -> it.contains(e) } }

/**
 * Validates if the [String] property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAny(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.contains(e) } }

/**
 * Validates if the [String] property doesn't contain any value ignoring case sensitive
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAnyIgnoringCase(vararg values: String): Validator<E>.Property<String?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.toSet().any { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property doesn't contain any value ignoring case sensitive
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContainAnyIgnoringCase(values: Iterable<String>): Validator<E>.Property<String?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.contains(other = e, ignoreCase = true) } }

/**
 * Validates if the [String] property matches the value
 *
 * @param regex specifies the pattern value that should match
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.matches(regex: Regex): Validator<E>.Property<String?> =
    this.validate(Matches(regex)) { it == null || it.matches(regex) }

/**
 * Validates if the [String] property doesn't match the value
 *
 * @param regex specifies the pattern value that shouldn't match
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotMatch(regex: Regex): Validator<E>.Property<String?> =
    this.validate(NotMatch(regex)) { it == null || !it.matches(regex) }

/**
 * Validates if the [String] property contains a pattern
 *
 * @param regex specifies the pattern value that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.contains(regex: Regex): Validator<E>.Property<String?> =
    this.validate(ContainsRegex(regex)) { it == null || it.contains(regex) }

/**
 * Validates if the [String] property doesn't contain the pattern
 *
 * @param regex specifies the pattern value that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotContain(regex: Regex): Validator<E>.Property<String?> =
    this.validate(NotContainRegex(regex)) { it == null || !it.contains(regex) }

/**
 * Validates if the [String] property value starts with another value
 *
 * @param prefix specifies the value that should start
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.startsWith(prefix: String): Validator<E>.Property<String?> =
    this.validate(StartsWith(prefix)) { it == null || it.startsWith(prefix) }

/**
 * Validates if the [String] property value starts with another value ignoring case sensitive
 *
 * @param prefix specifies the value that should start
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.startsWithIgnoringCase(prefix: String): Validator<E>.Property<String?> =
    this.validate(StartsWith(prefix)) { it == null || it.startsWith(prefix = prefix, ignoreCase = true) }

/**
 * Validates if the [String] property value doesn't start with another value
 *
 * @param prefix specifies the value that shouldn't start
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotStartWith(prefix: String): Validator<E>.Property<String?> =
    this.validate(NotStartWith(prefix)) { it == null || !it.startsWith(prefix) }

/**
 * Validates if the [String] property value doesn't start with another value ignoring case sensitive
 *
 * @param prefix specifies the value that shouldn't start
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotStartWithIgnoringCase(prefix: String): Validator<E>.Property<String?> =
    this.validate(NotStartWith(prefix)) { it == null || !it.startsWith(prefix = prefix, ignoreCase = true) }

/**
 * Validates if the [String] property value ends with another value
 *
 * @param suffix specifies the value that should end
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.endsWith(suffix: String): Validator<E>.Property<String?> =
    this.validate(EndsWith(suffix)) { it == null || it.endsWith(suffix) }

/**
 * Validates if the [String] property value ends with another value ignoring case sensitive
 *
 * @param suffix specifies the value that should end
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.endsWithIgnoringCase(suffix: String): Validator<E>.Property<String?> =
    this.validate(EndsWith(suffix)) { it == null || it.endsWith(suffix = suffix, ignoreCase = true) }

/**
 * Validates if the [String] property value doesn't end with another value
 *
 * @param suffix specifies the value that shouldn't end
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotEndWith(suffix: String): Validator<E>.Property<String?> =
    this.validate(NotEndWith(suffix)) { it == null || !it.endsWith(suffix) }

/**
 * Validates if the [String] property value doesn't end with another value ignoring case sensitive
 *
 * @param suffix specifies the value that shouldn't end
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.doesNotEndWithIgnoringCase(suffix: String): Validator<E>.Property<String?> =
    this.validate(NotEndWith(suffix)) { it == null || !it.endsWith(suffix = suffix, ignoreCase = true) }

/**
 * Validates if the [String] property value is a valid email
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isEmail(): Validator<E>.Property<String?> =
    this.validate(Email) {
        it == null || it.matches(
            // see https://emailregex.com/
            Regex("""(?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""")
        )
    }

/**
 * Validates if the [String] property value is a valid website
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<String?>.isWebsite(): Validator<E>.Property<String?> =
    this.validate(Website) {
        it == null || it.matches(
            Regex("^(https?:\\/\\/)?([a-zA-Z0-9]+(-?[a-zA-Z0-9])*\\.)+[\\w]{2,}(\\/\\S*)?\$")
        )
    }
