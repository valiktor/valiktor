package org.valiktor.i18n

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.valiktor.EmptyConstraint

class I18nConstraintViolationTest {

    @Test
    fun `should create I18nConstraintViolation`() {
        val i18nConstraintViolation: I18nConstraintViolation =
                DefaultI18nConstraintViolation(
                        property = "name",
                        value = "Test",
                        constraint = EmptyConstraint(),
                        message = "some message")

        assertAll(
                { assertEquals(i18nConstraintViolation.property, "name") },
                { assertEquals(i18nConstraintViolation.value, "Test") },
                { assertEquals(i18nConstraintViolation.constraint.name, "EmptyConstraint") },
                { assertEquals(i18nConstraintViolation.constraint.messageKey, "org.valiktor.EmptyConstraint.message") },
                { assertEquals(i18nConstraintViolation.constraint.interpolator("some message"), "some message") },
                { assertEquals(i18nConstraintViolation.message, "some message") }
        )
    }
}