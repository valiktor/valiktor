package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ConstraintViolationTest {

    @Test
    fun `should create ConstraintViolation`() {
        val constraintViolation: ConstraintViolation =
                DefaultConstraintViolation(property = "name", value = "Test", constraint = EmptyConstraint())

        assertAll(
                { assertEquals(constraintViolation.property, "name") },
                { assertEquals(constraintViolation.value, "Test") },
                { assertEquals(constraintViolation.constraint.name, "EmptyConstraint") },
                { assertEquals(constraintViolation.constraint.messageKey, "org.valiktor.EmptyConstraint.message") },
                { assertEquals(constraintViolation.constraint.interpolator("some message"), "some message") }
        )
    }
}

class ConstraintViolationExceptionTest {

    @Test
    fun `should throws ConstraintViolationException`() {
        val exception = assertThrows<ConstraintViolationException> {
            throw ConstraintViolationException(setOf(
                    DefaultConstraintViolation(property = "name", value = "Test", constraint = EmptyConstraint()),
                    DefaultConstraintViolation(property = "name", value = "Test2", constraint = TestConstraint("test value 1", "test value 2"))))
        }

        assertThat(exception.constraintViolations)
                .containsExactly(
                        DefaultConstraintViolation(property = "name", value = "Test", constraint = EmptyConstraint()),
                        DefaultConstraintViolation(property = "name", value = "Test2", constraint = TestConstraint("test value 1", "test value 2")))
    }
}