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

import java.util.*
import java.util.ResourceBundle.Control
import java.util.ResourceBundle.Control.FORMAT_PROPERTIES
import java.util.concurrent.ConcurrentHashMap

private const val PREFIX_KEY = "org.valiktor"
private const val INITIAL_CACHE_SIZE = 48

private data class CacheKey(val baseName: String,
                            val locale: Locale,
                            val fallbackBaseName: String,
                            val fallbackLocale: Locale,
                            val defaultLocale: Locale)

private val cachedMessages = ConcurrentHashMap<CacheKey, Map<String, String>>(INITIAL_CACHE_SIZE)

/**
 * Represents a bundle of locale-specific messages.
 *
 * @property baseName specifies the base name of the message bundle
 * @property locale specifies the [Locale] of the message bundle
 * @constructor creates a new message bundle
 *
 * @author Rodolpho S. Couto
 * @see ResourceBundle
 * @see Locale
 * @since 0.1.0
 */
class MessageBundle(val baseName: String,
                    val locale: Locale,
                    private val fallbackBaseName: String,
                    private val fallbackLocale: Locale) {

    private val messages: Map<String, String> = cachedMessages.getOrPut(
            CacheKey(baseName, locale, fallbackBaseName, fallbackLocale, Locale.getDefault()), {
        getMessages(fallbackBaseName, fallbackLocale)
                .plus(getMessages(baseName, fallbackLocale))
                .plus(getMessages(fallbackBaseName, Locale.getDefault()))
                .plus(getMessages(baseName, Locale.getDefault()))
                .plus(getMessages(fallbackBaseName, locale))
                .plus(getMessages(baseName, locale))
    })

    private fun getMessages(baseName: String, locale: Locale): Map<String, String> =
            try {
                ResourceBundle.getBundle(baseName, locale, Control.getNoFallbackControl(FORMAT_PROPERTIES))
                        .let { bundle ->
                            if (bundle.baseBundleName != baseName || bundle.locale != locale)
                                emptyMap()
                            else
                                bundle.keySet()
                                        .filter { it.startsWith(PREFIX_KEY) }
                                        .map { it to bundle.getString(it) }
                                        .filter { it.second.isNotBlank() }
                                        .toMap()
                        }
            } catch (ex: MissingResourceException) {
                emptyMap()
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