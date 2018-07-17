package org.valiktor.i18n

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.Constraint
import org.valiktor.ConstraintViolation
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.NotEquals
import org.valiktor.i18n.ConstraintViolationMessageFixture.EmptyConstraint
import java.util.*

private object ConstraintViolationMessageFixture {

    object EmptyConstraint : Constraint
}

class ConstraintViolationMessageTest {

    @Test
    fun `should create ConstraintViolationMessage`() {
        val constraintViolationMessage: ConstraintViolationMessage =
                DefaultConstraintViolationMessage(
                        property = "name",
                        value = "Test",
                        constraint = EmptyConstraint,
                        message = "some message")

        assertAll(
                { assertEquals(constraintViolationMessage.property, "name") },
                { assertEquals(constraintViolationMessage.value, "Test") },
                { assertEquals(constraintViolationMessage.constraint.name, "EmptyConstraint") },
                { assertEquals(constraintViolationMessage.constraint.messageKey, "org.valiktor.i18n.ConstraintViolationMessageFixture\$EmptyConstraint.message") },
                { assertEquals(constraintViolationMessage.constraint.messageParams, emptyMap<String, Any>()) },
                { assertEquals(constraintViolationMessage.message, "some message") }
        )
    }
}

class ConstraintViolationFunctionsTest {

    private fun createConstraintViolation(): ConstraintViolation =
            DefaultConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"))

    @BeforeEach
    fun setUp() {
        Locale.setDefault(SupportedLocales.EN)
    }

    @Test
    fun `should convert to ConstraintViolationMessage with default params`() {
        val constraintViolationMessage = createConstraintViolation()
                .toMessage()

        assertAll(
                { assertEquals(constraintViolationMessage.property, "name") },
                { assertEquals(constraintViolationMessage.value, "Test") },
                { assertEquals(constraintViolationMessage.constraint, NotEquals("Test")) },
                { assertEquals(constraintViolationMessage.message, "Must not be equal to Test") }
        )
    }

    @Test
    fun `should convert to ConstraintViolationMessage with custom locale`() {
        val constraintViolationMessage = createConstraintViolation()
                .toMessage(locale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(constraintViolationMessage.property, "name") },
                { assertEquals(constraintViolationMessage.value, "Test") },
                { assertEquals(constraintViolationMessage.constraint, NotEquals("Test")) },
                { assertEquals(constraintViolationMessage.message, "Não deve ser igual a Test") }
        )
    }

    @Test
    fun `should convert to ConstraintViolationMessage with custom baseName`() {
        Locale.setDefault(SupportedLocales.DEFAULT)

        val constraintViolationMessage = createConstraintViolation()
                .toMessage(baseName = "testMessages")

        assertAll(
                { assertEquals(constraintViolationMessage.property, "name") },
                { assertEquals(constraintViolationMessage.value, "Test") },
                { assertEquals(constraintViolationMessage.constraint, NotEquals("Test")) },
                { assertEquals(constraintViolationMessage.message, "Should not be equal to Test") }
        )
    }

    @Test
    fun `should convert to ConstraintViolationMessage with custom locale and baseName`() {
        val constraintViolationMessage = createConstraintViolation()
                .toMessage(locale = SupportedLocales.EN,
                        baseName = "testMessages")

        assertAll(
                { assertEquals(constraintViolationMessage.property, "name") },
                { assertEquals(constraintViolationMessage.value, "Test") },
                { assertEquals(constraintViolationMessage.constraint, NotEquals("Test")) },
                { assertEquals(constraintViolationMessage.message, "Cannot be equal to Test") }
        )
    }
}

class ConstraintViolationSetFunctionsTest {

    private fun createConstraintViolations(): Set<ConstraintViolation> = setOf(
            DefaultConstraintViolation(property = "id", value = 1, constraint = NotEquals("1")),
            DefaultConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test")),
            DefaultConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com")))

    @BeforeEach
    fun setUp() {
        Locale.setDefault(SupportedLocales.EN)
    }

    @Test
    fun `should convert to set of ConstraintViolationMessage with default params`() {
        val constraintViolationMessages = createConstraintViolations()
                .mapToMessage()

        assertThat(constraintViolationMessages).containsExactly(
                DefaultConstraintViolationMessage(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Must not be equal to 1"),
                DefaultConstraintViolationMessage(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Must not be equal to Test"),
                DefaultConstraintViolationMessage(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Must not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of ConstraintViolationMessage with custom locale`() {
        val constraintViolationMessages = createConstraintViolations()
                .mapToMessage(locale = SupportedLocales.PT_BR)

        assertThat(constraintViolationMessages).containsExactly(
                DefaultConstraintViolationMessage(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Não deve ser igual a 1"),
                DefaultConstraintViolationMessage(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Não deve ser igual a Test"),
                DefaultConstraintViolationMessage(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Não deve ser igual a test@test.com"))
    }

    @Test
    fun `should convert to set of ConstraintViolationMessage with custom baseName`() {
        Locale.setDefault(SupportedLocales.DEFAULT)

        val constraintViolationMessages = createConstraintViolations()
                .mapToMessage(baseName = "testMessages")

        assertThat(constraintViolationMessages).containsExactly(
                DefaultConstraintViolationMessage(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Should not be equal to 1"),
                DefaultConstraintViolationMessage(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Should not be equal to Test"),
                DefaultConstraintViolationMessage(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Should not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of ConstraintViolationMessage with custom locale and baseName`() {
        val constraintViolationMessages = createConstraintViolations()
                .mapToMessage(locale = SupportedLocales.EN,
                        baseName = "testMessages")

        assertThat(constraintViolationMessages).containsExactly(
                DefaultConstraintViolationMessage(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Cannot be equal to 1"),
                DefaultConstraintViolationMessage(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Cannot be equal to Test"),
                DefaultConstraintViolationMessage(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Cannot be equal to test@test.com"))
    }
}