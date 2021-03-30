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

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.Validator
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.Empty
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.constraints.Size
import org.valiktor.constraints.Valid
import org.valiktor.functions.ArrayFunctionsFixture.Dependent
import org.valiktor.functions.ArrayFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private object ArrayFunctionsFixture {

    data class Employee(val dependents: Array<Dependent>? = null)
    data class Dependent(val id: Int? = null, val name: String? = null)
}

@ExperimentalCoroutinesApi
class ArrayFunctionsTest {

    @Test
    fun `inner null array properties should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).validateForEach {
                validate(Dependent::id).isNotNull()
            }
        }
    }

    @Test
    fun `inner array properties should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 1), Dependent(id = 1)))) {
            validate(Employee::dependents).validateForEach {
                validate(Dependent::id).isNotNull()
            }
        }
    }

    @Test
    fun `inner array properties should call suspending validation functions`() {
        suspend fun Validator<Dependent>.Property<Int?>.isValidId() = this.coValidate(Valid) {
            delay(10L)
            it == null || it > 0
        }

        runBlockingTest {
            validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 1), Dependent(id = 1)))) {
                validate(Employee::dependents).validateForEach {
                    validate(Dependent::id).isValidId()
                }
            }
        }
    }

    @Test
    fun `inner array properties should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = arrayOf(Dependent(), Dependent(), Dependent()))) {
                validate(Employee::dependents).validateForEach {
                    validate(Dependent::id).isNotNull()
                }
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(property = "dependents[0].id", constraint = NotNull),
            DefaultConstraintViolation(property = "dependents[1].id", constraint = NotNull),
            DefaultConstraintViolation(property = "dependents[2].id", constraint = NotNull)
        )
    }

    @Test
    fun `should receive the current value as function parameter`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            var id = 1
            validate(Employee::dependents).validateForEach { dependent ->
                assertEquals(dependent, Dependent(id = id))
                id++
            }
        }
    }

    @Test
    fun `should receive the current index and value as function parameter`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).validateForEachIndexed { index, dependent ->
                assertEquals(dependent, Dependent(id = index + 1))
            }
        }
    }

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Null
            )
        )
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(dependents = emptyArray())) {
            validate(Employee::dependents).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::dependents).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                constraint = NotNull
            )
        )
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isEqualTo(arrayOf(Dependent(id = 1)))
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2)))) {
            validate(Employee::dependents).isEqualTo(arrayOf(Dependent(id = 1), Dependent(id = 2)))
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2))
        val constraintDependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isEqualTo(constraintDependents)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Equals(constraintDependents)
            )
        )
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isNotEqualTo(arrayOf(Dependent(id = 1), Dependent(id = 2)))
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2)))) {
            validate(Employee::dependents).isNotEqualTo(arrayOf(Dependent(id = 1)))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2))
        val constraintDependents = arrayOf(Dependent(id = 1), Dependent(id = 2))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isNotEqualTo(constraintDependents)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotEquals(constraintDependents)
            )
        )
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isIn(arrayOf(Dependent(id = 1)))
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1)))) {
            validate(Employee::dependents).isIn(
                arrayOf(Dependent(id = 1)),
                arrayOf(Dependent(id = 1), Dependent(id = 2))
            )
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val dependents = emptyArray<Dependent>()
        val constraintDependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isIn(constraintDependents)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = In(setOf(constraintDependents))
            )
        )
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isIn(listOf(arrayOf(Dependent(id = 1))))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1)))) {
            validate(Employee::dependents).isIn(
                listOf(
                    arrayOf(Dependent(id = 1)),
                    arrayOf(Dependent(id = 1), Dependent(id = 2))
                )
            )
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val dependents = emptyArray<Dependent>()
        val constraintDependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isIn(listOf(constraintDependents))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = In(listOf(constraintDependents))
            )
        )
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isNotIn(arrayOf(Dependent(id = 1)))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be valid`() {
        validate(Employee(dependents = emptyArray())) {
            validate(Employee::dependents).isNotIn(arrayOf(Dependent(id = 1), Dependent(id = 2)))
        }
    }

    @Test
    fun `isNotIn vararg with different value should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1))
        val constraintDependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isNotIn(constraintDependents)
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotIn(setOf(constraintDependents))
            )
        )
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isNotIn(listOf(arrayOf(Dependent(id = 1))))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be valid`() {
        validate(Employee(dependents = emptyArray())) {
            validate(Employee::dependents).isNotIn(listOf(arrayOf(Dependent(id = 1), Dependent(id = 2))))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1))
        val constraintDependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isNotIn(listOf(constraintDependents))
            }
        }
        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotIn(listOf(constraintDependents))
            )
        )
    }

    @Test
    fun `isEmpty with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isEmpty()
        }
    }

    @Test
    fun `isEmpty with empty property should be valid`() {
        validate(Employee(dependents = emptyArray())) {
            validate(Employee::dependents).isEmpty()
        }
    }

    @Test
    fun `isEmpty with not empty property should be invalid`() {
        val dependents = arrayOf(Dependent())

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Empty
            )
        )
    }

    @Test
    fun `isNotEmpty with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).isNotEmpty()
        }
    }

    @Test
    fun `isNotEmpty with not empty property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent()))) {
            validate(Employee::dependents).isNotEmpty()
        }
    }

    @Test
    fun `isNotEmpty with empty property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).isNotEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotEmpty
            )
        )
    }

    @Test
    fun `size with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).hasSize(min = 1, max = 10)
        }
    }

    @Test
    fun `size with valid min length property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent()))) {
            validate(Employee::dependents).hasSize(min = 1)
        }
    }

    @Test
    fun `size with valid max length property should be valid`() {
        validate(Employee(dependents = emptyArray())) {
            validate(Employee::dependents).hasSize(max = 4)
        }
    }

    @Test
    fun `size with valid min and max length property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent()))) {
            validate(Employee::dependents).hasSize(min = 1, max = 2)
        }
    }

    @Test
    fun `size without min and max should be valid`() {
        validate(Employee(dependents = emptyArray())) {
            validate(Employee::dependents).hasSize()
        }
    }

    @Test
    fun `size with invalid min size property should be invalid`() {
        val dependents = arrayOf(Dependent(), Dependent())

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).hasSize(min = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Size(min = 5)
            )
        )
    }

    @Test
    fun `size with invalid max size property should be invalid`() {
        val dependents = arrayOf(Dependent(), Dependent())

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).hasSize(max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Size(max = 1)
            )
        )
    }

    @Test
    fun `size with invalid min and max size property should be invalid`() {
        val dependents = arrayOf(Dependent(), Dependent())

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).hasSize(min = 3, max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Size(min = 3, max = 1)
            )
        )
    }

    @Test
    fun `contains with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).contains(Dependent())
        }
    }

    @Test
    fun `contains with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2)))) {
            validate(Employee::dependents).contains(Dependent(id = 1))
        }
    }

    @Test
    fun `contains with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).contains(Dependent(id = 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = Contains(Dependent(id = 1))
            )
        )
    }

    @Test
    fun `containsAll vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).containsAll(Dependent())
        }
    }

    @Test
    fun `containsAll vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).containsAll(Dependent(id = 1), Dependent(id = 2))
        }
    }

    @Test
    fun `containsAll vararg with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).containsAll(Dependent(id = 1), Dependent(id = 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = ContainsAll(setOf(Dependent(id = 1), Dependent(id = 2)))
            )
        )
    }

    @Test
    fun `containsAll iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).containsAll(listOf(Dependent()))
        }
    }

    @Test
    fun `containsAll iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).containsAll(listOf(Dependent(id = 1), Dependent(id = 2)))
        }
    }

    @Test
    fun `containsAll iterable with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).containsAll(listOf(Dependent(id = 1), Dependent(id = 2)))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = ContainsAll(listOf(Dependent(id = 1), Dependent(id = 2)))
            )
        )
    }

    @Test
    fun `containsAny vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).containsAny(Dependent())
        }
    }

    @Test
    fun `containsAny vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 3)))) {
            validate(Employee::dependents).containsAny(Dependent(id = 1), Dependent(id = 2))
        }
    }

    @Test
    fun `containsAny vararg with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).containsAny(Dependent(id = 1), Dependent(id = 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = ContainsAny(setOf(Dependent(id = 1), Dependent(id = 2)))
            )
        )
    }

    @Test
    fun `containsAny iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).containsAny(listOf(Dependent()))
        }
    }

    @Test
    fun `containsAny iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 3)))) {
            validate(Employee::dependents).containsAny(listOf(Dependent(id = 1), Dependent(id = 2)))
        }
    }

    @Test
    fun `containsAny iterable with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).containsAny(listOf(Dependent(id = 1), Dependent(id = 2)))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = ContainsAny(listOf(Dependent(id = 1), Dependent(id = 2)))
            )
        )
    }

    @Test
    fun `doesNotContain with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).doesNotContain(Dependent())
        }
    }

    @Test
    fun `doesNotContain with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2)))) {
            validate(Employee::dependents).doesNotContain(Dependent(id = 3))
        }
    }

    @Test
    fun `doesNotContain with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).doesNotContain(Dependent(id = 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotContain(Dependent(id = 1))
            )
        )
    }

    @Test
    fun `doesNotContainAll vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).doesNotContainAll(Dependent())
        }
    }

    @Test
    fun `doesNotContainAll vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).doesNotContainAll(Dependent(id = 1), Dependent(id = 5))
        }
    }

    @Test
    fun `doesNotContainAll vararg with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).doesNotContainAll(Dependent(id = 1), Dependent(id = 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotContainAll(setOf(Dependent(id = 1), Dependent(id = 2)))
            )
        )
    }

    @Test
    fun `doesNotContainAll iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).doesNotContainAll(listOf(Dependent()))
        }
    }

    @Test
    fun `doesNotContainAll iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).doesNotContainAll(listOf(Dependent(id = 1), Dependent(id = 5)))
        }
    }

    @Test
    fun `doesNotContainAll iterable with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).doesNotContainAll(listOf(Dependent(id = 1), Dependent(id = 2)))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotContainAll(listOf(Dependent(id = 1), Dependent(id = 2)))
            )
        )
    }

    @Test
    fun `doesNotContainAny vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).doesNotContainAny(Dependent())
        }
    }

    @Test
    fun `doesNotContainAny vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).doesNotContainAny(Dependent(id = 4), Dependent(id = 5))
        }
    }

    @Test
    fun `doesNotContainAny vararg with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).doesNotContainAny(Dependent(id = 1), Dependent(id = 5))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotContainAny(setOf(Dependent(id = 1), Dependent(id = 5)))
            )
        )
    }

    @Test
    fun `doesNotContainAny iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::dependents).doesNotContainAny(listOf(Dependent()))
        }
    }

    @Test
    fun `doesNotContainAny iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3)))) {
            validate(Employee::dependents).doesNotContainAny(listOf(Dependent(id = 4), Dependent(id = 5)))
        }
    }

    @Test
    fun `doesNotContainAny iterable with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(dependents = dependents)) {
                validate(Employee::dependents).doesNotContainAny(listOf(Dependent(id = 1), Dependent(id = 5)))
            }
        }

        assertThat(exception.constraintViolations).containsExactlyInAnyOrder(
            DefaultConstraintViolation(
                property = "dependents",
                value = dependents,
                constraint = NotContainAny(listOf(Dependent(id = 1), Dependent(id = 5)))
            )
        )
    }
}
