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

package org.valiktor.test

import org.valiktor.ConstraintViolation
import org.valiktor.ConstraintViolationException
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * Assert that a [block] fails with [ConstraintViolationException] being thrown.
 *
 * @param block specifies the code to be evaluated
 * @return DSL to verify expected constraint violations
 *
 * @author Rodolpho S. Couto
 * @since 0.8.0
 */
fun <E> shouldFailValidation(block: () -> Unit): TestValidator<E> {
    val ex = assertFailsWith<ConstraintViolationException> { block() }
    return TestValidator(ex.constraintViolations)
}

/**
 * Represents the DSL class containing the [verify] function
 *
 * @param constraintViolations specifies the set of [ConstraintViolation]
 * @constructor creates new DSL object
 *
 * @author Rodolpho S. Couto
 * @see shouldFailValidation
 * @see ConstraintViolation
 * @since 0.8.0
 */
class TestValidator<E>(private val constraintViolations: Set<ConstraintViolation>) {

    /**
     * Verify expected constraint violations using [TestValidatorVerifier] DSL
     *
     * @param block specifies the DSL to verify expected constraint violations
     */
    fun verify(block: TestValidatorVerifier<E>.() -> Unit) {
        val expectedConstraintViolations = TestValidatorVerifier<E>().apply(block).expectedConstraintViolations
        val constraintViolations = constraintViolations
            .map { TestConstraintViolation(it.property, it.value, it.constraint) }
            .toSet()

        assertTrue(message(expectedConstraintViolations, constraintViolations)) {
            expectedConstraintViolations == constraintViolations
        }
    }
}