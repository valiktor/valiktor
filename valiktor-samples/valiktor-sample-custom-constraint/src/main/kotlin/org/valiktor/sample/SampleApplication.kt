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

import org.valiktor.Constraint
import org.valiktor.ConstraintViolationException
import org.valiktor.Validator
import org.valiktor.i18n.toMessage
import org.valiktor.validate
import java.util.Locale

object Document : Constraint

fun Validator<Employee>.Property<String?>.isDocumentNumber() = this.validate(Document) {
    it == null || it.matches(Regex("^\\d{3}.\\d{3}.\\d{3}-\\d{2}\$"))
}

data class Employee(val documentNumber: String) {
    init {
        validate(this) {
            validate(Employee::documentNumber).isDocumentNumber()
        }
    }
}

fun main() {
    try {
        Employee(documentNumber = "0000")
    } catch (ex: ConstraintViolationException) {
        ex.constraintViolations
            .map { it.toMessage(baseName = "messages", locale = Locale.ENGLISH) }
            .map { "${it.property}: ${it.message}" }
            .forEach(::println)
    }
}