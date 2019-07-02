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

import org.valiktor.constraints.Between
import org.valiktor.constraints.LessOrEqual
import org.valiktor.test.shouldFailValidation
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.Test

class SampleApplicationTest {

    @Test
    fun `should validate employee`() {
        shouldFailValidation<Employee> {
            Employee(
                dateOfBirth = LocalDate.now(),
                workStartTime = LocalTime.of(7, 0, 0),
                workEndTime = LocalTime.of(21, 0, 0)
            )
        }.verify {
            expect(Employee::dateOfBirth, LocalDate.now(), LessOrEqual(LocalDate.now().minusYears(18L)))
            expect(Employee::workStartTime, LocalTime.of(7, 0, 0), Between(start = LocalTime.of(8, 0, 0), end = LocalTime.of(10, 0, 0)))
            expect(Employee::workEndTime, LocalTime.of(21, 0, 0), Between(start = LocalTime.of(18, 0, 0), end = LocalTime.of(20, 0, 0)))
        }
    }
}