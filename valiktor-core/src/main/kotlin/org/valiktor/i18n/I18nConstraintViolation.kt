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
import java.util.*

/**
 * Represents a constraint violation with internationalized message
 *
 * @property message specifies the internationalized message of this constraint violation
 * @constructor creates a i18n constraint violation
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @since 0.1.0
 */
interface I18nConstraintViolation : ConstraintViolation {

    val message: String
}

internal data class DefaultI18nConstraintViolation(override val property: String,
                                                   override val value: Any? = null,
                                                   override val constraint: Constraint,
                                                   override val message: String) :
        ConstraintViolation by DefaultConstraintViolation(property, value, constraint), I18nConstraintViolation

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
                                fallbackBaseName = this.constraint.messageBundle),
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