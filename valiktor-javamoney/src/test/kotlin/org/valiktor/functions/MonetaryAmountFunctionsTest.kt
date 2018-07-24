package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.MonetaryAmountFunctionsFixture.Employee
import org.valiktor.validate
import java.math.BigDecimal.*
import javax.money.Monetary
import javax.money.MonetaryAmount
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object MonetaryAmountFunctionsFixture {

    data class Employee(val salary: MonetaryAmount? = null)
}

private val REAL = Monetary.getCurrency("BRL")
private val DOLLAR = Monetary.getCurrency("USD")

private val ONE_NUMBERS = listOf<Number>(
        1.toByte(), 1.toShort(), 1, 1.toLong(), 1.toBigInteger(),
        1.toFloat(), 1.toDouble(), 1.toBigDecimal())

private val NEGATIVE_ONE_NUMBERS = listOf<Number>(
        1.unaryMinus().toByte(), 1.unaryMinus().toShort(), 1.unaryMinus(), 1.unaryMinus().toLong(), 1.unaryMinus().toBigInteger(),
        1.unaryMinus().toFloat(), 1.unaryMinus().toDouble(), 1.unaryMinus().toBigDecimal())

class MonetaryAmountFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNull()
        })
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(REAL).create()), {
                validate(Employee::salary).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(REAL).create(), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()), {
            validate(Employee::salary).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
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
            validate(Employee::salary).isEqualTo(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isEqualTo(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isEqualTo with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isEqualTo(Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isEqualTo with same value and different type should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isEqualTo(Monetary.getDefaultAmountFactory().setNumber(1).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isEqualTo(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), constraint = Equals(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isEqualTo with different currency should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isEqualTo(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), constraint = Equals(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create())))
    }

    @Test
    fun `isEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isEqualTo(it)
            })
        }
    }

    @Test
    fun `isEqualTo Number with same value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isEqualTo(it)
            })
        }
    }

    @Test
    fun `isEqualTo Number with same value and 2 decimal digits should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber("1.00".toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isEqualTo(it)
            })
        }
    }

    @Test
    fun `isEqualTo Number with different value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isEqualTo(it)
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = Equals(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotEqualTo(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotEqualTo(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isNotEqualTo with different currency should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotEqualTo(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotEqualTo(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotEqualTo with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotEqualTo(Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNotEqualTo with same value and different type should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotEqualTo(Monetary.getDefaultAmountFactory().setNumber(1).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(1).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNotEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isNotEqualTo(it)
            })
        }
    }

    @Test
    fun `isNotEqualTo Number with different value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotEqualTo(it)
            })
        }
    }

    @Test
    fun `isNotEqualTo Number with same value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotEqualTo(it)
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = NotEquals(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isNotEqualTo Number with same value and 2 decimal digits should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber("1.00".toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotEqualTo(it)
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = NotEquals(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isIn vararg with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isIn vararg with same value and different type should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isIn(Monetary.getDefaultAmountFactory().setNumber(0).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(1).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(10).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = In(setOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isIn vararg with different currency should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isIn(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = In(setOf(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isIn vararg Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isIn(ZERO, it, TEN)
            })
        }
    }

    @Test
    fun `isIn vararg Number with same value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isIn(ZERO, it, TEN)
            })
        }
    }

    @Test
    fun `isIn vararg Number with same value and 2 decimal digits should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber("1.00".toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isIn(ZERO, it, TEN)
            })
        }
    }

    @Test
    fun `isIn vararg Number with different value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isIn(it, TEN)
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = In(setOf(
                            salary.factory.setNumber(it).create(),
                            salary.factory.setNumber(TEN).create()))))
        }
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))
        })
    }

    @Test
    fun `isIn iterable with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))
        })
    }

    @Test
    fun `isIn iterable with same value and different type should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isIn(listOf(Monetary.getDefaultAmountFactory().setNumber(0).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(1).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(10).setCurrency(DOLLAR).create()))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = In(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))))
    }

    @Test
    fun `isIn iterable with different currency should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = In(listOf(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))))
    }

    @Test
    fun `isIn iterable Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isIn(listOf(ZERO, it, TEN))
            })
        }
    }

    @Test
    fun `isIn iterable Number with same value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isIn(listOf(ZERO, it, TEN))
            })
        }
    }

    @Test
    fun `isIn iterable Number with same value and 2 decimal digits should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber("1.00".toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isIn(listOf(ZERO, it, TEN))
            })
        }
    }

    @Test
    fun `isIn iterable Number with different value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isIn(listOf(it, TEN))
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = In(listOf(
                            salary.factory.setNumber(it).create(),
                            salary.factory.setNumber(TEN).create()))))
        }
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isNotIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isNotIn vararg with different currency should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isNotIn(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotIn(setOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isNotIn vararg with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotIn(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotIn(setOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isNotIn vararg with same value and different type should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotIn(Monetary.getDefaultAmountFactory().setNumber(0L).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1L).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(10L).setCurrency(DOLLAR).create())
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotIn(setOf(Monetary.getDefaultAmountFactory().setNumber(0L).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1L).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(10L).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isNotIn vararg Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isNotIn(ZERO, it, TEN)
            })
        }
    }

    @Test
    fun `isNotIn vararg Number with different value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotIn(it, TEN)
            })
        }
    }

    @Test
    fun `isNotIn vararg Number with same value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotIn(ZERO, it, TEN)
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = NotIn(setOf(
                            salary.factory.setNumber(ZERO).create(),
                            salary.factory.setNumber(it).create(),
                            salary.factory.setNumber(TEN).create()))))
        }
    }

    @Test
    fun `isNotIn vararg Number with same value and 2 decimal digits should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber("1.00".toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotIn(ZERO, it, TEN)
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = NotIn(setOf(
                            salary.factory.setNumber(ZERO).create(),
                            salary.factory.setNumber(it).create(),
                            salary.factory.setNumber(TEN).create()))))
        }
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))
        })
    }

    @Test
    fun `isNotIn iterable with different currency should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = NotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(REAL).create()))))
    }

    @Test
    fun `isNotIn iterable with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(TEN).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isNotIn iterable with same value and different type should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(0.0).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1.00).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(10.00).setCurrency(DOLLAR).create()))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = NotIn(listOf(Monetary.getDefaultAmountFactory().setNumber(0.0).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(1.0).setCurrency(DOLLAR).create(), Monetary.getDefaultAmountFactory().setNumber(10.0).setCurrency(DOLLAR).create()))))
    }

    @Test
    fun `isNotIn iterable Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isNotIn(listOf(ZERO, it, TEN))
            })
        }
    }

    @Test
    fun `isNotIn iterable Number with different value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotIn(listOf(it, TEN))
            })
        }
    }

    @Test
    fun `isNotIn iterable Number with same value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotIn(listOf(ZERO, it, TEN))
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = NotIn(listOf(
                            salary.factory.setNumber(ZERO).create(),
                            salary.factory.setNumber(it).create(),
                            salary.factory.setNumber(TEN).create()))))
        }
    }

    @Test
    fun `isNotIn iterable Number with same value and 2 decimal digits should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber("1.00".toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotIn(listOf(ZERO, it, TEN))
                })
            }
            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(property = "salary", value = salary, constraint = NotIn(listOf(
                            salary.factory.setNumber(ZERO).create(),
                            salary.factory.setNumber(it).create(),
                            salary.factory.setNumber(TEN).create()))))
        }
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThan(Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isLessThan(Monetary.getDefaultAmountFactory().setNumber(10000.00.toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.38576.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isLessThan(Monetary.getDefaultAmountFactory().setNumber(0.3.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(50.0.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isLessThan(Monetary.getDefaultAmountFactory().setNumber(49.9.toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(50.0.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = Less(Monetary.getDefaultAmountFactory().setNumber(49.9.toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(50.9.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isLessThan(Monetary.getDefaultAmountFactory().setNumber(51.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(50.9.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = Less(Monetary.getDefaultAmountFactory().setNumber(51.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isLessThan(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(),
                        constraint = Less(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isLessThan Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isLessThan(it)
            })
        }
    }

    @Test
    fun `isLessThan Number with less value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.99.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isLessThan(it)
            })
        }
    }

    @Test
    fun `isLessThan Number with negative less value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(1.1.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isLessThan(it)
            })
        }
    }

    @Test
    fun `isLessThan Number with greater value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isLessThan(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Less(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isLessThan Number with negative greater value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isLessThan(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Less(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isLessThan Number with equal value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isLessThan(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Less(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isLessThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isLessThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(10000.00.toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.38576.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isLessThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(0.3.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isLessThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(56789.19.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isLessThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(57.0.toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(56789.19.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = LessOrEqual(Monetary.getDefaultAmountFactory().setNumber(57.0.toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(96.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isLessThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(97.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(96.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = LessOrEqual(Monetary.getDefaultAmountFactory().setNumber(97.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isLessThanOrEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isLessThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with less value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.99.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isLessThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with negative less value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isLessThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with equal value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isLessThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with greater value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isLessThanOrEqualTo(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = LessOrEqual(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with negative greater value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isLessThanOrEqualTo(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = LessOrEqual(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThan(Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isGreaterThan(Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(88.88.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isGreaterThan(Monetary.getDefaultAmountFactory().setNumber(89.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isGreaterThan(Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = Greater(Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(189.20.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isGreaterThan(Monetary.getDefaultAmountFactory().setNumber(180.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(189.20.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = Greater(Monetary.getDefaultAmountFactory().setNumber(180.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isGreaterThan(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(),
                        constraint = Greater(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isGreaterThan Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isGreaterThan(it)
            })
        }
    }

    @Test
    fun `isGreaterThan Number with greater value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isGreaterThan(it)
            })
        }
    }

    @Test
    fun `isGreaterThan Number with negative greater value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isGreaterThan(it)
            })
        }
    }

    @Test
    fun `isGreaterThan Number with less value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isGreaterThan(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Greater(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isGreaterThan Number with negative less value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isGreaterThan(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Greater(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isGreaterThan Number with equal value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isGreaterThan(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Greater(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10000.0.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.3.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(0.38576.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isGreaterThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(57.0.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(56789.19.toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(57.0.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = GreaterOrEqual(Monetary.getDefaultAmountFactory().setNumber(56789.19.toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(97.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isGreaterThanOrEqualTo(Monetary.getDefaultAmountFactory().setNumber(96.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(97.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = GreaterOrEqual(Monetary.getDefaultAmountFactory().setNumber(96.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isGreaterThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with greater value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isGreaterThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with negative greater value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isGreaterThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with equal value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isGreaterThanOrEqualTo(it)
            })
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with less value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isGreaterThanOrEqualTo(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = GreaterOrEqual(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with negative less value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isGreaterThanOrEqualTo(it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = GreaterOrEqual(salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(0.99.toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(9.99.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.5.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = Between(start = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(12.0.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(12.0.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = Between(start = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(9.9.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(8.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(10.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = Between(start = Monetary.getDefaultAmountFactory().setNumber(9.9.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(8.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(12.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isBetween(start = Monetary.getDefaultAmountFactory().setNumber(13.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(12.9.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(12.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = Between(start = Monetary.getDefaultAmountFactory().setNumber(13.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(12.9.unaryMinus().toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isBetween Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isBetween(start = it, end = 2.00)
            })
        }
    }

    @Test
    fun `isBetween Number with equal start value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isBetween(start = it, end = 2)
            })
        }
    }

    @Test
    fun `isBetween Number with equal end value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isBetween(start = it, end = 2)
            })
        }
    }

    @Test
    fun `isBetween Number with within value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isBetween(start = 0, end = it)
            })
        }
    }

    @Test
    fun `isBetween Number with equal negative start value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(1.00.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isBetween(start = it, end = 0.5.unaryMinus())
            })
        }
    }

    @Test
    fun `isBetween Number with equal negative end value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isBetween(start = it, end = 0.5.unaryMinus())
            })
        }
    }

    @Test
    fun `isBetween Number with within negative value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(1.5.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isBetween(start = 2.unaryMinus().toLong(), end = it)
            })
        }
    }

    @Test
    fun `isBetween Number with less start value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isBetween(start = 0.1, end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Between(start = salary.factory.setNumber(0.1).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isBetween Number with greater end value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(1.8.toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isBetween(start = 0.5, end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Between(start = salary.factory.setNumber(0.5).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isBetween Number with less negative start value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.unaryMinus().toByte()).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isBetween(start = it, end = 0.5.unaryMinus())
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Between(start = salary.factory.setNumber(it).create(), end = salary.factory.setNumber(0.5.unaryMinus()).create())))
        }
    }

    @Test
    fun `isBetween Number with greater negative end value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isBetween(start = 2.unaryMinus().toFloat(), end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = Between(start = salary.factory.setNumber(2.unaryMinus()).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(0.99.toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(9.99.toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10.0.toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(12.0.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(10.1.toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(11.0.toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(10.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(9.9.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(8.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
        })
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(12.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(13.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(12.9.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(),
                        constraint = NotBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(),
                        constraint = NotBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = NotBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = NotBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = NotBetween(start = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(), end = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.5.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(1.5.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = NotBetween(start = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(), end = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNotBetween Number with null value should be valid`() {
        ONE_NUMBERS.forEach {
            validate(Employee(), {
                validate(Employee::salary).isNotBetween(start = it, end = 2)
            })
        }
    }

    @Test
    fun `isNotBetween Number with less start value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotBetween(start = it, end = 2.0f)
            })
        }
    }

    @Test
    fun `isNotBetween Number with greater end value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.5.toBigDecimal()).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotBetween(start = it, end = 2)
            })
        }
    }

    @Test
    fun `isNotBetween Number with less negative start value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotBetween(start = it, end = 0.5.unaryMinus())
            })
        }
    }

    @Test
    fun `isNotBetween Number with greater negative end value should be valid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            validate(Employee(salary = salary), {
                validate(Employee::salary).isNotBetween(start = it, end = ZERO)
            })
        }
    }

    @Test
    fun `isNotBetween Number with equal start value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotBetween(start = it, end = 2)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = NotBetween(start = salary.factory.setNumber(it).create(), end = salary.factory.setNumber(2).create())))
        }
    }

    @Test
    fun `isNotBetween Number with equal end value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2).setCurrency(DOLLAR).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotBetween(start = it, end = 2)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = NotBetween(start = salary.factory.setNumber(it).create(), end = salary.factory.setNumber(2).create())))
        }
    }

    @Test
    fun `isNotBetween Number with equal negative start value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(2.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotBetween(start = -2, end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = NotBetween(start = salary.factory.setNumber(-2).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isNotBetween Number with equal negative end value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotBetween(start = -2, end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = NotBetween(start = salary.factory.setNumber(-2).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isNotBetween Number with within value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(0.5.toBigDecimal()).setCurrency(REAL).create()

        ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotBetween(start = ZERO, end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = NotBetween(start = salary.factory.setNumber(ZERO).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `isNotBetween Number with within negative value should be invalid`() {
        val salary = Monetary.getDefaultAmountFactory().setNumber(1.5.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()

        NEGATIVE_ONE_NUMBERS.forEach {
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary), {
                    validate(Employee::salary).isNotBetween(start = -2, end = it)
                })
            }

            assertThat(exception.constraintViolations).containsExactly(
                    DefaultConstraintViolation(
                            property = "salary",
                            value = salary,
                            constraint = NotBetween(start = salary.factory.setNumber(-2).create(), end = salary.factory.setNumber(it).create())))
        }
    }

    @Test
    fun `hasCurrencyEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyEqualTo(REAL)
        })
    }

    @Test
    fun `hasCurrencyEqualTo with same value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasCurrencyEqualTo(DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasCurrencyEqualTo(REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(), constraint = CurrencyEquals(REAL)))
    }

    @Test
    fun `hasCurrencyNotEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyNotEqualTo(REAL)
        })
    }

    @Test
    fun `hasCurrencyNotEqualTo with different value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasCurrencyNotEqualTo(REAL)
        })
    }

    @Test
    fun `hasCurrencyNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).hasCurrencyNotEqualTo(REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = CurrencyNotEquals(REAL)))
    }

    @Test
    fun `hasCurrencyIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyIn(REAL, DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyIn vararg with same value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasCurrencyIn(REAL, DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasCurrencyIn(REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = CurrencyIn(setOf(REAL))))
    }

    @Test
    fun `hasCurrencyIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyIn(listOf(REAL, DOLLAR))
        })
    }

    @Test
    fun `hasCurrencyIn iterable with same value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasCurrencyIn(listOf(REAL, DOLLAR))
        })
    }

    @Test
    fun `hasCurrencyIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).hasCurrencyIn(listOf(DOLLAR))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = CurrencyIn(listOf(DOLLAR))))
    }

    @Test
    fun `hasCurrencyNotIn vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyNotIn(DOLLAR, REAL)
        })
    }

    @Test
    fun `hasCurrencyNotIn vararg with different value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).hasCurrencyNotIn(DOLLAR)
        })
    }

    @Test
    fun `hasCurrencyNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasCurrencyNotIn(DOLLAR, REAL)
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(), constraint = CurrencyNotIn(setOf(DOLLAR, REAL))))
    }

    @Test
    fun `hasCurrencyNotIn iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasCurrencyNotIn(listOf(DOLLAR, REAL))
        })
    }

    @Test
    fun `hasCurrencyNotIn iterable with different value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasCurrencyNotIn(listOf(REAL))
        })
    }

    @Test
    fun `hasCurrencyNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).hasCurrencyNotIn(listOf(DOLLAR, REAL))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "salary", value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(), constraint = CurrencyNotIn(listOf(DOLLAR, REAL))))
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with zero and 2 decimal digits should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.00.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isZero()
        })
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(),
                        constraint = Equals(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotZero()
        })
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(),
                        constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotZero with zero and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(0.00.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(0.00.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with one and 2 decimal digits should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isOne()
        })
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create()), {
                validate(Employee::salary).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create(),
                        constraint = Equals(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNotOne()
        })
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(),
                        constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create())))
    }

    @Test
    fun `isNotOne with one and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(1.00.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = NotEquals(Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isPositive()
        })
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(),
                        constraint = Greater(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(98765.432.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).isPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(98765.432.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = Greater(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create())))
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(98765.432.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isNegativeOrZero()
        })
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNegativeOrZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(DOLLAR).create(),
                        constraint = LessOrEqual(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(1.0.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).isNegative()
        })
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create(),
                        constraint = Less(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
                validate(Employee::salary).isNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create(),
                        constraint = Less(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(REAL).create())))
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(ONE).setCurrency(REAL).create()), {
            validate(Employee::salary).isPositiveOrZero()
        })
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(98765.432.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).isPositiveOrZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(98765.432.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = GreaterOrEqual(Monetary.getDefaultAmountFactory().setNumber(ZERO).setCurrency(DOLLAR).create())))
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99999.99.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99999.99.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99999.99.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(9999.99.toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasIntegerDigits()
        })
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(748536.78.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(748536.78.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(748536.78.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(748536.78.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(748536.78.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(748536.78.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = IntegerDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(748536.78.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(748536.78.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = IntegerDigits(min = 7)))
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(748536.78.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(748536.78.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = IntegerDigits(max = 5)))
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(748536.78.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(748536.78.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
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
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.9999.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.9999.toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.9999.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.99999.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.99999.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.99999.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        })
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(99.9999.toBigDecimal()).setCurrency(DOLLAR).create()), {
            validate(Employee::salary).hasDecimalDigits()
        })
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(78.748536.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(78.748536.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(78.748536.toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(78.748536.toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(78.748536.toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(78.748536.toBigDecimal()).setCurrency(REAL).create(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(78.748536.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(78.748536.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = DecimalDigits(min = 7)))
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(78.748536.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create()), {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(78.748536.unaryMinus().toBigDecimal()).setCurrency(DOLLAR).create(),
                        constraint = DecimalDigits(max = 5)))
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = Monetary.getDefaultAmountFactory().setNumber(78.748536.unaryMinus().toBigDecimal()).setCurrency(REAL).create()), {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "salary",
                        value = Monetary.getDefaultAmountFactory().setNumber(78.748536.unaryMinus().toBigDecimal()).setCurrency(REAL).create(),
                        constraint = DecimalDigits(min = 7, max = 5)))
    }
}