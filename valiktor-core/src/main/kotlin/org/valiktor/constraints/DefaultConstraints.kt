package org.valiktor.constraints

import org.valiktor.AbstractConstraint
import org.valiktor.Constraint

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
class Valid<in T>(val validator: (T) -> Boolean) : AbstractConstraint()