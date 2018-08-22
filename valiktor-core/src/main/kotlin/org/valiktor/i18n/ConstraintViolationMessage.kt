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

import org.valiktor.Constraint
import org.valiktor.ConstraintViolation
import org.valiktor.DefaultConstraintViolation
import java.util.Locale

/**
 * Represents a constraint violation with internationalized message
 *
 * @property message specifies the internationalized message of this constraint violation
 * @constructor creates a constraint violation with message
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @since 0.1.0
 */
interface ConstraintViolationMessage : ConstraintViolation {

    val message: String
}

data class DefaultConstraintViolationMessage(
    override val property: String,
    override val value: Any? = null,
    override val constraint: Constraint,
    override val message: String
) : ConstraintViolation by DefaultConstraintViolation(property, value, constraint), ConstraintViolationMessage

/**
 * Converts this object to [ConstraintViolationMessage]
 *
 * @param baseName specifies the prefix name of the message properties
 * @param locale specifies the [Locale] of the message properties
 * @return a new [ConstraintViolationMessage]
 */
fun ConstraintViolation.toMessage(baseName: String = constraint.messageBundle, locale: Locale = Locale.getDefault()): ConstraintViolationMessage =
    DefaultConstraintViolationMessage(
        property = this.property,
        value = this.value,
        constraint = this.constraint,
        message = interpolate(
            MessageBundle(
                baseName = baseName,
                locale = locale,
                fallbackBaseName = this.constraint.messageBundle,
                fallbackLocale = Locale.getDefault()),
            this.constraint.messageKey,
            this.constraint.messageParams))

/**
 * Converts to List<[ConstraintViolationMessage]>
 *
 * @param baseName specifies the prefix name of the message properties
 * @param locale specifies the [Locale] of the message properties
 * @receiver the Iterable of <[ConstraintViolation]>
 * @return the List of <[ConstraintViolationMessage]>
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see ConstraintViolationMessage
 * @since 0.1.0
 */
fun Iterable<ConstraintViolation>.mapToMessage(baseName: String? = null, locale: Locale = Locale.getDefault()): List<ConstraintViolationMessage> =
    this.map { it.toMessage(baseName ?: it.constraint.messageBundle, locale) }

/**
 * Converts to Sequence<[ConstraintViolationMessage]>
 *
 * @param baseName specifies the prefix name of the message properties
 * @param locale specifies the [Locale] of the message properties
 * @receiver the Sequence of <[ConstraintViolation]>
 * @return the Sequence of <[ConstraintViolationMessage]>
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see ConstraintViolationMessage
 * @since 0.1.0
 */
fun Sequence<ConstraintViolation>.mapToMessage(baseName: String? = null, locale: Locale = Locale.getDefault()): Sequence<ConstraintViolationMessage> =
    this.map { it.toMessage(baseName ?: it.constraint.messageBundle, locale) }