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
import org.valiktor.functions.IntFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object IntFunctionsFixture {

    data class Employee(val id: Int? = null)
}

class IntFunctionsTest {

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
            DefaultConstraintViolation(property = "id", value = 1, constraint = Null))
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
            DefaultConstraintViolation(property = "id", value = 2, constraint = Equals(1)))
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
            DefaultConstraintViolation(property = "id", value = 1, constraint = NotEquals(1)))
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
            DefaultConstraintViolation(property = "id", value = 1, constraint = In(setOf(0, 2, 3))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isIn(listOf(1, 2, 3))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(id = 2)) {
            validate(Employee::id).isIn(listOf(1, 2, 3))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isIn(listOf(0, 2, 3))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1, constraint = In(listOf(0, 2, 3))))
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
            DefaultConstraintViolation(property = "id", value = 1, constraint = NotIn(setOf(1, 2, 3))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotIn(listOf(0, 2, 3))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isNotIn(listOf(0, 2, 3))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotIn(listOf(1, 2, 3))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "id", value = 1, constraint = NotIn(listOf(1, 2, 3))))
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
                value = 1,
                constraint = Equals(0)))
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
                value = 0,
                constraint = NotEquals(0)))
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
                value = 0,
                constraint = Equals(1)))
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
                value = 1,
                constraint = NotEquals(1)))
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
                value = 0,
                constraint = Greater(0)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -98765)) {
                validate(Employee::id).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -98765,
                constraint = Greater(0)))
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
        validate(Employee(id = -98765)) {
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
                value = 1,
                constraint = LessOrEqual(0)))
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
                value = 0,
                constraint = Less(0)))
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
                value = 1,
                constraint = Less(0)))
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
            validate(Employee(id = -98765)) {
                validate(Employee::id).isPositiveOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -98765,
                constraint = GreaterOrEqual(0)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isLessThan(10)
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(id = 9999)) {
            validate(Employee::id).isLessThan(100000)
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(id = -4)) {
            validate(Employee::id).isLessThan(-3)
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 50)) {
                validate(Employee::id).isLessThan(49)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 50,
                constraint = Less(49)))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -50)) {
                validate(Employee::id).isLessThan(-51)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -50,
                constraint = Less(-51)))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isLessThan(0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0,
                constraint = Less(0)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isLessThanOrEqualTo(10)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(id = 9999)) {
            validate(Employee::id).isLessThanOrEqualTo(100000)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(id = -4)) {
            validate(Employee::id).isLessThanOrEqualTo(-3)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isLessThanOrEqualTo(0)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 56789)) {
                validate(Employee::id).isLessThanOrEqualTo(57)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 56789,
                constraint = LessOrEqual(57)))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -96)) {
                validate(Employee::id).isLessThanOrEqualTo(-97)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -96,
                constraint = LessOrEqual(-97)))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isGreaterThan(10)
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(id = 11)) {
            validate(Employee::id).isGreaterThan(10)
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(id = -88)) {
            validate(Employee::id).isGreaterThan(-89)
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10)) {
                validate(Employee::id).isGreaterThan(11)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10,
                constraint = Greater(11)))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -189)) {
                validate(Employee::id).isGreaterThan(-180)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -189,
                constraint = Greater(-180)))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isGreaterThan(0)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0,
                constraint = Greater(0)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isGreaterThanOrEqualTo(10)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(id = 10000)) {
            validate(Employee::id).isGreaterThanOrEqualTo(9999)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(id = -3)) {
            validate(Employee::id).isGreaterThanOrEqualTo(-4)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isGreaterThanOrEqualTo(0)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 57)) {
                validate(Employee::id).isGreaterThanOrEqualTo(56789)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 57,
                constraint = GreaterOrEqual(56789)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -97)) {
                validate(Employee::id).isGreaterThanOrEqualTo(-96)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -97,
                constraint = GreaterOrEqual(-96)))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isBetween(start = 1, end = 9)
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(id = 0)) {
            validate(Employee::id).isBetween(start = 0, end = 1)
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(id = 1)) {
            validate(Employee::id).isBetween(start = 0, end = 1)
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(id = 5)) {
            validate(Employee::id).isBetween(start = 0, end = 10)
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(id = -2)) {
            validate(Employee::id).isBetween(start = -2, end = -1)
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(id = -1)) {
            validate(Employee::id).isBetween(start = -2, end = -1)
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(id = -15)) {
            validate(Employee::id).isBetween(start = -20, end = -10)
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10)) {
                validate(Employee::id).isBetween(start = 11, end = 12)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 10,
                constraint = Between(start = 11, end = 12)))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 12)) {
                validate(Employee::id).isBetween(start = 10, end = 11)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 12,
                constraint = Between(start = 10, end = 11)))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -10)) {
                validate(Employee::id).isBetween(start = -9, end = -8)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -10,
                constraint = Between(start = -9, end = -8)))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -12)) {
                validate(Employee::id).isBetween(start = -14, end = -13)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -12,
                constraint = Between(start = -14, end = -13)))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).isNotBetween(start = 1, end = 9)
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(id = 10)) {
            validate(Employee::id).isNotBetween(start = 11, end = 12)
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(id = 12)) {
            validate(Employee::id).isNotBetween(start = 10, end = 11)
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(id = -10)) {
            validate(Employee::id).isNotBetween(start = -9, end = -8)
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(id = -12)) {
            validate(Employee::id).isNotBetween(start = -14, end = -13)
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0)) {
                validate(Employee::id).isNotBetween(start = 0, end = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 0,
                constraint = NotBetween(start = 0, end = 1)))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1)) {
                validate(Employee::id).isNotBetween(start = 0, end = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 1,
                constraint = NotBetween(start = 0, end = 1)))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -2)) {
                validate(Employee::id).isNotBetween(start = -2, end = -1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -2,
                constraint = NotBetween(start = -2, end = -1)))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -1)) {
                validate(Employee::id).isNotBetween(start = -2, end = -1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -1,
                constraint = NotBetween(start = -2, end = -1)))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 5)) {
                validate(Employee::id).isNotBetween(start = 0, end = 10)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 5,
                constraint = NotBetween(start = 0, end = 10)))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -15)) {
                validate(Employee::id).isNotBetween(start = -20, end = -10)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -15,
                constraint = NotBetween(start = -20, end = -10)))
    }

    @Test
    fun `hasDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::id).hasDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasDigits with valid min value should be valid`() {
        validate(Employee(id = 9999)) {
            validate(Employee::id).hasDigits(min = 4)
        }
    }

    @Test
    fun `hasDigits with valid max value should be valid`() {
        validate(Employee(id = 9999)) {
            validate(Employee::id).hasDigits(max = 4)
        }
    }

    @Test
    fun `hasDigits with valid min and max value should be valid`() {
        validate(Employee(id = 9999)) {
            validate(Employee::id).hasDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasDigits with negative valid min value should be valid`() {
        validate(Employee(id = -999999)) {
            validate(Employee::id).hasDigits(min = 6)
        }
    }

    @Test
    fun `hasDigits with negative valid max value should be valid`() {
        validate(Employee(id = -999999)) {
            validate(Employee::id).hasDigits(max = 6)
        }
    }

    @Test
    fun `hasDigits with negative valid min and max value should be valid`() {
        validate(Employee(id = -999999)) {
            validate(Employee::id).hasDigits(min = 6, max = 6)
        }
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(id = 9999)) {
            validate(Employee::id).hasDigits()
        }
    }

    @Test
    fun `hasDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536)) {
                validate(Employee::id).hasDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536,
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536)) {
                validate(Employee::id).hasDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536,
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536)) {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = 748536,
                constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -748536)) {
                validate(Employee::id).hasDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -748536,
                constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -748536)) {
                validate(Employee::id).hasDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -748536,
                constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = -748536)) {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "id",
                value = -748536,
                constraint = IntegerDigits(min = 7, max = 5)))
    }
}