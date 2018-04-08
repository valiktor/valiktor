package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationFixture.TestConstraint
import org.valiktor.ConstraintViolationFixture.TestConstraint2

private object ConstraintViolationFixture {

    data class TestConstraint(val value: String) : Constraint {
        override val interpolator: (String) -> String = { it.replace("{value}", value) }
    }

    data class TestConstraint2(val value1: String, val value2: String) : Constraint {
        override val interpolator: (String) -> String = { it.replace("{value1}", value1).replace("{value2}", value2) }
    }

    fun createConstraintViolation(): ConstraintViolation =
            DefaultConstraintViolation(property = "name", value = "Test", constraint = TestConstraint("test value"))

    fun createConstraintViolations(): Set<ConstraintViolation> = setOf(
            DefaultConstraintViolation(property = "name", value = "Test", constraint = TestConstraint("test value")),
            DefaultConstraintViolation(property = "name", value = "Test2", constraint = TestConstraint2("test value1", "test value2")))
}

class ConstraintViolationTest {

    @Test
    fun `should create ConstraintViolation`() {
        val constraintViolation: ConstraintViolation = ConstraintViolationFixture.createConstraintViolation()

        assertAll(
                { assertEquals(constraintViolation.property, "name") },
                { assertEquals(constraintViolation.value, "Test") },
                { assertEquals(constraintViolation.constraint.name, "TestConstraint") },
                { assertEquals(constraintViolation.constraint.messageKey, "org.valiktor.ConstraintViolationFixture\$TestConstraint.message") },
                { assertEquals(constraintViolation.constraint.interpolator("must be {value}"), "must be test value") }
        )
    }
}

class ConstraintViolationExceptionTest {

    @Test
    fun `should throws ConstraintViolationException`() {
        val exception = assertThrows<ConstraintViolationException> {
            throw ConstraintViolationException(ConstraintViolationFixture.createConstraintViolations())
        }

        assertThat(exception.constraintViolations)
                .containsExactly(
                        DefaultConstraintViolation(property = "name", value = "Test", constraint = TestConstraint("test value")),
                        DefaultConstraintViolation(property = "name", value = "Test2", constraint = TestConstraint2("test value1", "test value2")))
    }
}