package org.valiktor.i18n

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.ConstraintViolation
import org.valiktor.DefaultConstraintViolation
import org.valiktor.Locales
import org.valiktor.constraints.NotEquals
import java.util.ResourceBundle

class I18nFunctionsTest {

    private fun createConstraintViolation(): ConstraintViolation =
            DefaultConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"))

    @Test
    fun `should convert to I18nConstraintViolation with default params`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n()

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Must not be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom locale`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(locale = Locales.PT_BR)

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Não deve ser igual a Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom baseName`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(baseName = "testMessages")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Should not be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom key`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(key = "org.valiktor.constraints.Equals.message")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Must be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom locale and baseName`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(locale = Locales.EN, baseName = "testMessages")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Cannot be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom locale and key`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(locale = Locales.EN, key = "org.valiktor.constraints.Equals.message")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Must be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom baseName and key`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(baseName = "testMessages", key = "org.valiktor.constraints.NotEquals.message.custom")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Cannot be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom locale, baseName and key`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(locale = Locales.EN, baseName = "testMessages", key = "org.valiktor.constraints.NotEquals.message.custom")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Should not be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom ResourceBundle`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(resourceBundle = ResourceBundle.getBundle("testMessages", Locales.DEFAULT))

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Should not be equal to Test") }
        )
    }

    @Test
    fun `should convert to I18nConstraintViolation with custom ResourceBundle and key`() {
        val i18nConstraintViolation = createConstraintViolation()
                .toI18n(resourceBundle = ResourceBundle.getBundle("testMessages", Locales.EN), key = "org.valiktor.constraints.NotEquals.message.custom")

        assertAll(
                { Assertions.assertEquals(i18nConstraintViolation.property, "name") },
                { Assertions.assertEquals(i18nConstraintViolation.value, "Test") },
                { Assertions.assertEquals(i18nConstraintViolation.constraint, NotEquals("Test")) },
                { Assertions.assertEquals(i18nConstraintViolation.message, "Should not be equal to Test") }
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
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Must not be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Must not be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Must not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom locale`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(locale = Locales.PT_BR)

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Não deve ser igual a 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Não deve ser igual a Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Não deve ser igual a test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom baseName`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(baseName = "testMessages")

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Should not be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Should not be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Should not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom key`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(key = { "org.valiktor.constraints.Equals.message" })

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Must be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Must be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Must be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom locale and baseName`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(locale = Locales.EN, baseName = "testMessages")

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Cannot be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Cannot be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Cannot be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom locale and key`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(locale = Locales.EN, key = { "org.valiktor.constraints.Equals.message" })

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Must be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Must be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Must be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom baseName and key`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(baseName = "testMessages", key = { "org.valiktor.constraints.NotEquals.message.custom" })

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Cannot be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Cannot be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Cannot be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom locale, baseName and key`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(locale = Locales.EN, baseName = "testMessages", key = { "org.valiktor.constraints.NotEquals.message.custom" })

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Should not be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Should not be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Should not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom ResourceBundle`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(resourceBundle = ResourceBundle.getBundle("testMessages", Locales.DEFAULT))

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Should not be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Should not be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Should not be equal to test@test.com"))
    }

    @Test
    fun `should convert to set of I18nConstraintViolation with custom ResourceBundle and key`() {
        val i18nConstraintViolations = createConstraintViolations()
                .mapToI18n(resourceBundle = ResourceBundle.getBundle("testMessages", Locales.EN), key = { "org.valiktor.constraints.NotEquals.message.custom" })

        assertThat(i18nConstraintViolations).containsExactly(
                DefaultI18nConstraintViolation(property = "id", value = 1, constraint = NotEquals("1"), message = "Should not be equal to 1"),
                DefaultI18nConstraintViolation(property = "name", value = "Test", constraint = NotEquals("Test"), message = "Should not be equal to Test"),
                DefaultI18nConstraintViolation(property = "email", value = "test@test.com", constraint = NotEquals("test@test.com"), message = "Should not be equal to test@test.com"))
    }
}