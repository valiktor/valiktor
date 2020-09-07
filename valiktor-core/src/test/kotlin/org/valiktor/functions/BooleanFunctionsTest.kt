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
import org.valiktor.Constraint
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.BooleanFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object BooleanFunctionsFixture {

    data class Employee(val active: Boolean? = null) {

        fun isActive(): Boolean? = this.active
    }
}

class BooleanFunctionsTest {

    private val names = setOf("active", "isActive")

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNull()
            validate(Employee::isActive).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isNull()
                validate(Employee::isActive).isNull()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(true, Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(active = true)) {
            validate(Employee::active).isNotNull()
            validate(Employee::isActive).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::active).isNotNull()
                validate(Employee::isActive).isNotNull()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(null, NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isEqualTo(true)
            validate(Employee::isActive).isEqualTo(true)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(active = true)) {
            validate(Employee::active).isEqualTo(true)
            validate(Employee::isActive).isEqualTo(true)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isEqualTo(false)
                validate(Employee::isActive).isEqualTo(false)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(true, Equals(false)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNotEqualTo(false)
            validate(Employee::isActive).isNotEqualTo(false)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isNotEqualTo(true)
            validate(Employee::isActive).isNotEqualTo(true)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isNotEqualTo(false)
                validate(Employee::isActive).isNotEqualTo(false)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(false, NotEquals(false)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isIn(true, false)
            validate(Employee::isActive).isIn(true, false)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isIn(true, false)
            validate(Employee::isActive).isIn(true, false)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isIn(false)
                validate(Employee::isActive).isIn(false)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(true, In(setOf(false))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isIn(listOf(true, false))
            validate(Employee::isActive).isIn(listOf(true, false))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isIn(listOf(true, false))
            validate(Employee::isActive).isIn(listOf(true, false))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isIn(listOf(false))
                validate(Employee::isActive).isIn(listOf(false))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(true, In(listOf(false))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNotIn(false, true)
            validate(Employee::isActive).isNotIn(false, true)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isNotIn(true)
            validate(Employee::isActive).isNotIn(true)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isNotIn(true, false)
                validate(Employee::isActive).isNotIn(true, false)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(false, NotIn(setOf(true, false))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isNotIn(listOf(false, true))
            validate(Employee::isActive).isNotIn(listOf(false, true))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isNotIn(listOf(true))
            validate(Employee::isActive).isNotIn(listOf(true))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isNotIn(listOf(true, false))
                validate(Employee::isActive).isNotIn(listOf(true, false))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(false, NotIn(listOf(true, false))))
    }

    @Test
    fun `isTrue with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isTrue()
            validate(Employee::isActive).isTrue()
        }
    }

    @Test
    fun `isTrue with true should be valid`() {
        validate(Employee(active = true)) {
            validate(Employee::active).isTrue()
            validate(Employee::isActive).isTrue()
        }
    }

    @Test
    fun `isTrue with false should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = false)) {
                validate(Employee::active).isTrue()
                validate(Employee::isActive).isTrue()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(false, True))
    }

    @Test
    fun `isFalse with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::active).isFalse()
            validate(Employee::isActive).isFalse()
        }
    }

    @Test
    fun `isFalse with false should be valid`() {
        validate(Employee(active = false)) {
            validate(Employee::active).isFalse()
            validate(Employee::isActive).isFalse()
        }
    }

    @Test
    fun `isFalse with true should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(active = true)) {
                validate(Employee::active).isFalse()
                validate(Employee::isActive).isFalse()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(*expectedViolations(true, False))
    }

    private fun expectedViolations(
        value: Boolean?,
        constraint: Constraint
    ): Array<DefaultConstraintViolation> = names.map {
        DefaultConstraintViolation(property = it, value = value, constraint = constraint)
    }.toTypedArray()
}
