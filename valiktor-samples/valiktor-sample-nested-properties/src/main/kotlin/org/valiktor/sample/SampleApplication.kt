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

import org.valiktor.ConstraintViolationException
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.validate
import org.valiktor.i18n.toMessage
import org.valiktor.validate
import java.util.Locale

data class City(val name: String)

data class Address(val city: City)

data class Company(val address: Address)

data class Employee(val company: Company) {
    init {
        validate(this) {
            validate(Employee::company).validate {
                validate(Company::address).validate {
                    validate(Address::city).validate {
                        validate(City::name).isNotBlank()
                    }
                }
            }
        }
    }
}

fun main() {
    try {
        Employee(
            company = Company(
                address = Address(
                    city = City(
                        name = "   "
                    )
                )
            )
        )
    } catch (ex: ConstraintViolationException) {
        ex.constraintViolations
            .map { it.toMessage(locale = Locale.ENGLISH) }
            .map { "${it.property}: ${it.message}" }
            .forEach(::println)
    }
}
