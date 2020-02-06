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
 * Represents a constraint that validate if the value is blank
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object Blank : Constraint

/**
 * Represents a constraint that validate if the value is not blank
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object NotBlank : Constraint

/**
 * Represents a constraint that validate if the value is letter
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object Letter : Constraint

/**
 * Represents a constraint that validate if the value is not letter
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object NotLetter : Constraint

/**
 * Represents a constraint that validate if the value is digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object Digit : Constraint

/**
 * Represents a constraint that validate if the value is not digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object NotDigit : Constraint

/**
 * Represents a constraint that validate if the value is letter or digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object LetterOrDigit : Constraint

/**
 * Represents a constraint that validate if the value is not letter or digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object NotLetterOrDigit : Constraint

/**
 * Represents a constraint that validate if the value is uppercase
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object UpperCase : Constraint

/**
 * Represents a constraint that validate if the value is lowercase
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object LowerCase : Constraint

/**
 * Represents a constraint that validate if the value matches with a pattern
 *
 * @property pattern specifies the pattern value that should match
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Matches(val pattern: String) : Constraint {
    constructor(regex: Regex) : this(regex.pattern)
}

/**
 * Represents a constraint that validate if the value doesn't match with a pattern
 *
 * @property pattern specifies the pattern value that shouldn't match
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotMatch(val pattern: String) : Constraint {
    constructor(regex: Regex) : this(regex.pattern)
}

/**
 * Represents a constraint that validate if the value contains a pattern
 *
 * @property pattern specifies the pattern value that should contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class ContainsRegex(val pattern: String) : Constraint {
    constructor(regex: Regex) : this(regex.pattern)
}

/**
 * Represents a constraint that validate if the value doesn't contain a pattern
 *
 * @property pattern specifies the value pattern that shouldn't contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotContainRegex(val pattern: String) : Constraint {
    constructor(regex: Regex) : this(regex.pattern)
}

/**
 * Represents a constraint that validate if the value starts with another value
 *
 * @property prefix specifies the value that should start
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class StartsWith(val prefix: String) : Constraint

/**
 * Represents a constraint that validate if the value doesn't start with another value
 *
 * @property prefix specifies the value that shouldn't start
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotStartWith(val prefix: String) : Constraint

/**
 * Represents a constraint that validate if the value ends with another value
 *
 * @property suffix specifies the value that should end
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class EndsWith(val suffix: String) : Constraint

/**
 * Represents a constraint that validate if the value doesn't end with another value
 *
 * @property suffix specifies the value that not shouldn't end
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotEndWith(val suffix: String) : Constraint

/**
 * Represents a constraint that validate if the value is a valid e-mail
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object Email : Constraint

/**
 * Represents a constraint that validate if the value is a valid website
 *
 * @author Roberto A. Neto
 * @see Constraint
 * @since 0.10.0
 */
object Website : Constraint
