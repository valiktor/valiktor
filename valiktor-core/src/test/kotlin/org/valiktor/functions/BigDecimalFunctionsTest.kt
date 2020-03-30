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
import org.valiktor.functions.BigDecimalFunctionsFixture.Employee
import org.valiktor.validate
import java.math.BigDecimal
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object BigDecimalFunctionsFixture {

    data class Employee(val salary: BigDecimal? = null)
}

class BigDecimalFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ZERO, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(salary = ZERO)) {
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
            validate(Employee::salary).isEqualTo(ONE)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isEqualTo(ONE)
        }
    }

    @Test
    fun `isEqualTo with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isEqualTo(1.00.toBigDecimal())
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isEqualTo(ONE)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ZERO, constraint = Equals(ONE)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotEqualTo(ONE)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isNotEqualTo(ZERO)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotEqualTo(ONE)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = NotEquals(ONE)))
    }

    @Test
    fun `isNotEqualTo with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotEqualTo(1.00.toBigDecimal())
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = NotEquals(1.00.toBigDecimal())))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isIn(ZERO, ONE, TEN)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isIn(ZERO, ONE, TEN)
        }
    }

    @Test
    fun `isIn vararg with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isIn(ZERO, 1.00.toBigDecimal(), TEN)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isIn(ZERO, TEN)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = In(setOf(ZERO, TEN))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isIn(listOf(ZERO, ONE, TEN))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isIn(listOf(ZERO, ONE, TEN))
        }
    }

    @Test
    fun `isIn iterable with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isIn(listOf(ZERO, 1.00.toBigDecimal(), TEN))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isIn(listOf(ZERO, TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = In(listOf(ZERO, TEN))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotIn(ZERO, ONE, TEN)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isNotIn(ZERO, TEN)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotIn(ZERO, ONE, TEN)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = NotIn(setOf(ZERO, ONE, TEN))))
    }

    @Test
    fun `isNotIn vararg with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotIn(ZERO, 1.00.toBigDecimal(), TEN)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = NotIn(setOf(ZERO, 1.00.toBigDecimal(), TEN))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotIn(listOf(ZERO, ONE, TEN))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isNotIn(listOf(ZERO, TEN))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotIn(listOf(ZERO, ONE, TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = NotIn(listOf(ZERO, ONE, TEN))))
    }

    @Test
    fun `isNotIn iterable with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotIn(listOf(ZERO, 1.00.toBigDecimal(), TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "salary", value = ONE, constraint = NotIn(listOf(ZERO, 1.00.toBigDecimal(), TEN))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with zero and 2 decimal digits should be valid`() {
        validate(Employee(salary = 0.00.toBigDecimal())) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ONE,
                constraint = Equals(ZERO)))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotZero()
        }
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isNotZero()
        }
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = NotEquals(ZERO)))
    }

    @Test
    fun `isNotZero with zero and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.00.toBigDecimal())) {
                validate(Employee::salary).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.00.toBigDecimal(),
                constraint = NotEquals(ZERO)))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with one and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1.00.toBigDecimal())) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = Equals(ONE)))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotOne()
        }
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isNotOne()
        }
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ONE,
                constraint = NotEquals(ONE)))
    }

    @Test
    fun `isNotOne with one and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.00.toBigDecimal())) {
                validate(Employee::salary).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.00.toBigDecimal(),
                constraint = NotEquals(ONE)))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isPositive()
        }
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isPositive()
        }
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = Greater(ZERO)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 98765.432.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 98765.432.unaryMinus().toBigDecimal(),
                constraint = Greater(ZERO)))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(salary = 98765.432.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNegativeOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ONE,
                constraint = LessOrEqual(ZERO)))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNegative()
        }
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = 1.0.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isNegative()
        }
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = Less(ZERO)))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ONE,
                constraint = Less(ZERO)))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 98765.432.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isPositiveOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 98765.432.unaryMinus().toBigDecimal(),
                constraint = GreaterOrEqual(ZERO)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isLessThan(10.0.toBigDecimal())
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal())) {
            validate(Employee::salary).isLessThan(10000.00.toBigDecimal())
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = 0.38576.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isLessThan(0.3.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 50.0.toBigDecimal())) {
                validate(Employee::salary).isLessThan(49.9.toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 50.0.toBigDecimal(),
                constraint = Less(49.9.toBigDecimal())))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 50.9.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isLessThan(51.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 50.9.unaryMinus().toBigDecimal(),
                constraint = Less(51.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isLessThan(ZERO)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = Less(ZERO)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isLessThanOrEqualTo(10.0.toBigDecimal())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal())) {
            validate(Employee::salary).isLessThanOrEqualTo(10000.00.toBigDecimal())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = 0.38576.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isLessThanOrEqualTo(0.3.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isLessThanOrEqualTo(ZERO)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 56789.19.toBigDecimal())) {
                validate(Employee::salary).isLessThanOrEqualTo(57.0.toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 56789.19.toBigDecimal(),
                constraint = LessOrEqual(57.0.toBigDecimal())))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 96.0.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isLessThanOrEqualTo(97.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 96.0.unaryMinus().toBigDecimal(),
                constraint = LessOrEqual(97.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isGreaterThan(10.0.toBigDecimal())
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = 10.1.toBigDecimal())) {
            validate(Employee::salary).isGreaterThan(10.0.toBigDecimal())
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = 88.88.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isGreaterThan(89.0.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 10.0.toBigDecimal())) {
                validate(Employee::salary).isGreaterThan(11.0.toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 10.0.toBigDecimal(),
                constraint = Greater(11.0.toBigDecimal())))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 189.20.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isGreaterThan(180.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 189.20.unaryMinus().toBigDecimal(),
                constraint = Greater(180.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isGreaterThan(ZERO)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = Greater(ZERO)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isGreaterThanOrEqualTo(10.0.toBigDecimal())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = 10000.0.toBigDecimal())) {
            validate(Employee::salary).isGreaterThanOrEqualTo(9999.99.toBigDecimal())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = 0.3.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.38576.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isGreaterThanOrEqualTo(ZERO)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 57.0.toBigDecimal())) {
                validate(Employee::salary).isGreaterThanOrEqualTo(56789.19.toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 57.0.toBigDecimal(),
                constraint = GreaterOrEqual(56789.19.toBigDecimal())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 97.0.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isGreaterThanOrEqualTo(96.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 97.0.unaryMinus().toBigDecimal(),
                constraint = GreaterOrEqual(96.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isBetween(start = 0.99.toBigDecimal(), end = 9.99.toBigDecimal())
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = ZERO)) {
            validate(Employee::salary).isBetween(start = ZERO, end = ONE)
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = ONE)) {
            validate(Employee::salary).isBetween(start = ZERO, end = ONE)
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = 0.5.toBigDecimal())) {
            validate(Employee::salary).isBetween(start = ZERO, end = ONE)
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = 2.0.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = 1.0.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = 1.5.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 10.0.toBigDecimal())) {
                validate(Employee::salary).isBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 10.0.toBigDecimal(),
                constraint = Between(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 12.0.toBigDecimal())) {
                validate(Employee::salary).isBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 12.0.toBigDecimal(),
                constraint = Between(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 10.0.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isBetween(start = 9.9.unaryMinus().toBigDecimal(), end = 8.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 10.0.unaryMinus().toBigDecimal(),
                constraint = Between(start = 9.9.unaryMinus().toBigDecimal(), end = 8.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 12.0.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isBetween(start = 13.0.unaryMinus().toBigDecimal(), end = 12.9.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 12.0.unaryMinus().toBigDecimal(),
                constraint = Between(start = 13.0.unaryMinus().toBigDecimal(), end = 12.9.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotBetween(start = 0.99.toBigDecimal(), end = 9.99.toBigDecimal())
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = 10.0.toBigDecimal())) {
            validate(Employee::salary).isNotBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = 12.0.toBigDecimal())) {
            validate(Employee::salary).isNotBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = 10.0.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isNotBetween(start = 9.9.unaryMinus().toBigDecimal(), end = 8.0.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = 12.0.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).isNotBetween(start = 13.0.unaryMinus().toBigDecimal(), end = 12.9.unaryMinus().toBigDecimal())
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ZERO)) {
                validate(Employee::salary).isNotBetween(start = ZERO, end = ONE)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ZERO,
                constraint = NotBetween(start = ZERO, end = ONE)))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = ONE)) {
                validate(Employee::salary).isNotBetween(start = ZERO, end = ONE)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = ONE,
                constraint = NotBetween(start = ZERO, end = ONE)))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 2.0.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 2.0.unaryMinus().toBigDecimal(),
                constraint = NotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.0.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.0.unaryMinus().toBigDecimal(),
                constraint = NotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 0.5.toBigDecimal())) {
                validate(Employee::salary).isNotBetween(start = ZERO, end = ONE)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 0.5.toBigDecimal(),
                constraint = NotBetween(start = ZERO, end = ONE)))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 1.5.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 1.5.unaryMinus().toBigDecimal(),
                constraint = NotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        }
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal())) {
            validate(Employee::salary).hasIntegerDigits()
        }
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toBigDecimal())) {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78.toBigDecimal(),
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toBigDecimal())) {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78.toBigDecimal(),
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toBigDecimal())) {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78.toBigDecimal(),
                constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78.unaryMinus().toBigDecimal(),
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78.unaryMinus().toBigDecimal(),
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 748536.78.unaryMinus().toBigDecimal(),
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
        validate(Employee(salary = 99.9999.toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = 99.9999.toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 99.9999.toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        }
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = 99.9999.toBigDecimal())) {
            validate(Employee::salary).hasDecimalDigits()
        }
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toBigDecimal())) {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536.toBigDecimal(),
                constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toBigDecimal())) {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536.toBigDecimal(),
                constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toBigDecimal())) {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536.toBigDecimal(),
                constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536.unaryMinus().toBigDecimal(),
                constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536.unaryMinus().toBigDecimal(),
                constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toBigDecimal())) {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "salary",
                value = 78.748536.unaryMinus().toBigDecimal(),
                constraint = DecimalDigits(min = 7, max = 5)))
    }
}
