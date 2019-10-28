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

/**
 * Represents the DSL class to verify expected constraint violations from [Iterable] and [Array] properties
 *
 * @constructor creates new DSL object
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see TestValidatorVerifier
 * @since 0.8.0
 */
class TestValidatorCollectionVerifier<E> {

    private val _expectedConstraintViolations = mutableMapOf<Int, Set<ConstraintViolation>>()
    internal val expectedConstraintViolations: Map<Int, Set<ConstraintViolation>> get() = _expectedConstraintViolations

    /**
     * Expect the next element
     *
     * @param block specifies the DSL to verify expected constraint violations
     */
    fun expectElement(block: TestValidatorVerifier<E>.() -> Unit) {
        _expectedConstraintViolations += _expectedConstraintViolations.size to TestValidatorVerifier<E>().apply(block).expectedConstraintViolations
    }
}
