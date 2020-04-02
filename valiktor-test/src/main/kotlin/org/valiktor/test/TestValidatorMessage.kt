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

package org.valiktor.test

import org.valiktor.Constraint
import org.valiktor.ConstraintViolation
import java.lang.System.lineSeparator

fun message(expectedConstraintViolations: Set<ConstraintViolation>, constraintViolations: Set<ConstraintViolation>) =
    "Expected:${lineSeparator()}${lineSeparator()}${expectedConstraintViolations.toTestString()}${lineSeparator()}${lineSeparator()}" +
        "but was:${lineSeparator()}${lineSeparator()}${constraintViolations.toTestString()}${lineSeparator()}"

private fun Set<ConstraintViolation>.toTestString() =
    this.joinToString(separator = lineSeparator()) { constraintViolation ->
        "${constraintViolation.toTestString(this) { it.property }} | " +
            "${constraintViolation.toTestString(this) { it.value?.toString() ?: "" }} | " +
            constraintViolation.constraint.toTestString()
    }

private fun ConstraintViolation.toTestString(constraintViolations: Set<ConstraintViolation>, f: (ConstraintViolation) -> String) =
    f(this) + (0 until (constraintViolations.maxBy { f(it).length }?.let(f) ?: "").length.minus(f(this).length))
        .joinToString(separator = "") { " " }

private fun Constraint.toTestString() = this.name + if (this.messageParams.isEmpty()) "" else
    "(${this.messageParams.toList().joinToString { "${it.first} = ${it.second}" }})"
