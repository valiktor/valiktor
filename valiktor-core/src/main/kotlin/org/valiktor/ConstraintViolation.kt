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