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

package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Between
import org.valiktor.constraints.DecimalDigits
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.In
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.functions.DoubleFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object DoubleFunctionsFixture {

    data class Employee(val salary: Double? = null)
}

class DoubleFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 0.0, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::salary).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isEqualTo(1.0)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isEqualTo(1.0)
        }
    }

    @Test
    fun `isEqualTo with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isEqualTo(1.00)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isEqualTo(1.0)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 0.0, constraint = Equals(1.0)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotEqualTo(1.0)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isNotEqualTo(0.0)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotEqualTo(1.0)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = NotEquals(1.0)))
    }

    @Test
    fun `isNotEqualTo with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotEqualTo(1.00)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = NotEquals(1.00)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isIn(0.0, 1.0, 10.0)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isIn(0.0, 1.0, 10.0)
        }
    }

    @Test
    fun `isIn vararg with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isIn(0.0, 1.00, 10.0)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isIn(0.0, 10.0)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = In(setOf(0.0, 10.0))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isIn(listOf(0.0, 1.0, 10.0))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isIn(listOf(0.0, 1.0, 10.0))
        }
    }

    @Test
    fun `isIn iterable with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isIn(listOf(0.0, 1.00, 10.0))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isIn(listOf(0.0, 10.0))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = In(listOf(0.0, 10.0))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotIn(0.0, 1.0, 10.0)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isNotIn(0.0, 10.0)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotIn(0.0, 1.0, 10.0)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = NotIn(setOf(0.0, 1.0, 10.0))))
    }

    @Test
    fun `isNotIn vararg with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotIn(0.0, 1.00, 10.0)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = NotIn(setOf(0.0, 1.00, 10.0))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotIn(listOf(0.0, 1.0, 10.0))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isNotIn(listOf(0.0, 10.0))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotIn(listOf(0.0, 1.0, 10.0))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = NotIn(listOf(0.0, 1.0, 10.0))))
    }

    @Test
    fun `isNotIn iterable with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotIn(listOf(0.0, 1.00, 10.0))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = 1.0, constraint = NotIn(listOf(0.0, 1.00, 10.0))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with zero and 2 decimal digits should be valid`() {
        validate(Employee(salary = 0.00)) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.0,
                constraint = Equals(0.0)))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotZero()
        }
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isNotZero()
        }
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = NotEquals(0.0)))
    }

    @Test
    fun `isNotZero with zero and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.00)) {
                validate(Employee::salary).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.00,
                constraint = NotEquals(0.0)))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with one and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1.00)) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = Equals(1.0)))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotOne()
        }
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isNotOne()
        }
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.0,
                constraint = NotEquals(1.0)))
    }

    @Test
    fun `isNotOne with one and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.00)) {
                validate(Employee::salary).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.00,
                constraint = NotEquals(1.0)))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isPositive()
        }
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isPositive()
        }
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = Greater(0.0)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -98765.432)) {
                validate(Employee::salary).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -98765.432,
                constraint = Greater(0.0)))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(salary = -98765.432)) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNegativeOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.0,
                constraint = LessOrEqual(0.0)))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNegative()
        }
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = -1.0)) {
            validate(Employee::salary).isNegative()
        }
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = Less(0.0)))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.0,
                constraint = Less(0.0)))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -98765.432)) {
                validate(Employee::salary).isPositiveOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -98765.432,
                constraint = GreaterOrEqual(0.0)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isLessThan(10.0)
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = 9999.99)) {
            validate(Employee::salary).isLessThan(10000.00)
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = -0.38576)) {
            validate(Employee::salary).isLessThan(-0.3)
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 50.0)) {
                validate(Employee::salary).isLessThan(49.9)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 50.0,
                constraint = Less(49.9)))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -50.9)) {
                validate(Employee::salary).isLessThan(-51.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -50.9,
                constraint = Less(-51.0)))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isLessThan(0.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = Less(0.0)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isLessThanOrEqualTo(10.0)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = 9999.99)) {
            validate(Employee::salary).isLessThanOrEqualTo(10000.00)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = -0.38576)) {
            validate(Employee::salary).isLessThanOrEqualTo(-0.3)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isLessThanOrEqualTo(0.0)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 56789.19)) {
                validate(Employee::salary).isLessThanOrEqualTo(57.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 56789.19,
                constraint = LessOrEqual(57.0)))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -96.0)) {
                validate(Employee::salary).isLessThanOrEqualTo(-97.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -96.0,
                constraint = LessOrEqual(-97.0)))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isGreaterThan(10.0)
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = 10.1)) {
            validate(Employee::salary).isGreaterThan(10.0)
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = -88.88)) {
            validate(Employee::salary).isGreaterThan(-89.0)
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 10.0)) {
                validate(Employee::salary).isGreaterThan(11.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 10.0,
                constraint = Greater(11.0)))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -189.20)) {
                validate(Employee::salary).isGreaterThan(-180.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -189.20,
                constraint = Greater(-180.0)))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isGreaterThan(0.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = Greater(0.0)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isGreaterThanOrEqualTo(10.0)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = 10000.0)) {
            validate(Employee::salary).isGreaterThanOrEqualTo(9999.99)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = -0.3)) {
            validate(Employee::salary).isGreaterThanOrEqualTo(-0.38576)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.0)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 57.0)) {
                validate(Employee::salary).isGreaterThanOrEqualTo(56789.19)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 57.0,
                constraint = GreaterOrEqual(56789.19)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -97.0)) {
                validate(Employee::salary).isGreaterThanOrEqualTo(-96.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -97.0,
                constraint = GreaterOrEqual(-96.0)))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isBetween(start = 0.99, end = 9.99)
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = 0.0)) {
            validate(Employee::salary).isBetween(start = 0.0, end = 1.0)
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = 1.0)) {
            validate(Employee::salary).isBetween(start = 0.0, end = 1.0)
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = 0.5)) {
            validate(Employee::salary).isBetween(start = 0.0, end = 1.0)
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = -2.0)) {
            validate(Employee::salary).isBetween(start = -2.0, end = -1.0)
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = -1.0)) {
            validate(Employee::salary).isBetween(start = -2.0, end = -1.0)
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = -1.5)) {
            validate(Employee::salary).isBetween(start = -2.0, end = -1.0)
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 10.0)) {
                validate(Employee::salary).isBetween(start = 10.1, end = 11.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 10.0,
                constraint = Between(start = 10.1, end = 11.0)))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 12.0)) {
                validate(Employee::salary).isBetween(start = 10.1, end = 11.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 12.0,
                constraint = Between(start = 10.1, end = 11.0)))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -10.0)) {
                validate(Employee::salary).isBetween(start = -9.9, end = -8.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -10.0,
                constraint = Between(start = -9.9, end = -8.0)))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -12.0)) {
                validate(Employee::salary).isBetween(start = -13.0, end = -12.9)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -12.0,
                constraint = Between(start = -13.0, end = -12.9)))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotBetween(start = 0.99, end = 9.99)
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = 10.0)) {
            validate(Employee::salary).isNotBetween(start = 10.1, end = 11.0)
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = 12.0)) {
            validate(Employee::salary).isNotBetween(start = 10.1, end = 11.0)
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = -10.0)) {
            validate(Employee::salary).isNotBetween(start = -9.9, end = -8.0)
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = -12.0)) {
            validate(Employee::salary).isNotBetween(start = -13.0, end = -12.9)
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.0)) {
                validate(Employee::salary).isNotBetween(start = 0.0, end = 1.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.0,
                constraint = NotBetween(start = 0.0, end = 1.0)))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0)) {
                validate(Employee::salary).isNotBetween(start = 0.0, end = 1.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.0,
                constraint = NotBetween(start = 0.0, end = 1.0)))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -2.0)) {
                validate(Employee::salary).isNotBetween(start = -2.0, end = -1.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -2.0,
                constraint = NotBetween(start = -2.0, end = -1.0)))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -1.0)) {
                validate(Employee::salary).isNotBetween(start = -2.0, end = -1.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -1.0,
                constraint = NotBetween(start = -2.0, end = -1.0)))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.5)) {
                validate(Employee::salary).isNotBetween(start = 0.0, end = 1.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.5,
                constraint = NotBetween(start = 0.0, end = 1.0)))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -1.5)) {
                validate(Employee::salary).isNotBetween(start = -2.0, end = -1.0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -1.5,
                constraint = NotBetween(start = -2.0, end = -1.0)))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = 9999.99)) {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = 9999.99)) {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 9999.99)) {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = -999999.99)) {
            validate(Employee::salary).hasIntegerDigits(min = 6)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = -999999.99)) {
            validate(Employee::salary).hasIntegerDigits(max = 6)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = -999999.99)) {
            validate(Employee::salary).hasIntegerDigits(min = 6, max = 6)
        }
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = 9999.99)) {
            validate(Employee::salary).hasIntegerDigits()
        }
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78)) {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78,
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78)) {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78,
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78)) {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78,
                constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -748536.78)) {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -748536.78,
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -748536.78)) {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -748536.78,
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -748536.78)) {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -748536.78,
                constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasDecimalDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasDecimalDigits with valid min value should be valid`() {
        validate(Employee(salary = 99.9999)) {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = 99.9999)) {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 99.9999)) {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = -99.999999)) {
            validate(Employee::salary).hasDecimalDigits(min = 6)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = -99.999999)) {
            validate(Employee::salary).hasDecimalDigits(max = 6)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = -99.999999)) {
            validate(Employee::salary).hasDecimalDigits(min = 6, max = 6)
        }
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = 99.9999)) {
            validate(Employee::salary).hasDecimalDigits()
        }
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536)) {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536,
                constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536)) {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536,
                constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536)) {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536,
                constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -78.748536)) {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -78.748536,
                constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -78.748536)) {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -78.748536,
                constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = -78.748536)) {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = -78.748536,
                constraint = DecimalDigits(min = 7, max = 5)))
    }
}
