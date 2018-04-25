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

package org.valiktor.constraints

import org.valiktor.AbstractConstraint
import org.valiktor.Constraint

/**
 * Represents a constraint that validate if the value is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Less<out T>(val value: T) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{value}", value.toString()) }
}

/**
 * Represents a constraint that validate if the value is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class LessOrEqual<out T>(val value: T) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{value}", value.toString()) }
}

/**
 * Represents a constraint that validate if the value is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Greater<out T>(val value: T) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{value}", value.toString()) }
}

/**
 * Represents a constraint that validate if the value is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class GreaterOrEqual<out T>(val value: T) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{value}", value.toString()) }
}

/**
 * Represents a constraint that validate if the value is between two values
 *
 * @property start (inclusive) specifies value that should start
 * @property end (inclusive) specifies value that should end
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Between<out T>(val start: T, val end: T) : AbstractConstraint() {
    override val interpolator: (String) -> String =
            { it.replace("{start}", start.toString()).replace("{end}", end.toString()) }
}

/**
 * Represents a constraint that validate if the value isn't between two values
 *
 * @property start (inclusive) specifies value that shouldn't start
 * @property end (inclusive) specifies value that shouldn't end
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotBetween<out T>(val start: T, val end: T) : AbstractConstraint() {
    override val interpolator: (String) -> String =
            { it.replace("{start}", start.toString()).replace("{end}", end.toString()) }
}

/**
 * Represents a constraint that validate if the integer value (before decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class IntegerDigits(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : AbstractConstraint() {
    override val messageKey: String =
            if (min != Int.MIN_VALUE && max != Int.MAX_VALUE) super.messageKey
            else if (min != Int.MIN_VALUE) "${this.javaClass.name}.min.message"
            else if (max != Int.MAX_VALUE) "${this.javaClass.name}.max.message"
            else super.messageKey

    override val interpolator: (String) -> String =
            { it.replace("{min}", min.toString()).replace("{max}", max.toString()) }
}

/**
 * Represents a constraint that validate if the decimal value (after decimal separator) is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class DecimalDigits(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : AbstractConstraint() {
    override val messageKey: String =
            if (min != Int.MIN_VALUE && max != Int.MAX_VALUE) super.messageKey
            else if (min != Int.MIN_VALUE) "${this.javaClass.name}.min.message"
            else if (max != Int.MAX_VALUE) "${this.javaClass.name}.max.message"
            else super.messageKey

    override val interpolator: (String) -> String =
            { it.replace("{min}", min.toString()).replace("{max}", max.toString()) }
}