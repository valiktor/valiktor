package org.valiktor.functions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.functions.MapFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object MapFunctionsFixture {

    data class Employee(val metadata: Map<String, String>? = null)
}

@ExperimentalCoroutinesApi
class MapFunctionsTest {

    @Test
    fun `isNull with null map should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).isNull()
        }
    }

    @Test
    fun `isNull with not null map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf("data" to "value"))) {
                validate(Employee::metadata).isNull()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf("data" to "value"),
                constraint = Null))
    }

    @Test
    fun `isNotNull with not null map should be valid`() {
        validate(Employee(metadata = mapOf("data" to "value"))) {
            validate(Employee::metadata).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::metadata).isNotNull()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = null,
                constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null map should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).isEqualTo(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isEqualTo with same map should be valid`() {
        validate(Employee(mapOf("data" to "value"))) {
            validate(Employee::metadata).isEqualTo(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isEqualTo with different key on map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(mapOf("differentKey" to "value"))) {
                validate(Employee::metadata).isEqualTo(mapOf("data" to "value"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf("differentKey" to "value"),
                constraint = Equals(mapOf("data" to "value"))))
    }

    @Test
    fun `isEqualTo with different value on map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(mapOf("data" to "differentValue"))) {
                validate(Employee::metadata).isEqualTo(mapOf("data" to "value"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf("data" to "differentValue"),
                constraint = Equals(mapOf("data" to "value"))))
    }

    @Test
    fun `isNotEqualTo with null map should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).isNotEqualTo(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isNotEqualTo with same map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(mapOf("data" to "value"))) {
                validate(Employee::metadata).isNotEqualTo(mapOf("data" to "value"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf("data" to "value"),
                constraint = NotEquals(mapOf("data" to "value"))))
    }

    @Test
    fun `isNotEqualTo with different key on map should be valid`() {
        validate(Employee(mapOf("differentKey" to "value"))) {
            validate(Employee::metadata).isNotEqualTo(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isNotEqualTo with different value on map should be valid`() {
        validate(Employee(mapOf("data" to "differentValue"))) {
            validate(Employee::metadata).isNotEqualTo(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isIn vararg with null map should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).isIn(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isIn vararg with same map should be valid`() {
        validate(Employee(metadata = mapOf("data" to "value"))) {
            validate(Employee::metadata).isIn(mapOf("data" to "value"))
        }
    }

    @Test
    fun `isIn vararg with empty map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).isIn(mapOf("data" to "value"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = In(setOf(mapOf("data" to "value")))))
    }

    @Test
    fun `isIn vararg with different key should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf("differentKey" to "value"))) {
                validate(Employee::metadata).isIn(mapOf("data" to "value"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf("differentKey" to "value"),
                constraint = In(setOf(mapOf("data" to "value")))))
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf("data" to "differentValue"))) {
                validate(Employee::metadata).isIn(mapOf("data" to "value"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf("data" to "differentValue"),
                constraint = In(setOf(mapOf("data" to "value")))))
    }

    @Test
    fun `isEmpty with null property should be valid`() {
        validate(Employee(metadata = mapOf())) {
            validate(Employee::metadata).isEmpty()
        }
    }

    @Test
    fun `isNotEmpty with null property should be valid`() {
        validate(Employee(metadata = mapOf("data" to "value"))) {
            validate(Employee::metadata).isNotEmpty()
        }
    }

    @Test
    fun `isNotEmpty with empty property should be invalid`() {

        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).isNotEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = NotEmpty))
    }

}