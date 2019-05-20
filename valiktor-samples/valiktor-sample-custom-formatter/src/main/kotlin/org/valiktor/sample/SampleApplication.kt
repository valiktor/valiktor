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
import org.valiktor.functions.isGreaterThan
import org.valiktor.i18n.Formatter
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.MessageBundle
import org.valiktor.i18n.toMessage
import org.valiktor.validate
import java.util.Locale

data class Document(val number: Int) : Comparable<Document> {
    override fun compareTo(other: Document) = number.compareTo(other.number)
}

data class Employee(val document: Document) {
    init {
        validate(this) {
            validate(Employee::document).isGreaterThan(Document(0))
        }
    }
}

object DocumentFormatter : Formatter<Document> {
    override fun format(value: Document, messageBundle: MessageBundle) = value.number.toString()
}

fun main() {
    Formatters[Document::class] = DocumentFormatter

    try {
        Employee(document = Document(0))
    } catch (ex: ConstraintViolationException) {
        ex.constraintViolations
            .map { it.toMessage(locale = Locale.ENGLISH) }
            .map { "${it.property}: ${it.message}" }
            .forEach(::println)
    }
}