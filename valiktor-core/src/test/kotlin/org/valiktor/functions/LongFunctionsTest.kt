package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.LongFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object LongFunctionsFixture {

    data class Employee(val id: Long? = null)
}

class LongFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNull()
        })
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1L, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::id).isNotNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isEqualTo(1L)
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isEqualTo(1L)
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 2L), {
                validate(Employee::id).isEqualTo(1L)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 2L, constraint = Equals(1L)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotEqualTo(1L)
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(id = 2L), {
            validate(Employee::id).isNotEqualTo(1L)
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNotEqualTo(1L)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1L, constraint = NotEquals(1L)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isIn(1L, 2L, 3L)
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(id = 2L), {
            validate(Employee::id).isIn(1L, 2L, 3L)
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isIn(0L, 2L, 3L)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1L, constraint = In(setOf(0L, 2L, 3L))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isIn(listOf(1L, 2L, 3L))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(id = 2L), {
            validate(Employee::id).isIn(listOf(1L, 2L, 3L))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isIn(listOf(0L, 2L, 3L))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1L, constraint = In(listOf(0L, 2L, 3L))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotIn(0L, 2L, 3L)
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isNotIn(0L, 2L, 3L)
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNotIn(1L, 2L, 3L)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1L, constraint = NotIn(setOf(1L, 2L, 3L))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotIn(listOf(0L, 2L, 3L))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isNotIn(listOf(0L, 2L, 3L))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNotIn(listOf(1L, 2L, 3L))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1L, constraint = NotIn(listOf(1L, 2L, 3L))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1L,
                        constraint = Equals(0L)))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = NotEquals(0L)))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = Equals(1L)))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1L,
                        constraint = NotEquals(1L)))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = Greater(0L)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 98765L.unaryMinus()), {
                validate(Employee::id).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 98765L.unaryMinus(),
                        constraint = Greater(0L)))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(id = 98765L.unaryMinus()), {
            validate(Employee::id).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNegativeOrZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1L,
                        constraint = LessOrEqual(0L)))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(id = 1L.unaryMinus()), {
            validate(Employee::id).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = Less(0L)))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1L,
                        constraint = Less(0L)))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 98765L.unaryMinus()), {
                validate(Employee::id).isPositiveOrZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 98765L.unaryMinus(),
                        constraint = GreaterOrEqual(0L)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isLessThan(10L)
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(id = 9999L), {
            validate(Employee::id).isLessThan(100000L)
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(id = 4L.unaryMinus()), {
            validate(Employee::id).isLessThan(3L.unaryMinus())
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 50L), {
                validate(Employee::id).isLessThan(49L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 50L,
                        constraint = Less(49L)))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 50L.unaryMinus()), {
                validate(Employee::id).isLessThan(51L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 50L.unaryMinus(),
                        constraint = Less(51L.unaryMinus())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isLessThan(0L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = Less(0L)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isLessThanOrEqualTo(10L)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(id = 9999L), {
            validate(Employee::id).isLessThanOrEqualTo(100000L)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(id = 4L.unaryMinus()), {
            validate(Employee::id).isLessThanOrEqualTo(3L.unaryMinus())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isLessThanOrEqualTo(0L)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 56789L), {
                validate(Employee::id).isLessThanOrEqualTo(57L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 56789L,
                        constraint = LessOrEqual(57L)))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 96L.unaryMinus()), {
                validate(Employee::id).isLessThanOrEqualTo(97L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 96L.unaryMinus(),
                        constraint = LessOrEqual(97L.unaryMinus())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isGreaterThan(10L)
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(id = 11L), {
            validate(Employee::id).isGreaterThan(10L)
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(id = 88L.unaryMinus()), {
            validate(Employee::id).isGreaterThan(89L.unaryMinus())
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10L), {
                validate(Employee::id).isGreaterThan(11L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 10L,
                        constraint = Greater(11L)))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 189L.unaryMinus()), {
                validate(Employee::id).isGreaterThan(180L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 189L.unaryMinus(),
                        constraint = Greater(180L.unaryMinus())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isGreaterThan(0L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = Greater(0L)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isGreaterThanOrEqualTo(10L)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(id = 10000L), {
            validate(Employee::id).isGreaterThanOrEqualTo(9999L)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(id = 3L.unaryMinus()), {
            validate(Employee::id).isGreaterThanOrEqualTo(4L.unaryMinus())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isGreaterThanOrEqualTo(0L)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 57L), {
                validate(Employee::id).isGreaterThanOrEqualTo(56789L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 57L,
                        constraint = GreaterOrEqual(56789L)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 97L.unaryMinus()), {
                validate(Employee::id).isGreaterThanOrEqualTo(96L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 97L.unaryMinus(),
                        constraint = GreaterOrEqual(96L.unaryMinus())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isBetween(start = 1L, end = 9L)
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(id = 0L), {
            validate(Employee::id).isBetween(start = 0L, end = 1L)
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(id = 1L), {
            validate(Employee::id).isBetween(start = 0L, end = 1L)
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(id = 5L), {
            validate(Employee::id).isBetween(start = 0L, end = 10L)
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(id = 2L.unaryMinus()), {
            validate(Employee::id).isBetween(start = 2L.unaryMinus(), end = 1L.unaryMinus())
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(id = 1L.unaryMinus()), {
            validate(Employee::id).isBetween(start = 2L.unaryMinus(), end = 1L.unaryMinus())
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(id = 15L.unaryMinus()), {
            validate(Employee::id).isBetween(start = 20L.unaryMinus(), end = 10L.unaryMinus())
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10L), {
                validate(Employee::id).isBetween(start = 11L, end = 12L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 10L,
                        constraint = Between(start = 11L, end = 12L)))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 12L), {
                validate(Employee::id).isBetween(start = 10L, end = 11L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 12L,
                        constraint = Between(start = 10L, end = 11L)))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 10L.unaryMinus()), {
                validate(Employee::id).isBetween(start = 9L.unaryMinus(), end = 8L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 10L.unaryMinus(),
                        constraint = Between(start = 9L.unaryMinus(), end = 8L.unaryMinus())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 12L.unaryMinus()), {
                validate(Employee::id).isBetween(start = 14L.unaryMinus(), end = 13L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 12L.unaryMinus(),
                        constraint = Between(start = 14L.unaryMinus(), end = 13L.unaryMinus())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotBetween(start = 1L, end = 9L)
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(id = 10L), {
            validate(Employee::id).isNotBetween(start = 11L, end = 12L)
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(id = 12L), {
            validate(Employee::id).isNotBetween(start = 10L, end = 11L)
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(id = 10L.unaryMinus()), {
            validate(Employee::id).isNotBetween(start = 9L.unaryMinus(), end = 8L.unaryMinus())
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(id = 12L.unaryMinus()), {
            validate(Employee::id).isNotBetween(start = 14L.unaryMinus(), end = 13L.unaryMinus())
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 0L), {
                validate(Employee::id).isNotBetween(start = 0L, end = 1L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0L,
                        constraint = NotBetween(start = 0L, end = 1L)))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L), {
                validate(Employee::id).isNotBetween(start = 0L, end = 1L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1L,
                        constraint = NotBetween(start = 0L, end = 1L)))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 2L.unaryMinus()), {
                validate(Employee::id).isNotBetween(start = 2L.unaryMinus(), end = 1L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 2L.unaryMinus(),
                        constraint = NotBetween(start = 2L.unaryMinus(), end = 1L.unaryMinus())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 1L.unaryMinus()), {
                validate(Employee::id).isNotBetween(start = 2L.unaryMinus(), end = 1L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1L.unaryMinus(),
                        constraint = NotBetween(start = 2L.unaryMinus(), end = 1L.unaryMinus())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 5L), {
                validate(Employee::id).isNotBetween(start = 0L, end = 10L)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 5L,
                        constraint = NotBetween(start = 0L, end = 10L)))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 15L.unaryMinus()), {
                validate(Employee::id).isNotBetween(start = 20L.unaryMinus(), end = 10L.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 15L.unaryMinus(),
                        constraint = NotBetween(start = 20L.unaryMinus(), end = 10L.unaryMinus())))
    }

    @Test
    fun `hasDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).hasDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDigits with valid min value should be valid`() {
        validate(Employee(id = 9999L), {
            validate(Employee::id).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with valid max value should be valid`() {
        validate(Employee(id = 9999L), {
            validate(Employee::id).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with valid min and max value should be valid`() {
        validate(Employee(id = 9999L), {
            validate(Employee::id).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid min value should be valid`() {
        validate(Employee(id = 999999L.unaryMinus()), {
            validate(Employee::id).hasDigits(min = 6)
        })
    }

    @Test
    fun `hasDigits with negative valid max value should be valid`() {
        validate(Employee(id = 999999L.unaryMinus()), {
            validate(Employee::id).hasDigits(max = 6)
        })
    }

    @Test
    fun `hasDigits with negative valid min and max value should be valid`() {
        validate(Employee(id = 999999L.unaryMinus()), {
            validate(Employee::id).hasDigits(min = 6, max = 6)
        })
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(id = 9999L), {
            validate(Employee::id).hasDigits()
        })
    }

    @Test
    fun `hasDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536L), {
                validate(Employee::id).hasDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536L,
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536L), {
                validate(Employee::id).hasDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536L,
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536L), {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536L,
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536L.unaryMinus()), {
                validate(Employee::id).hasDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536L.unaryMinus(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536L.unaryMinus()), {
                validate(Employee::id).hasDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536L.unaryMinus(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(id = 748536L.unaryMinus()), {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536L.unaryMinus(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }
}