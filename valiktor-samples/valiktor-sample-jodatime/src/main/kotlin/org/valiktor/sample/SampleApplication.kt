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

package org.valiktor.sample

import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.isBetween
import org.valiktor.functions.isLessThanOrEqualTo
import org.valiktor.i18n.toMessage
import org.valiktor.validate
import java.util.Locale

data class Employee(
    val dateOfBirth: LocalDate,
    val workStartTime: LocalTime,
    val workEndTime: LocalTime
) {
    init {
        validate(this) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(LocalDate.now().minusYears(18))
            validate(Employee::workStartTime).isBetween(start = LocalTime(8, 0, 0), end = LocalTime(10, 0, 0))
            validate(Employee::workEndTime).isBetween(start = LocalTime(18, 0, 0), end = LocalTime(20, 0, 0))
        }
    }
}

fun main() {
    try {
        Employee(
            dateOfBirth = LocalDate.now(),
            workStartTime = LocalTime(7, 0, 0),
            workEndTime = LocalTime(21, 0, 0)
        )
    } catch (ex: ConstraintViolationException) {
        ex.constraintViolations
            .map { it.toMessage(locale = Locale.ENGLISH) }
            .map { "${it.property}: ${it.message}" }
            .forEach(::println)
    }
}
