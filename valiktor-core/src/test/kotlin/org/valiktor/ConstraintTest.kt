package org.valiktor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.ConstraintFixture.CustomConstraint
import org.valiktor.ConstraintFixture.EmptyConstraint

private object ConstraintFixture {

    class EmptyConstraint : AbstractConstraint()

    data class CustomConstraint(override val name: String,
                                override val messageKey: String,
                                override val interpolator: (String) -> String) : AbstractConstraint()
}

class ConstraintTest {

    @Test
    fun `should create Constraint with default properties`() {
        val constraint = EmptyConstraint()

        assertAll(
                { assertEquals(constraint.name, "EmptyConstraint") },
                { assertEquals(constraint.messageKey, "org.valiktor.ConstraintFixture\$EmptyConstraint.message") },
                { assertEquals(constraint.interpolator("some message"), "some message") }
        )
    }

    @Test
    fun `should create Constraint with custom properties`() {
        val constraint = CustomConstraint(
                name = "TestTestConstraint",
                messageKey = "org.valiktor.test.constraints.TestConstraint.message",
                interpolator = { it.replace("{value}", "test") })

        assertAll(
                { assertEquals(constraint.name, "TestTestConstraint") },
                { assertEquals(constraint.messageKey, "org.valiktor.test.constraints.TestConstraint.message") },
                { assertEquals(constraint.interpolator("some {value} message"), "some test message") }
        )
    }

    @Test
    fun `should not be equal to null`() {
        val constraint = EmptyConstraint()
        assertNotEquals(constraint, null)
    }

    @Test
    fun `should not be equal to another constraint`() {
        val constraint = EmptyConstraint()
        assertNotEquals(constraint, object : AbstractConstraint() {})
    }

    @Test
    fun `toString should be class name`() {
        val constraint = EmptyConstraint()
        assertEquals(constraint.toString(), "EmptyConstraint")
    }
}