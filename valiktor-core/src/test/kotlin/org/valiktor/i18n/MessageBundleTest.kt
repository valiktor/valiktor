package org.valiktor.i18n

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*

private const val FALLBACK_BASENAME = "org/valiktor/messages"

class MessageBundleTest {

    @Test
    fun `should get message from baseName, language and country`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.US,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.US) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Empty.message"), "Should be empty") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName, language and country`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = SupportedLocales.PT_BR,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = SupportedLocales.EN)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, SupportedLocales.PT_BR) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Empty.message"), "Deve ser vazio") }
        )
    }

    @Test
    fun `should get message from baseName and language`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.US,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.US) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotNull.message"), "Should not be null") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName and language`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.US,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.US) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Null.message"), "Must be null") }
        )
    }

    @Test
    fun `should get message from baseName, default language and default country`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEmpty.message"), "Não pode ser vazio") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName, default language and default country`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = SupportedLocales.PT_BR)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotNull.message"), "Não deve ser nulo") }
        )
    }

    @Test
    fun `should get message from baseName and default language`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = Locale.US)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotNull.message"), "Should not be null") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName and default language`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = Locale.US)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Valid.message"), "Must be valid") }
        )
    }

    @Test
    fun `should get message from baseName`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = INVALID_LOCALE)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Should not be equal to {value}") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName`() {
        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME,
                fallbackLocale = INVALID_LOCALE)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Valid.message"), "Must be valid") }
        )
    }
}