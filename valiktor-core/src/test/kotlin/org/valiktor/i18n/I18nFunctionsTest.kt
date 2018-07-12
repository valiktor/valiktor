package org.valiktor.i18n

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.ConstraintViolation
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.NotEquals
import java.util.*

class I18nFunctionsTest {

    private fun createConstraintViolation(): ConstraintViolation =
            DefaultConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"))

    @Test
    fun `should convert to I18nConstraintViolation with default params`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n()

        assertAll(
                { assertEquals(i18nConstraintViolation.property, "name") },
                { assertEquals(i18nConstraintViolation.value, "Test") },
                { assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { assertEquals(i18nConstraintViolation.message, "Must not be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom locale`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(locale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(i18nConstraintViolation.property, "name") },
                { assertEquals(i18nConstraintViolation.value, "Test") },
                { assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { assertEquals(i18nConstraintViolation.message, "Não deve ser igual a Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom baseName`() {
        Locale.setDefault(SupportedLocales.DEFAULT)

        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(baseName = "testMessages")

        assertAll(
                { assertEquals(i18nConstraintViolation.property, "name") },
                { assertEquals(i18nConstraintViolation.value, "Test") },
                { assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { assertEquals(i18nConstraintViolation.message, "Should not be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom locale and baseName`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(locale = SupportedLocales.EN,
                        baseName = "testMessages")

        assertAll(
                { assertEquals(i18nConstraintViolation.property, "name") },
                { assertEquals(i18nConstraintViolation.value, "Test") },
                { assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { assertEquals(i18nConstraintViolation.message, "Cannot be equal to Test") }
        )
    }
}

class I18nSetFunctionsTest {

    private fun createConstraintViolations(): Set<ConstraintViolation> = setOf(
            DefaultConstraintViolation(property = "id", value = 1, constraint = NotEquals("1")),
            DefaultConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test")),
            DefaultConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com")))

    @Test
    fun `should convert to set of I18nConstraintViolation with default params`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n()

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Must not be equal to 1"),
                DefaultI18nConstraintViolation(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Must not be equal to Test"),
                DefaultI18nConstraintViolation(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Must not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom locale`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(locale = SupportedLocales.PT_BR)

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Não deve ser igual a 1"),
                DefaultI18nConstraintViolation(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Não deve ser igual a Test"),
                DefaultI18nConstraintViolation(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Não deve ser igual a test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom baseName`() {
        Locale.setDefault(SupportedLocales.DEFAULT)

        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(baseName = "testMessages")

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Should not be equal to 1"),
                DefaultI18nConstraintViolation(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Should not be equal to Test"),
                DefaultI18nConstraintViolation(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Should not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom locale and baseName`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(locale = SupportedLocales.EN,
                        baseName = "testMessages")

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = NotEquals("1"),
                        message = "Cannot be equal to 1"),
                DefaultI18nConstraintViolation(
                        property = "name",
                        value = "Test",
                        constraint = NotEquals("Test"),
                        message = "Cannot be equal to Test"),
                DefaultI18nConstraintViolation(
                        property = "email",
                        value = "test@test.com",
                        constraint = NotEquals("test@test.com"),
                        message = "Cannot be equal to test@test.com"))
    }
}