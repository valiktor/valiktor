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
import java.util.Locale
import java.util.ResourceBundle
import java.util.ResourceBundle.getBundle

private const val DEFAULT_BASE_NAME = "org/valiktor/messages"

/**
 * Converts this object to [I18nConstraintViolation]
 *
 * @param locale specifies the [Locale] of the message properties
 * @param baseName specifies the prefix name of the message properties
 * @param key specifies the message key in the message properties
 * @return a new [I18nConstraintViolation]
 */
fun ConstraintViolation.toI18n(locale: Locale? = null,
                               baseName: String = DEFAULT_BASE_NAME,
                               key: String = constraint.messageKey): I18nConstraintViolation =
        DefaultI18nConstraintViolation(
                property = this.property,
                value = this.value,
                constraint = this.constraint,
                message = this.constraint.interpolator(getBundle(baseName, locale ?: Locale("")).getString(key)))


/**
 * Converts this object to [I18nConstraintViolation]
 *
 * @param resourceBundle specifies the [ResourceBundle] that contains the messages
 * @param key specifies the message key in the message properties
 * @return a new [I18nConstraintViolation]
 */
fun ConstraintViolation.toI18n(resourceBundle: ResourceBundle,
                               key: String = constraint.messageKey): I18nConstraintViolation =
        DefaultI18nConstraintViolation(
                property = this.property,
                value = this.value,
                constraint = this.constraint,
                message = this.constraint.interpolator(resourceBundle.getString(key)))

/**
 * Converts to Set<[I18nConstraintViolation]>
 *
 * @param locale specifies the [Locale] of the message properties
 * @param baseName specifies the prefix name of the message properties
 * @param key specifies the function that returns the message key in the message properties
 * @receiver the Set of <[ConstraintViolation]>
 * @return the Set of <[I18nConstraintViolation]>
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see I18nConstraintViolation
 * @since 0.1.0
 */
fun Set<ConstraintViolation>.mapToI18n(locale: Locale? = null,
                                       baseName: String = DEFAULT_BASE_NAME,
                                       key: (ConstraintViolation) -> String = { it.constraint.messageKey }) =
        this.map { it.toI18n(locale, baseName, key(it)) }.toSet()

/**
 * Converts to Set<[I18nConstraintViolation]>
 *
 * @param resourceBundle specifies the [ResourceBundle] that contains the messages
 * @param key specifies the function that returns the message key in the message properties
 * @receiver the Set of <[ConstraintViolation]>
 * @return the Set of <[I18nConstraintViolation]>
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see I18nConstraintViolation
 * @since 0.1.0
 */
fun Set<ConstraintViolation>.mapToI18n(resourceBundle: ResourceBundle,
                                       key: (ConstraintViolation) -> String = { it.constraint.messageKey }) =
        this.map { it.toI18n(resourceBundle, key(it)) }.toSet()