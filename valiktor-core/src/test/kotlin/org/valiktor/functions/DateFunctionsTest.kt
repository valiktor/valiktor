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
import org.valiktor.constraints.Today
import org.valiktor.functions.DateFunctionsFixture.Employee
import org.valiktor.validate
import java.util.Calendar
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object DateFunctionsFixture {

    data class Employee(val dateOfBirth: Date? = null)
}

class DateFunctionsTest {

    private companion object {
        val NOW = System.currentTimeMillis()
        const val ONE_DAY = 1000L * 60L * 60L * 24L
    }

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(dateOfBirth = Date())) {
            validate(Employee::dateOfBirth).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::dateOfBirth).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isEqualTo(Date())
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isEqualTo(Date(NOW))
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isEqualTo(Date(NOW - 1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = Equals(Date(NOW - 1))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotEqualTo(Date())
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isNotEqualTo(Date(NOW - 1))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isNotEqualTo(Date(NOW))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = NotEquals(Date(NOW))))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isIn(Date(), Date())
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isIn(Date(NOW), Date(NOW - 1))
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isIn(Date(NOW - 1), Date(NOW - 2))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = In(setOf(Date(NOW - 1), Date(NOW - 2)))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isIn(listOf(Date(), Date()))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isIn(listOf(Date(NOW), Date(NOW - 1)))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isIn(listOf(Date(NOW - 1), Date(NOW - 2)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = In(listOf(Date(NOW - 1), Date(NOW - 2)))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotIn(Date(), Date())
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isNotIn(Date(NOW - 1), Date(NOW + 1))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isNotIn(Date(NOW), Date(NOW + 1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = NotIn(setOf(Date(NOW), Date(NOW + 1)))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotIn(listOf(Date(), Date()))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isNotIn(listOf(Date(NOW - 1), Date(NOW + 1)))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isNotIn(listOf(Date(NOW), Date(NOW + 1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW), constraint = NotIn(listOf(Date(NOW), Date(NOW + 1)))))
    }

    @Test
    fun `isToday with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with 00h00m00 should be valid`() {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        validate(Employee(dateOfBirth = today.time)) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with 23h59m59 should be valid`() {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 23)
        today.set(Calendar.MINUTE, 59)
        today.set(Calendar.SECOND, 59)
        today.set(Calendar.MILLISECOND, 999)

        validate(Employee(dateOfBirth = today.time)) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with now should be valid`() {
        validate(Employee(dateOfBirth = Date())) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with yesterday value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW - ONE_DAY))) {
                validate(Employee::dateOfBirth).isToday()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW - ONE_DAY), constraint = Today))
    }

    @Test
    fun `isToday with tomorrow value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW + ONE_DAY))) {
                validate(Employee::dateOfBirth).isToday()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "dateOfBirth", value = Date(NOW + ONE_DAY), constraint = Today))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isLessThan(Date())
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isLessThan(Date(NOW + 1))
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isLessThan(Date(NOW - 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = Less(Date(NOW - 1))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isLessThan(Date(NOW))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = Less(Date(NOW))))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(Date())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(Date(NOW + 1))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(Date(NOW))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isLessThanOrEqualTo(Date(NOW - 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = LessOrEqual(Date(NOW - 1))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isGreaterThan(Date())
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isGreaterThan(Date(NOW - 1))
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isGreaterThan(Date(NOW + 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = Greater(Date(NOW + 1))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isGreaterThan(Date(NOW))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = Greater(Date(NOW))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(Date())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(Date(NOW - 1))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(Date(NOW))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(Date(NOW + 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = GreaterOrEqual(Date(NOW + 1))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isBetween(start = Date(), end = Date())
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isBetween(start = Date(NOW), end = Date(NOW + 1))
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW + 1))) {
            validate(Employee::dateOfBirth).isBetween(start = Date(NOW), end = Date(NOW + 1))
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW + 1))) {
            validate(Employee::dateOfBirth).isBetween(start = Date(NOW), end = Date(NOW + 2))
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isBetween(start = Date(NOW + 1), end = Date(NOW + 3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = Between(start = Date(NOW + 1), end = Date(NOW + 3))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW + 4))) {
                validate(Employee::dateOfBirth).isBetween(start = Date(NOW + 1), end = Date(NOW + 3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW + 4),
                constraint = Between(start = Date(NOW + 1), end = Date(NOW + 3))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotBetween(start = Date(), end = Date())
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW))) {
            validate(Employee::dateOfBirth).isNotBetween(start = Date(NOW + 1), end = Date(NOW + 2))
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(dateOfBirth = Date(NOW + 2))) {
            validate(Employee::dateOfBirth).isNotBetween(start = Date(NOW), end = Date(NOW + 1))
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW))) {
                validate(Employee::dateOfBirth).isNotBetween(start = Date(NOW), end = Date(NOW + 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW),
                constraint = NotBetween(start = Date(NOW), end = Date(NOW + 1))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW + 1))) {
                validate(Employee::dateOfBirth).isNotBetween(start = Date(NOW), end = Date(NOW + 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW + 1),
                constraint = NotBetween(start = Date(NOW), end = Date(NOW + 1))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = Date(NOW + 1))) {
                validate(Employee::dateOfBirth).isNotBetween(start = Date(NOW), end = Date(NOW + 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "dateOfBirth",
                value = Date(NOW + 1),
                constraint = NotBetween(start = Date(NOW), end = Date(NOW + 2))))
    }
}