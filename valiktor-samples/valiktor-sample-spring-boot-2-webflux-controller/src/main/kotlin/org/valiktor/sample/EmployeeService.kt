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

import org.springframework.stereotype.Service
import org.valiktor.Constraint
import org.valiktor.Validator
import org.valiktor.functions.hasCurrencyEqualTo
import org.valiktor.functions.hasDecimalDigits
import org.valiktor.functions.hasDigits
import org.valiktor.functions.hasIntegerDigits
import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.functions.isEmail
import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isLessThan
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isPositive
import org.valiktor.functions.validate
import org.valiktor.functions.validateForEach
import org.valiktor.validate
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.Month
import java.util.concurrent.ConcurrentHashMap
import javax.money.Monetary

private val DOLLAR = Monetary.getCurrency("USD")
private var employees: Map<Int, Employee> = ConcurrentHashMap()

@Service
class EmployeeService {

    fun create(employee: Mono<Employee>): Mono<Employee> =
        employee
            .map {
                validate(it) {
                    validate(Employee::id).hasDigits(max = 6).isPositive().isUnique()
                    validate(Employee::name).isNotBlank().hasSize(min = 3, max = 30)
                    validate(Employee::email).isNotBlank().isEmail()
                    validate(Employee::dateOfBirth).isGreaterThan(LocalDate.of(1950, Month.JANUARY, 1))
                    validate(Employee::salary).hasIntegerDigits(min = 3, max = 5).hasDecimalDigits(max = 2).hasCurrencyEqualTo(DOLLAR)

                    validate(Employee::company).validate {
                        validate(Company::name).isNotBlank().hasSize(min = 3, max = 50)
                        validate(Company::foundedDate).isLessThan(LocalDate.now().minusYears(1))
                    }

                    validate(Employee::dependents).validateForEach {
                        validate(Dependent::name).isNotBlank().hasSize(min = 3, max = 50)
                        validate(Dependent::age).isBetween(start = 1, end = 16)
                    }
                }
            }
            .map {
                employees += Pair(it.id, it)
                it
            }
}

// custom validation constraint
object Unique : Constraint

// custom validation function
fun Validator<Employee>.Property<Int?>.isUnique(): Validator<Employee>.Property<Int?> =
    this.validate(Unique) { !employees.containsKey(it) }