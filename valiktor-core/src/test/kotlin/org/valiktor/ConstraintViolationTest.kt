package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationFixture.EmptyConstraint
import org.valiktor.ConstraintViolationFixture.TestConstraint

private object ConstraintViolationFixture {

    class EmptyConstraint : AbstractConstraint()

    data class TestConstraint(val value1: String,
                              val value2: String) : AbstractConstraint() {

        override val interpolator: (String) -> String =
                { it.replace("{value1}", value1).replace("{value2}", value2) }
    }
}

class ConstraintViolationTest {

    @Test
    fun `should create ConstraintViolation`() {
        val constraintViolation: ConstraintViolation =
                DefaultConstraintViolation(
                        property = "name",
                        value = "Test",
                        constraint = EmptyConstraint())

        assertAll(
                { assertEquals(constraintViolation.property, "name") },
                { assertEquals(constraintViolation.value, "Test") },
                { assertEquals(constraintViolation.constraint.name, "EmptyConstraint") },
                { assertEquals(constraintViolation.constraint.messageKey, "org.valiktor.ConstraintViolationFixture\$EmptyConstraint.message") },
                { assertEquals(constraintViolation.constraint.interpolator("some message"), "some message") }
        )
    }
}

class ConstraintViolationExceptionTest {

    @Test
    fun `should throws ConstraintViolationException`() {
        val exception = assertThrows<ConstraintViolationException> {
            throw ConstraintViolationException(setOf(
                    DefaultConstraintViolation(
                            property = "name",
                            value = "Test",
                            constraint = EmptyConstraint()),
                    DefaultConstraintViolation(
                            property = "name",
                            value = "Test2",
                            constraint = TestConstraint("test value 1", "test value 2"))))
        }

        assertThat(exception.constraintViolations)
                .containsExactly(
                        DefaultConstraintViolation(
                                property = "name",
                                value = "Test",
                                constraint = EmptyConstraint()),
                        DefaultConstraintViolation(
                                property = "name",
                                value = "Test2",
                                constraint = TestConstraint("test value 1", "test value 2")))
    }
}