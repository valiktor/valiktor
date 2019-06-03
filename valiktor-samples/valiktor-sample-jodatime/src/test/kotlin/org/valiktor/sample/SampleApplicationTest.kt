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

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Between
import org.valiktor.constraints.LessOrEqual
import kotlin.test.Test
import kotlin.test.assertFailsWith

class SampleApplicationTest {

    @Test
    fun `should validate employee`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            Employee(
                dateOfBirth = LocalDate.now(),
                workStartTime = LocalTime(7, 0, 0),
                workEndTime = LocalTime(21, 0, 0)
            )
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = LocalDate.now(),
                constraint = LessOrEqual(LocalDate.now().minusYears(18))
            ),
            DefaultConstraintViolation(
                property = "workStartTime",
                value = LocalTime(7, 0, 0),
                constraint = Between(start = LocalTime(8, 0, 0), end = LocalTime(10, 0, 0))
            ),
            DefaultConstraintViolation(
                property = "workEndTime",
                value = LocalTime(21, 0, 0),
                constraint = Between(start = LocalTime(18, 0, 0), end = LocalTime(20, 0, 0))
            )
        )
    }
}