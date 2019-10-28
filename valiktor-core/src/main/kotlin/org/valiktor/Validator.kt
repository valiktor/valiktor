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

import kotlin.reflect.KProperty1

/**
 * Validate an object
 *
 * Represents the function that validating any Kotlin object
 * If any constraint is violated, a [ConstraintViolationException] will be thrown
 *
 * @param block specifies the function DSL
 * @receiver the object to be validated
 * @return the same receiver object
 * @throws ConstraintViolationException
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
fun <E> validate(obj: E, block: Validator<E>.(E) -> Unit): E {
    val validator = Validator(obj).apply { block(obj) }
    if (validator.constraintViolations.isNotEmpty()) {
        throw ConstraintViolationException(validator.constraintViolations)
    }
    return obj
}

/**
 * Represents the DSL class that contains validation functions
 *
 * @param obj specifies the object to be validated
 * @property constraintViolations specifies the set of [ConstraintViolation]
 * @constructor creates new DSL object
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @see ConstraintViolation
 * @since 0.1.0
 */
open class Validator<E>(private val obj: E) {

    /**
     * Specifies the violated constraints
     */
    internal val constraintViolations = mutableSetOf<ConstraintViolation>()

    /**
     * Returns a [Property] for this property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validate")
    fun <T> validate(property: KProperty1<E, T?>): Property<T?> = Property(obj, property)

    /**
     * Returns a [Property] for this iterable property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateIterable")
    fun <T> validate(property: KProperty1<E, Iterable<T>?>): Property<Iterable<T>?> = Property(obj, property)

    /**
     * Returns a [Property] for this array property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateArray")
    fun <T> validate(property: KProperty1<E, Array<T>?>): Property<Array<T>?> = Property(obj, property)

    /**
     * Represents a property validator that contains extended functions
     *
     * @param obj specifies the object to be validated
     * @param property specifies the property to be validated
     *
     * @author Rodolpho S. Couto
     * @see Validator
     * @see KProperty1
     * @since 0.1.0
     */
    open inner class Property<T>(internal val obj: E, internal val property: KProperty1<E, T?>) {

        /**
         * Validates the property by passing the constraint and the validation function
         *
         * This function is used by all constraint validations
         *
         * @param constraint specifies the function that returns the constraint to be validated
         * @param isValid specifies the validation function
         * @return the property validator
         */
        fun validate(constraint: (T?) -> Constraint, isValid: (T?) -> Boolean): Property<T> {
            val value = this.property.get(obj)
            if (this@Validator.constraintViolations.none { it.property == this.property.name } && !isValid(value)) {
                this@Validator.constraintViolations += DefaultConstraintViolation(this.property.name, value, constraint(value))
            }
            return this
        }

        /**
         * Validates the property by passing the constraint and the validation function
         *
         * This function is used by all constraint validations
         *
         * @param constraint specifies the constraint that will be validated
         * @param isValid specifies the validation function
         * @return the property validator
         */
        fun validate(constraint: Constraint, isValid: (T?) -> Boolean): Property<T> = validate({ constraint }, isValid)

        /**
         * Adds the constraint violations to property
         *
         * @param constraintViolations specifies the constraint violations
         */
        internal fun addConstraintViolations(constraintViolations: Iterable<ConstraintViolation>) {
            this@Validator.constraintViolations += constraintViolations
        }
    }
}
