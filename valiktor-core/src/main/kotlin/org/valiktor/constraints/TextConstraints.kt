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
 * Represents a constraint that validate if the value is blank
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Blank : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is not blank
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotBlank : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is letter
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Letter : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is not letter
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotLetter : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Digit : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is not digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotDigit : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is letter or digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class LetterOrDigit : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is not letter or digit
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class NotLetterOrDigit : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is uppercase
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class UpperCase : AbstractConstraint()

/**
 * Represents a constraint that validate if the value is lowercase
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class LowerCase : AbstractConstraint()

/**
 * Represents a constraint that validate if the value matches with a pattern
 *
 * @property regex specifies the pattern value that should match
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class Matches(val regex: String) : AbstractConstraint() {
    constructor(regex: Regex) : this(regex.pattern)

    override val interpolator: (String) -> String = { it.replace("{pattern}", regex) }
}

/**
 * Represents a constraint that validate if the value doesn't match with a pattern
 *
 * @property regex specifies the pattern value that shouldn't match
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotMatch(val regex: String) : AbstractConstraint() {
    constructor(regex: Regex) : this(regex.pattern)

    override val interpolator: (String) -> String = { it.replace("{pattern}", regex) }
}

/**
 * Represents a constraint that validate if the value contains a pattern
 *
 * @property regex specifies the pattern value that should contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class ContainsRegex(val regex: String) : AbstractConstraint() {
    constructor(regex: Regex) : this(regex.pattern)

    override val interpolator: (String) -> String = { it.replace("{pattern}", regex) }
}

/**
 * Represents a constraint that validate if the value doesn't contain a pattern
 *
 * @property regex specifies the value pattern that shouldn't contain
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotContainRegex(val regex: String) : AbstractConstraint() {
    constructor(regex: Regex) : this(regex.pattern)

    override val interpolator: (String) -> String = { it.replace("{pattern}", regex) }
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
data class StartsWith(val prefix: String) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{prefix}", prefix) }
}

/**
 * Represents a constraint that validate if the value doesn't start with another value
 *
 * @property prefix specifies the value that shouldn't start
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotStartWith(val prefix: String) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{prefix}", prefix) }
}

/**
 * Represents a constraint that validate if the value ends with another value
 *
 * @property suffix specifies the value that should end
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class EndsWith(val suffix: String) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{suffix}", suffix) }
}

/**
 * Represents a constraint that validate if the value doesn't end with another value
 *
 * @property suffix specifies the value that not shouldn't end
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
data class NotEndWith(val suffix: String) : AbstractConstraint() {
    override val interpolator: (String) -> String = { it.replace("{suffix}", suffix) }
}

/**
 * Represents a constraint that validate if the value is a valid e-mail
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
class Email : AbstractConstraint()