package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

private object ConstraintViolationFixture {

    private data class TestConstraint(override val name: String,
                                      override val messageKey: String,
                                      override val interpolator: (String) -> String) : Constraint

    fun createDefaultConstraintViolation(): ConstraintViolation =
            DefaultConstraintViolation(
                    property = "name",
                    value = "Valiktor",
                    constraint = TestConstraint(
                            name = "TestConstraint",
                            messageKey = "org.valiktor.test.constraints.TestConstraint.message",
                            interpolator = { it.replace("{value}", "test") }))

    fun createSetOfDefaultConstraintViolation(): Set<ConstraintViolation> = setOf(
            DefaultConstraintViolation(
                    property = "name",
                    value = "Valiktor",
                    constraint = TestConstraint(
                            name = "TestConstraint",
                            messageKey = "org.valiktor.test.constraints.TestConstraint.message",
                            interpolator = { it.replace("{value}", "test") })),
            DefaultConstraintViolation(
                    property = "title",
                    value = "Valiktor Title",
                    constraint = TestConstraint(
                            name = "TestConstraint2",
                            messageKey = "org.valiktor.test.constraints.TestConstraint2.message",
                            interpolator = { it.replace("{value}", "test2") })))
}

class ConstraintViolationTest {

    @Test
    fun `should create DefaultConstraintViolation`() {
        val constraintViolation: ConstraintViolation = ConstraintViolationFixture.createDefaultConstraintViolation()

        assertAll(
                Executable { assertEquals(constraintViolation.property, "name") },
                Executable { assertEquals(constraintViolation.value, "Valiktor") },
                Executable { assertEquals(constraintViolation.constraint.name, "TestConstraint") },
                Executable { assertEquals(constraintViolation.constraint.messageKey, "org.valiktor.test.constraints.TestConstraint.message") },
                Executable { assertEquals(constraintViolation.constraint.interpolator("some message"), "some message") }
        )
    }
}

class ConstraintViolationExceptionTest {

    @Test
    fun `should create ConstraintViolationException`() {
        val constraintViolationException = ConstraintViolationException(ConstraintViolationFixture.createSetOfDefaultConstraintViolation())
        assertThat(constraintViolationException.constraintViolations)
                .containsExactlyElementsOf(ConstraintViolationFixture.createSetOfDefaultConstraintViolation())
    }
}