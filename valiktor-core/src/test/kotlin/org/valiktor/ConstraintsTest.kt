package org.valiktor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

private object ConstraintsFixture {

    class DefaultConstraint : AbstractConstraint()

    data class TestConstraint(override val name: String,
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
                { assertEquals(constraint.name, "DefaultConstraint") },
                { assertEquals(constraint.messageKey, "org.valiktor.ConstraintsFixture\$DefaultConstraint.message") },
                { assertEquals(constraint.interpolator("some message"), "some message") }
        )
    }

    @Test
    fun `should create Constraint with specific properties`() {
        val constraint = ConstraintsFixture.createTestConstraint()

        assertAll(
                { assertEquals(constraint.name, "TestTestConstraint") },
                { assertEquals(constraint.messageKey, "org.valiktor.test.constraints.TestConstraint.message") },
                { assertEquals(constraint.interpolator("some {value} message"), "some test message") }
        )
    }
}