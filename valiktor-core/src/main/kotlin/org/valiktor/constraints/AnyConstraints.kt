/*
 * Copyright 2018-2019 https://www.valiktor.org
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
 * Represents a constraint that validate if the value is null
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object Null : Constraint

/**
 * Represents a constraint that validate if the value is not null
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object NotNull : Constraint

/**
 * Represents a constraint that validate if the value is equal to another value
 *
 * @property value specifies the value that should be equal
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Equals<T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value isn't equal to another value
 *
 * @property value specifies the value that should not be equal
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotEquals<T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value is equal to one of the values
 *
 * @property values specifies the values to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class In<T>(val values: Iterable<T>) : Constraint

/**
 * Represents a constraint that validate if the value isn't equal to any value
 *
 * @property values specifies the values to be compared
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotIn<T>(val values: Iterable<T>) : Constraint

/**
 * Represents a constraint that validate if the value is valid by passing a custom function
 *
 * @property validator specifies the validation function
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object Valid : Constraint