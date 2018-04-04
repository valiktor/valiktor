package org.valiktor

import java.util.ResourceBundle

/**
 * Represents a constraint violation
 *
 * @param property specifies the property that violated the constraint
 * @param value specifies the invalid value
 * @param constraint specifies the violated constraint
 * @constructor creates a constraint violation
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class ConstraintViolation(val property: String,
                               val value: Any?,
                               val constraint: Constraint) {

    /**
     * Returns the validation message from {@link java.util.ResourceBundle}
     *
     * @param resourceBundle specifies the {@link java.util.ResourceBundle ResourceBundle}
     * @return the validation message
     */
    fun message(resourceBundle: ResourceBundle): String =
            constraint.interpolator(resourceBundle.getString(constraint.messageKey))

    /**
     * Returns the validation message from {@link java.util.ResourceBundle ResourceBundle} with a specific key
     *
     * @param resourceBundle specifies the {@link java.util.ResourceBundle ResourceBundle}
     * @param key specifies the key name
     * @return the validation message
     */
    fun message(resourceBundle: ResourceBundle, key: String): String =
            constraint.interpolator(resourceBundle.getString(key))
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