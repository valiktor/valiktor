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
 * Represents a constraint that validate if the value is empty
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Empty : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is not empty
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotEmpty : AbstractConstraint()

/**
 * Represents a constraint that validate if the value size is within the limits (min and max)
 *
 * @property min specifies the minimum size
 * @property max specifies the maximum size
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Size(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : AbstractConstraint() {
    override val messageKey: String =
            if (min != Int.MIN_VALUE && max != Int.MAX_VALUE) super.messageKey
            else if (min != Int.MIN_VALUE) "${this.javaClass.name}.min.message"
            else if (max != Int.MAX_VALUE) "${this.javaClass.name}.max.message"
            else super.messageKey

    override val interpolator: (String) -> String =
            { it.replace("{min}", min.toString()).replace("{max}", max.toString()) }
}

/**
 * Represents a constraint that validate if the value contains another value
 *
 * @property value specifies the value that should contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Contains<out T>(val value: T) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{value}", value.toString()) }
}

/**
 * Represents a constraint that validate if the value contains all values
 *
 * @property values specifies the all values that should contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class ContainsAll<out T>(val values: Iterable<T>) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{values}", values.joinToString()) }
}

/**
 * Represents a constraint that validate if the value contains any value
 *
 * @property values specifies the values that one should contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class ContainsAny<out T>(val values: Iterable<T>) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{values}", values.joinToString()) }
}

/**
 * Represents a constraint that validate if the value doesn't contain another value
 *
 * @property value specifies the value that shouldn't contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotContain<out T>(val value: T) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{value}", value.toString()) }
}

/**
 * Represents a constraint that validate if the value doesn't contain all values
 *
 * @property values specifies the all values that shouldn't contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotContainAll<out T>(val values: Iterable<T>) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{values}", values.joinToString()) }
}

/**
 * Represents a constraint that validate if the value doesn't contain any value
 *
 * @property values specifies the values that one shouldn't contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotContainAny<out T>(val values: Iterable<T>) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{values}", values.joinToString()) }
}