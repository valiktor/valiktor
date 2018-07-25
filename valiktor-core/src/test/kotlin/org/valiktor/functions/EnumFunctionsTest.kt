package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.EnumFunctionsFixture.Employee
import org.valiktor.functions.EnumFunctionsFixture.Gender.FEMALE
import org.valiktor.functions.EnumFunctionsFixture.Gender.MALE
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object EnumFunctionsFixture {

    data class Employee(val gender: Gender? = null)
    enum class Gender { MALE, FEMALE }
}

class EnumFunctionsTest {

    @Test
    fun `isNull with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee()) {
                validate(Employee::gender).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isEqualTo(MALE)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isEqualTo(MALE)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isEqualTo(FEMALE)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = Equals(FEMALE)))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isNotEqualTo(MALE)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isNotEqualTo(FEMALE)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isNotEqualTo(MALE)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = NotEquals(MALE)))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isIn(MALE, FEMALE)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isIn(MALE, FEMALE)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isIn(FEMALE)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = In(setOf(FEMALE))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isIn(listOf(MALE, FEMALE))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isIn(listOf(MALE, FEMALE))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isIn(listOf(FEMALE))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = In(listOf(FEMALE))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isNotIn(MALE, FEMALE)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isNotIn(FEMALE)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isNotIn(MALE, FEMALE)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = NotIn(setOf(MALE, FEMALE))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::gender).isNotIn(listOf(MALE, FEMALE))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(gender = MALE)) {
            validate(Employee::gender).isNotIn(listOf(FEMALE))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(gender = MALE)) {
                validate(Employee::gender).isNotIn(listOf(MALE, FEMALE))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = MALE, constraint = NotIn(listOf(MALE, FEMALE))))
    }
}