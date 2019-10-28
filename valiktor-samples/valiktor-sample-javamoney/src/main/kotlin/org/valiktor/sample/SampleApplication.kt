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
import org.valiktor.functions.hasCurrencyEqualTo
import org.valiktor.functions.hasCurrencyIn
import org.valiktor.functions.hasDecimalDigits
import org.valiktor.i18n.toMessage
import org.valiktor.validate
import java.util.Locale
import javax.money.Monetary
import javax.money.MonetaryAmount

val SUPPORTED_CURRENCIES = setOf(
    Monetary.getCurrency("USD"),
    Monetary.getCurrency("BRL")
)

data class Employee(
    val grossSalary: MonetaryAmount,
    val netSalary: MonetaryAmount
) {
    init {
        validate(this) { employee ->
            validate(Employee::grossSalary).hasCurrencyIn(SUPPORTED_CURRENCIES).hasDecimalDigits(max = 2)
            validate(Employee::netSalary).hasCurrencyEqualTo(employee.grossSalary.currency).hasDecimalDigits(max = 2)
        }
    }
}

fun main() {
    try {
        Employee(
            grossSalary = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(Monetary.getCurrency("EUR")).create(),
            netSalary = Monetary.getDefaultAmountFactory().setNumber(999.999).setCurrency(Monetary.getCurrency("EUR")).create()
        )
    } catch (ex: ConstraintViolationException) {
        ex.constraintViolations
            .map { it.toMessage(locale = Locale.ENGLISH) }
            .map { "${it.property}: ${it.message}" }
            .forEach(::println)
    }
}
