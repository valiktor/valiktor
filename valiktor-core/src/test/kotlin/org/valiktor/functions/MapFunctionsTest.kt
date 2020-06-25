package org.valiktor.functions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.functions.MapFunctionsFixture.Employee
import org.valiktor.functions.MapFunctionsFixture.updatedAtMetadata
import org.valiktor.functions.MapFunctionsFixture.createdAtMetadata
import org.valiktor.functions.MapFunctionsFixture.startedPositionMetadata
import org.valiktor.functions.MapFunctionsFixture.currentPositionMetadata
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object MapFunctionsFixture {

    data class Employee(val metadata: Map<String, String>? = null)
    val createdAtMetadata = "createdAt" to "2020-02-20 10:05Z"
    val updatedAtMetadata = "updatedAt" to "2001-01-01 15:48Z"
    val startedPositionMetadata = "startedOn" to "BackEnd Development"
    val currentPositionMetadata = "currentOn" to "BackEnd Development II"
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
            validate(Employee(metadata = mapOf(startedPositionMetadata))) {
                validate(Employee::metadata).isNull()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(startedPositionMetadata),
                constraint = Null))
    }

    @Test
    fun `isNotNull with not null map should be valid`() {
        validate(Employee(metadata = mapOf(startedPositionMetadata))) {
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
            validate(Employee::metadata).isEqualTo(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isEqualTo with same map should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata))) {
            validate(Employee::metadata).isEqualTo(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isEqualTo with different key on map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(mapOf(startedPositionMetadata.copy(first = "endedOn")))) {
                validate(Employee::metadata).isEqualTo(mapOf(startedPositionMetadata))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(startedPositionMetadata.copy(first = "endedOn")),
                constraint = Equals(mapOf(startedPositionMetadata))))
    }

    @Test
    fun `isEqualTo with different value on map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(mapOf(startedPositionMetadata.copy(second = "Manager")))) {
                validate(Employee::metadata).isEqualTo(mapOf(startedPositionMetadata))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(startedPositionMetadata.copy(second = "Manager")),
                constraint = Equals(mapOf(startedPositionMetadata))))
    }

    @Test
    fun `isNotEqualTo with null map should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).isNotEqualTo(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isNotEqualTo with same map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(mapOf(startedPositionMetadata))) {
                validate(Employee::metadata).isNotEqualTo(mapOf(startedPositionMetadata))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(startedPositionMetadata),
                constraint = NotEquals(mapOf(startedPositionMetadata))))
    }

    @Test
    fun `isNotEqualTo with different key on map should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata.copy(first = "endedOn")))) {
            validate(Employee::metadata).isNotEqualTo(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isNotEqualTo with different value on map should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata.copy(second = "Manager")))) {
            validate(Employee::metadata).isNotEqualTo(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isIn vararg with null map should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).isIn(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isIn vararg with same map should be valid`() {
        validate(Employee(metadata = mapOf(startedPositionMetadata))) {
            validate(Employee::metadata).isIn(mapOf(startedPositionMetadata))
        }
    }

    @Test
    fun `isIn vararg with empty map should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).isIn(mapOf(startedPositionMetadata))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = In(setOf(mapOf(startedPositionMetadata)))))
    }

    @Test
    fun `isIn vararg with different key should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(startedPositionMetadata.copy(first = "endedOn")))) {
                validate(Employee::metadata).isIn(mapOf(startedPositionMetadata))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(startedPositionMetadata.copy(first = "endedOn")),
                constraint = In(setOf(mapOf(startedPositionMetadata)))))
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(startedPositionMetadata.copy(second = "Manager")))) {
                validate(Employee::metadata).isIn(mapOf(startedPositionMetadata))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(startedPositionMetadata.copy(second = "Manager")),
                constraint = In(setOf(mapOf(startedPositionMetadata)))))
    }

    @Test
    fun `isEmpty with null property should be valid`() {
        validate(Employee(metadata = mapOf())) {
            validate(Employee::metadata).isEmpty()
        }
    }

    @Test
    fun `isNotEmpty with null property should be valid`() {
        validate(Employee(metadata = mapOf(startedPositionMetadata))) {
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

    @Test
    fun `containsKey with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsKey("startedOn")
        }
    }

    @Test
    fun `containsKey with same property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, startedPositionMetadata))) {
            validate(Employee::metadata).containsKey("startedOn")
        }
    }

    @Test
    fun `containsKey with different property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata))) {
                validate(Employee::metadata).containsKey("startedOn")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata),
                constraint = Contains("startedOn")))
    }

    @Test
    fun `containsAllKeys vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAllKeys("createdAt", "updatedAt")
        }
    }

    @Test
    fun `containsAllKeys vararg with valid property should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata, createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).containsAllKeys("startedOn", "createdAt")
        }
    }

    @Test
    fun `containsAllKeys vararg with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(emptyMap())) {
                validate(Employee::metadata).containsAllKeys("startedOn", "createdAt")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAll(setOf("startedOn", "createdAt"))))
    }

    @Test
    fun `containsAllKeys iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAllKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `containsAllKeys iterable with valid property should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata, createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).containsAllKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `containsAllKeys iterable with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(emptyMap())) {
                validate(Employee::metadata).containsAllKeys(setOf("startedOn", "createdAt"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAll(setOf("startedOn", "createdAt"))))
    }

    @Test
    fun `containsAnyKey vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAnyKeys("startedOn")
        }
    }

    @Test
    fun `containsAnyKey vararg with valid property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata, currentPositionMetadata))) {
            validate(Employee::metadata).containsAnyKeys("startedOn", "currentOn")
        }
    }

    @Test
    fun `containsAnyKey vararg with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).containsAnyKeys("startedOn", "currentOn")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAny(setOf("startedOn", "currentOn"))))
    }

    @Test
    fun `containsAnyKey vararg with no match property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).containsAnyKeys("startedOn", "currentOn")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = ContainsAny(setOf("startedOn", "currentOn"))))
    }

    @Test
    fun `containsAnyKey iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAnyKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `containsAnyKey iterable with valid property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata, currentPositionMetadata))) {
            validate(Employee::metadata).containsAnyKeys(setOf("startedOn", "currentOn"))
        }
    }

    @Test
    fun `containsAnyKey iterable with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).containsAnyKeys(setOf("startedOn", "currentOn"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAny(setOf("startedOn", "currentOn"))))
    }

    @Test
    fun `containsAnyKey iterable with no match property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).containsAnyKeys(setOf("startedOn", "currentOn"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = ContainsAny(setOf("startedOn", "currentOn"))))
    }

    @Test
    fun `doesNotContainKey with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsKey("startedOn")
        }
    }

    @Test
    fun `doesNotContainKey with different property should be valid`() {
        validate(Employee(metadata = mapOf(startedPositionMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsKey("createdAt")
        }
    }

    @Test
    fun `doesNotContainKey with same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsKey("createdAt")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContain("createdAt")))
    }

    @Test
    fun `doesNotContainsAllKeys vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAllKeys("startedOn", "createdAt")
        }
    }

    @Test
    fun `doesNotContainsAllKeys vararg with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAllKeys("startedOn", "createdAt")
        }
    }

    @Test
    fun `doesNotContainsAllKeys vararg with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAllKeys("startedOn", "currentOn")
        }
    }

    @Test
    fun `doesNotContainsAllKeys vararg with same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAllKeys("createdAt", "updatedAt")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAll(setOf("createdAt", "updatedAt"))))
    }

    @Test
    fun `doesNotContainsAllKeys iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAllKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `doesNotContainsAllKeys iterable with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAllKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `doesNotContainsAllKeys iterable with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAllKeys(setOf("startedOn", "currentOn"))
        }
    }

    @Test
    fun `doesNotContainsAllKeys iterable with same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAllKeys(setOf("createdAt", "updatedAt"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAll(setOf("createdAt", "updatedAt"))))
    }

    @Test
    fun `doesNotContainsAnyKeys vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAnyKeys("startedOn", "createdAt")
        }
    }

    @Test
    fun `doesNotContainsAnyKeys vararg with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAnyKeys("startedOn", "createdAt")
        }
    }

    @Test
    fun `doesNotContainsAnyKeys vararg with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAnyKeys("startedOn", "currentOn")
        }
    }

    @Test
    fun `doesNotContainsAnyKeys vararg with one same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAnyKeys("createdAt", "currentOn")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAny(setOf("createdAt", "currentOn"))))
    }

    @Test
    fun `doesNotContainsAnyKeys iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAnyKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `doesNotContainsAnyKeys iterable with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAnyKeys(setOf("startedOn", "createdAt"))
        }
    }

    @Test
    fun `doesNotContainsAnyKeys iterable with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAnyKeys(setOf("startedOn", "currentOn"))
        }
    }

    @Test
    fun `doesNotContainsAnyKeys iterable with one same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAnyKeys(setOf("createdAt", "currentOn"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAny(setOf("createdAt", "currentOn"))))
    }

    @Test
    fun `containsValue with same property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, startedPositionMetadata))) {
            validate(Employee::metadata).containsValue("BackEnd Development")
        }
    }

    @Test
    fun `containsValue with different property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata))) {
                validate(Employee::metadata).containsValue("BackEnd Development")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata),
                constraint = Contains("BackEnd Development")))
    }

    @Test
    fun `containsAllValues vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAllValues("2020-02-20 10:05Z", "2001-01-01 15:48Z")
        }
    }

    @Test
    fun `containsAllValues vararg with valid property should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata, createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).containsAllValues("BackEnd Development", "2020-02-20 10:05Z")
        }
    }

    @Test
    fun `containsAllValues vararg with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(emptyMap())) {
                validate(Employee::metadata).containsAllValues("BackEnd Development", "2020-02-20 10:05Z")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAll(setOf("BackEnd Development", "2020-02-20 10:05Z"))))
    }

    @Test
    fun `containsAllValues iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAllValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `containsAllValues iterable with valid property should be valid`() {
        validate(Employee(mapOf(startedPositionMetadata, createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).containsAllValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `containsAllValues iterable with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(emptyMap())) {
                validate(Employee::metadata).containsAllValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAll(setOf("BackEnd Development", "2020-02-20 10:05Z"))))
    }

    @Test
    fun `containsAllValues iterable with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(updatedAtMetadata))) {
                validate(Employee::metadata).containsAllValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(updatedAtMetadata),
                constraint = ContainsAll(setOf("BackEnd Development", "2020-02-20 10:05Z"))))
    }

    @Test
    fun `containsAnyValue vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAnyValues("BackEnd Development")
        }
    }

    @Test
    fun `containsAnyValue vararg with valid property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata, currentPositionMetadata))) {
            validate(Employee::metadata).containsAnyValues("BackEnd Development", "BackEnd Development II")
        }
    }

    @Test
    fun `containsAnyValue vararg with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).containsAnyValues("BackEnd Development", "BackEnd Development II")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAny(setOf("BackEnd Development", "BackEnd Development II"))))
    }

    @Test
    fun `containsAnyValue vararg with no match property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).containsAnyValues("BackEnd Development", "BackEnd Development II")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = ContainsAny(setOf("BackEnd Development", "BackEnd Development II"))))
    }

    @Test
    fun `containsAnyValue iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).containsAnyValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `containsAnyValue iterable with valid property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata, currentPositionMetadata))) {
            validate(Employee::metadata).containsAnyValues(setOf("BackEnd Development", "BackEnd Development II"))
        }
    }

    @Test
    fun `containsAnyValue iterable with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = emptyMap())) {
                validate(Employee::metadata).containsAnyValues(setOf("BackEnd Development", "BackEnd Development II"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = emptyMap<String, String>(),
                constraint = ContainsAny(setOf("BackEnd Development", "BackEnd Development II"))))
    }

    @Test
    fun `containsAnyValue iterable with no match property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).containsAnyValues(setOf("BackEnd Development", "BackEnd Development II"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = ContainsAny(setOf("BackEnd Development", "BackEnd Development II"))))
    }

    @Test
    fun `doesNotContainsValue with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsValue("BackEnd Development")
        }
    }

    @Test
    fun `doesNotContainsValue with different property should be valid`() {
        validate(Employee(metadata = mapOf(startedPositionMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsValue("2020-02-20 10:05Z")
        }
    }

    @Test
    fun `doesNotContainsValue with same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsValue("2020-02-20 10:05Z")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContain("2020-02-20 10:05Z")))
    }

    @Test
    fun `doesNotContainsAllValues vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAllValues("BackEnd Development", "2020-02-20 10:05Z")
        }
    }

    @Test
    fun `doesNotContainsAllValues vararg with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAllValues("BackEnd Development", "2020-02-20 10:05Z")
        }
    }

    @Test
    fun `doesNotContainsAllValues vararg with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAllValues("BackEnd Development", "BackEnd Development II")
        }
    }

    @Test
    fun `doesNotContainsAllValues vararg with same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAllValues("2020-02-20 10:05Z", "2001-01-01 15:48Z")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAll(setOf("2020-02-20 10:05Z", "2001-01-01 15:48Z"))))
    }

    @Test
    fun `doesNotContainsAllValues iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAllValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `doesNotContainsAllValues iterable with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAllValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `doesNotContainsAllValues iterable with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAllValues(setOf("BackEnd Development", "BackEnd Development II"))
        }
    }

    @Test
    fun `doesNotContainsAllValues iterable with same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAllValues(setOf("2020-02-20 10:05Z", "2001-01-01 15:48Z"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAll(setOf("2020-02-20 10:05Z", "2001-01-01 15:48Z"))))
    }

    @Test
    fun `doesNotContainsAnyValues vararg with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAnyValues("BackEnd Development", "2020-02-20 10:05Z")
        }
    }

    @Test
    fun `doesNotContainsAnyValues vararg with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAnyValues("BackEnd Development", "2020-02-20 10:05Z")
        }
    }

    @Test
    fun `doesNotContainsAnyValues vararg with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAnyValues("BackEnd Development", "BackEnd Development II")
        }
    }

    @Test
    fun `doesNotContainsAnyValues vararg with one same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAnyValues("2020-02-20 10:05Z", "BackEnd Development II")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAny(setOf("2020-02-20 10:05Z", "BackEnd Development II"))))
    }

    @Test
    fun `doesNotContainsAnyValues iterable with null property should be valid`() {
        validate(Employee()) {
            validate(Employee::metadata).doesNotContainsAnyValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `doesNotContainsAnyValues iterable with empty property should be valid`() {
        validate(Employee(metadata = emptyMap())) {
            validate(Employee::metadata).doesNotContainsAnyValues(setOf("BackEnd Development", "2020-02-20 10:05Z"))
        }
    }

    @Test
    fun `doesNotContainsAnyValues iterable with different property should be valid`() {
        validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
            validate(Employee::metadata).doesNotContainsAnyValues(setOf("BackEnd Development", "BackEnd Development II"))
        }
    }

    @Test
    fun `doesNotContainsAnyValues iterable with one same property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(metadata = mapOf(createdAtMetadata, updatedAtMetadata))) {
                validate(Employee::metadata).doesNotContainsAnyValues(setOf("2020-02-20 10:05Z", "BackEnd Development II"))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "metadata",
                value = mapOf(createdAtMetadata, updatedAtMetadata),
                constraint = NotContainAny(setOf("2020-02-20 10:05Z", "BackEnd Development II"))))
    }
}
