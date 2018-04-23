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

package org.valiktor.functions

import org.valiktor.DefaultConstraintViolation
import org.valiktor.Validator

/**
 * Validates the iterable property initializing another DSL function recursively
 *
 * @param block specifies the function DSL
 * @receiver the property to be validated
 * @return the same receiver property
 */
@JvmName("validateForEachIterable")
fun <E, T> Validator<E>.Property<Iterable<T>?>.validateForEach(block: Validator<T>.() -> Unit): Validator<E>.Property<Iterable<T>?> {
    this.property.get(obj)?.forEachIndexed { index, value ->
        this.addConstraintViolations(Validator(value).apply(block).constraintViolations.map {
            DefaultConstraintViolation(
                    property = "${this.property.name}[$index].${it.property}",
                    value = it.value,
                    constraint = it.constraint)
        })
    }
    return this
}