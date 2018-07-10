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

import org.valiktor.ConstraintViolation
import java.util.*
import java.util.ResourceBundle.getBundle

private val FALLBACK_LOCALE = Locale("")

/**
 * Interpolate the message with parameters
 *
 * @param messageBundle specifies the [MessageBundle] that contains the messages
 * @param messageKey specifies the message key in the message properties
 * @param messageParams specifies the parameters to replace in the message
 * @return the interpolated message
 */
internal fun interpolate(messageBundle: MessageBundle, messageKey: String, messageParams: Map<String, *>): String =
        messageParams.toList()
                .stream()
                .reduce(messageBundle.getMessage(messageKey),
                        { message, pair ->
                            message.replace("{${pair.first}}",
                                    pair.second?.let { Formatters[it.javaClass.kotlin].format(it, messageBundle) }
                                            ?: "")
                        },
                        { message, _ -> message })

/**
 * Converts this object to [I18nConstraintViolation]
 *
 * @param baseName specifies the prefix name of the message properties
 * @param locale specifies the [Locale] of the message properties
 * @return a new [I18nConstraintViolation]
 */
fun ConstraintViolation.toI18n(baseName: String = constraint.messageBundle,
                               locale: Locale = Locale.getDefault()): I18nConstraintViolation =
        DefaultI18nConstraintViolation(
                property = this.property,
                value = this.value,
                constraint = this.constraint,
                message = interpolate(
                        MessageBundle(
                                baseName = baseName,
                                locale = locale,
                                fallbackBundle = getBundle(this.constraint.messageBundle, FALLBACK_LOCALE)),
                        this.constraint.messageKey,
                        this.constraint.messageParams))

/**
 * Converts this object to [I18nConstraintViolation]
 *
 * @param resourceBundle specifies the [ResourceBundle] that contains the messages
 * @return a new [I18nConstraintViolation]
 */
fun ConstraintViolation.toI18n(resourceBundle: ResourceBundle): I18nConstraintViolation =
        DefaultI18nConstraintViolation(
                property = this.property,
                value = this.value,
                constraint = this.constraint,
                message = interpolate(
                        MessageBundle(
                                bundle = resourceBundle,
                                fallbackBundle = getBundle(this.constraint.messageBundle, FALLBACK_LOCALE)),
                        this.constraint.messageKey,
                        this.constraint.messageParams))

/**
 * Converts to Set<[I18nConstraintViolation]>
 *
 * @param baseName specifies the prefix name of the message properties
 * @param locale specifies the [Locale] of the message properties
 * @receiver the Set of <[ConstraintViolation]>
 * @return the Set of <[I18nConstraintViolation]>
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see I18nConstraintViolation
 * @since 0.1.0
 */
fun Set<ConstraintViolation>.mapToI18n(baseName: String? = null,
                                       locale: Locale = Locale.getDefault()) =
        this.map { it.toI18n(baseName ?: it.constraint.messageBundle, locale) }.toSet()

/**
 * Converts to Set<[I18nConstraintViolation]>
 *
 * @param resourceBundle specifies the [ResourceBundle] that contains the messages
 * @receiver the Set of <[ConstraintViolation]>
 * @return the Set of <[I18nConstraintViolation]>
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see I18nConstraintViolation
 * @since 0.1.0
 */
fun Set<ConstraintViolation>.mapToI18n(resourceBundle: ResourceBundle) =
        this.map { it.toI18n(resourceBundle) }.toSet()