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

package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationFixture.EmptyConstraint
import org.valiktor.ConstraintViolationFixture.TestConstraint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private object ConstraintViolationFixture {

    object EmptyConstraint : Constraint

    data class TestConstraint(val value1: String, val value2: String) : Constraint {

        override val messageParams: Map<String, *> = mapOf("value1" to value1, "value2" to value2)
    }
}

class ConstraintViolationTest {

    @Test
    fun `should create ConstraintViolation`() {
        val constraintViolation: ConstraintViolation =
            DefaultConstraintViolation(
                property = "name",
                value = "Test",
                constraint = EmptyConstraint)

        assertEquals(constraintViolation.property, "name")
        assertEquals(constraintViolation.value, "Test")
        assertEquals(constraintViolation.constraint.name, "EmptyConstraint")
        assertEquals(constraintViolation.constraint.messageKey, "org.valiktor.ConstraintViolationFixture\$EmptyConstraint.message")
        assertEquals(constraintViolation.constraint.messageParams, emptyMap<String, Any>())
    }
}

class ConstraintViolationExceptionTest {

    @Test
    fun `should throws ConstraintViolationException`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            throw ConstraintViolationException(setOf(
                DefaultConstraintViolation(
                    property = "name",
                    value = "Test",
                    constraint = EmptyConstraint),
                DefaultConstraintViolation(
                    property = "name",
                    value = "Test2",
                    constraint = TestConstraint("test value 1", "test value 2"))))
        }

        assertThat(exception.constraintViolations)
            .containsExactly(
                DefaultConstraintViolation(
                    property = "name",
                    value = "Test",
                    constraint = EmptyConstraint),
                DefaultConstraintViolation(
                    property = "name",
                    value = "Test2",
                    constraint = TestConstraint("test value 1", "test value 2")))
    }
}
