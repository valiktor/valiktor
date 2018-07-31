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
import org.valiktor.functions.*
import org.valiktor.validate
import java.time.LocalDate
import java.time.Month

@Service
class EmployeeService {

    fun createEmployee(employee: Employee) =
            validate(employee) {
                validate(Employee::id).isPositive()
                validate(Employee::name).isNotBlank().hasSize(min = 4, max = 50)
                validate(Employee::email).isNotBlank().isEmail()
                validate(Employee::salary).isBetween(start = 1000, end = 10000)
                validate(Employee::dateOfBirth).isBetween(start = LocalDate.of(1950, Month.JANUARY, 1), end = LocalDate.now())
            }
}