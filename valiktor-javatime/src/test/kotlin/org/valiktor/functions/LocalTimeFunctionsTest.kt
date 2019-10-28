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

package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Between
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.In
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.functions.LocalTimeFunctionsFixture.Employee
import org.valiktor.validate
import java.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object LocalTimeFunctionsFixture {

    data class Employee(val startTime: LocalTime? = null)
}

class LocalTimeFunctionsTest {

    private val dateTime = LocalTime.of(13, 25, 57)

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::startTime).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isEqualTo(dateTime)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isEqualTo(dateTime)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isEqualTo(dateTime.minusSeconds(1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = Equals(dateTime.minusSeconds(1))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isNotEqualTo(dateTime)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isNotEqualTo(dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isNotEqualTo(dateTime)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = NotEquals(dateTime)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isIn(dateTime, dateTime)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isIn(dateTime, dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isIn(dateTime.minusSeconds(1), dateTime.minusSeconds(2))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = In(setOf(dateTime.minusSeconds(1), dateTime.minusSeconds(2)))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isIn(listOf(dateTime, dateTime))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isIn(listOf(dateTime, dateTime.minusSeconds(1)))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isIn(listOf(dateTime.minusSeconds(1), dateTime.minusSeconds(2)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = In(listOf(dateTime.minusSeconds(1), dateTime.minusSeconds(2)))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isNotIn(dateTime, dateTime)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isNotIn(dateTime.minusSeconds(1), dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isNotIn(dateTime, dateTime.plusSeconds(1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = NotIn(setOf(dateTime, dateTime.plusSeconds(1)))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isNotIn(listOf(dateTime, dateTime))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isNotIn(listOf(dateTime.minusSeconds(1), dateTime.plusSeconds(1)))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isNotIn(listOf(dateTime, dateTime.plusSeconds(1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "startTime", value = dateTime, constraint = NotIn(listOf(dateTime, dateTime.plusSeconds(1)))))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isLessThan(dateTime)
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isLessThan(dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isLessThan(dateTime.minusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = Less(dateTime.minusSeconds(1))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isLessThan(dateTime)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = Less(dateTime)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isLessThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isLessThanOrEqualTo(dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isLessThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isLessThanOrEqualTo(dateTime.minusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = LessOrEqual(dateTime.minusSeconds(1))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isGreaterThan(dateTime)
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isGreaterThan(dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isGreaterThan(dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = Greater(dateTime.plusSeconds(1))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isGreaterThan(dateTime)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = Greater(dateTime)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isGreaterThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isGreaterThanOrEqualTo(dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isGreaterThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isGreaterThanOrEqualTo(dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = GreaterOrEqual(dateTime.plusSeconds(1))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isBetween(start = dateTime, end = dateTime)
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isBetween(start = dateTime, end = dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(startTime = dateTime.plusSeconds(1))) {
            validate(Employee::startTime).isBetween(start = dateTime, end = dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(startTime = dateTime.plusSeconds(1))) {
            validate(Employee::startTime).isBetween(start = dateTime, end = dateTime.plusSeconds(2))
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isBetween(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = Between(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime.plusSeconds(4))) {
                validate(Employee::startTime).isBetween(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime.plusSeconds(4),
                constraint = Between(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::startTime).isNotBetween(start = dateTime, end = dateTime)
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(startTime = dateTime)) {
            validate(Employee::startTime).isNotBetween(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(2))
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(startTime = dateTime.plusSeconds(2))) {
            validate(Employee::startTime).isNotBetween(start = dateTime, end = dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime)) {
                validate(Employee::startTime).isNotBetween(start = dateTime, end = dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime,
                constraint = NotBetween(start = dateTime, end = dateTime.plusSeconds(1))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime.plusSeconds(1))) {
                validate(Employee::startTime).isNotBetween(start = dateTime, end = dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime.plusSeconds(1),
                constraint = NotBetween(start = dateTime, end = dateTime.plusSeconds(1))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(startTime = dateTime.plusSeconds(1))) {
                validate(Employee::startTime).isNotBetween(start = dateTime, end = dateTime.plusSeconds(2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "startTime",
                value = dateTime.plusSeconds(1),
                constraint = NotBetween(start = dateTime, end = dateTime.plusSeconds(2))))
    }
}
