package org.valiktor.i18n

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*

private const val FALLBACK_BASENAME = "org/valiktor/messages"

class MessageBundleTest {

    @Test
    fun `should get message from baseName, language and country`() {
        Locale.setDefault(SupportedLocales.PT_BR)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.US,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.US) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Empty.message"), "Should be empty") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName, language and country`() {
        Locale.setDefault(SupportedLocales.EN)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = SupportedLocales.PT_BR,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, SupportedLocales.PT_BR) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Empty.message"), "Deve ser vazio") }
        )
    }

    @Test
    fun `should get message from baseName and language`() {
        Locale.setDefault(SupportedLocales.PT_BR)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.US,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.US) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotNull.message"), "Should not be null") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName and language`() {
        Locale.setDefault(SupportedLocales.PT_BR)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = Locale.US,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, Locale.US) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Null.message"), "Must be null") }
        )
    }

    @Test
    fun `should get message from baseName, default language and default country`() {
        Locale.setDefault(SupportedLocales.PT_BR)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEmpty.message"), "Não pode ser vazio") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName, default language and default country`() {
        Locale.setDefault(SupportedLocales.PT_BR)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotNull.message"), "Não deve ser nulo") }
        )
    }

    @Test
    fun `should get message from baseName and default language`() {
        Locale.setDefault(Locale.US)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotNull.message"), "Should not be null") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName and default language`() {
        Locale.setDefault(Locale.US)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Valid.message"), "Must be valid") }
        )
    }

    @Test
    fun `should get message from baseName`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.NotEquals.message"), "Should not be equal to {value}") }
        )
    }

    @Test
    fun `should get message from fallbackBaseName`() {
        Locale.setDefault(INVALID_LOCALE)

        val messageBundle = MessageBundle(
                baseName = "testMessages",
                locale = INVALID_LOCALE,
                fallbackBaseName = FALLBACK_BASENAME)

        assertAll(
                { assertEquals(messageBundle.baseName, "testMessages") },
                { assertEquals(messageBundle.locale, INVALID_LOCALE) },
                { assertEquals(messageBundle.getMessage("org.valiktor.constraints.Valid.message"), "Must be valid") }
        )
    }
}