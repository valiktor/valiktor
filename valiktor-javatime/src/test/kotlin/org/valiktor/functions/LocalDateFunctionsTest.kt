package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.LocalDateFunctionsFixture.Employee
import org.valiktor.validate
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object LocalDateFunctionsFixture {

    data class Employee(val dateOfBirth: LocalDate? = null)
}

class LocalDateFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
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
            validate(Employee::dateOfBirth).isEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isEqualTo(LocalDate.now().minusDays(1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = Equals(LocalDate.now().minusDays(1))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isNotEqualTo(LocalDate.now().minusDays(1))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isNotEqualTo(LocalDate.now())
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = NotEquals(LocalDate.now())))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isIn(LocalDate.now(), LocalDate.now())
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isIn(LocalDate.now(), LocalDate.now().minusDays(1))
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isIn(LocalDate.now().minusDays(1), LocalDate.now().minusDays(2))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = In(setOf(LocalDate.now().minusDays(1), LocalDate.now().minusDays(2)))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isIn(listOf(LocalDate.now(), LocalDate.now()))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isIn(listOf(LocalDate.now(), LocalDate.now().minusDays(1)))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isIn(listOf(LocalDate.now().minusDays(1), LocalDate.now().minusDays(2)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = In(listOf(LocalDate.now().minusDays(1), LocalDate.now().minusDays(2)))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotIn(LocalDate.now(), LocalDate.now())
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isNotIn(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isNotIn(LocalDate.now(), LocalDate.now().plusDays(1))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = NotIn(setOf(LocalDate.now(), LocalDate.now().plusDays(1)))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotIn(listOf(LocalDate.now(), LocalDate.now()))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isNotIn(listOf(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isNotIn(listOf(LocalDate.now(), LocalDate.now().plusDays(1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now(), constraint = NotIn(listOf(LocalDate.now(), LocalDate.now().plusDays(1)))))
    }

    @Test
    fun `isToday with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with now should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isToday()
        }
    }

    @Test
    fun `isToday with yesterday value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now().minusDays(1))) {
                validate(Employee::dateOfBirth).isToday()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now().minusDays(1), constraint = Today))
    }

    @Test
    fun `isToday with tomorrow value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now().plusDays(1))) {
                validate(Employee::dateOfBirth).isToday()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = LocalDate.now().plusDays(1), constraint = Today))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isLessThan(LocalDate.now())
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isLessThan(LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isLessThan(LocalDate.now().minusDays(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = Less(LocalDate.now().minusDays(1))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isLessThan(LocalDate.now())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = Less(LocalDate.now())))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isLessThanOrEqualTo(LocalDate.now().minusDays(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = LessOrEqual(LocalDate.now().minusDays(1))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isGreaterThan(LocalDate.now())
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isGreaterThan(LocalDate.now().minusDays(1))
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isGreaterThan(LocalDate.now().plusDays(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = Greater(LocalDate.now().plusDays(1))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isGreaterThan(LocalDate.now())
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = Greater(LocalDate.now())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(LocalDate.now().minusDays(1))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(LocalDate.now().plusDays(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = GreaterOrEqual(LocalDate.now().plusDays(1))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isBetween(start = LocalDate.now(), end = LocalDate.now())
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now().plusDays(1))) {
            validate(Employee::dateOfBirth).isBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now().plusDays(1))) {
            validate(Employee::dateOfBirth).isBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(2))
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isBetween(start = LocalDate.now().plusDays(1), end = LocalDate.now().plusDays(3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = Between(start = LocalDate.now().plusDays(1), end = LocalDate.now().plusDays(3))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now().plusDays(4))) {
                validate(Employee::dateOfBirth).isBetween(start = LocalDate.now().plusDays(1), end = LocalDate.now().plusDays(3))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now().plusDays(4),
                        constraint = Between(start = LocalDate.now().plusDays(1), end = LocalDate.now().plusDays(3))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dateOfBirth).isNotBetween(start = LocalDate.now(), end = LocalDate.now())
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now())) {
            validate(Employee::dateOfBirth).isNotBetween(start = LocalDate.now().plusDays(1), end = LocalDate.now().plusDays(2))
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(dateOfBirth = LocalDate.now().plusDays(2))) {
            validate(Employee::dateOfBirth).isNotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now())) {
                validate(Employee::dateOfBirth).isNotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now(),
                        constraint = NotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now().plusDays(1))) {
                validate(Employee::dateOfBirth).isNotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now().plusDays(1),
                        constraint = NotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(1))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dateOfBirth = LocalDate.now().plusDays(1))) {
                validate(Employee::dateOfBirth).isNotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = LocalDate.now().plusDays(1),
                        constraint = NotBetween(start = LocalDate.now(), end = LocalDate.now().plusDays(2))))
    }
}