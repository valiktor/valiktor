package org.valiktor

import java.util.*

private val defaultLocale = Locale.ENGLISH
private const val defaultBaseName = "org/valiktor/messages"

/**
 * Represents a constraint violation
 *
 * @property property specifies the property that violated the constraint
 * @property value specifies the invalid value
 * @property constraint specifies the violated constraint
 * @constructor creates a constraint violation
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
interface ConstraintViolation {

    val property: String
    val value: Any?
    val constraint: Constraint

    /**
     * Converts this object to [I18nConstraintViolation]
     *
     * @param locale specifies the [Locale] of the message properties
     * @param baseName specifies the prefix name of the message properties
     * @param key specifies the message key in the message properties
     * @return a new [I18nConstraintViolation]
     */
    fun toI18n(locale: Locale = defaultLocale,
               baseName: String = defaultBaseName,
               key: String = constraint.messageKey): I18nConstraintViolation

    /**
     * Converts this object to [I18nConstraintViolation]
     *
     * @param resourceBundle specifies the [ResourceBundle] that contains the messages
     * @param key specifies the message key in the message properties
     * @return a new [I18nConstraintViolation]
     */
    fun toI18n(resourceBundle: ResourceBundle,
               key: String = constraint.messageKey): I18nConstraintViolation
}

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

/**
 * Represents a exception that contains all the violated constraints of an object
 *
 * @param constraintViolations specifies a set of violated constraints
 * @constructor creates a exception with the violated constraints
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @since 0.1.0
 */
class ConstraintViolationException(val constraintViolations: Set<ConstraintViolation>) : RuntimeException()

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
fun Set<ConstraintViolation>.mapToI18n(locale: Locale = defaultLocale,
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

internal data class DefaultConstraintViolation(override val property: String,
                                               override val value: Any?,
                                               override val constraint: Constraint) : ConstraintViolation {

    override fun toI18n(locale: Locale, baseName: String, key: String): I18nConstraintViolation =
            DefaultI18nConstraintViolation(
                    property = this.property,
                    value = this.value,
                    constraint = this.constraint,
                    message = this.constraint.interpolator(ResourceBundle.getBundle(baseName, locale).getString(key)))


    override fun toI18n(resourceBundle: ResourceBundle, key: String): I18nConstraintViolation =
            DefaultI18nConstraintViolation(
                    property = this.property,
                    value = this.value,
                    constraint = this.constraint,
                    message = this.constraint.interpolator(resourceBundle.getString(key)))
}

internal data class DefaultI18nConstraintViolation(override val property: String,
                                                   override val value: Any?,
                                                   override val constraint: Constraint,
                                                   override val message: String) :
        ConstraintViolation by DefaultConstraintViolation(property, value, constraint), I18nConstraintViolation