package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.BigDecimalFunctionsFixture.Employee
import org.valiktor.validate
import java.math.BigDecimal

private object BigDecimalFunctionsFixture {

    data class Employee(val salary: BigDecimal? = null)
}

class BigDecimalFunctionsTest {

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toBigDecimal()), {
                validate(Employee::salary).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toBigDecimal(),
                        constraint = Equals(BigDecimal.ZERO)))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = 1.0.toBigDecimal()), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = NotEquals(BigDecimal.ZERO)))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = 1.0.toBigDecimal()), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = Equals(BigDecimal.ONE)))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toBigDecimal()), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toBigDecimal(),
                        constraint = NotEquals(BigDecimal.ONE)))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = 1.0.toBigDecimal()), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = Greater(BigDecimal.ZERO)))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 98765.432.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 98765.432.unaryMinus().toBigDecimal(),
                        constraint = Greater(BigDecimal.ZERO)))
    }

    @Test
    fun `isNotPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with zero should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with negative value should be valid`() {
        validate(Employee(salary = 98765.432.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toBigDecimal()), {
                validate(Employee::salary).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toBigDecimal(),
                        constraint = LessOrEqual(BigDecimal.ZERO)))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = 1.0.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = Less(BigDecimal.ZERO)))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toBigDecimal()), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toBigDecimal(),
                        constraint = Less(BigDecimal.ZERO)))
    }

    @Test
    fun `isNotNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with zero should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with positive value should be valid`() {
        validate(Employee(salary = 1.0.toBigDecimal()), {
            validate(Employee::salary).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 98765.432.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 98765.432.unaryMinus().toBigDecimal(),
                        constraint = GreaterOrEqual(BigDecimal.ZERO)))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThan(10.0.toBigDecimal())
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal()), {
            validate(Employee::salary).isLessThan(10000.00.toBigDecimal())
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = 0.38576.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isLessThan(0.3.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 50.0.toBigDecimal()), {
                validate(Employee::salary).isLessThan(49.9.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 50.0.toBigDecimal(),
                        constraint = Less(49.9.toBigDecimal())))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 50.9.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isLessThan(51.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 50.9.unaryMinus().toBigDecimal(),
                        constraint = Less(51.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isLessThan(0.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = Less(0.0.toBigDecimal())))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThanOrEqualTo(10.0.toBigDecimal())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal()), {
            validate(Employee::salary).isLessThanOrEqualTo(10000.00.toBigDecimal())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = 0.38576.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isLessThanOrEqualTo(0.3.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isLessThanOrEqualTo(0.0.toBigDecimal())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 56789.19.toBigDecimal()), {
                validate(Employee::salary).isLessThanOrEqualTo(57.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 56789.19.toBigDecimal(),
                        constraint = LessOrEqual(57.0.toBigDecimal())))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 96.0.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isLessThanOrEqualTo(97.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 96.0.unaryMinus().toBigDecimal(),
                        constraint = LessOrEqual(97.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThan(10.0.toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = 10.1.toBigDecimal()), {
            validate(Employee::salary).isGreaterThan(10.0.toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = 88.88.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isGreaterThan(89.0.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0.toBigDecimal()), {
                validate(Employee::salary).isGreaterThan(11.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0.toBigDecimal(),
                        constraint = Greater(11.0.toBigDecimal())))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 189.20.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isGreaterThan(180.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 189.20.unaryMinus().toBigDecimal(),
                        constraint = Greater(180.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isGreaterThan(0.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = Greater(0.0.toBigDecimal())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThanOrEqualTo(10.0.toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = 10000.0.toBigDecimal()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(9999.99.toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = 0.3.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.38576.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(0.0.toBigDecimal())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 57.0.toBigDecimal()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(56789.19.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 57.0.toBigDecimal(),
                        constraint = GreaterOrEqual(56789.19.toBigDecimal())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 97.0.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(96.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 97.0.unaryMinus().toBigDecimal(),
                        constraint = GreaterOrEqual(96.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isBetween(start = 0.99.toBigDecimal(), end = 9.99.toBigDecimal())
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = 0.0.toBigDecimal()), {
            validate(Employee::salary).isBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = 1.0.toBigDecimal()), {
            validate(Employee::salary).isBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = 0.5.toBigDecimal()), {
            validate(Employee::salary).isBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = 2.0.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = 1.0.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = 1.5.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0.toBigDecimal()), {
                validate(Employee::salary).isBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0.toBigDecimal(),
                        constraint = Between(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 12.0.toBigDecimal()), {
                validate(Employee::salary).isBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 12.0.toBigDecimal(),
                        constraint = Between(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 10.0.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isBetween(start = 9.9.unaryMinus().toBigDecimal(), end = 8.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 10.0.unaryMinus().toBigDecimal(),
                        constraint = Between(start = 9.9.unaryMinus().toBigDecimal(), end = 8.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 12.0.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isBetween(start = 13.0.unaryMinus().toBigDecimal(), end = 12.9.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 12.0.unaryMinus().toBigDecimal(),
                        constraint = Between(start = 13.0.unaryMinus().toBigDecimal(), end = 12.9.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotBetween(start = 0.99.toBigDecimal(), end = 9.99.toBigDecimal())
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = 10.0.toBigDecimal()), {
            validate(Employee::salary).isNotBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = 12.0.toBigDecimal()), {
            validate(Employee::salary).isNotBetween(start = 10.1.toBigDecimal(), end = 11.0.toBigDecimal())
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = 10.0.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isNotBetween(start = 9.9.unaryMinus().toBigDecimal(), end = 8.0.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = 12.0.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).isNotBetween(start = 13.0.unaryMinus().toBigDecimal(), end = 12.9.unaryMinus().toBigDecimal())
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.0.toBigDecimal()), {
                validate(Employee::salary).isNotBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.0.toBigDecimal(),
                        constraint = NotBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.toBigDecimal()), {
                validate(Employee::salary).isNotBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.toBigDecimal(),
                        constraint = NotBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 2.0.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 2.0.unaryMinus().toBigDecimal(),
                        constraint = NotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.0.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.0.unaryMinus().toBigDecimal(),
                        constraint = NotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 0.5.toBigDecimal()), {
                validate(Employee::salary).isNotBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 0.5.toBigDecimal(),
                        constraint = NotBetween(start = 0.0.toBigDecimal(), end = 1.0.toBigDecimal())))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 1.5.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).isNotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 1.5.unaryMinus().toBigDecimal(),
                        constraint = NotBetween(start = 2.0.unaryMinus().toBigDecimal(), end = 1.0.unaryMinus().toBigDecimal())))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99999.99.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = 9999.99.toBigDecimal()), {
            validate(Employee::salary).hasIntegerDigits()
        })
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toBigDecimal()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.toBigDecimal(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toBigDecimal()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.toBigDecimal(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.toBigDecimal()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.toBigDecimal(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.unaryMinus().toBigDecimal(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.unaryMinus().toBigDecimal(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 748536.78.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 748536.78.unaryMinus().toBigDecimal(),
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
        validate(Employee(salary = 99.9999.toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = 99.9999.toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = 99.9999.toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = 99.99999.unaryMinus().toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = 99.9999.toBigDecimal()), {
            validate(Employee::salary).hasDecimalDigits()
        })
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toBigDecimal()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.toBigDecimal(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toBigDecimal()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.toBigDecimal(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.toBigDecimal()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.toBigDecimal(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.unaryMinus().toBigDecimal(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.unaryMinus().toBigDecimal(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = 78.748536.unaryMinus().toBigDecimal()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = 78.748536.unaryMinus().toBigDecimal(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }
}