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

package org.valiktor.sample

import org.valiktor.ConstraintViolationException
import org.valiktor.functions.hasDecimalDigits
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isPositive
import org.valiktor.i18n.toMessage
import org.valiktor.validate
import java.util.Locale

data class Employee(
    val id: Int,
    val name: String,
    val email: String,
    val salary: Double
) {
    init {
        validate(this) {
            validate(Employee::id).isPositive()
            validate(Employee::name).hasSize(min = 3, max = 30)
            validate(Employee::email).isNotBlank().isEmail()
            validate(Employee::salary).hasDecimalDigits(max = 2)
        }
    }
}

fun main() {
    try {
        Employee(
            id = -1,
            name = "aa",
            email = "aaa",
            salary = 9999.999
        )
    } catch (ex: ConstraintViolationException) {
        ex.constraintViolations
            .map { it.toMessage(locale = Locale.ENGLISH) }
            .map { "${it.property}: ${it.message}" }
            .forEach(::println)
    }
}