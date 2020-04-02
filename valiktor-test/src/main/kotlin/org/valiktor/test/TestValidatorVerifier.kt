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

package org.valiktor.test

import org.valiktor.Constraint
import org.valiktor.ConstraintViolation
import kotlin.reflect.KProperty1

/**
 * Represents the DSL class to verify expected constraint violations
 *
 * @constructor creates new DSL object
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolation
 * @see TestValidator
 * @since 0.8.0
 */
class TestValidatorVerifier<E> {

    val expectedConstraintViolations = mutableSetOf<ConstraintViolation>()

    /**
     * Expect a constraint violation with [property], [value] and [constraint]
     *
     * @param property specifies the invalid property
     * @param value specifies the invalid value
     * @param constraint specifies the violated constraint
     */
    fun <T> expect(property: KProperty1<E, T?>, value: Any?, constraint: Constraint) {
        expectedConstraintViolations += TestConstraintViolation(
            property = property.name,
            value = value,
            constraint = constraint
        )
    }

    /**
     * Initialize a [block] to expect nested constraint violations from [property]
     *
     * @param property specifies the invalid property
     * @param block specifies the DSL to verify nested properties
     */
    inline fun <T> expect(property: KProperty1<E, T?>, block: TestValidatorVerifier<T>.() -> Unit) {
        expectedConstraintViolations += TestValidatorVerifier<T>().apply(block).expectedConstraintViolations
            .map {
                TestConstraintViolation(
                    property = "${property.name}.${it.property}",
                    value = it.value,
                    constraint = it.constraint
                )
            }
    }

    /**
     * Initialize a [block] to expect iterable constraint violations from [property]
     *
     * @param property specifies the invalid property
     * @param block specifies the DSL to verify iterable elements
     */
    @JvmName("expectAllIterable")
    inline fun <T> expectAll(
        property: KProperty1<E, Iterable<T>?>,
        block: TestValidatorCollectionVerifier<T>.() -> Unit
    ) {
        expectedConstraintViolations += TestValidatorCollectionVerifier<T>().apply(block).expectedConstraintViolations
            .toList()
            .flatMap { pair ->
                pair.second.map {
                    TestConstraintViolation(
                        property = "${property.name}[${pair.first}].${it.property}",
                        value = it.value,
                        constraint = it.constraint
                    )
                }
            }
    }

    /**
     * Initialize a [block] to expect array constraint violations from [property]
     *
     * @param property specifies the invalid property
     * @param block specifies the DSL to verify array elements
     */
    @JvmName("expectAllArray")
    inline fun <T> expectAll(property: KProperty1<E, Array<T>?>, block: TestValidatorCollectionVerifier<T>.() -> Unit) {
        expectedConstraintViolations += TestValidatorCollectionVerifier<T>().apply(block).expectedConstraintViolations
            .toList()
            .flatMap { pair ->
                pair.second.map {
                    TestConstraintViolation(
                        property = "${property.name}[${pair.first}].${it.property}",
                        value = it.value,
                        constraint = it.constraint
                    )
                }
            }
    }
}
