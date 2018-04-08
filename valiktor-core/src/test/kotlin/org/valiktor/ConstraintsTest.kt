package org.valiktor

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

private object ConstraintsFixture {

    private class DefaultConstraint : Constraint

    private data class TestConstraint(override val name: String,
                                      override val messageKey: String,
                                      override val interpolator: (String) -> String) : Constraint

    fun createDefaultConstraint(): Constraint = DefaultConstraint()

    fun createTestConstraint(): Constraint =
            TestConstraint(
                    name = "TestTestConstraint",
                    messageKey = "org.valiktor.test.constraints.TestConstraint.message",
                    interpolator = { it.replace("{value}", "test") })
}

class ConstraintTest {

    @Test
    fun `should create Constraint with default properties`() {
        val constraint = ConstraintsFixture.createDefaultConstraint()

        assertAll(
                Executable { assertEquals(constraint.name, "DefaultConstraint") },
                Executable { assertEquals(constraint.messageKey, "org.valiktor.ConstraintsFixture\$DefaultConstraint.message") },
                Executable { assertEquals(constraint.interpolator("some message"), "some message") }
        )
    }

    @Test
    fun `should create Constraint with specific properties`() {
        val constraint = ConstraintsFixture.createTestConstraint()

        assertAll(
                Executable { assertEquals(constraint.name, "TestTestConstraint") },
                Executable { assertEquals(constraint.messageKey, "org.valiktor.test.constraints.TestConstraint.message") },
                Executable { assertEquals(constraint.interpolator("some {value} message"), "some test message") }
        )
    }
}