package org.valiktor.i18n

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*
import java.util.ResourceBundle.getBundle

class MessageBundleTest {

    @Test
    fun `should get bundle`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = SupportedLocales.EN,
                fallbackBundle = getBundle("org/valiktor/messages", SupportedLocales.DEFAULT))

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, SupportedLocales.EN) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Cannot be equal to {value}") }
        )
    }

    @Test
    fun `should get default locale with invalid locale`() {
        Locale.setDefault(SupportedLocales.EN)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBundle = getBundle("org/valiktor/messages", SupportedLocales.DEFAULT))

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, SupportedLocales.EN) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Cannot be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback locale with invalid default locale`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBundle = getBundle("org/valiktor/messages", SupportedLocales.DEFAULT))

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, SupportedLocales.DEFAULT) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Should not be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback baseName with invalid baseName`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "invalidTestMessages",
                locale = SupportedLocales.EN,
                fallbackBundle = getBundle("org/valiktor/messages", SupportedLocales.DEFAULT))

        assertAll(
                { assertEquals(messageBundle.baseName, "org/valiktor/messages") },
                { assertEquals(messageBundle.locale, SupportedLocales.EN) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Must not be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback baseName and default locale with invalid baseName and locale`() {
        Locale.setDefault(SupportedLocales.EN)

        val messageBundle = MessageBundle(
                baseName = "invalidTestMessages",
                locale = INVALID_LOCALE,
                fallbackBundle = getBundle("org/valiktor/messages", SupportedLocales.DEFAULT))

        assertAll(
                { assertEquals(messageBundle.baseName, "org/valiktor/messages") },
                { assertEquals(messageBundle.locale, SupportedLocales.EN) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Must not be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback baseName and locale with invalid baseName, locale and default locale`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "invalidTestMessages",
                locale = INVALID_LOCALE,
                fallbackBundle = getBundle("org/valiktor/messages", SupportedLocales.DEFAULT))

        assertAll(
                { assertEquals(messageBundle.baseName, "org/valiktor/messages") },
                { assertEquals(messageBundle.locale, SupportedLocales.DEFAULT) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Must not be equal to {value}") }
        )
    }
}