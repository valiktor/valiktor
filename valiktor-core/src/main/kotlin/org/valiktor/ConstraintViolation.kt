package org.valiktor

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
}

internal data class DefaultConstraintViolation(override val property: String,
                                               override val value: Any?,
                                               override val constraint: Constraint) : ConstraintViolation

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