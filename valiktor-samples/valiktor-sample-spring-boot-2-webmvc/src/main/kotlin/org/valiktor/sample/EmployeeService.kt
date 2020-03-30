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

import org.springframework.stereotype.Service
import org.valiktor.Constraint
import org.valiktor.Validator
import org.valiktor.functions.hasCurrencyEqualTo
import org.valiktor.functions.hasDecimalDigits
import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.functions.isEmail
import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isLessThan
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.validate
import org.valiktor.functions.validateForEach
import org.valiktor.validate
import java.time.LocalDate
import java.time.Month
import javax.money.Monetary

@Service
class EmployeeService {

    fun create(employee: Employee) {
        validate(employee) {
            validate(Employee::documentNumber).isDocumentNumber()
            validate(Employee::name).hasSize(min = 3, max = 100)
            validate(Employee::email).isNotBlank().isEmail()
            validate(Employee::dateOfBirth).isGreaterThan(LocalDate.of(1950, Month.JANUARY, 1))
            validate(Employee::salary).hasDecimalDigits(max = 2).hasCurrencyEqualTo(Monetary.getCurrency("USD"))

            validate(Employee::company).validate {
                validate(Company::name).hasSize(min = 3, max = 50)
                validate(Company::foundationDate).isLessThan(LocalDate.now().minusYears(1))
            }

            validate(Employee::dependents).validateForEach {
                validate(Dependent::name).hasSize(min = 3, max = 50)
                validate(Dependent::age).isBetween(start = 1, end = 18)
            }
        }
    }
}

// custom validation constraint
object Document : Constraint

// custom validation function
fun Validator<Employee>.Property<String?>.isDocumentNumber() =
    this.validate(Document) { it == null || it.matches(Regex("^\\d{3}.\\d{3}.\\d{3}-\\d{2}\$")) }
