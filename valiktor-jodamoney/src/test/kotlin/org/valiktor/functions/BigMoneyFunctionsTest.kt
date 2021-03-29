/*
 * Copyright 2018-2020 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.asBigDecimal
import org.valiktor.constraints.Between
import org.valiktor.constraints.CurrencyEquals
import org.valiktor.constraints.CurrencyIn
import org.valiktor.constraints.CurrencyNotEquals
import org.valiktor.constraints.CurrencyNotIn
import org.valiktor.constraints.DecimalDigits
import org.valiktor.constraints.Equals
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.In
import org.valiktor.constraints.IntegerDigits
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.functions.BigMoneyFunctionsFixture.Employee
import org.valiktor.validate
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object BigMoneyFunctionsFixture {

    data class Employee(val salary: BigMoney? = null)
}

private val BRL = CurrencyUnit.of("BRL")
private val USD = CurrencyUnit.of("USD")

private fun <T : Number> moneyOf(currency: CurrencyUnit, number: T) = BigMoney.of(currency, number.asBigDecimal())

private val ONE_NUMBERS = listOf<Number>(
    1.toByte(), 1.toShort(), 1, 1.toLong(), 1.toBigInteger(),
    1.toFloat(), 1.toDouble(), 1.toBigDecimal()
)

private val NEGATIVE_ONE_NUMBERS = listOf<Number>(
    1.unaryMinus().toByte(), 1.unaryMinus().toShort(), 1.unaryMinus(), 1.unaryMinus().toLong(), 1.unaryMinus().toBigInteger(),
    1.unaryMinus().toFloat(), 1.unaryMinus().toDouble(), 1.unaryMinus().toBigDecimal()
)

class BigMoneyFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 1000))) {
                validate(Employee::salary).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, 1000), constraint = Null)
        )
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ZERO))) {
            validate(Employee::salary).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::salary).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", constraint = NotNull)
        )
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isEqualTo(moneyOf(BRL, ONE))
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isEqualTo(moneyOf(USD, ONE))
        }
    }

    @Test
    fun `isEqualTo with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isEqualTo(moneyOf(BRL, 1.00.toBigDecimal()))
        }
    }

    @Test
    fun `isEqualTo with same value and different type should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isEqualTo(moneyOf(BRL, 1))
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).isEqualTo(moneyOf(USD, ONE))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ZERO), constraint = Equals(moneyOf(USD, ONE)))
        )
    }

    @Test
    fun `isEqualTo with different currency should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).isEqualTo(moneyOf(BRL, ZERO))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ZERO), constraint = Equals(moneyOf(BRL, ZERO)))
        )
    }

    @Test
    fun `isEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isEqualTo(one)
            }
        }
    }

    @Test
    fun `isEqualTo Number with same value should be valid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isEqualTo(one)
            }
        }
    }

    @Test
    fun `isEqualTo Number with same value and 2 decimal digits should be valid`() {
        val salary = moneyOf(BRL, "1.00".toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isEqualTo(one)
            }
        }
    }

    @Test
    fun `isEqualTo Number with different value should be invalid`() {
        val salary = moneyOf(USD, ZERO)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isEqualTo(one)
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(property = "salary", value = salary, constraint = Equals(moneyOf(USD, one)))
            )
        }
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotEqualTo(moneyOf(BRL, ONE))
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isNotEqualTo(moneyOf(USD, ZERO))
        }
    }

    @Test
    fun `isNotEqualTo with different currency should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isNotEqualTo(moneyOf(BRL, ONE))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isNotEqualTo(moneyOf(BRL, ONE))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = NotEquals(moneyOf(BRL, ONE)))
        )
    }

    @Test
    fun `isNotEqualTo with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotEqualTo(moneyOf(USD, 1.00.toBigDecimal()))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotEquals(moneyOf(USD, 1.00.toBigDecimal())))
        )
    }

    @Test
    fun `isNotEqualTo with same value and different type should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotEqualTo(moneyOf(USD, 1))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotEquals(moneyOf(USD, 1)))
        )
    }

    @Test
    fun `isNotEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isNotEqualTo(one)
            }
        }
    }

    @Test
    fun `isNotEqualTo Number with different value should be valid`() {
        val salary = moneyOf(USD, ZERO)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotEqualTo(one)
            }
        }
    }

    @Test
    fun `isNotEqualTo Number with same value should be invalid`() {
        val salary = moneyOf(BRL, ONE)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotEqualTo(one)
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(property = "salary", value = salary, constraint = NotEquals(moneyOf(BRL, one)))
            )
        }
    }

    @Test
    fun `isNotEqualTo Number with same value and 2 decimal digits should be invalid`() {
        val salary = moneyOf(USD, "1.00".toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotEqualTo(one)
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(property = "salary", value = salary, constraint = NotEquals(moneyOf(USD, one)))
            )
        }
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isIn(moneyOf(BRL, ZERO), moneyOf(BRL, ONE), moneyOf(BRL, TEN))
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isIn(moneyOf(USD, ZERO), moneyOf(USD, ONE), moneyOf(USD, TEN))
        }
    }

    @Test
    fun `isIn vararg with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isIn(moneyOf(BRL, ZERO), moneyOf(BRL, 1.00.toBigDecimal()), moneyOf(BRL, TEN))
        }
    }

    @Test
    fun `isIn vararg with same value and different type should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isIn(moneyOf(BRL, 0), moneyOf(BRL, 1), moneyOf(BRL, 10))
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isIn(moneyOf(USD, ZERO), moneyOf(USD, TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = In(setOf(moneyOf(USD, ZERO), moneyOf(USD, TEN))))
        )
    }

    @Test
    fun `isIn vararg with different currency should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isIn(moneyOf(BRL, ONE), moneyOf(USD, TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = In(setOf(moneyOf(BRL, ONE), moneyOf(USD, TEN))))
        )
    }

    @Test
    fun `isIn vararg Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isIn(ZERO, one, TEN)
            }
        }
    }

    @Test
    fun `isIn vararg Number with same value should be valid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isIn(ZERO, one, TEN)
            }
        }
    }

    @Test
    fun `isIn vararg Number with same value and 2 decimal digits should be valid`() {
        val salary = moneyOf(BRL, "1.00".toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isIn(ZERO, one, TEN)
            }
        }
    }

    @Test
    fun `isIn vararg Number with different value should be invalid`() {
        val salary = moneyOf(USD, ZERO)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isIn(one, TEN)
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary", value = salary,
                    constraint = In(
                        setOf(
                            moneyOf(USD, one),
                            moneyOf(USD, TEN)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isIn(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, ONE), moneyOf(BRL, TEN)))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isIn(listOf(moneyOf(USD, ZERO), moneyOf(USD, ONE), moneyOf(USD, TEN)))
        }
    }

    @Test
    fun `isIn iterable with same value and 2 decimal digits should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isIn(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, 1.00.toBigDecimal()), moneyOf(USD, TEN)))
        }
    }

    @Test
    fun `isIn iterable with same value and different type should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isIn(listOf(moneyOf(BRL, 0), moneyOf(BRL, 1), moneyOf(USD, 10)))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isIn(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, TEN)))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = In(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, TEN))))
        )
    }

    @Test
    fun `isIn iterable with different currency should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isIn(listOf(moneyOf(USD, ONE), moneyOf(BRL, TEN)))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = In(listOf(moneyOf(USD, ONE), moneyOf(BRL, TEN))))
        )
    }

    @Test
    fun `isIn iterable Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isIn(listOf(ZERO, one, TEN))
            }
        }
    }

    @Test
    fun `isIn iterable Number with same value should be valid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isIn(listOf(ZERO, one, TEN))
            }
        }
    }

    @Test
    fun `isIn iterable Number with same value and 2 decimal digits should be valid`() {
        val salary = moneyOf(BRL, "1.00".toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isIn(listOf(ZERO, one, TEN))
            }
        }
    }

    @Test
    fun `isIn iterable Number with different value should be invalid`() {
        val salary = moneyOf(USD, ZERO)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isIn(listOf(one, TEN))
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary", value = salary,
                    constraint = In(
                        listOf(
                            moneyOf(USD, one),
                            moneyOf(USD, TEN)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotIn(moneyOf(USD, ZERO), moneyOf(USD, ONE), moneyOf(USD, TEN))
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isNotIn(moneyOf(BRL, ZERO), moneyOf(BRL, TEN))
        }
    }

    @Test
    fun `isNotIn vararg with different currency should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isNotIn(moneyOf(USD, ONE), moneyOf(BRL, TEN))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotIn(moneyOf(USD, ZERO), moneyOf(USD, ONE), moneyOf(USD, TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotIn(setOf(moneyOf(USD, ZERO), moneyOf(USD, ONE), moneyOf(USD, TEN))))
        )
    }

    @Test
    fun `isNotIn vararg with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotIn(moneyOf(USD, ZERO), moneyOf(USD, 1.00.toBigDecimal()), moneyOf(USD, TEN))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotIn(setOf(moneyOf(USD, ZERO), moneyOf(USD, 1.00.toBigDecimal()), moneyOf(USD, TEN))))
        )
    }

    @Test
    fun `isNotIn vararg with same value and different type should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotIn(moneyOf(USD, 0L), moneyOf(USD, 1L), moneyOf(USD, 10L))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotIn(setOf(moneyOf(USD, 0L), moneyOf(USD, 1L), moneyOf(USD, 10L))))
        )
    }

    @Test
    fun `isNotIn vararg Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isNotIn(ZERO, one, TEN)
            }
        }
    }

    @Test
    fun `isNotIn vararg Number with different value should be valid`() {
        val salary = moneyOf(BRL, ZERO)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotIn(one, TEN)
            }
        }
    }

    @Test
    fun `isNotIn vararg Number with same value should be invalid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotIn(ZERO, one, TEN)
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary", value = salary,
                    constraint = NotIn(
                        setOf(
                            moneyOf(USD, ZERO),
                            moneyOf(USD, one),
                            moneyOf(USD, TEN)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `isNotIn vararg Number with same value and 2 decimal digits should be invalid`() {
        val salary = moneyOf(USD, "1.00".toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotIn(ZERO, one, TEN)
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary", value = salary,
                    constraint = NotIn(
                        setOf(
                            moneyOf(USD, ZERO),
                            moneyOf(USD, one),
                            moneyOf(USD, TEN)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotIn(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, ONE), moneyOf(BRL, TEN)))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isNotIn(listOf(moneyOf(USD, ZERO), moneyOf(USD, TEN)))
        }
    }

    @Test
    fun `isNotIn iterable with different currency should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isNotIn(listOf(moneyOf(BRL, ONE), moneyOf(USD, TEN)))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isNotIn(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, ONE), moneyOf(BRL, TEN)))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = NotIn(listOf(moneyOf(BRL, ZERO), moneyOf(BRL, ONE), moneyOf(BRL, TEN))))
        )
    }

    @Test
    fun `isNotIn iterable with same value and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotIn(listOf(moneyOf(USD, ZERO), moneyOf(USD, 1.00.toBigDecimal()), moneyOf(USD, TEN)))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotIn(listOf(moneyOf(USD, ZERO), moneyOf(USD, 1.00.toBigDecimal()), moneyOf(USD, TEN))))
        )
    }

    @Test
    fun `isNotIn iterable with same value and different type should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotIn(listOf(moneyOf(USD, 0.0), moneyOf(USD, 1.00), moneyOf(USD, 10.00)))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = NotIn(listOf(moneyOf(USD, 0.0), moneyOf(USD, 1.0), moneyOf(USD, 10.0))))
        )
    }

    @Test
    fun `isNotIn iterable Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isNotIn(listOf(ZERO, one, TEN))
            }
        }
    }

    @Test
    fun `isNotIn iterable Number with different value should be valid`() {
        val salary = moneyOf(BRL, ZERO)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotIn(listOf(one, TEN))
            }
        }
    }

    @Test
    fun `isNotIn iterable Number with same value should be invalid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotIn(listOf(ZERO, one, TEN))
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary", value = salary,
                    constraint = NotIn(
                        listOf(
                            moneyOf(USD, ZERO),
                            moneyOf(USD, one),
                            moneyOf(USD, TEN)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `isNotIn iterable Number with same value and 2 decimal digits should be invalid`() {
        val salary = moneyOf(USD, "1.00".toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotIn(listOf(ZERO, one, TEN))
                }
            }
            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary", value = salary,
                    constraint = NotIn(
                        listOf(
                            moneyOf(USD, ZERO),
                            moneyOf(USD, one),
                            moneyOf(USD, TEN)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isLessThan(moneyOf(BRL, 10.0.toBigDecimal()))
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 9999.99.toBigDecimal()))) {
            validate(Employee::salary).isLessThan(moneyOf(USD, 10000.00.toBigDecimal()))
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 0.38576.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isLessThan(moneyOf(BRL, 0.3.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 50.0.toBigDecimal()))) {
                validate(Employee::salary).isLessThan(moneyOf(USD, 49.9.toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 50.0.toBigDecimal()),
                constraint = Less(moneyOf(USD, 49.9.toBigDecimal()))
            )
        )
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 50.9.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isLessThan(moneyOf(BRL, 51.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 50.9.unaryMinus().toBigDecimal()),
                constraint = Less(moneyOf(BRL, 51.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).isLessThan(moneyOf(USD, ZERO))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, ZERO),
                constraint = Less(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `isLessThan Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isLessThan(one)
            }
        }
    }

    @Test
    fun `isLessThan Number with less value should be valid`() {
        val salary = moneyOf(USD, 0.99.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isLessThan(one)
            }
        }
    }

    @Test
    fun `isLessThan Number with negative less value should be valid`() {
        val salary = moneyOf(BRL, 1.1.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isLessThan(one)
            }
        }
    }

    @Test
    fun `isLessThan Number with greater value should be invalid`() {
        val salary = moneyOf(USD, 2.0.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isLessThan(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Less(moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isLessThan Number with negative greater value should be invalid`() {
        val salary = moneyOf(BRL, 0.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isLessThan(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Less(moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isLessThan Number with equal value should be invalid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isLessThan(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Less(moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isLessThanOrEqualTo(moneyOf(BRL, 10.0.toBigDecimal()))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 9999.99.toBigDecimal()))) {
            validate(Employee::salary).isLessThanOrEqualTo(moneyOf(USD, 10000.00.toBigDecimal()))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 0.38576.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isLessThanOrEqualTo(moneyOf(BRL, 0.3.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ZERO))) {
            validate(Employee::salary).isLessThanOrEqualTo(moneyOf(USD, ZERO))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 56789.19.toBigDecimal()))) {
                validate(Employee::salary).isLessThanOrEqualTo(moneyOf(BRL, 57.0.toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 56789.19.toBigDecimal()),
                constraint = LessOrEqual(moneyOf(BRL, 57.0.toBigDecimal()))
            )
        )
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 96.0.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isLessThanOrEqualTo(moneyOf(USD, 97.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 96.0.unaryMinus().toBigDecimal()),
                constraint = LessOrEqual(moneyOf(USD, 97.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isLessThanOrEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isLessThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with less value should be valid`() {
        val salary = moneyOf(USD, 0.99.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isLessThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with negative less value should be valid`() {
        val salary = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isLessThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with equal value should be valid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isLessThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with greater value should be invalid`() {
        val salary = moneyOf(BRL, 2.0.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isLessThanOrEqualTo(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = LessOrEqual(moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isLessThanOrEqualTo Number with negative greater value should be invalid`() {
        val salary = moneyOf(USD, 0.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isLessThanOrEqualTo(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = LessOrEqual(moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isGreaterThan(moneyOf(BRL, 10.0.toBigDecimal()))
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 10.1.toBigDecimal()))) {
            validate(Employee::salary).isGreaterThan(moneyOf(USD, 10.0.toBigDecimal()))
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 88.88.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isGreaterThan(moneyOf(BRL, 89.0.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 10.0.toBigDecimal()))) {
                validate(Employee::salary).isGreaterThan(moneyOf(USD, 11.0.toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 10.0.toBigDecimal()),
                constraint = Greater(moneyOf(USD, 11.0.toBigDecimal()))
            )
        )
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 189.20.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isGreaterThan(moneyOf(BRL, 180.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 189.20.unaryMinus().toBigDecimal()),
                constraint = Greater(moneyOf(BRL, 180.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).isGreaterThan(moneyOf(USD, ZERO))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, ZERO),
                constraint = Greater(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `isGreaterThan Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isGreaterThan(one)
            }
        }
    }

    @Test
    fun `isGreaterThan Number with greater value should be valid`() {
        val salary = moneyOf(USD, 2.0.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isGreaterThan(one)
            }
        }
    }

    @Test
    fun `isGreaterThan Number with negative greater value should be valid`() {
        val salary = moneyOf(BRL, 0.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isGreaterThan(one)
            }
        }
    }

    @Test
    fun `isGreaterThan Number with less value should be invalid`() {
        val salary = moneyOf(USD, 0.5.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isGreaterThan(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Greater(moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isGreaterThan Number with negative less value should be invalid`() {
        val salary = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isGreaterThan(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Greater(moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isGreaterThan Number with equal value should be invalid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isGreaterThan(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Greater(moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isGreaterThanOrEqualTo(moneyOf(BRL, 10.0.toBigDecimal()))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 10000.0.toBigDecimal()))) {
            validate(Employee::salary).isGreaterThanOrEqualTo(moneyOf(USD, 9999.99.toBigDecimal()))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 0.3.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isGreaterThanOrEqualTo(moneyOf(BRL, 0.38576.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ZERO))) {
            validate(Employee::salary).isGreaterThanOrEqualTo(moneyOf(USD, ZERO))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 57.0.toBigDecimal()))) {
                validate(Employee::salary).isGreaterThanOrEqualTo(moneyOf(BRL, 56789.19.toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 57.0.toBigDecimal()),
                constraint = GreaterOrEqual(moneyOf(BRL, 56789.19.toBigDecimal()))
            )
        )
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 97.0.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isGreaterThanOrEqualTo(moneyOf(USD, 96.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 97.0.unaryMinus().toBigDecimal()),
                constraint = GreaterOrEqual(moneyOf(USD, 96.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isGreaterThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with greater value should be valid`() {
        val salary = moneyOf(USD, 2.0.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isGreaterThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with negative greater value should be valid`() {
        val salary = moneyOf(BRL, 0.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isGreaterThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with equal value should be valid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isGreaterThanOrEqualTo(one)
            }
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with less value should be invalid`() {
        val salary = moneyOf(BRL, 0.5.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isGreaterThanOrEqualTo(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = GreaterOrEqual(moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo Number with negative less value should be invalid`() {
        val salary = moneyOf(USD, 2.0.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isGreaterThanOrEqualTo(one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = GreaterOrEqual(moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isBetween(start = moneyOf(BRL, 0.99.toBigDecimal()), end = moneyOf(BRL, 9.99.toBigDecimal()))
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ZERO))) {
            validate(Employee::salary).isBetween(start = moneyOf(USD, ZERO), end = moneyOf(USD, ONE))
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isBetween(start = moneyOf(BRL, ZERO), end = moneyOf(BRL, ONE))
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 0.5.toBigDecimal()))) {
            validate(Employee::salary).isBetween(start = moneyOf(USD, ZERO), end = moneyOf(USD, ONE))
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isBetween(start = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 1.0.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isBetween(start = moneyOf(USD, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 1.5.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isBetween(start = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 1.0.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 10.0.toBigDecimal()))) {
                validate(Employee::salary).isBetween(start = moneyOf(USD, 10.1.toBigDecimal()), end = moneyOf(USD, 11.0.toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 10.0.toBigDecimal()),
                constraint = Between(start = moneyOf(USD, 10.1.toBigDecimal()), end = moneyOf(USD, 11.0.toBigDecimal()))
            )
        )
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 12.0.toBigDecimal()))) {
                validate(Employee::salary).isBetween(start = moneyOf(BRL, 10.1.toBigDecimal()), end = moneyOf(BRL, 11.0.toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 12.0.toBigDecimal()),
                constraint = Between(start = moneyOf(BRL, 10.1.toBigDecimal()), end = moneyOf(BRL, 11.0.toBigDecimal()))
            )
        )
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 10.0.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isBetween(start = moneyOf(USD, 9.9.unaryMinus().toBigDecimal()), end = moneyOf(USD, 8.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 10.0.unaryMinus().toBigDecimal()),
                constraint = Between(start = moneyOf(USD, 9.9.unaryMinus().toBigDecimal()), end = moneyOf(USD, 8.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 12.0.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isBetween(start = moneyOf(BRL, 13.0.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 12.9.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 12.0.unaryMinus().toBigDecimal()),
                constraint = Between(start = moneyOf(BRL, 13.0.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 12.9.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isBetween Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isBetween(start = one, end = 2.00)
            }
        }
    }

    @Test
    fun `isBetween Number with equal start value should be valid`() {
        val salary = moneyOf(USD, ONE)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isBetween(start = one, end = 2)
            }
        }
    }

    @Test
    fun `isBetween Number with equal end value should be valid`() {
        val salary = moneyOf(BRL, 2)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isBetween(start = one, end = 2)
            }
        }
    }

    @Test
    fun `isBetween Number with within value should be valid`() {
        val salary = moneyOf(USD, 0.5.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isBetween(start = 0, end = one)
            }
        }
    }

    @Test
    fun `isBetween Number with equal negative start value should be valid`() {
        val salary = moneyOf(BRL, 1.00.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isBetween(start = one, end = 0.5.unaryMinus())
            }
        }
    }

    @Test
    fun `isBetween Number with equal negative end value should be valid`() {
        val salary = moneyOf(USD, 0.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isBetween(start = one, end = 0.5.unaryMinus())
            }
        }
    }

    @Test
    fun `isBetween Number with within negative value should be valid`() {
        val salary = moneyOf(BRL, 1.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isBetween(start = 2.unaryMinus().toLong(), end = one)
            }
        }
    }

    @Test
    fun `isBetween Number with less start value should be invalid`() {
        val salary = moneyOf(USD, 0.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isBetween(start = 0.1, end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Between(start = moneyOf(USD, 0.1), end = moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isBetween Number with greater end value should be invalid`() {
        val salary = moneyOf(BRL, 1.8.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isBetween(start = 0.5, end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Between(start = moneyOf(BRL, 0.5), end = moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isBetween Number with less negative start value should be invalid`() {
        val salary = moneyOf(USD, 2.unaryMinus().toByte())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isBetween(start = one, end = 0.5.unaryMinus())
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Between(start = moneyOf(USD, one), end = moneyOf(USD, 0.5.unaryMinus()))
                )
            )
        }
    }

    @Test
    fun `isBetween Number with greater negative end value should be invalid`() {
        val salary = moneyOf(BRL, ZERO)

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isBetween(start = 2.unaryMinus().toFloat(), end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = Between(start = moneyOf(BRL, 2.unaryMinus().toFloat()), end = moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotBetween(start = moneyOf(USD, 0.99.toBigDecimal()), end = moneyOf(USD, 9.99.toBigDecimal()))
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 10.0.toBigDecimal()))) {
            validate(Employee::salary).isNotBetween(start = moneyOf(BRL, 10.1.toBigDecimal()), end = moneyOf(BRL, 11.0.toBigDecimal()))
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 12.0.toBigDecimal()))) {
            validate(Employee::salary).isNotBetween(start = moneyOf(USD, 10.1.toBigDecimal()), end = moneyOf(USD, 11.0.toBigDecimal()))
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 10.0.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isNotBetween(start = moneyOf(BRL, 9.9.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 8.0.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 12.0.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isNotBetween(start = moneyOf(USD, 13.0.unaryMinus().toBigDecimal()), end = moneyOf(USD, 12.9.unaryMinus().toBigDecimal()))
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ZERO))) {
                validate(Employee::salary).isNotBetween(start = moneyOf(BRL, ZERO), end = moneyOf(BRL, ONE))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, ZERO),
                constraint = NotBetween(start = moneyOf(BRL, ZERO), end = moneyOf(BRL, ONE))
            )
        )
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNotBetween(start = moneyOf(USD, ZERO), end = moneyOf(USD, ONE))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, ONE),
                constraint = NotBetween(start = moneyOf(USD, ZERO), end = moneyOf(USD, ONE))
            )
        )
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isNotBetween(start = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 1.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()),
                constraint = NotBetween(start = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(BRL, 1.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isNotBetween(start = moneyOf(USD, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()),
                constraint = NotBetween(start = moneyOf(USD, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 0.5.toBigDecimal()))) {
                validate(Employee::salary).isNotBetween(start = moneyOf(BRL, ZERO), end = moneyOf(BRL, ONE))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 0.5.toBigDecimal()),
                constraint = NotBetween(start = moneyOf(BRL, ZERO), end = moneyOf(BRL, ONE))
            )
        )
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 1.5.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isNotBetween(start = moneyOf(USD, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 1.5.unaryMinus().toBigDecimal()),
                constraint = NotBetween(start = moneyOf(USD, 2.0.unaryMinus().toBigDecimal()), end = moneyOf(USD, 1.0.unaryMinus().toBigDecimal()))
            )
        )
    }

    @Test
    fun `isNotBetween Number with null value should be valid`() {
        ONE_NUMBERS.forEach { one ->
            validate(Employee()) {
                validate(Employee::salary).isNotBetween(start = one, end = 2)
            }
        }
    }

    @Test
    fun `isNotBetween Number with less start value should be valid`() {
        val salary = moneyOf(BRL, 0.5)

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotBetween(start = one, end = 2.0f)
            }
        }
    }

    @Test
    fun `isNotBetween Number with greater end value should be valid`() {
        val salary = moneyOf(USD, 2.5.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotBetween(start = one, end = 2)
            }
        }
    }

    @Test
    fun `isNotBetween Number with less negative start value should be valid`() {
        val salary = moneyOf(BRL, 2.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotBetween(start = one, end = 0.5.unaryMinus())
            }
        }
    }

    @Test
    fun `isNotBetween Number with greater negative end value should be valid`() {
        val salary = moneyOf(USD, ONE)

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            validate(Employee(salary = salary)) {
                validate(Employee::salary).isNotBetween(start = one, end = ZERO)
            }
        }
    }

    @Test
    fun `isNotBetween Number with equal start value should be invalid`() {
        val salary = moneyOf(BRL, ONE)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotBetween(start = one, end = 2)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = NotBetween(start = moneyOf(BRL, one), end = moneyOf(BRL, 2))
                )
            )
        }
    }

    @Test
    fun `isNotBetween Number with equal end value should be invalid`() {
        val salary = moneyOf(USD, 2)

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotBetween(start = one, end = 2)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = NotBetween(start = moneyOf(USD, one), end = moneyOf(USD, 2))
                )
            )
        }
    }

    @Test
    fun `isNotBetween Number with equal negative start value should be invalid`() {
        val salary = moneyOf(BRL, 2.0.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotBetween(start = -2, end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = NotBetween(start = moneyOf(BRL, -2), end = moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isNotBetween Number with equal negative end value should be invalid`() {
        val salary = moneyOf(USD, 1.0.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotBetween(start = -2, end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = NotBetween(start = moneyOf(USD, -2), end = moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `isNotBetween Number with within value should be invalid`() {
        val salary = moneyOf(BRL, 0.5.toBigDecimal())

        ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotBetween(start = ZERO, end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = NotBetween(start = moneyOf(BRL, ZERO), end = moneyOf(BRL, one))
                )
            )
        }
    }

    @Test
    fun `isNotBetween Number with within negative value should be invalid`() {
        val salary = moneyOf(USD, 1.5.unaryMinus().toBigDecimal())

        NEGATIVE_ONE_NUMBERS.forEach { one ->
            val exception = assertFailsWith<ConstraintViolationException> {
                validate(Employee(salary = salary)) {
                    validate(Employee::salary).isNotBetween(start = -2, end = one)
                }
            }

            assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
                DefaultConstraintViolation(
                    property = "salary",
                    value = salary,
                    constraint = NotBetween(start = moneyOf(USD, -2), end = moneyOf(USD, one))
                )
            )
        }
    }

    @Test
    fun `hasCurrencyEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasCurrencyEqualTo(BRL)
        }
    }

    @Test
    fun `hasCurrencyEqualTo with same value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).hasCurrencyEqualTo(USD)
        }
    }

    @Test
    fun `hasCurrencyEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).hasCurrencyEqualTo(BRL)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ZERO), constraint = CurrencyEquals(BRL))
        )
    }

    @Test
    fun `hasCurrencyNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasCurrencyNotEqualTo(BRL)
        }
    }

    @Test
    fun `hasCurrencyNotEqualTo with different value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).hasCurrencyNotEqualTo(BRL)
        }
    }

    @Test
    fun `hasCurrencyNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).hasCurrencyNotEqualTo(BRL)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = CurrencyNotEquals(BRL))
        )
    }

    @Test
    fun `hasCurrencyIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasCurrencyIn(BRL, USD)
        }
    }

    @Test
    fun `hasCurrencyIn vararg with same value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).hasCurrencyIn(BRL, USD)
        }
    }

    @Test
    fun `hasCurrencyIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).hasCurrencyIn(BRL)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = CurrencyIn(setOf(BRL)))
        )
    }

    @Test
    fun `hasCurrencyIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasCurrencyIn(listOf(BRL, USD))
        }
    }

    @Test
    fun `hasCurrencyIn iterable with same value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).hasCurrencyIn(listOf(BRL, USD))
        }
    }

    @Test
    fun `hasCurrencyIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).hasCurrencyIn(listOf(USD))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = CurrencyIn(listOf(USD)))
        )
    }

    @Test
    fun `hasCurrencyNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasCurrencyNotIn(USD, BRL)
        }
    }

    @Test
    fun `hasCurrencyNotIn vararg with different value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).hasCurrencyNotIn(USD)
        }
    }

    @Test
    fun `hasCurrencyNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).hasCurrencyNotIn(USD, BRL)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(USD, ONE), constraint = CurrencyNotIn(setOf(USD, BRL)))
        )
    }

    @Test
    fun `hasCurrencyNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasCurrencyNotIn(listOf(USD, BRL))
        }
    }

    @Test
    fun `hasCurrencyNotIn iterable with different value should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).hasCurrencyNotIn(listOf(BRL))
        }
    }

    @Test
    fun `hasCurrencyNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).hasCurrencyNotIn(listOf(USD, BRL))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "salary", value = moneyOf(BRL, ONE), constraint = CurrencyNotIn(listOf(USD, BRL)))
        )
    }

    @Test
    fun `isZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with zero should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ZERO))) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with zero and 2 decimal digits should be valid`() {
        validate(Employee(salary = moneyOf(USD, 0.00.toBigDecimal()))) {
            validate(Employee::salary).isZero()
        }
    }

    @Test
    fun `isZero with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, ONE),
                constraint = Equals(moneyOf(BRL, ZERO))
            )
        )
    }

    @Test
    fun `isNotZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotZero()
        }
    }

    @Test
    fun `isNotZero with one should be valid`() {
        validate(Employee(salary = moneyOf(USD, ONE))) {
            validate(Employee::salary).isNotZero()
        }
    }

    @Test
    fun `isNotZero with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ZERO))) {
                validate(Employee::salary).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, ZERO),
                constraint = NotEquals(moneyOf(BRL, ZERO))
            )
        )
    }

    @Test
    fun `isNotZero with zero and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 0.00.toBigDecimal()))) {
                validate(Employee::salary).isNotZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 0.00.toBigDecimal()),
                constraint = NotEquals(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `isOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with one should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with one and 2 decimal digits should be valid`() {
        validate(Employee(salary = moneyOf(USD, 1.00.toBigDecimal()))) {
            validate(Employee::salary).isOne()
        }
    }

    @Test
    fun `isOne with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ZERO))) {
                validate(Employee::salary).isOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, ZERO),
                constraint = Equals(moneyOf(BRL, ONE))
            )
        )
    }

    @Test
    fun `isNotOne with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNotOne()
        }
    }

    @Test
    fun `isNotOne with zero should be valid`() {
        validate(Employee(salary = moneyOf(USD, ZERO))) {
            validate(Employee::salary).isNotOne()
        }
    }

    @Test
    fun `isNotOne with one should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, ONE),
                constraint = NotEquals(moneyOf(BRL, ONE))
            )
        )
    }

    @Test
    fun `isNotOne with one and 2 decimal digits should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 1.00.toBigDecimal()))) {
                validate(Employee::salary).isNotOne()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 1.00.toBigDecimal()),
                constraint = NotEquals(moneyOf(USD, ONE))
            )
        )
    }

    @Test
    fun `isPositive with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isPositive()
        }
    }

    @Test
    fun `isPositive with positive value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isPositive()
        }
    }

    @Test
    fun `isPositive with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, ZERO),
                constraint = Greater(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `isPositive with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 98765.432.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isPositive()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 98765.432.unaryMinus().toBigDecimal()),
                constraint = Greater(moneyOf(BRL, ZERO))
            )
        )
    }

    @Test
    fun `isNegativeOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with zero should be valid`() {
        validate(Employee(salary = moneyOf(USD, ZERO))) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with negative value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 98765.432.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isNegativeOrZero()
        }
    }

    @Test
    fun `isNegativeOrZero with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ONE))) {
                validate(Employee::salary).isNegativeOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, ONE),
                constraint = LessOrEqual(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `isNegative with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isNegative()
        }
    }

    @Test
    fun `isNegative with negative value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 1.0.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).isNegative()
        }
    }

    @Test
    fun `isNegative with zero should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, ZERO))) {
                validate(Employee::salary).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, ZERO),
                constraint = Less(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `isNegative with positive value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, ONE))) {
                validate(Employee::salary).isNegative()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, ONE),
                constraint = Less(moneyOf(BRL, ZERO))
            )
        )
    }

    @Test
    fun `isPositiveOrZero with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with zero should be valid`() {
        validate(Employee(salary = moneyOf(USD, ZERO))) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with positive value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, ONE))) {
            validate(Employee::salary).isPositiveOrZero()
        }
    }

    @Test
    fun `isPositiveOrZero with negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 98765.432.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).isPositiveOrZero()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 98765.432.unaryMinus().toBigDecimal()),
                constraint = GreaterOrEqual(moneyOf(USD, ZERO))
            )
        )
    }

    @Test
    fun `hasIntegerDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasIntegerDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasIntegerDigits with valid min value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 9999.99.toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits(min = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with valid max value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 9999.99.toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits(max = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with valid min and max value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 9999.99.toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid min value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 99999.99.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits(min = 5)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid max value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 99999.99.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits(max = 5)
        }
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 99999.99.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits(min = 5, max = 5)
        }
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 9999.99.toBigDecimal()))) {
            validate(Employee::salary).hasIntegerDigits()
        }
    }

    @Test
    fun `hasIntegerDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 748536.78.toBigDecimal()))) {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 748536.78.toBigDecimal()),
                constraint = IntegerDigits(min = 7)
            )
        )
    }

    @Test
    fun `hasIntegerDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 748536.78.toBigDecimal()))) {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 748536.78.toBigDecimal()),
                constraint = IntegerDigits(max = 5)
            )
        )
    }

    @Test
    fun `hasIntegerDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 748536.78.toBigDecimal()))) {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 748536.78.toBigDecimal()),
                constraint = IntegerDigits(min = 7, max = 5)
            )
        )
    }

    @Test
    fun `hasIntegerDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 748536.78.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).hasIntegerDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 748536.78.unaryMinus().toBigDecimal()),
                constraint = IntegerDigits(min = 7)
            )
        )
    }

    @Test
    fun `hasIntegerDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 748536.78.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).hasIntegerDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 748536.78.unaryMinus().toBigDecimal()),
                constraint = IntegerDigits(max = 5)
            )
        )
    }

    @Test
    fun `hasIntegerDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 748536.78.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).hasIntegerDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 748536.78.unaryMinus().toBigDecimal()),
                constraint = IntegerDigits(min = 7, max = 5)
            )
        )
    }

    @Test
    fun `hasDecimalDigits with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::salary).hasDecimalDigits(min = 1, max = 10)
        }
    }

    @Test
    fun `hasDecimalDigits with valid min value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 99.9999.toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits(min = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with valid max value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 99.9999.toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits(max = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with valid min and max value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 99.9999.toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits(min = 4, max = 4)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid min value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 99.99999.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits(min = 5)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid max value should be valid`() {
        validate(Employee(salary = moneyOf(USD, 99.99999.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits(max = 5)
        }
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max value should be valid`() {
        validate(Employee(salary = moneyOf(BRL, 99.99999.unaryMinus().toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits(min = 5, max = 5)
        }
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Employee(salary = moneyOf(USD, 99.9999.toBigDecimal()))) {
            validate(Employee::salary).hasDecimalDigits()
        }
    }

    @Test
    fun `hasDecimalDigits with less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 78.748536.toBigDecimal()))) {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 78.748536.toBigDecimal()),
                constraint = DecimalDigits(min = 7)
            )
        )
    }

    @Test
    fun `hasDecimalDigits with greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 78.748536.toBigDecimal()))) {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 78.748536.toBigDecimal()),
                constraint = DecimalDigits(max = 5)
            )
        )
    }

    @Test
    fun `hasDecimalDigits with less value and greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 78.748536.toBigDecimal()))) {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 78.748536.toBigDecimal()),
                constraint = DecimalDigits(min = 7, max = 5)
            )
        )
    }

    @Test
    fun `hasDecimalDigits with negative less min value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 78.748536.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).hasDecimalDigits(min = 7)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 78.748536.unaryMinus().toBigDecimal()),
                constraint = DecimalDigits(min = 7)
            )
        )
    }

    @Test
    fun `hasDecimalDigits with negative greater max value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(USD, 78.748536.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).hasDecimalDigits(max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(USD, 78.748536.unaryMinus().toBigDecimal()),
                constraint = DecimalDigits(max = 5)
            )
        )
    }

    @Test
    fun `hasDecimalDigits with negative less value and negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(salary = moneyOf(BRL, 78.748536.unaryMinus().toBigDecimal()))) {
                validate(Employee::salary).hasDecimalDigits(min = 7, max = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "salary",
                value = moneyOf(BRL, 78.748536.unaryMinus().toBigDecimal()),
                constraint = DecimalDigits(min = 7, max = 5)
            )
        )
    }
}
