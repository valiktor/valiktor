package org.valiktor

import java.util.Objects

/**
 * Represents a validation constraint
 *
 * @property name specifies the name of the constraint, generally it will be the name of the class
 * @property messageKey specifies the name of the key in the message properties file
 * @property interpolator specifies the interpolation function to replace variables in the message
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
interface Constraint {

    val name: String
    val messageKey: String
    val interpolator: (String) -> String
}

/**
 * Represents a abstract implementation of [Constraint]
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
abstract class AbstractConstraint : Constraint {
    override val name: String = this.javaClass.simpleName
    override val messageKey: String = "${this.javaClass.name}.message"
    override val interpolator: (String) -> String = { it }

    override fun equals(other: Any?): Boolean = other != null && other::class == this::class
    override fun hashCode(): Int = Objects.hash()
    override fun toString(): String = this.javaClass.simpleName
}

/**
 * Represents a constraint that validate if the value is null
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Null : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is not null
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotNull : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is equal to another value
 *
 * @property value specifies the value that should be equal
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Equals<out T>(val value: T) : AbstractConstraint()

/**
 * Represents a constraint that validate if the value isn't equal to another value
 *
 * @property value specifies the value that should not be equal
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotEquals<out T>(val value: T) : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is equal to one of the values
 *
 * @property values specifies the values to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class In<out T>(val values: Iterable<T>) : AbstractConstraint()

/**
 * Represents a constraint that validate if the value isn't equal to any value
 *
 * @property values specifies the values to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotIn<out T>(val values: Iterable<T>) : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is valid by passing a custom function
 *
 * @property validator specifies the validation function
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Valid<in T>(val validator: (T) -> Boolean) : AbstractConstraint()