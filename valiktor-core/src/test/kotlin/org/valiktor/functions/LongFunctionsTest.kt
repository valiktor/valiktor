package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.LongFunctionsFixture.Employee
import org.valiktor.validate

private object LongFunctionsFixture {

    data class Employee(val id: Long? = null)
}

class LongFunctionsTest {

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1.toLong()), {
                validate(Employee::id).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1.toLong(),
                        constraint = Equals(0.toLong())))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(id = 1.toLong()), {
            validate(Employee::id).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = NotEquals(0.toLong())))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(id = 1.toLong()), {
            validate(Employee::id).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = Equals(1.toLong())))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1.toLong()), {
                validate(Employee::id).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1.toLong(),
                        constraint = NotEquals(1.toLong())))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(id = 1.toLong()), {
            validate(Employee::id).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = Greater(0.toLong())))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 98765.unaryMinus().toLong()), {
                validate(Employee::id).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 98765.unaryMinus().toLong(),
                        constraint = Greater(0.toLong())))
    }

    @Test
    fun `isNotPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with zero should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with negative value should be valid`() {
        validate(Employee(id = 98765.unaryMinus().toLong()), {
            validate(Employee::id).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1.toLong()), {
                validate(Employee::id).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1.toLong(),
                        constraint = LessOrEqual(0.toLong())))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(id = 1.unaryMinus().toLong()), {
            validate(Employee::id).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = Less(0.toLong())))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1.toLong()), {
                validate(Employee::id).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1.toLong(),
                        constraint = Less(0.toLong())))
    }

    @Test
    fun `isNotNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with zero should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with positive value should be valid`() {
        validate(Employee(id = 1.toLong()), {
            validate(Employee::id).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 98765.unaryMinus().toLong()), {
                validate(Employee::id).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 98765.unaryMinus().toLong(),
                        constraint = GreaterOrEqual(0.toLong())))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isLessThan(10.toLong())
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(id = 9999.toLong()), {
            validate(Employee::id).isLessThan(100000.toLong())
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(id = 4.unaryMinus().toLong()), {
            validate(Employee::id).isLessThan(3.unaryMinus().toLong())
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 50.toLong()), {
                validate(Employee::id).isLessThan(49.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 50.toLong(),
                        constraint = Less(49.toLong())))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 50.unaryMinus().toLong()), {
                validate(Employee::id).isLessThan(51.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 50.unaryMinus().toLong(),
                        constraint = Less(51.unaryMinus().toLong())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isLessThan(0.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = Less(0.toLong())))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isLessThanOrEqualTo(10.toLong())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(id = 9999.toLong()), {
            validate(Employee::id).isLessThanOrEqualTo(100000.toLong())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(id = 4.unaryMinus().toLong()), {
            validate(Employee::id).isLessThanOrEqualTo(3.unaryMinus().toLong())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isLessThanOrEqualTo(0.toLong())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 56789.toLong()), {
                validate(Employee::id).isLessThanOrEqualTo(57.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 56789.toLong(),
                        constraint = LessOrEqual(57.toLong())))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 96.unaryMinus().toLong()), {
                validate(Employee::id).isLessThanOrEqualTo(97.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 96.unaryMinus().toLong(),
                        constraint = LessOrEqual(97.unaryMinus().toLong())))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isGreaterThan(10.toLong())
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(id = 11.toLong()), {
            validate(Employee::id).isGreaterThan(10.toLong())
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(id = 88.unaryMinus().toLong()), {
            validate(Employee::id).isGreaterThan(89.unaryMinus().toLong())
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 10.toLong()), {
                validate(Employee::id).isGreaterThan(11.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 10.toLong(),
                        constraint = Greater(11.toLong())))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 189.unaryMinus().toLong()), {
                validate(Employee::id).isGreaterThan(180.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 189.unaryMinus().toLong(),
                        constraint = Greater(180.unaryMinus().toLong())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isGreaterThan(0.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = Greater(0.toLong())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isGreaterThanOrEqualTo(10.toLong())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(id = 10000.toLong()), {
            validate(Employee::id).isGreaterThanOrEqualTo(9999.toLong())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(id = 3.unaryMinus().toLong()), {
            validate(Employee::id).isGreaterThanOrEqualTo(4.unaryMinus().toLong())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isGreaterThanOrEqualTo(0.toLong())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 57.toLong()), {
                validate(Employee::id).isGreaterThanOrEqualTo(56789.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 57.toLong(),
                        constraint = GreaterOrEqual(56789.toLong())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 97.unaryMinus().toLong()), {
                validate(Employee::id).isGreaterThanOrEqualTo(96.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 97.unaryMinus().toLong(),
                        constraint = GreaterOrEqual(96.unaryMinus().toLong())))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isBetween(start = 1.toLong(), end = 9.toLong())
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(id = 0.toLong()), {
            validate(Employee::id).isBetween(start = 0.toLong(), end = 1.toLong())
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(id = 1.toLong()), {
            validate(Employee::id).isBetween(start = 0.toLong(), end = 1.toLong())
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(id = 5.toLong()), {
            validate(Employee::id).isBetween(start = 0.toLong(), end = 10.toLong())
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(id = 2.unaryMinus().toLong()), {
            validate(Employee::id).isBetween(start = 2.unaryMinus().toLong(), end = 1.unaryMinus().toLong())
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(id = 1.unaryMinus().toLong()), {
            validate(Employee::id).isBetween(start = 2.unaryMinus().toLong(), end = 1.unaryMinus().toLong())
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(id = 15.unaryMinus().toLong()), {
            validate(Employee::id).isBetween(start = 20.unaryMinus().toLong(), end = 10.unaryMinus().toLong())
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 10.toLong()), {
                validate(Employee::id).isBetween(start = 11.toLong(), end = 12.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 10.toLong(),
                        constraint = Between(start = 11.toLong(), end = 12.toLong())))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 12.toLong()), {
                validate(Employee::id).isBetween(start = 10.toLong(), end = 11.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 12.toLong(),
                        constraint = Between(start = 10.toLong(), end = 11.toLong())))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 10.unaryMinus().toLong()), {
                validate(Employee::id).isBetween(start = 9.unaryMinus().toLong(), end = 8.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 10.unaryMinus().toLong(),
                        constraint = Between(start = 9.unaryMinus().toLong(), end = 8.unaryMinus().toLong())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 12.unaryMinus().toLong()), {
                validate(Employee::id).isBetween(start = 14.unaryMinus().toLong(), end = 13.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 12.unaryMinus().toLong(),
                        constraint = Between(start = 14.unaryMinus().toLong(), end = 13.unaryMinus().toLong())))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotBetween(start = 1.toLong(), end = 9.toLong())
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(id = 10.toLong()), {
            validate(Employee::id).isNotBetween(start = 11.toLong(), end = 12.toLong())
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(id = 12.toLong()), {
            validate(Employee::id).isNotBetween(start = 10.toLong(), end = 11.toLong())
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(id = 10.unaryMinus().toLong()), {
            validate(Employee::id).isNotBetween(start = 9.unaryMinus().toLong(), end = 8.unaryMinus().toLong())
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(id = 12.unaryMinus().toLong()), {
            validate(Employee::id).isNotBetween(start = 14.unaryMinus().toLong(), end = 13.unaryMinus().toLong())
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0.toLong()), {
                validate(Employee::id).isNotBetween(start = 0.toLong(), end = 1.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0.toLong(),
                        constraint = NotBetween(start = 0.toLong(), end = 1.toLong())))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1.toLong()), {
                validate(Employee::id).isNotBetween(start = 0.toLong(), end = 1.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1.toLong(),
                        constraint = NotBetween(start = 0.toLong(), end = 1.toLong())))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 2.unaryMinus().toLong()), {
                validate(Employee::id).isNotBetween(start = 2.unaryMinus().toLong(), end = 1.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 2.unaryMinus().toLong(),
                        constraint = NotBetween(start = 2.unaryMinus().toLong(), end = 1.unaryMinus().toLong())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1.unaryMinus().toLong()), {
                validate(Employee::id).isNotBetween(start = 2.unaryMinus().toLong(), end = 1.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1.unaryMinus().toLong(),
                        constraint = NotBetween(start = 2.unaryMinus().toLong(), end = 1.unaryMinus().toLong())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 5.toLong()), {
                validate(Employee::id).isNotBetween(start = 0.toLong(), end = 10.toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 5.toLong(),
                        constraint = NotBetween(start = 0.toLong(), end = 10.toLong())))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 15.unaryMinus().toLong()), {
                validate(Employee::id).isNotBetween(start = 20.unaryMinus().toLong(), end = 10.unaryMinus().toLong())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 15.unaryMinus().toLong(),
                        constraint = NotBetween(start = 20.unaryMinus().toLong(), end = 10.unaryMinus().toLong())))
    }

    @Test
    fun `hasDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::id).hasDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDigits with valid min value should be valid`() {
        validate(Employee(id = 9999.toLong()), {
            validate(Employee::id).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with valid max value should be valid`() {
        validate(Employee(id = 9999.toLong()), {
            validate(Employee::id).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with valid min and max value should be valid`() {
        validate(Employee(id = 9999.toLong()), {
            validate(Employee::id).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid min value should be valid`() {
        validate(Employee(id = 999999.unaryMinus().toLong()), {
            validate(Employee::id).hasDigits(min = 6)
        })
    }

    @Test
    fun `hasDigits with negative valid max value should be valid`() {
        validate(Employee(id = 999999.unaryMinus().toLong()), {
            validate(Employee::id).hasDigits(max = 6)
        })
    }

    @Test
    fun `hasDigits with negative valid min and max value should be valid`() {
        validate(Employee(id = 999999.unaryMinus().toLong()), {
            validate(Employee::id).hasDigits(min = 6, max = 6)
        })
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(id = 9999.toLong()), {
            validate(Employee::id).hasDigits()
        })
    }

    @Test
    fun `hasDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 748536.toLong()), {
                validate(Employee::id).hasDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536.toLong(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 748536.toLong()), {
                validate(Employee::id).hasDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536.toLong(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 748536.toLong()), {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536.toLong(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 748536.unaryMinus().toLong()), {
                validate(Employee::id).hasDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536.unaryMinus().toLong(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 748536.unaryMinus().toLong()), {
                validate(Employee::id).hasDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536.unaryMinus().toLong(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 748536.unaryMinus().toLong()), {
                validate(Employee::id).hasDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 748536.unaryMinus().toLong(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }
}