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
 * Represents a constraint that validate if the value is true
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object True : Constraint

/**
 * Represents a constraint that validate if the value is false
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @since 0.1.0
 */
object False : Constraint