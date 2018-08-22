/*
 * Copyright 2018 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.valiktor.i18n

import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle
import java.util.ResourceBundle.Control.FORMAT_PROPERTIES
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val PREFIX_KEY = "org.valiktor"
private const val DEFAULT_BASE_NAME = "org/valiktor/messages"
private const val INITIAL_CACHE_SIZE = 48

private data class CacheKey(
    val baseName: String,
    val locale: Locale,
    val fallbackBaseName: String,
    val fallbackLocale: Locale
)

private val cachedMessages: ConcurrentMap<CacheKey, Map<String, String>> = ConcurrentHashMap(INITIAL_CACHE_SIZE)

/**
 * Represents a bundle of locale-specific messages.
 *
 * @property baseName specifies the base name of the message bundle
 * @property locale specifies the [Locale] of the message bundle
 * @param fallbackBaseName specifies the base name that will be used if the [baseName] does not exist
 * @param fallbackLocale specifies the locale that will be used if the [locale] does not exist
 * @constructor creates a new message bundle
 *
 * @author Rodolpho S. Couto
 * @see ResourceBundle
 * @see Locale
 * @since 0.1.0
 */
class MessageBundle(
    val baseName: String,
    val locale: Locale,
    fallbackBaseName: String,
    fallbackLocale: Locale
) {

    private val messages: Map<String, String> = cachedMessages.getOrPut(
        CacheKey(baseName, locale, fallbackBaseName, fallbackLocale)) {
        val control = ResourceBundle.Control.getControl(FORMAT_PROPERTIES)

        if (locale == Locale(""))
            getMessages(DEFAULT_BASE_NAME, locale)
                .plus(getMessages(fallbackBaseName, locale))
                .plus(getMessages(baseName, locale))
                .toMap()
        else
            control.getCandidateLocales(baseName, locale)
                .asSequence()
                .filter { it != Locale("") }
                .plus(control.getCandidateLocales(baseName, fallbackLocale))
                .asIterable()
                .reversed()
                .asSequence()
                .flatMap {
                    getMessages(DEFAULT_BASE_NAME, locale)
                        .plus(getMessages(fallbackBaseName, it))
                        .plus(getMessages(baseName, it))
                }
                .toMap()
    }

    private fun getMessages(baseName: String, locale: Locale): Sequence<Pair<String, String>> =
        try {
            ResourceBundle.getBundle(baseName, locale,
                object : ResourceBundle.Control() {
                    override fun getFormats(baseName: String?): List<String> = FORMAT_PROPERTIES
                    override fun getFallbackLocale(baseName: String?, locale: Locale?): Locale? = null
                    override fun getCandidateLocales(baseName: String, locale: Locale): List<Locale> = listOf(locale)
                })
                .let { bundle ->
                    bundle.keySet()
                        .asSequence()
                        .filter { it.startsWith(PREFIX_KEY) }
                        .map { it to bundle.getString(it) }
                        .filter { it.second.isNotBlank() }
                }
        } catch (ex: MissingResourceException) {
            emptySequence()
        }

    /**
     * Gets a message from the bundle.
     *
     * If the primary bundle does not contain the message, the fallback bundle is called.
     *
     * @param key: the message key
     * @return the message value
     */
    fun getMessage(key: String): String = messages[key] ?: ""
}