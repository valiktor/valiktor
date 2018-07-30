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

import kotlin.reflect.full.declaredMemberProperties

/**
 * Represents a validation constraint
 *
 * @property name specifies the name of the constraint, generally it will be the name of the class
 * @property messageBundle specifies the base name of the default message properties file
 * @property messageKey specifies the name of the key in the message properties file
 * @property messageParams specifies the parameters to replace in the message
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
interface Constraint {

    val name: String
        get() = this.javaClass.simpleName

    val messageBundle: String
        get() = "org/valiktor/messages"

    val messageKey: String
        get() = "${this.javaClass.name}.message"

    val messageParams: Map<String, *>
        get() = this.javaClass.kotlin.declaredMemberProperties
                .asSequence()
                .filter {
                    Constraint::class.declaredMemberProperties
                            .none { p -> p.name == it.name }
                }
                .map { it.name to it.get(this) }
                .toMap()
}