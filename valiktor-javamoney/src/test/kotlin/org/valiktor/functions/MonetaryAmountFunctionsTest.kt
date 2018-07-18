package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.javamoney.moneta.Money
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.MonetaryAmountFunctionsFixture.Employee
import org.valiktor.validate
import java.math.BigDecimal.*
import javax.money.Monetary
import javax.money.MonetaryAmount

private object MonetaryAmountFunctionsFixture {

    data class Employee(val salary: MonetaryAmount? = null)
}

private val REAL = Monetary.getCurrency("BRL")
private val DOLLAR = Monetary.getCurrency("USD")

class MonetaryAmountFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNull()
        })
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(1000, REAL)), {
                validate(Employee::salary).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(1000, REAL), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(salary = Money.of(ZERO, REAL)), {
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
            validate(Employee::salary).isEqualTo(Money.of(ONE, REAL))
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isEqualTo(Money.of(ONE, DOLLAR))
        })
    }

    @Test
    fun `isEqualTo with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isEqualTo(Money.of(1.00.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isEqualTo with same value and different type should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isEqualTo(Money.of(1, REAL))
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).isEqualTo(Money.of(ONE, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ZERO, DOLLAR), constraint = Equals(Money.of(ONE, DOLLAR))))
    }

    @Test
    fun `isEqualTo with different currency should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).isEqualTo(Money.of(ZERO, REAL))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ZERO, DOLLAR), constraint = Equals(Money.of(ZERO, REAL))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotEqualTo(Money.of(ONE, REAL))
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isNotEqualTo(Money.of(ZERO, DOLLAR))
        })
    }

    @Test
    fun `isNotEqualTo with different currency should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isNotEqualTo(Money.of(ONE, REAL))
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isNotEqualTo(Money.of(ONE, REAL))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = NotEquals(Money.of(ONE, REAL))))
    }

    @Test
    fun `isNotEqualTo with same value and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotEqualTo(Money.of(1.00.toBigDecimal(), DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotEquals(Money.of(1.00.toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isNotEqualTo with same value and different type should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotEqualTo(Money.of(1, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotEquals(Money.of(1, DOLLAR))))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isIn(Money.of(ZERO, REAL), Money.of(ONE, REAL), Money.of(TEN, REAL))
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isIn(Money.of(ZERO, DOLLAR), Money.of(ONE, DOLLAR), Money.of(TEN, DOLLAR))
        })
    }

    @Test
    fun `isIn vararg with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isIn(Money.of(ZERO, REAL), Money.of(1.00.toBigDecimal(), REAL), Money.of(TEN, REAL))
        })
    }

    @Test
    fun `isIn vararg with same value and different type should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isIn(Money.of(0, REAL), Money.of(1, REAL), Money.of(10, REAL))
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isIn(Money.of(ZERO, DOLLAR), Money.of(TEN, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = In(setOf(Money.of(ZERO, DOLLAR), Money.of(TEN, DOLLAR)))))
    }

    @Test
    fun `isIn vararg with different currency should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isIn(Money.of(ONE, REAL), Money.of(TEN, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = In(setOf(Money.of(ONE, REAL), Money.of(TEN, DOLLAR)))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isIn(listOf(Money.of(ZERO, REAL), Money.of(ONE, REAL), Money.of(TEN, REAL)))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isIn(listOf(Money.of(ZERO, DOLLAR), Money.of(ONE, DOLLAR), Money.of(TEN, DOLLAR)))
        })
    }

    @Test
    fun `isIn iterable with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isIn(listOf(Money.of(ZERO, REAL), Money.of(1.00.toBigDecimal(), REAL), Money.of(TEN, DOLLAR)))
        })
    }

    @Test
    fun `isIn iterable with same value and different type should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isIn(listOf(Money.of(0, REAL), Money.of(1, REAL), Money.of(10, DOLLAR)))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isIn(listOf(Money.of(ZERO, REAL), Money.of(TEN, REAL)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = In(listOf(Money.of(ZERO, REAL), Money.of(TEN, REAL)))))
    }

    @Test
    fun `isIn iterable with different currency should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isIn(listOf(Money.of(ONE, DOLLAR), Money.of(TEN, REAL)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = In(listOf(Money.of(ONE, DOLLAR), Money.of(TEN, REAL)))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotIn(Money.of(ZERO, DOLLAR), Money.of(ONE, DOLLAR), Money.of(TEN, DOLLAR))
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isNotIn(Money.of(ZERO, REAL), Money.of(TEN, REAL))
        })
    }

    @Test
    fun `isNotIn vararg with different currency should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isNotIn(Money.of(ONE, DOLLAR), Money.of(TEN, REAL))
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotIn(Money.of(ZERO, DOLLAR), Money.of(ONE, DOLLAR), Money.of(TEN, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotIn(setOf(Money.of(ZERO, DOLLAR), Money.of(ONE, DOLLAR), Money.of(TEN, DOLLAR)))))
    }

    @Test
    fun `isNotIn vararg with same value and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotIn(Money.of(ZERO, DOLLAR), Money.of(1.00.toBigDecimal(), DOLLAR), Money.of(TEN, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotIn(setOf(Money.of(ZERO, DOLLAR), Money.of(1.00.toBigDecimal(), DOLLAR), Money.of(TEN, DOLLAR)))))
    }

    @Test
    fun `isNotIn vararg with same value and different type should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotIn(Money.of(0L, DOLLAR), Money.of(1L, DOLLAR), Money.of(10L, DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotIn(setOf(Money.of(0L, DOLLAR), Money.of(1L, DOLLAR), Money.of(10L, DOLLAR)))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotIn(listOf(Money.of(ZERO, REAL), Money.of(ONE, REAL), Money.of(TEN, REAL)))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isNotIn(listOf(Money.of(ZERO, DOLLAR), Money.of(TEN, DOLLAR)))
        })
    }

    @Test
    fun `isNotIn iterable with different currency should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isNotIn(listOf(Money.of(ONE, REAL), Money.of(TEN, DOLLAR)))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isNotIn(listOf(Money.of(ZERO, REAL), Money.of(ONE, REAL), Money.of(TEN, REAL)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = NotIn(listOf(Money.of(ZERO, REAL), Money.of(ONE, REAL), Money.of(TEN, REAL)))))
    }

    @Test
    fun `isNotIn iterable with same value and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotIn(listOf(Money.of(ZERO, DOLLAR), Money.of(1.00.toBigDecimal(), DOLLAR), Money.of(TEN, DOLLAR)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotIn(listOf(Money.of(ZERO, DOLLAR), Money.of(1.00.toBigDecimal(), DOLLAR), Money.of(TEN, DOLLAR)))))
    }

    @Test
    fun `isNotIn iterable with same value and different type should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotIn(listOf(Money.of(0.0, DOLLAR), Money.of(1.00, DOLLAR), Money.of(10.00, DOLLAR)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = NotIn(listOf(Money.of(0.0, DOLLAR), Money.of(1.0, DOLLAR), Money.of(10.0, DOLLAR)))))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThan(Money.of(10.0.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = Money.of(9999.99.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isLessThan(Money.of(10000.00.toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = Money.of(0.38576.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isLessThan(Money.of(0.3.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(50.0.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isLessThan(Money.of(49.9.toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(50.0.toBigDecimal(), DOLLAR),
                        constraint = Less(Money.of(49.9.toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(50.9.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).isLessThan(Money.of(51.0.unaryMinus().toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(50.9.unaryMinus().toBigDecimal(), REAL),
                        constraint = Less(Money.of(51.0.unaryMinus().toBigDecimal(), REAL))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).isLessThan(Money.of(ZERO, DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, DOLLAR),
                        constraint = Less(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThanOrEqualTo(Money.of(10.0.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = Money.of(9999.99.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isLessThanOrEqualTo(Money.of(10000.00.toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = Money.of(0.38576.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isLessThanOrEqualTo(Money.of(0.3.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
            validate(Employee::salary).isLessThanOrEqualTo(Money.of(ZERO, DOLLAR))
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(56789.19.toBigDecimal(), REAL)), {
                validate(Employee::salary).isLessThanOrEqualTo(Money.of(57.0.toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(56789.19.toBigDecimal(), REAL),
                        constraint = LessOrEqual(Money.of(57.0.toBigDecimal(), REAL))))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(96.0.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isLessThanOrEqualTo(Money.of(97.0.unaryMinus().toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(96.0.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = LessOrEqual(Money.of(97.0.unaryMinus().toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThan(Money.of(10.0.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = Money.of(10.1.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isGreaterThan(Money.of(10.0.toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = Money.of(88.88.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isGreaterThan(Money.of(89.0.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(10.0.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isGreaterThan(Money.of(11.0.toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(10.0.toBigDecimal(), DOLLAR),
                        constraint = Greater(Money.of(11.0.toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(189.20.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).isGreaterThan(Money.of(180.0.unaryMinus().toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(189.20.unaryMinus().toBigDecimal(), REAL),
                        constraint = Greater(Money.of(180.0.unaryMinus().toBigDecimal(), REAL))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).isGreaterThan(Money.of(ZERO, DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, DOLLAR),
                        constraint = Greater(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Money.of(10.0.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = Money.of(10000.0.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Money.of(9999.99.toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = Money.of(0.3.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Money.of(0.38576.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Money.of(ZERO, DOLLAR))
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(57.0.toBigDecimal(), REAL)), {
                validate(Employee::salary).isGreaterThanOrEqualTo(Money.of(56789.19.toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(57.0.toBigDecimal(), REAL),
                        constraint = GreaterOrEqual(Money.of(56789.19.toBigDecimal(), REAL))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(97.0.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isGreaterThanOrEqualTo(Money.of(96.0.unaryMinus().toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(97.0.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = GreaterOrEqual(Money.of(96.0.unaryMinus().toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isBetween(start = Money.of(0.99.toBigDecimal(), REAL), end = Money.of(9.99.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
            validate(Employee::salary).isBetween(start = Money.of(ZERO, DOLLAR), end = Money.of(ONE, DOLLAR))
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isBetween(start = Money.of(ZERO, REAL), end = Money.of(ONE, REAL))
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = Money.of(0.5.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isBetween(start = Money.of(ZERO, DOLLAR), end = Money.of(ONE, DOLLAR))
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = Money.of(2.0.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), REAL), end = Money.of(1.0.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = Money.of(1.5.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), REAL), end = Money.of(1.0.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(10.0.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isBetween(start = Money.of(10.1.toBigDecimal(), DOLLAR), end = Money.of(11.0.toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(10.0.toBigDecimal(), DOLLAR),
                        constraint = Between(start = Money.of(10.1.toBigDecimal(), DOLLAR), end = Money.of(11.0.toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(12.0.toBigDecimal(), REAL)), {
                validate(Employee::salary).isBetween(start = Money.of(10.1.toBigDecimal(), REAL), end = Money.of(11.0.toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(12.0.toBigDecimal(), REAL),
                        constraint = Between(start = Money.of(10.1.toBigDecimal(), REAL), end = Money.of(11.0.toBigDecimal(), REAL))))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(10.0.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isBetween(start = Money.of(9.9.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(8.0.unaryMinus().toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(10.0.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = Between(start = Money.of(9.9.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(8.0.unaryMinus().toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(12.0.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).isBetween(start = Money.of(13.0.unaryMinus().toBigDecimal(), REAL), end = Money.of(12.9.unaryMinus().toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(12.0.unaryMinus().toBigDecimal(), REAL),
                        constraint = Between(start = Money.of(13.0.unaryMinus().toBigDecimal(), REAL), end = Money.of(12.9.unaryMinus().toBigDecimal(), REAL))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotBetween(start = Money.of(0.99.toBigDecimal(), DOLLAR), end = Money.of(9.99.toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = Money.of(10.0.toBigDecimal(), REAL)), {
            validate(Employee::salary).isNotBetween(start = Money.of(10.1.toBigDecimal(), REAL), end = Money.of(11.0.toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = Money.of(12.0.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isNotBetween(start = Money.of(10.1.toBigDecimal(), DOLLAR), end = Money.of(11.0.toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = Money.of(10.0.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isNotBetween(start = Money.of(9.9.unaryMinus().toBigDecimal(), REAL), end = Money.of(8.0.unaryMinus().toBigDecimal(), REAL))
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = Money.of(12.0.unaryMinus().toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isNotBetween(start = Money.of(13.0.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(12.9.unaryMinus().toBigDecimal(), DOLLAR))
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, REAL)), {
                validate(Employee::salary).isNotBetween(start = Money.of(ZERO, REAL), end = Money.of(ONE, REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, REAL),
                        constraint = NotBetween(start = Money.of(ZERO, REAL), end = Money.of(ONE, REAL))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNotBetween(start = Money.of(ZERO, DOLLAR), end = Money.of(ONE, DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ONE, DOLLAR),
                        constraint = NotBetween(start = Money.of(ZERO, DOLLAR), end = Money.of(ONE, DOLLAR))))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(2.0.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).isNotBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), REAL), end = Money.of(1.0.unaryMinus().toBigDecimal(), REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(2.0.unaryMinus().toBigDecimal(), REAL),
                        constraint = NotBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), REAL), end = Money.of(1.0.unaryMinus().toBigDecimal(), REAL))))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isNotBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = NotBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(0.5.toBigDecimal(), REAL)), {
                validate(Employee::salary).isNotBetween(start = Money.of(ZERO, REAL), end = Money.of(ONE, REAL))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(0.5.toBigDecimal(), REAL),
                        constraint = NotBetween(start = Money.of(ZERO, REAL), end = Money.of(ONE, REAL))))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(1.5.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isNotBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(1.5.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = NotBetween(start = Money.of(2.0.unaryMinus().toBigDecimal(), DOLLAR), end = Money.of(1.0.unaryMinus().toBigDecimal(), DOLLAR))))
    }

    @Test
    fun `hasCurrencyEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyEqualTo(REAL)
        })
    }

    @Test
    fun `hasCurrencyEqualTo with same value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).hasCurrencyEqualTo(DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyEqualTo with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).hasCurrencyEqualTo(REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ZERO, DOLLAR), constraint = CurrencyEquals(REAL)))
    }

    @Test
    fun `hasCurrencyNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyNotEqualTo(REAL)
        })
    }

    @Test
    fun `hasCurrencyNotEqualTo with different value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).hasCurrencyNotEqualTo(REAL)
        })
    }

    @Test
    fun `hasCurrencyNotEqualTo with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).hasCurrencyNotEqualTo(REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = CurrencyNotEquals(REAL)))
    }

    @Test
    fun `hasCurrencyIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyIn(REAL, DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyIn vararg with same value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).hasCurrencyIn(REAL, DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyIn vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).hasCurrencyIn(REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = CurrencyIn(setOf(REAL))))
    }

    @Test
    fun `hasCurrencyIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyIn(listOf(REAL, DOLLAR))
        })
    }

    @Test
    fun `hasCurrencyIn iterable with same value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).hasCurrencyIn(listOf(REAL, DOLLAR))
        })
    }

    @Test
    fun `hasCurrencyIn iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).hasCurrencyIn(listOf(DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = CurrencyIn(listOf(DOLLAR))))
    }

    @Test
    fun `hasCurrencyNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyNotIn(DOLLAR, REAL)
        })
    }

    @Test
    fun `hasCurrencyNotIn vararg with different value should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).hasCurrencyNotIn(DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyNotIn vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).hasCurrencyNotIn(DOLLAR, REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, DOLLAR), constraint = CurrencyNotIn(setOf(DOLLAR, REAL))))
    }

    @Test
    fun `hasCurrencyNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyNotIn(listOf(DOLLAR, REAL))
        })
    }

    @Test
    fun `hasCurrencyNotIn iterable with different value should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).hasCurrencyNotIn(listOf(REAL))
        })
    }

    @Test
    fun `hasCurrencyNotIn iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).hasCurrencyNotIn(listOf(DOLLAR, REAL))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Money.of(ONE, REAL), constraint = CurrencyNotIn(listOf(DOLLAR, REAL))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = Money.of(ZERO, REAL)), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero and 2 decimal digits should be valid`() {
        validate(Employee(salary = Money.of(0.00.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ONE, REAL),
                        constraint = Equals(Money.of(ZERO, REAL))))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = Money.of(ONE, DOLLAR)), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, REAL)), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, REAL),
                        constraint = NotEquals(Money.of(ZERO, REAL))))
    }

    @Test
    fun `isNotZero with zero and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(0.00.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(0.00.toBigDecimal(), DOLLAR),
                        constraint = NotEquals(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one and 2 decimal digits should be valid`() {
        validate(Employee(salary = Money.of(1.00.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, REAL)), {
                validate(Employee::salary).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, REAL),
                        constraint = Equals(Money.of(ONE, REAL))))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ONE, REAL),
                        constraint = NotEquals(Money.of(ONE, REAL))))
    }

    @Test
    fun `isNotOne with one and 2 decimal digits should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(1.00.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(1.00.toBigDecimal(), DOLLAR),
                        constraint = NotEquals(Money.of(ONE, DOLLAR))))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, DOLLAR),
                        constraint = Greater(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(98765.432.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(98765.432.unaryMinus().toBigDecimal(), REAL),
                        constraint = Greater(Money.of(ZERO, REAL))))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
            validate(Employee::salary).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(salary = Money.of(98765.432.unaryMinus().toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, DOLLAR)), {
                validate(Employee::salary).isNegativeOrZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ONE, DOLLAR),
                        constraint = LessOrEqual(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = Money.of(1.0.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ZERO, DOLLAR),
                        constraint = Less(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(ONE, REAL)), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(ONE, REAL),
                        constraint = Less(Money.of(ZERO, REAL))))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(salary = Money.of(ZERO, DOLLAR)), {
            validate(Employee::salary).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(salary = Money.of(ONE, REAL)), {
            validate(Employee::salary).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(98765.432.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).isPositiveOrZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(98765.432.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = GreaterOrEqual(Money.of(ZERO, DOLLAR))))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = Money.of(9999.99.toBigDecimal(), REAL)), {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = Money.of(9999.99.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = Money.of(9999.99.toBigDecimal(), REAL)), {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = Money.of(99999.99.unaryMinus().toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = Money.of(99999.99.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = Money.of(99999.99.unaryMinus().toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = Money.of(9999.99.toBigDecimal(), REAL)), {
            validate(Employee::salary).hasIntegerDigits()
        })
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(748536.78.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(748536.78.toBigDecimal(), DOLLAR),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(748536.78.toBigDecimal(), REAL)), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(748536.78.toBigDecimal(), REAL),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(748536.78.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(748536.78.toBigDecimal(), DOLLAR),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(748536.78.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(748536.78.unaryMinus().toBigDecimal(), REAL),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(748536.78.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(748536.78.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(748536.78.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(748536.78.unaryMinus().toBigDecimal(), DOLLAR),
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
        validate(Employee(salary = Money.of(99.9999.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = Money.of(99.9999.toBigDecimal(), REAL)), {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = Money.of(99.9999.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = Money.of(99.99999.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = Money.of(99.99999.unaryMinus().toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = Money.of(99.99999.unaryMinus().toBigDecimal(), REAL)), {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = Money.of(99.9999.toBigDecimal(), DOLLAR)), {
            validate(Employee::salary).hasDecimalDigits()
        })
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(78.748536.toBigDecimal(), REAL)), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(78.748536.toBigDecimal(), REAL),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(78.748536.toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(78.748536.toBigDecimal(), DOLLAR),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(78.748536.toBigDecimal(), REAL)), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(78.748536.toBigDecimal(), REAL),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(78.748536.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(78.748536.unaryMinus().toBigDecimal(), REAL),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(78.748536.unaryMinus().toBigDecimal(), DOLLAR)), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(78.748536.unaryMinus().toBigDecimal(), DOLLAR),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(salary = Money.of(78.748536.unaryMinus().toBigDecimal(), REAL)), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Money.of(78.748536.unaryMinus().toBigDecimal(), REAL),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }
}