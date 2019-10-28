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
 * Represents a constraint that validate if the value is less than another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Less<T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value is less than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class LessOrEqual<T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value is greater than another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Greater<T>(val value: T) : Constraint

/**
 * Represents a constraint that validate if the value is greater than or equal to another value
 *
 * @property value specifies the value that should be validated
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class GreaterOrEqual<T>(val value: T) : Constraint

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
data class Between<T>(val start: T, val end: T) : Constraint

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
data class NotBetween<T>(val start: T, val end: T) : Constraint
