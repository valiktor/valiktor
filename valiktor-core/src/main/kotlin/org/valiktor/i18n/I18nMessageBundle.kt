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
import java.util.ResourceBundle.getBundle

/**
 * Represents a bundle of locale-specific messages.
 *
 * @property bundle specifies the primary bundle
 * @property fallbackBundle specifies the locale bundle
 * @constructor creates a new message bundle
 *
 * @author Rodolpho S. Couto
 * @see ResourceBundle
 * @since 0.1.0
 */
class MessageBundle(private val bundle: ResourceBundle,
                    private val fallbackBundle: ResourceBundle) {

    val baseName: String = bundle.baseBundleName
    val locale: Locale = bundle.locale

    constructor(baseName: String,
                locale: Locale,
                fallbackBundle: ResourceBundle) :
            this(
                    bundle =
                    try {
                        getBundle(baseName, locale)
                    } catch (ex: MissingResourceException) {
                        try {
                            getBundle(fallbackBundle.baseBundleName, locale)
                        } catch (ex: MissingResourceException) {
                            fallbackBundle
                        }
                    },
                    fallbackBundle = fallbackBundle
            )

    /**
     * Gets a message from the bundle.
     *
     * If the primary bundle does not contain the message, the fallback bundle is called.
     *
     * @param key: the message key
     * @return the message value
     */
    fun getMessage(key: String): String =
            try {
                bundle.getString(key)
            } catch (ex: MissingResourceException) {
                fallbackBundle.getString(key)
            }
}