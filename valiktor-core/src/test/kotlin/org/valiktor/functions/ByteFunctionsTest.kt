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
import org.valiktor.functions.ByteFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object ByteFunctionsFixture {

    data class Employee(val id: Byte? = null)
}

class ByteFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toByte(), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(id = 1)) {
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
            validate(Employee::id).isEqualTo(1)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isEqualTo(1)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 2)) {
                validate(Employee::id).isEqualTo(1)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 2.toByte(), constraint = Equals<Byte>(1)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotEqualTo(1)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(id = 2)) {
            validate(Employee::id).isNotEqualTo(1)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotEqualTo(1)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toByte(), constraint = NotEquals<Byte>(1)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isIn(1, 2, 3)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(id = 2)) {
            validate(Employee::id).isIn(1, 2, 3)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isIn(0, 2, 3)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toByte(), constraint = In(setOf<Byte>(0, 2, 3))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isIn(listOf<Byte>(1, 2, 3))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(id = 2)) {
            validate(Employee::id).isIn(listOf<Byte>(1, 2, 3))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isIn(listOf<Byte>(0, 2, 3))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toByte(), constraint = In(listOf<Byte>(0, 2, 3))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotIn(0, 2, 3)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isNotIn(0, 2, 3)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotIn(1, 2, 3)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toByte(), constraint = NotIn(setOf<Byte>(1, 2, 3))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotIn(listOf<Byte>(0, 2, 3))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isNotIn(listOf<Byte>(0, 2, 3))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotIn(listOf<Byte>(1, 2, 3))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1.toByte(), constraint = NotIn(listOf<Byte>(1, 2, 3))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isZero()
        }
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isZero()
        }
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toByte(),
                constraint = Equals<Byte>(0)))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotZero()
        }
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isNotZero()
        }
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = NotEquals<Byte>(0)))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isOne()
        }
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isOne()
        }
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = Equals<Byte>(1)))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotOne()
        }
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isNotOne()
        }
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toByte(),
                constraint = NotEquals<Byte>(1)))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isPositive()
        }
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isPositive()
        }
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = Greater<Byte>(0)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -98)) {
                validate(Employee::id).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 98.unaryMinus().toByte(),
                constraint = Greater<Byte>(0)))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(id = -98)) {
            validate(Employee::id).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNegativeOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toByte(),
                constraint = LessOrEqual<Byte>(0)))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNegative()
        }
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(id = -1)) {
            validate(Employee::id).isNegative()
        }
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = Less<Byte>(0)))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toByte(),
                constraint = Less<Byte>(0)))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -98)) {
                validate(Employee::id).isPositiveOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 98.unaryMinus().toByte(),
                constraint = GreaterOrEqual<Byte>(0)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isLessThan(10.toByte())
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(id = 99)) {
            validate(Employee::id).isLessThan(100.toByte())
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(id = -4)) {
            validate(Employee::id).isLessThan(3.unaryMinus().toByte())
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 50)) {
                validate(Employee::id).isLessThan(49.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 50.toByte(),
                constraint = Less<Byte>(49)))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -50)) {
                validate(Employee::id).isLessThan(51.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 50.unaryMinus().toByte(),
                constraint = Less<Byte>(-51)))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isLessThan(0.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = Less<Byte>(0)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isLessThanOrEqualTo(10.toByte())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(id = 99)) {
            validate(Employee::id).isLessThanOrEqualTo(100.toByte())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(id = -4)) {
            validate(Employee::id).isLessThanOrEqualTo(3.unaryMinus().toByte())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isLessThanOrEqualTo(0.toByte())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 58)) {
                validate(Employee::id).isLessThanOrEqualTo(57.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 58.toByte(),
                constraint = LessOrEqual<Byte>(57)))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -96)) {
                validate(Employee::id).isLessThanOrEqualTo(97.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 96.unaryMinus().toByte(),
                constraint = LessOrEqual<Byte>(-97)))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isGreaterThan(10.toByte())
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(id = 11)) {
            validate(Employee::id).isGreaterThan(10.toByte())
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(id = -88)) {
            validate(Employee::id).isGreaterThan(89.unaryMinus().toByte())
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10)) {
                validate(Employee::id).isGreaterThan(11.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10.toByte(),
                constraint = Greater<Byte>(11)))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -19)) {
                validate(Employee::id).isGreaterThan(18.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 19.unaryMinus().toByte(),
                constraint = Greater<Byte>(-18)))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isGreaterThan(0.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = Greater<Byte>(0)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isGreaterThanOrEqualTo(10.toByte())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(id = 100)) {
            validate(Employee::id).isGreaterThanOrEqualTo(99.toByte())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(id = -3)) {
            validate(Employee::id).isGreaterThanOrEqualTo(4.unaryMinus().toByte())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isGreaterThanOrEqualTo(0.toByte())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 57)) {
                validate(Employee::id).isGreaterThanOrEqualTo(58.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 57.toByte(),
                constraint = GreaterOrEqual<Byte>(58)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -97)) {
                validate(Employee::id).isGreaterThanOrEqualTo(96.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 97.unaryMinus().toByte(),
                constraint = GreaterOrEqual<Byte>(-96)))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isBetween(start = 1.toByte(), end = 9.toByte())
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isBetween(start = 0.toByte(), end = 1.toByte())
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isBetween(start = 0.toByte(), end = 1.toByte())
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(id = 5)) {
            validate(Employee::id).isBetween(start = 0.toByte(), end = 10.toByte())
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(id = -2)) {
            validate(Employee::id).isBetween(start = 2.unaryMinus().toByte(), end = 1.unaryMinus().toByte())
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(id = -1)) {
            validate(Employee::id).isBetween(start = 2.unaryMinus().toByte(), end = 1.unaryMinus().toByte())
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(id = -15)) {
            validate(Employee::id).isBetween(start = 20.unaryMinus().toByte(), end = 10.unaryMinus().toByte())
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10)) {
                validate(Employee::id).isBetween(start = 11.toByte(), end = 12.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10.toByte(),
                constraint = Between<Byte>(start = 11, end = 12)))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 12)) {
                validate(Employee::id).isBetween(start = 10.toByte(), end = 11.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 12.toByte(),
                constraint = Between<Byte>(start = 10, end = 11)))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -10)) {
                validate(Employee::id).isBetween(start = 9.unaryMinus().toByte(), end = 8.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10.unaryMinus().toByte(),
                constraint = Between<Byte>(start = -9, end = -8)))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -12)) {
                validate(Employee::id).isBetween(start = 14.unaryMinus().toByte(), end = 13.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 12.unaryMinus().toByte(),
                constraint = Between<Byte>(start = -14, end = -13)))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotBetween(start = 1.toByte(), end = 9.toByte())
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(id = 10)) {
            validate(Employee::id).isNotBetween(start = 11.toByte(), end = 12.toByte())
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(id = 12)) {
            validate(Employee::id).isNotBetween(start = 10.toByte(), end = 11.toByte())
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(id = -10)) {
            validate(Employee::id).isNotBetween(start = 9.unaryMinus().toByte(), end = 8.unaryMinus().toByte())
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(id = -12)) {
            validate(Employee::id).isNotBetween(start = 14.unaryMinus().toByte(), end = 13.unaryMinus().toByte())
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isNotBetween(start = 0.toByte(), end = 1.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0.toByte(),
                constraint = NotBetween<Byte>(start = 0, end = 1)))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotBetween(start = 0.toByte(), end = 1.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.toByte(),
                constraint = NotBetween<Byte>(start = 0, end = 1)))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -2)) {
                validate(Employee::id).isNotBetween(start = 2.unaryMinus().toByte(), end = 1.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 2.unaryMinus().toByte(),
                constraint = NotBetween<Byte>(start = -2, end = -1)))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -1)) {
                validate(Employee::id).isNotBetween(start = 2.unaryMinus().toByte(), end = 1.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1.unaryMinus().toByte(),
                constraint = NotBetween<Byte>(start = -2, end = -1)))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 5)) {
                validate(Employee::id).isNotBetween(start = 0.toByte(), end = 10.toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 5.toByte(),
                constraint = NotBetween<Byte>(start = 0, end = 10)))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -15)) {
                validate(Employee::id).isNotBetween(start = 20.unaryMinus().toByte(), end = 10.unaryMinus().toByte())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 15.unaryMinus().toByte(),
                constraint = NotBetween<Byte>(start = -20, end = -10)))
    }

    @Test
    fun `hasDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).hasDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasDigits with valid min value should be valid`() {
        validate(Employee(id = 99)) {
            validate(Employee::id).hasDigits(min = 2)
        }
    }

    @Test
    fun `hasDigits with valid max value should be valid`() {
        validate(Employee(id = 99)) {
            validate(Employee::id).hasDigits(max = 2)
        }
    }

    @Test
    fun `hasDigits with valid min and max value should be valid`() {
        validate(Employee(id = 99)) {
            validate(Employee::id).hasDigits(min = 2, max = 2)
        }
    }

    @Test
    fun `hasDigits with negative valid min value should be valid`() {
        validate(Employee(id = -99)) {
            validate(Employee::id).hasDigits(min = 2)
        }
    }

    @Test
    fun `hasDigits with negative valid max value should be valid`() {
        validate(Employee(id = -99)) {
            validate(Employee::id).hasDigits(max = 2)
        }
    }

    @Test
    fun `hasDigits with negative valid min and max value should be valid`() {
        validate(Employee(id = -99)) {
            validate(Employee::id).hasDigits(min = 2, max = 2)
        }
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(id = 99)) {
            validate(Employee::id).hasDigits()
        }
    }

    @Test
    fun `hasDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 74)) {
                validate(Employee::id).hasDigits(min = 3)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 74.toByte(),
                constraint = IntegerDigits(min = 3)))
    }

    @Test
    fun `hasDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 74)) {
                validate(Employee::id).hasDigits(max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 74.toByte(),
                constraint = IntegerDigits(max = 1)))
    }

    @Test
    fun `hasDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 74)) {
                validate(Employee::id).hasDigits(min = 3, max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 74.toByte(),
                constraint = IntegerDigits(min = 3, max = 1)))
    }

    @Test
    fun `hasDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -74)) {
                validate(Employee::id).hasDigits(min = 3)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 74.unaryMinus().toByte(),
                constraint = IntegerDigits(min = 3)))
    }

    @Test
    fun `hasDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -74)) {
                validate(Employee::id).hasDigits(max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 74.unaryMinus().toByte(),
                constraint = IntegerDigits(max = 1)))
    }

    @Test
    fun `hasDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -74)) {
                validate(Employee::id).hasDigits(min = 3, max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 74.unaryMinus().toByte(),
                constraint = IntegerDigits(min = 3, max = 1)))
    }
}
