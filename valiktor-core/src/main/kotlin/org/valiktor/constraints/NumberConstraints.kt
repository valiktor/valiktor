/*
 * Copyright 2018-2020 https://www.valiktor.org
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

import org.valiktor.Constraint

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
data class IntegerDigits(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : Constraint {
    override val messageKey: String =
        if (min != Int.MIN_VALUE && max != Int.MAX_VALUE) super.messageKey
        else if (min != Int.MIN_VALUE) "${this.javaClass.name}.min.message"
        else if (max != Int.MAX_VALUE) "${this.javaClass.name}.max.message"
        else super.messageKey

    override val messageParams: Map<String, *>
        get() = if (min != Int.MIN_VALUE && max != Int.MAX_VALUE)
            mapOf(this::min.name to this.min, this::max.name to this.max)
        else if (min != Int.MIN_VALUE)
            mapOf(this::min.name to this.min)
        else if (max != Int.MAX_VALUE)
            mapOf(this::max.name to this.max)
        else
            mapOf(this::min.name to this.min, this::max.name to this.max)
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
data class DecimalDigits(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : Constraint {
    override val messageKey: String =
        if (min != Int.MIN_VALUE && max != Int.MAX_VALUE) super.messageKey
        else if (min != Int.MIN_VALUE) "${this.javaClass.name}.min.message"
        else if (max != Int.MAX_VALUE) "${this.javaClass.name}.max.message"
        else super.messageKey

    override val messageParams: Map<String, *>
        get() = if (min != Int.MIN_VALUE && max != Int.MAX_VALUE)
            mapOf(this::min.name to this.min, this::max.name to this.max)
        else if (min != Int.MIN_VALUE)
            mapOf(this::min.name to this.min)
        else if (max != Int.MAX_VALUE)
            mapOf(this::max.name to this.max)
        else
            mapOf(this::min.name to this.min, this::max.name to this.max)
}
