/*
 * Copyright 2018 https://www.valiktor.org
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
import java.time.LocalDate
import java.time.Month
import javax.money.Monetary

private val DOLLAR = Monetary.getCurrency("USD")

@Service
class EmployeeService {

    fun createEmployee(employee: Employee) =
        validate(employee) {
            validate(Employee::id).hasDigits(max = 6).isPositive()
            validate(Employee::firstName).isNotBlank().hasSize(min = 3, max = 30)
            validate(Employee::lastName).isNotBlank().hasSize(min = 3, max = 30)
            validate(Employee::email).isNotBlank().isEmail()
            validate(Employee::dateOfBirth).isBetween(start = LocalDate.of(1950, Month.JANUARY, 1), end = LocalDate.now().minusYears(18))
            validate(Employee::salary).hasIntegerDigits(min = 3, max = 5).hasDecimalDigits(min = 2, max = 2).hasCurrencyEqualTo(DOLLAR)
            validate(Employee::startTime).isLessThan(employee.endTime)
            validate(Employee::endTime).isGreaterThan(employee.startTime)
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