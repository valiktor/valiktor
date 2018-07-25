package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.LocalDateTimeFunctionsFixture.Employee
import org.valiktor.validate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object LocalDateTimeFunctionsFixture {

    data class Employee(val dateOfBirth: LocalDateTime? = null)
}

class LocalDateTimeFunctionsTest {

    private val dateTime = LocalDateTime.of(2018, Month.JANUARY, 1, 13, 25, 57)

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
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
            validate(Employee::dateOfBirth).isEqualTo(dateTime)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isEqualTo(dateTime)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isEqualTo(dateTime.minusSeconds(1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = Equals(dateTime.minusSeconds(1))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotEqualTo(dateTime)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isNotEqualTo(dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isNotEqualTo(dateTime)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = NotEquals(dateTime)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isIn(dateTime, dateTime)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isIn(dateTime, dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isIn(dateTime.minusSeconds(1), dateTime.minusSeconds(2))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = In(setOf(dateTime.minusSeconds(1), dateTime.minusSeconds(2)))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isIn(listOf(dateTime, dateTime))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isIn(listOf(dateTime, dateTime.minusSeconds(1)))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isIn(listOf(dateTime.minusSeconds(1), dateTime.minusSeconds(2)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = In(listOf(dateTime.minusSeconds(1), dateTime.minusSeconds(2)))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotIn(dateTime, dateTime)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isNotIn(dateTime.minusSeconds(1), dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isNotIn(dateTime, dateTime.plusSeconds(1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = NotIn(setOf(dateTime, dateTime.plusSeconds(1)))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotIn(listOf(dateTime, dateTime))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isNotIn(listOf(dateTime.minusSeconds(1), dateTime.plusSeconds(1)))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isNotIn(listOf(dateTime, dateTime.plusSeconds(1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime, constraint = NotIn(listOf(dateTime, dateTime.plusSeconds(1)))))
    }

    @Test
    fun `isToday with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with 00h00m00 should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now().atTime(0, 0, 0))) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with 23h59m59 should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now().atTime(23, 59, 59))) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with now should be valid`() {
        validate(Employee(dateOfBirth = LocalDateTime.now())) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with yesterday value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime.minusDays(1))) {
                validate(Employee::dateOfBirth).isToday()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime.minusDays(1), constraint = Today))
    }

    @Test
    fun `isToday with tomorrow value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime.plusDays(1))) {
                validate(Employee::dateOfBirth).isToday()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = dateTime.plusDays(1), constraint = Today))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isLessThan(dateTime)
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isLessThan(dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isLessThan(dateTime.minusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = Less(dateTime.minusSeconds(1))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isLessThan(dateTime)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = Less(dateTime)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isLessThanOrEqualTo(dateTime.minusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = LessOrEqual(dateTime.minusSeconds(1))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isGreaterThan(dateTime)
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isGreaterThan(dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isGreaterThan(dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = Greater(dateTime.plusSeconds(1))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isGreaterThan(dateTime)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = Greater(dateTime)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(dateTime.minusSeconds(1))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(dateTime)
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = GreaterOrEqual(dateTime.plusSeconds(1))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isBetween(start = dateTime, end = dateTime)
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isBetween(start = dateTime, end = dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(dateOfBirth = dateTime.plusSeconds(1))) {
            validate(Employee::dateOfBirth).isBetween(start = dateTime, end = dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(dateOfBirth = dateTime.plusSeconds(1))) {
            validate(Employee::dateOfBirth).isBetween(start = dateTime, end = dateTime.plusSeconds(2))
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isBetween(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = Between(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime.plusSeconds(4))) {
                validate(Employee::dateOfBirth).isBetween(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime.plusSeconds(4),
                        constraint = Between(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(3))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotBetween(start = dateTime, end = dateTime)
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(dateOfBirth = dateTime)) {
            validate(Employee::dateOfBirth).isNotBetween(start = dateTime.plusSeconds(1), end = dateTime.plusSeconds(2))
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(dateOfBirth = dateTime.plusSeconds(2))) {
            validate(Employee::dateOfBirth).isNotBetween(start = dateTime, end = dateTime.plusSeconds(1))
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime)) {
                validate(Employee::dateOfBirth).isNotBetween(start = dateTime, end = dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime,
                        constraint = NotBetween(start = dateTime, end = dateTime.plusSeconds(1))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime.plusSeconds(1))) {
                validate(Employee::dateOfBirth).isNotBetween(start = dateTime, end = dateTime.plusSeconds(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime.plusSeconds(1),
                        constraint = NotBetween(start = dateTime, end = dateTime.plusSeconds(1))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = dateTime.plusSeconds(1))) {
                validate(Employee::dateOfBirth).isNotBetween(start = dateTime, end = dateTime.plusSeconds(2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = dateTime.plusSeconds(1),
                        constraint = NotBetween(start = dateTime, end = dateTime.plusSeconds(2))))
    }
}