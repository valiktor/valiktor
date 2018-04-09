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