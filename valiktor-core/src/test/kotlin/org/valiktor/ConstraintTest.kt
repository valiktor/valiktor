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

package org.valiktor

import org.valiktor.ConstraintFixture.CustomConstraint
import org.valiktor.ConstraintFixture.EmptyConstraint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

private object ConstraintFixture {

    object EmptyConstraint : Constraint

    data class CustomConstraint(
        override val name: String,
        override val messageKey: String,
        override val messageParams: Map<String, *>
    ) : Constraint
}

class ConstraintTest {

    @Test
    fun `should create Constraint with default properties`() {
        val constraint = EmptyConstraint

        assertEquals(constraint.name, "EmptyConstraint")
        assertEquals(constraint.messageKey, "org.valiktor.ConstraintFixture\$EmptyConstraint.message")
        assertEquals(constraint.messageParams, emptyMap<String, Any>())
    }

    @Test
    fun `should create Constraint with custom properties`() {
        val constraint = CustomConstraint(
            name = "TestTestConstraint",
            messageKey = "org.valiktor.test.constraints.TestConstraint.message",
            messageParams = mapOf("value" to 1))

        assertEquals(constraint.name, "TestTestConstraint")
        assertEquals(constraint.messageKey, "org.valiktor.test.constraints.TestConstraint.message")
        assertEquals(constraint.messageParams, mapOf("value" to 1))
    }

    @Test
    fun `should not be equal to null`() {
        val constraint = EmptyConstraint
        assertNotNull(constraint)
    }

    @Test
    fun `should not be equal to another constraint`() {
        val constraint = EmptyConstraint
        assertNotEquals<Constraint>(constraint, object : Constraint {})
    }
}
