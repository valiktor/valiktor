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
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Equals
import org.valiktor.constraints.False
import org.valiktor.constraints.In
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.constraints.True
import org.valiktor.functions.BooleanFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object BooleanFunctionsFixture {

    data class Employee(val active: Boolean? = null)
}

class BooleanFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = true, constraint = Null)
        )
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(active = true)) {
            validate(Employee::active).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::active).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", constraint = NotNull)
        )
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isEqualTo(true)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(active = true)) {
            validate(Employee::active).isEqualTo(true)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isEqualTo(false)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = true, constraint = Equals(false))
        )
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNotEqualTo(false)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isNotEqualTo(true)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isNotEqualTo(false)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = false, constraint = NotEquals(false))
        )
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isIn(true, false)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isIn(true, false)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isIn(false)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = true, constraint = In(setOf(false)))
        )
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isIn(listOf(true, false))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isIn(listOf(true, false))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isIn(listOf(false))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = true, constraint = In(listOf(false)))
        )
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNotIn(false, true)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isNotIn(true)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isNotIn(true, false)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = false, constraint = NotIn(setOf(true, false)))
        )
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNotIn(listOf(false, true))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isNotIn(listOf(true))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isNotIn(listOf(true, false))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "active", value = false, constraint = NotIn(listOf(true, false)))
        )
    }

    @Test
    fun `isTrue with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isTrue()
        }
    }

    @Test
    fun `isTrue with true should be valid`() {
        validate(Employee(active = true)) {
            validate(Employee::active).isTrue()
        }
    }

    @Test
    fun `isTrue with false should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isTrue()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "active",
                value = false,
                constraint = True
            )
        )
    }

    @Test
    fun `isFalse with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isFalse()
        }
    }

    @Test
    fun `isFalse with false should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isFalse()
        }
    }

    @Test
    fun `isFalse with true should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isFalse()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "active",
                value = true,
                constraint = False
            )
        )
    }
}
