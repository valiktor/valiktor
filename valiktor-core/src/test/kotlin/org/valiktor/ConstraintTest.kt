package org.valiktor

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.ConstraintFixture.CustomConstraint
import org.valiktor.ConstraintFixture.EmptyConstraint

private object ConstraintFixture {

    object EmptyConstraint : Constraint

    data class CustomConstraint(override val name: String,
                                override val messageKey: String,
                                override val messageParams: Map<String, *>) : Constraint
}

class ConstraintTest {

    @Test
    fun `should create Constraint with default properties`() {
        val constraint = EmptyConstraint

        assertAll(
                { assertEquals(constraint.name, "EmptyConstraint") },
                { assertEquals(constraint.messageKey, "org.valiktor.ConstraintFixture\$EmptyConstraint.message") },
                { assertEquals(constraint.messageParams, emptyMap<String, Any>()) }
        )
    }

    @Test
    fun `should create Constraint with custom properties`() {
        val constraint = CustomConstraint(
                name = "TestTestConstraint",
                messageKey = "org.valiktor.test.constraints.TestConstraint.message",
                messageParams = mapOf("value" to 1))

        assertAll(
                { assertEquals(constraint.name, "TestTestConstraint") },
                { assertEquals(constraint.messageKey, "org.valiktor.test.constraints.TestConstraint.message") },
                { assertEquals(constraint.messageParams, mapOf("value" to 1)) }
        )
    }

    @Test
    fun `should not be equal to null`() {
        val constraint = EmptyConstraint
        assertNotEquals(constraint, null)
    }

    @Test
    fun `should not be equal to another constraint`() {
        val constraint = EmptyConstraint
        assertNotEquals(constraint, object : Constraint {})
    }
}