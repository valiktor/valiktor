package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.CalendarFunctionsFixture.Employee
import org.valiktor.validate
import java.util.*

private object CalendarFunctionsFixture {

    data class Employee(val dateOfBirth: Calendar? = null)
}

class CalendarFunctionsTest {

    private companion object {
        val NOW = System.currentTimeMillis()
        const val ONE_DAY = 1000L * 60L * 60L * 24L
    }

    private fun calendarFrom(millis: Long): Calendar {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal
    }

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isNull()
        })
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(dateOfBirth = Calendar.getInstance()), {
            validate(Employee::dateOfBirth).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::dateOfBirth).isNotNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isEqualTo(Calendar.getInstance())
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isEqualTo(calendarFrom(NOW))
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isEqualTo(calendarFrom(NOW - 1))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = Equals(calendarFrom(NOW - 1))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isNotEqualTo(Calendar.getInstance())
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isNotEqualTo(calendarFrom(NOW - 1))
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isNotEqualTo(calendarFrom(NOW))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = NotEquals(calendarFrom(NOW))))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isIn(Calendar.getInstance(), Calendar.getInstance())
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isIn(calendarFrom(NOW), calendarFrom(NOW - 1))
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isIn(calendarFrom(NOW - 1), calendarFrom(NOW - 2))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = In(setOf(calendarFrom(NOW - 1), calendarFrom(NOW - 2)))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isIn(listOf(Calendar.getInstance(), Calendar.getInstance()))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isIn(listOf(calendarFrom(NOW), calendarFrom(NOW - 1)))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isIn(listOf(calendarFrom(NOW - 1), calendarFrom(NOW - 2)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = In(listOf(calendarFrom(NOW - 1), calendarFrom(NOW - 2)))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isNotIn(Calendar.getInstance(), Calendar.getInstance())
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isNotIn(calendarFrom(NOW - 1), calendarFrom(NOW + 1))
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isNotIn(calendarFrom(NOW), calendarFrom(NOW + 1))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = NotIn(setOf(calendarFrom(NOW), calendarFrom(NOW + 1)))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isNotIn(listOf(Calendar.getInstance(), Calendar.getInstance()))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isNotIn(listOf(calendarFrom(NOW - 1), calendarFrom(NOW + 1)))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isNotIn(listOf(calendarFrom(NOW), calendarFrom(NOW + 1)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW), constraint = NotIn(listOf(calendarFrom(NOW), calendarFrom(NOW + 1)))))
    }

    @Test
    fun `isToday with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isToday()
        })
    }

    @Test
    fun `isToday with 00h00m00 should be valid`() {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        validate(Employee(dateOfBirth = today), {
            validate(Employee::dateOfBirth).isToday()
        })
    }

    @Test
    fun `isToday with 23h59m59 should be valid`() {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 23)
        today.set(Calendar.MINUTE, 59)
        today.set(Calendar.SECOND, 59)
        today.set(Calendar.MILLISECOND, 999)

        validate(Employee(dateOfBirth = today), {
            validate(Employee::dateOfBirth).isToday()
        })
    }

    @Test
    fun `isToday with now should be valid`() {
        validate(Employee(dateOfBirth = Calendar.getInstance()), {
            validate(Employee::dateOfBirth).isToday()
        })
    }

    @Test
    fun `isToday with yesterday value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW - ONE_DAY)), {
                validate(Employee::dateOfBirth).isToday()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW - ONE_DAY), constraint = Today))
    }

    @Test
    fun `isToday with tomorrow value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW + ONE_DAY)), {
                validate(Employee::dateOfBirth).isToday()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dateOfBirth", value = calendarFrom(NOW + ONE_DAY), constraint = Today))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isLessThan(Calendar.getInstance())
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isLessThan(calendarFrom(NOW + 1))
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isLessThan(calendarFrom(NOW - 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = Less(calendarFrom(NOW - 1))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isLessThan(calendarFrom(NOW))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = Less(calendarFrom(NOW))))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(Calendar.getInstance())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(calendarFrom(NOW + 1))
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isLessThanOrEqualTo(calendarFrom(NOW))
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isLessThanOrEqualTo(calendarFrom(NOW - 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = LessOrEqual(calendarFrom(NOW - 1))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isGreaterThan(Calendar.getInstance())
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isGreaterThan(calendarFrom(NOW - 1))
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isGreaterThan(calendarFrom(NOW + 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = Greater(calendarFrom(NOW + 1))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isGreaterThan(calendarFrom(NOW))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = Greater(calendarFrom(NOW))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(Calendar.getInstance())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(calendarFrom(NOW - 1))
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(calendarFrom(NOW))
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isGreaterThanOrEqualTo(calendarFrom(NOW + 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = GreaterOrEqual(calendarFrom(NOW + 1))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isBetween(start = Calendar.getInstance(), end = Calendar.getInstance())
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW + 1)), {
            validate(Employee::dateOfBirth).isBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW + 1)), {
            validate(Employee::dateOfBirth).isBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 2))
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isBetween(start = calendarFrom(NOW + 1), end = calendarFrom(NOW + 3))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = Between(start = calendarFrom(NOW + 1), end = calendarFrom(NOW + 3))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW + 4)), {
                validate(Employee::dateOfBirth).isBetween(start = calendarFrom(NOW + 1), end = calendarFrom(NOW + 3))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW + 4),
                        constraint = Between(start = calendarFrom(NOW + 1), end = calendarFrom(NOW + 3))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::dateOfBirth).isNotBetween(start = Calendar.getInstance(), end = Calendar.getInstance())
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW)), {
            validate(Employee::dateOfBirth).isNotBetween(start = calendarFrom(NOW + 1), end = calendarFrom(NOW + 2))
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(dateOfBirth = calendarFrom(NOW + 2)), {
            validate(Employee::dateOfBirth).isNotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW)), {
                validate(Employee::dateOfBirth).isNotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW),
                        constraint = NotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW + 1)), {
                validate(Employee::dateOfBirth).isNotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW + 1),
                        constraint = NotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 1))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dateOfBirth = calendarFrom(NOW + 1)), {
                validate(Employee::dateOfBirth).isNotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dateOfBirth",
                        value = calendarFrom(NOW + 1),
                        constraint = NotBetween(start = calendarFrom(NOW), end = calendarFrom(NOW + 2))))
    }
}