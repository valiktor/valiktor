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
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.functions.BigIntegerFunctionsFixture.Employee
import org.valiktor.validate
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object BigIntegerFunctionsFixture {

    data class Employee(val id: BigInteger? = null)
}

class BigIntegerFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toBigInteger(), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::id).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isEqualTo(1.toBigInteger())
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isEqualTo(1.toBigInteger())
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 2.toBigInteger())) {
                validate(Employee::id).isEqualTo(1.toBigInteger())
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 2.toBigInteger(), constraint = Equals(1.toBigInteger())))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotEqualTo(1.toBigInteger())
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(id = 2.toBigInteger())) {
            validate(Employee::id).isNotEqualTo(1.toBigInteger())
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNotEqualTo(1.toBigInteger())
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toBigInteger(), constraint = NotEquals(1.toBigInteger())))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isIn(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(id = 2.toBigInteger())) {
            validate(Employee::id).isIn(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isIn(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toBigInteger(), constraint = In(setOf(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isIn(listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(id = 2.toBigInteger())) {
            validate(Employee::id).isIn(listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isIn(listOf(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toBigInteger(), constraint = In(listOf(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotIn(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isNotIn(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNotIn(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger())
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toBigInteger(), constraint = NotIn(setOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotIn(listOf(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isNotIn(listOf(0.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNotIn(listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toBigInteger(), constraint = NotIn(listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger()))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isZero()
        }
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isZero()
        }
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toBigInteger(),
                constraint = Equals(0.toBigInteger())))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotZero()
        }
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isNotZero()
        }
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = NotEquals(0.toBigInteger())))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isOne()
        }
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isOne()
        }
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = Equals(1.toBigInteger())))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotOne()
        }
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isNotOne()
        }
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toBigInteger(),
                constraint = NotEquals(1.toBigInteger())))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isPositive()
        }
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isPositive()
        }
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = Greater(0.toBigInteger())))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 98765.unaryMinus().toBigInteger())) {
                validate(Employee::id).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 98765.unaryMinus().toBigInteger(),
                constraint = Greater(0.toBigInteger())))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(id = 98765.unaryMinus().toBigInteger())) {
            validate(Employee::id).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNegativeOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toBigInteger(),
                constraint = LessOrEqual(0.toBigInteger())))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNegative()
        }
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(id = 1.unaryMinus().toBigInteger())) {
            validate(Employee::id).isNegative()
        }
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = Less(0.toBigInteger())))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toBigInteger(),
                constraint = Less(0.toBigInteger())))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 98765.unaryMinus().toBigInteger())) {
                validate(Employee::id).isPositiveOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 98765.unaryMinus().toBigInteger(),
                constraint = GreaterOrEqual(0.toBigInteger())))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isLessThan(10.toBigInteger())
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(id = 9999.toBigInteger())) {
            validate(Employee::id).isLessThan(100000.toBigInteger())
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(id = 4.unaryMinus().toBigInteger())) {
            validate(Employee::id).isLessThan(3.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 50.toBigInteger())) {
                validate(Employee::id).isLessThan(49.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 50.toBigInteger(),
                constraint = Less(49.toBigInteger())))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 50.unaryMinus().toBigInteger())) {
                validate(Employee::id).isLessThan(51.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 50.unaryMinus().toBigInteger(),
                constraint = Less(51.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isLessThan(0.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = Less(0.toBigInteger())))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isLessThanOrEqualTo(10.toBigInteger())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(id = 9999.toBigInteger())) {
            validate(Employee::id).isLessThanOrEqualTo(100000.toBigInteger())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(id = 4.unaryMinus().toBigInteger())) {
            validate(Employee::id).isLessThanOrEqualTo(3.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isLessThanOrEqualTo(0.toBigInteger())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 56789.toBigInteger())) {
                validate(Employee::id).isLessThanOrEqualTo(57.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 56789.toBigInteger(),
                constraint = LessOrEqual(57.toBigInteger())))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 96.unaryMinus().toBigInteger())) {
                validate(Employee::id).isLessThanOrEqualTo(97.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 96.unaryMinus().toBigInteger(),
                constraint = LessOrEqual(97.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isGreaterThan(10.toBigInteger())
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(id = 11.toBigInteger())) {
            validate(Employee::id).isGreaterThan(10.toBigInteger())
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(id = 88.unaryMinus().toBigInteger())) {
            validate(Employee::id).isGreaterThan(89.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10.toBigInteger())) {
                validate(Employee::id).isGreaterThan(11.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10.toBigInteger(),
                constraint = Greater(11.toBigInteger())))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 189.unaryMinus().toBigInteger())) {
                validate(Employee::id).isGreaterThan(180.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 189.unaryMinus().toBigInteger(),
                constraint = Greater(180.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isGreaterThan(0.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = Greater(0.toBigInteger())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isGreaterThanOrEqualTo(10.toBigInteger())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(id = 10000.toBigInteger())) {
            validate(Employee::id).isGreaterThanOrEqualTo(9999.toBigInteger())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(id = 3.unaryMinus().toBigInteger())) {
            validate(Employee::id).isGreaterThanOrEqualTo(4.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isGreaterThanOrEqualTo(0.toBigInteger())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 57.toBigInteger())) {
                validate(Employee::id).isGreaterThanOrEqualTo(56789.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 57.toBigInteger(),
                constraint = GreaterOrEqual(56789.toBigInteger())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 97.unaryMinus().toBigInteger())) {
                validate(Employee::id).isGreaterThanOrEqualTo(96.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 97.unaryMinus().toBigInteger(),
                constraint = GreaterOrEqual(96.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isBetween(start = 1.toBigInteger(), end = 9.toBigInteger())
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(id = 0.toBigInteger())) {
            validate(Employee::id).isBetween(start = 0.toBigInteger(), end = 1.toBigInteger())
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(id = 1.toBigInteger())) {
            validate(Employee::id).isBetween(start = 0.toBigInteger(), end = 1.toBigInteger())
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(id = 5.toBigInteger())) {
            validate(Employee::id).isBetween(start = 0.toBigInteger(), end = 10.toBigInteger())
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(id = 2.unaryMinus().toBigInteger())) {
            validate(Employee::id).isBetween(start = 2.unaryMinus().toBigInteger(), end = 1.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(id = 1.unaryMinus().toBigInteger())) {
            validate(Employee::id).isBetween(start = 2.unaryMinus().toBigInteger(), end = 1.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(id = 15.unaryMinus().toBigInteger())) {
            validate(Employee::id).isBetween(start = 20.unaryMinus().toBigInteger(), end = 10.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10.toBigInteger())) {
                validate(Employee::id).isBetween(start = 11.toBigInteger(), end = 12.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10.toBigInteger(),
                constraint = Between(start = 11.toBigInteger(), end = 12.toBigInteger())))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 12.toBigInteger())) {
                validate(Employee::id).isBetween(start = 10.toBigInteger(), end = 11.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 12.toBigInteger(),
                constraint = Between(start = 10.toBigInteger(), end = 11.toBigInteger())))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10.unaryMinus().toBigInteger())) {
                validate(Employee::id).isBetween(start = 9.unaryMinus().toBigInteger(), end = 8.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10.unaryMinus().toBigInteger(),
                constraint = Between(start = 9.unaryMinus().toBigInteger(), end = 8.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 12.unaryMinus().toBigInteger())) {
                validate(Employee::id).isBetween(start = 14.unaryMinus().toBigInteger(), end = 13.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 12.unaryMinus().toBigInteger(),
                constraint = Between(start = 14.unaryMinus().toBigInteger(), end = 13.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotBetween(start = 1.toBigInteger(), end = 9.toBigInteger())
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(id = 10.toBigInteger())) {
            validate(Employee::id).isNotBetween(start = 11.toBigInteger(), end = 12.toBigInteger())
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(id = 12.toBigInteger())) {
            validate(Employee::id).isNotBetween(start = 10.toBigInteger(), end = 11.toBigInteger())
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(id = 10.unaryMinus().toBigInteger())) {
            validate(Employee::id).isNotBetween(start = 9.unaryMinus().toBigInteger(), end = 8.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(id = 12.unaryMinus().toBigInteger())) {
            validate(Employee::id).isNotBetween(start = 14.unaryMinus().toBigInteger(), end = 13.unaryMinus().toBigInteger())
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0.toBigInteger())) {
                validate(Employee::id).isNotBetween(start = 0.toBigInteger(), end = 1.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toBigInteger(),
                constraint = NotBetween(start = 0.toBigInteger(), end = 1.toBigInteger())))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.toBigInteger())) {
                validate(Employee::id).isNotBetween(start = 0.toBigInteger(), end = 1.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toBigInteger(),
                constraint = NotBetween(start = 0.toBigInteger(), end = 1.toBigInteger())))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 2.unaryMinus().toBigInteger())) {
                validate(Employee::id).isNotBetween(start = 2.unaryMinus().toBigInteger(), end = 1.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 2.unaryMinus().toBigInteger(),
                constraint = NotBetween(start = 2.unaryMinus().toBigInteger(), end = 1.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1.unaryMinus().toBigInteger())) {
                validate(Employee::id).isNotBetween(start = 2.unaryMinus().toBigInteger(), end = 1.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.unaryMinus().toBigInteger(),
                constraint = NotBetween(start = 2.unaryMinus().toBigInteger(), end = 1.unaryMinus().toBigInteger())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 5.toBigInteger())) {
                validate(Employee::id).isNotBetween(start = 0.toBigInteger(), end = 10.toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 5.toBigInteger(),
                constraint = NotBetween(start = 0.toBigInteger(), end = 10.toBigInteger())))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 15.unaryMinus().toBigInteger())) {
                validate(Employee::id).isNotBetween(start = 20.unaryMinus().toBigInteger(), end = 10.unaryMinus().toBigInteger())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 15.unaryMinus().toBigInteger(),
                constraint = NotBetween(start = 20.unaryMinus().toBigInteger(), end = 10.unaryMinus().toBigInteger())))
    }

    @Test
    fun `hasDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).hasDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasDigits with valid min value should be valid`() {
        validate(Employee(id = 9999.toBigInteger())) {
            validate(Employee::id).hasDigits(min = 4)
        }
    }

    @Test
    fun `hasDigits with valid max value should be valid`() {
        validate(Employee(id = 9999.toBigInteger())) {
            validate(Employee::id).hasDigits(max = 4)
        }
    }

    @Test
    fun `hasDigits with valid min and max value should be valid`() {
        validate(Employee(id = 9999.toBigInteger())) {
            validate(Employee::id).hasDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasDigits with negative valid min value should be valid`() {
        validate(Employee(id = 999999.unaryMinus().toBigInteger())) {
            validate(Employee::id).hasDigits(min = 6)
        }
    }

    @Test
    fun `hasDigits with negative valid max value should be valid`() {
        validate(Employee(id = 999999.unaryMinus().toBigInteger())) {
            validate(Employee::id).hasDigits(max = 6)
        }
    }

    @Test
    fun `hasDigits with negative valid min and max value should be valid`() {
        validate(Employee(id = 999999.unaryMinus().toBigInteger())) {
            validate(Employee::id).hasDigits(min = 6, max = 6)
        }
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(id = 9999.toBigInteger())) {
            validate(Employee::id).hasDigits()
        }
    }

    @Test
    fun `hasDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536.toBigInteger())) {
                validate(Employee::id).hasDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536.toBigInteger(),
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536.toBigInteger())) {
                validate(Employee::id).hasDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536.toBigInteger(),
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536.toBigInteger())) {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536.toBigInteger(),
                constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536.unaryMinus().toBigInteger())) {
                validate(Employee::id).hasDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536.unaryMinus().toBigInteger(),
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536.unaryMinus().toBigInteger())) {
                validate(Employee::id).hasDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536.unaryMinus().toBigInteger(),
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536.unaryMinus().toBigInteger())) {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536.unaryMinus().toBigInteger(),
                constraint = IntegerDigits(min = 7, max = 5)))
    }
}