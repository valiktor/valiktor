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
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toFloat()), {
                validate(Employee::salary).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toFloat(),
                        constraint = Equals(0.0.toFloat())))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = 1.0.toFloat()), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = NotEquals(0.0.toFloat())))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = 1.0.toFloat()), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = Equals(1.0.toFloat())))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toFloat()), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toFloat(),
                        constraint = NotEquals(1.0.toFloat())))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = 1.0.toFloat()), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = Greater(0.0.toFloat())))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 98765.432.unaryMinus().toFloat()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 98765.432.unaryMinus().toFloat(),
                        constraint = Greater(0.0.toFloat())))
    }

    @Test
    fun `isNotPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with zero should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with negative value should be valid`() {
        validate(Employee(salary = 98765.432.unaryMinus().toFloat()), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toFloat()), {
                validate(Employee::salary).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toFloat(),
                        constraint = LessOrEqual(0.0.toFloat())))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = 1.0.unaryMinus().toFloat()), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = Less(0.0.toFloat())))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toFloat()), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toFloat(),
                        constraint = Less(0.0.toFloat())))
    }

    @Test
    fun `isNotNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with zero should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with positive value should be valid`() {
        validate(Employee(salary = 1.0.toFloat()), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 98765.432.unaryMinus().toFloat()), {
                validate(Employee::salary).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 98765.432.unaryMinus().toFloat(),
                        constraint = GreaterOrEqual(0.0.toFloat())))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThan(10.0.toFloat())
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = 9999.99.toFloat()), {
            validate(Employee::salary).isLessThan(10000.00.toFloat())
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = 0.38576.unaryMinus().toFloat()), {
            validate(Employee::salary).isLessThan(0.3.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 50.0.toFloat()), {
                validate(Employee::salary).isLessThan(49.9.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 50.0.toFloat(),
                        constraint = Less(49.9.toFloat())))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 50.9.unaryMinus().toFloat()), {
                validate(Employee::salary).isLessThan(51.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 50.9.unaryMinus().toFloat(),
                        constraint = Less(51.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isLessThan(0.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = Less(0.0.toFloat())))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThanOrEqualTo(10.0.toFloat())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = 9999.99.toFloat()), {
            validate(Employee::salary).isLessThanOrEqualTo(10000.00.toFloat())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = 0.38576.unaryMinus().toFloat()), {
            validate(Employee::salary).isLessThanOrEqualTo(0.3.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isLessThanOrEqualTo(0.0.toFloat())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 56789.19.toFloat()), {
                validate(Employee::salary).isLessThanOrEqualTo(57.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 56789.19.toFloat(),
                        constraint = LessOrEqual(57.0.toFloat())))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 96.0.unaryMinus().toFloat()), {
                validate(Employee::salary).isLessThanOrEqualTo(97.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 96.0.unaryMinus().toFloat(),
                        constraint = LessOrEqual(97.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThan(10.0.toFloat())
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = 10.1.toFloat()), {
            validate(Employee::salary).isGreaterThan(10.0.toFloat())
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = 88.88.unaryMinus().toFloat()), {
            validate(Employee::salary).isGreaterThan(89.0.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0.toFloat()), {
                validate(Employee::salary).isGreaterThan(11.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0.toFloat(),
                        constraint = Greater(11.0.toFloat())))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 189.20.unaryMinus().toFloat()), {
                validate(Employee::salary).isGreaterThan(180.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 189.20.unaryMinus().toFloat(),
                        constraint = Greater(180.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isGreaterThan(0.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = Greater(0.0.toFloat())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThanOrEqualTo(10.0.toFloat())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = 10000.0.toFloat()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(9999.99.toFloat())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = 0.3.unaryMinus().toFloat()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.38576.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.0.toFloat())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 57.0.toFloat()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(56789.19.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 57.0.toFloat(),
                        constraint = GreaterOrEqual(56789.19.toFloat())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 97.0.unaryMinus().toFloat()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(96.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 97.0.unaryMinus().toFloat(),
                        constraint = GreaterOrEqual(96.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isBetween(start = 0.99.toFloat(), end = 9.99.toFloat())
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = 0.0.toFloat()), {
            validate(Employee::salary).isBetween(start = 0.0.toFloat(), end = 1.0.toFloat())
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = 1.0.toFloat()), {
            validate(Employee::salary).isBetween(start = 0.0.toFloat(), end = 1.0.toFloat())
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = 0.5.toFloat()), {
            validate(Employee::salary).isBetween(start = 0.0.toFloat(), end = 1.0.toFloat())
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = 2.0.unaryMinus().toFloat()), {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = 1.0.unaryMinus().toFloat()), {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = 1.5.unaryMinus().toFloat()), {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0.toFloat()), {
                validate(Employee::salary).isBetween(start = 10.1.toFloat(), end = 11.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0.toFloat(),
                        constraint = Between(start = 10.1.toFloat(), end = 11.0.toFloat())))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 12.0.toFloat()), {
                validate(Employee::salary).isBetween(start = 10.1.toFloat(), end = 11.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 12.0.toFloat(),
                        constraint = Between(start = 10.1.toFloat(), end = 11.0.toFloat())))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0.unaryMinus().toFloat()), {
                validate(Employee::salary).isBetween(start = 9.9.unaryMinus().toFloat(), end = 8.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0.unaryMinus().toFloat(),
                        constraint = Between(start = 9.9.unaryMinus().toFloat(), end = 8.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 12.0.unaryMinus().toFloat()), {
                validate(Employee::salary).isBetween(start = 13.0.unaryMinus().toFloat(), end = 12.9.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 12.0.unaryMinus().toFloat(),
                        constraint = Between(start = 13.0.unaryMinus().toFloat(), end = 12.9.unaryMinus().toFloat())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotBetween(start = 0.99.toFloat(), end = 9.99.toFloat())
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = 10.0.toFloat()), {
            validate(Employee::salary).isNotBetween(start = 10.1.toFloat(), end = 11.0.toFloat())
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = 12.0.toFloat()), {
            validate(Employee::salary).isNotBetween(start = 10.1.toFloat(), end = 11.0.toFloat())
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = 10.0.unaryMinus().toFloat()), {
            validate(Employee::salary).isNotBetween(start = 9.9.unaryMinus().toFloat(), end = 8.0.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = 12.0.unaryMinus().toFloat()), {
            validate(Employee::salary).isNotBetween(start = 13.0.unaryMinus().toFloat(), end = 12.9.unaryMinus().toFloat())
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toFloat()), {
                validate(Employee::salary).isNotBetween(start = 0.0.toFloat(), end = 1.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toFloat(),
                        constraint = NotBetween(start = 0.0.toFloat(), end = 1.0.toFloat())))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toFloat()), {
                validate(Employee::salary).isNotBetween(start = 0.0.toFloat(), end = 1.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toFloat(),
                        constraint = NotBetween(start = 0.0.toFloat(), end = 1.0.toFloat())))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 2.0.unaryMinus().toFloat()), {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 2.0.unaryMinus().toFloat(),
                        constraint = NotBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.unaryMinus().toFloat()), {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.unaryMinus().toFloat(),
                        constraint = NotBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.5.toFloat()), {
                validate(Employee::salary).isNotBetween(start = 0.0.toFloat(), end = 1.0.toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.5.toFloat(),
                        constraint = NotBetween(start = 0.0.toFloat(), end = 1.0.toFloat())))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.5.unaryMinus().toFloat()), {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.5.unaryMinus().toFloat(),
                        constraint = NotBetween(start = 2.0.unaryMinus().toFloat(), end = 1.0.unaryMinus().toFloat())))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = 9999.99.toFloat()), {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = 9999.99.toFloat()), {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 9999.99.toFloat()), {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toFloat()), {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toFloat()), {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toFloat()), {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = 9999.99.toFloat()), {
            validate(Employee::salary).hasIntegerDigits()
        })
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toFloat()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.toFloat(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toFloat()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.toFloat(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toFloat()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.toFloat(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toFloat()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.unaryMinus().toFloat(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toFloat()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.unaryMinus().toFloat(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toFloat()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.unaryMinus().toFloat(),
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
        validate(Employee(salary = 99.9999.toFloat()), {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = 99.9999.toFloat()), {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 99.9999.toFloat()), {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toFloat()), {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toFloat()), {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toFloat()), {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = 99.9999.toFloat()), {
            validate(Employee::salary).hasDecimalDigits()
        })
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toFloat()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.toFloat(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toFloat()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.toFloat(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toFloat()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.toFloat(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toFloat()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.unaryMinus().toFloat(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toFloat()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.unaryMinus().toFloat(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toFloat()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.unaryMinus().toFloat(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }
}