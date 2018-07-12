package org.valiktor.i18n

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*

class MessageBundleTest {

    @Test
    fun `should get bundle`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = SupportedLocales.EN,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

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
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Cannot be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback locale with invalid default locale`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Should not be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback baseName with invalid baseName`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "invalidTestMessages",
                locale = SupportedLocales.EN,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "invalidTestMessages") },
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
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "invalidTestMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Must not be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback baseName and locale with invalid baseName, locale and default locale`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "invalidTestMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "invalidTestMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Must not be equal to {value}") }
        )
    }

    @Test
    fun `should get fallback locale message with invalid message key`() {
        Locale.setDefault(SupportedLocales.EN)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = SupportedLocales.PT_BR,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, SupportedLocales.PT_BR) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.GreaterOrEqual.message"), "Deve ser maior ou igual a {value}") }
        )
    }

    @Test
    fun `should get fallback baseName and default locale with invalid message key`() {
        Locale.setDefault(SupportedLocales.PT_BR)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.JAPANESE,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.JAPANESE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.GreaterOrEqual.message"), "Deve ser maior ou igual a {value}") }
        )
    }

    @Test
    fun `should get fallback baseName and locale with invalid message key and invalid default locale`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.JAPANESE,
                fallbackBaseName = "org/valiktor/messages",
                fallbackLocale = SupportedLocales.DEFAULT)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.JAPANESE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.GreaterOrEqual.message"), "Must be greater than or equal to {value}") }
        )
    }
}