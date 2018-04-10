package org.valiktor.i18n

import org.valiktor.Constraint
import org.valiktor.ConstraintViolation
import org.valiktor.DefaultConstraintViolation
import java.util.Locale
import java.util.ResourceBundle
import java.util.ResourceBundle.getBundle

private const val defaultBaseName = "org/valiktor/messages"

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
                                                   override val value: Any?,
                                                   override val constraint: Constraint,
                                                   override val message: String) :
        ConstraintViolation by DefaultConstraintViolation(property, value, constraint), I18nConstraintViolation

/**
 * Converts this object to [I18nConstraintViolation]
 *
 * @param locale specifies the [Locale] of the message properties
 * @param baseName specifies the prefix name of the message properties
 * @param key specifies the message key in the message properties
 * @return a new [I18nConstraintViolation]
 */
fun ConstraintViolation.toI18n(locale: Locale? = null,
                               baseName: String = defaultBaseName,
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
                                       baseName: String = defaultBaseName,
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