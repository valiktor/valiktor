package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.FloatFunctionsFixture.Employee
import org.valiktor.validate

private object FloatFunctionsFixture {

    data class Employee(val salary: Float? = null)
}

class FloatFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNull()
        })
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0f), {
                validate(Employee::salary).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 0f, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(salary = 0f), {
            validate(Employee::salary).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::salary).isNotNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isEqualTo(1f)
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isEqualTo(1f)
        })
    }

    @Test
    fun `isEqualTo with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isEqualTo(1.00f)
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0f), {
                validate(Employee::salary).isEqualTo(1f)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 0f, constraint = Equals(1f)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotEqualTo(1f)
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isNotEqualTo(0f)
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotEqualTo(1f)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = NotEquals(1f)))
    }

    @Test
    fun `isNotEqualTo with same value and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotEqualTo(1.00f)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = NotEquals(1.00f)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isIn(0f, 1f, 10f)
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isIn(0f, 1f, 10f)
        })
    }

    @Test
    fun `isIn vararg with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isIn(0f, 1.00f, 10f)
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isIn(0f, 10f)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = In(setOf(0f, 10f))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isIn(listOf(0f, 1f, 10f))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isIn(listOf(0f, 1f, 10f))
        })
    }

    @Test
    fun `isIn iterable with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isIn(listOf(0f, 1.00f, 10f))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isIn(listOf(0f, 10f))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = In(listOf(0f, 10f))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotIn(0f, 1f, 10f)
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isNotIn(0f, 10f)
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotIn(0f, 1f, 10f)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = NotIn(setOf(0f, 1f, 10f))))
    }

    @Test
    fun `isNotIn vararg with same value and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotIn(0f, 1.00f, 10f)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = NotIn(setOf(0f, 1.00f, 10f))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotIn(listOf(0f, 1f, 10f))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isNotIn(listOf(0f, 10f))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotIn(listOf(0f, 1f, 10f))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = NotIn(listOf(0f, 1f, 10f))))
    }

    @Test
    fun `isNotIn iterable with same value and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotIn(listOf(0f, 1.00f, 10f))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = 1f, constraint = NotIn(listOf(0f, 1.00f, 10f))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = 0f), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero and 2 decimal digits should be valid`() {
        validate(Employee(salary = 0.00f), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1f,
                        constraint = Equals(0f)))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0f), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0f,
                        constraint = NotEquals(0f)))
    }

    @Test
    fun `isNotZero with zero and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.00f), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.00f,
                        constraint = NotEquals(0f)))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = 1f), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one and 2 decimal digits should be valid`() {
        validate(Employee(salary = 1.00f), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0f), {
                validate(Employee::salary).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0f,
                        constraint = Equals(1f)))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = 0f), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1f), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1f,
                        constraint = NotEquals(1f)))
    }

    @Test
    fun `isNotOne with one and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.00f), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.00f,
                        constraint = NotEquals(1f)))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = 1.0f), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0f), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0f,
                        constraint = Greater(0.0f)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 98765.432f.unaryMinus()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 98765.432f.unaryMinus(),
                        constraint = Greater(0.0f)))
    }

    @Test
    fun `isNotPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with zero should be valid`() {
        validate(Employee(salary = 0.0f), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with negative value should be valid`() {
        validate(Employee(salary = 98765.432f.unaryMinus()), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0f), {
                validate(Employee::salary).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0f,
                        constraint = LessOrEqual(0.0f)))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = 1.0f.unaryMinus()), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0f), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0f,
                        constraint = Less(0.0f)))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0f), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0f,
                        constraint = Less(0.0f)))
    }

    @Test
    fun `isNotNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with zero should be valid`() {
        validate(Employee(salary = 0.0f), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with positive value should be valid`() {
        validate(Employee(salary = 1.0f), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 98765.432f.unaryMinus()), {
                validate(Employee::salary).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 98765.432f.unaryMinus(),
                        constraint = GreaterOrEqual(0.0f)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThan(10.0f)
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = 9999.99f), {
            validate(Employee::salary).isLessThan(10000.00f)
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = 0.38576f.unaryMinus()), {
            validate(Employee::salary).isLessThan(0.3f.unaryMinus())
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 50.0f), {
                validate(Employee::salary).isLessThan(49.9f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 50.0f,
                        constraint = Less(49.9f)))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 50.9f.unaryMinus()), {
                validate(Employee::salary).isLessThan(51.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 50.9f.unaryMinus(),
                        constraint = Less(51.0f.unaryMinus())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0f), {
                validate(Employee::salary).isLessThan(0.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0f,
                        constraint = Less(0.0f)))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThanOrEqualTo(10.0f)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = 9999.99f), {
            validate(Employee::salary).isLessThanOrEqualTo(10000.00f)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = 0.38576f.unaryMinus()), {
            validate(Employee::salary).isLessThanOrEqualTo(0.3f.unaryMinus())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0f), {
            validate(Employee::salary).isLessThanOrEqualTo(0.0f)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 56789.19f), {
                validate(Employee::salary).isLessThanOrEqualTo(57.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 56789.19f,
                        constraint = LessOrEqual(57.0f)))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 96.0f.unaryMinus()), {
                validate(Employee::salary).isLessThanOrEqualTo(97.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 96.0f.unaryMinus(),
                        constraint = LessOrEqual(97.0f.unaryMinus())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThan(10.0f)
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = 10.1f), {
            validate(Employee::salary).isGreaterThan(10.0f)
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = 88.88f.unaryMinus()), {
            validate(Employee::salary).isGreaterThan(89.0f.unaryMinus())
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0f), {
                validate(Employee::salary).isGreaterThan(11.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0f,
                        constraint = Greater(11.0f)))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 189.20f.unaryMinus()), {
                validate(Employee::salary).isGreaterThan(180.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 189.20f.unaryMinus(),
                        constraint = Greater(180.0f.unaryMinus())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0f), {
                validate(Employee::salary).isGreaterThan(0.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0f,
                        constraint = Greater(0.0f)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThanOrEqualTo(10.0f)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = 10000.0f), {
            validate(Employee::salary).isGreaterThanOrEqualTo(9999.99f)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = 0.3f.unaryMinus()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.38576f.unaryMinus())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0f), {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.0f)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 57.0f), {
                validate(Employee::salary).isGreaterThanOrEqualTo(56789.19f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 57.0f,
                        constraint = GreaterOrEqual(56789.19f)))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 97.0f.unaryMinus()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(96.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 97.0f.unaryMinus(),
                        constraint = GreaterOrEqual(96.0f.unaryMinus())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isBetween(start = 0.99f, end = 9.99f)
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = 0.0f), {
            validate(Employee::salary).isBetween(start = 0.0f, end = 1.0f)
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = 1.0f), {
            validate(Employee::salary).isBetween(start = 0.0f, end = 1.0f)
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = 0.5f), {
            validate(Employee::salary).isBetween(start = 0.0f, end = 1.0f)
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = 2.0f.unaryMinus()), {
            validate(Employee::salary).isBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = 1.0f.unaryMinus()), {
            validate(Employee::salary).isBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = 1.5f.unaryMinus()), {
            validate(Employee::salary).isBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0f), {
                validate(Employee::salary).isBetween(start = 10.1f, end = 11.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0f,
                        constraint = Between(start = 10.1f, end = 11.0f)))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 12.0f), {
                validate(Employee::salary).isBetween(start = 10.1f, end = 11.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 12.0f,
                        constraint = Between(start = 10.1f, end = 11.0f)))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0f.unaryMinus()), {
                validate(Employee::salary).isBetween(start = 9.9f.unaryMinus(), end = 8.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0f.unaryMinus(),
                        constraint = Between(start = 9.9f.unaryMinus(), end = 8.0f.unaryMinus())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 12.0f.unaryMinus()), {
                validate(Employee::salary).isBetween(start = 13.0f.unaryMinus(), end = 12.9f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 12.0f.unaryMinus(),
                        constraint = Between(start = 13.0f.unaryMinus(), end = 12.9f.unaryMinus())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotBetween(start = 0.99f, end = 9.99f)
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = 10.0f), {
            validate(Employee::salary).isNotBetween(start = 10.1f, end = 11.0f)
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = 12.0f), {
            validate(Employee::salary).isNotBetween(start = 10.1f, end = 11.0f)
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = 10.0f.unaryMinus()), {
            validate(Employee::salary).isNotBetween(start = 9.9f.unaryMinus(), end = 8.0f.unaryMinus())
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = 12.0f.unaryMinus()), {
            validate(Employee::salary).isNotBetween(start = 13.0f.unaryMinus(), end = 12.9f.unaryMinus())
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0f), {
                validate(Employee::salary).isNotBetween(start = 0.0f, end = 1.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0f,
                        constraint = NotBetween(start = 0.0f, end = 1.0f)))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0f), {
                validate(Employee::salary).isNotBetween(start = 0.0f, end = 1.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0f,
                        constraint = NotBetween(start = 0.0f, end = 1.0f)))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 2.0f.unaryMinus()), {
                validate(Employee::salary).isNotBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 2.0f.unaryMinus(),
                        constraint = NotBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0f.unaryMinus()), {
                validate(Employee::salary).isNotBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0f.unaryMinus(),
                        constraint = NotBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.5f), {
                validate(Employee::salary).isNotBetween(start = 0.0f, end = 1.0f)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.5f,
                        constraint = NotBetween(start = 0.0f, end = 1.0f)))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.5f.unaryMinus()), {
                validate(Employee::salary).isNotBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.5f.unaryMinus(),
                        constraint = NotBetween(start = 2.0f.unaryMinus(), end = 1.0f.unaryMinus())))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = 9999.99f), {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = 9999.99f), {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 9999.99f), {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99999.99f.unaryMinus()), {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99999.99f.unaryMinus()), {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99999.99f.unaryMinus()), {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = 9999.99f), {
            validate(Employee::salary).hasIntegerDigits()
        })
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78f), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78f,
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78f), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78f,
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78f), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78f,
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78f.unaryMinus()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78f.unaryMinus(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78f.unaryMinus()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78f.unaryMinus(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78f.unaryMinus()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78f.unaryMinus(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasDecimalDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min value should be valid`() {
        validate(Employee(salary = 99.9999f), {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = 99.9999f), {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 99.9999f), {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99.99999f.unaryMinus()), {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99.99999f.unaryMinus()), {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99.99999f.unaryMinus()), {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = 99.9999f), {
            validate(Employee::salary).hasDecimalDigits()
        })
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536f), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536f,
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536f), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536f,
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536f), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536f,
                        constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536f.unaryMinus()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536f.unaryMinus(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536f.unaryMinus()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536f.unaryMinus(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536f.unaryMinus()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536f.unaryMinus(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }
}