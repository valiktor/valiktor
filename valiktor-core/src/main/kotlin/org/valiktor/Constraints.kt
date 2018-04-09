package org.valiktor

/**
 * Represents a validation constraint
 *
 * @property name specifies the name of the constraint
 * @property messageKey specifies the message key in the properties files
 * @property interpolator specifies the interpolation function
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
interface Constraint {

    /**
     * Specifies the name of the constraint, generally it will be the name of the class
     */
    val name: String
        get() = this.javaClass.simpleName

    /**
     * Specifies the name of the key in the message properties file
     */
    val messageKey: String
        get() = "${this.javaClass.name}.message"

    /**
     * Specifies the interpolation function to replace variables in the message
     */
    val interpolator: (String) -> String
        get() = { it }
}

/**
 * Represents a constraint that validate if the value is null
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Null : Constraint {
    override fun equals(other: Any?): Boolean = other is Null
}

/**
 * Represents a constraint that validate if the value is not null
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotNull : Constraint {
    override fun equals(other: Any?): Boolean = other is NotNull
}

/**
 * Represents a constraint that validate if the value is equal to another value
 *
 * @property value specifies the value that should be equal
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Equals<out T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value isn't equal to another value
 *
 * @property value specifies the value that should not be equal
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotEquals<out T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value is equal to one of the values
 *
 * @property values specifies the values to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class In<out T>(val values: Iterable<T>) : Constraint

/**
 * Represents a constraint that validate if the value isn't equal to any value
 *
 * @property values specifies the values to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotIn<out T>(val values: Iterable<T>) : Constraint

/**
 * Represents a constraint that validate if the value is valid by passing a custom function
 *
 * @property validator specifies the validation function
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Valid<in T>(val validator: (T) -> Boolean) : Constraint