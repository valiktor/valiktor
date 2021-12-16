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

import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
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
inline fun <E> validate(obj: E, block: Validator<E>.(E) -> Unit): E {
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
    val constraintViolations = mutableSetOf<ConstraintViolation>()

    /**
     * Returns a [Property] for this property
     *
     * @receiver the class property to be validated
     * @return the property validator
     *
     * @see KProperty1
     */
    @JvmName("validate")
    fun <T> validate(property: KProperty1<E, T?>): Property<T?> = Property1(obj, property)

    /**
     * Returns a [Property] for this property
     *
     * @receiver the object property to be validated
     * @return the property validator
     *
     * @see KProperty0
     */
    @JvmName("validate")
    fun <T> validate(property: KProperty0<T?>): Property<T?> = Property0(property)

    /**
     * Returns a [Property] for this iterable property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateIterable")
    fun <T> validate(property: KProperty1<E, Iterable<T>?>): Property<Iterable<T>?> = Property1(obj, property)

    /**
     * Returns a [Property] for this iterable property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateIterable")
    fun <T> validate(property: KProperty0<Iterable<T>?>): Property<Iterable<T>?> = Property0(property)

    /**
     * Returns a [Property] for this array property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateArray")
    fun <T> validate(property: KProperty1<E, Array<T>?>): Property<Array<T>?> = Property1(obj, property)

    /**
     * Returns a [Property] for this array property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateArray")
    fun <T> validate(property: KProperty0<Array<T>?>): Property<Array<T>?> = Property0(property)

    /**
     * KProperty1 based implementation of a Property
     *
     * @param property KProperty1
     *
     * @author Gennadi Kudrjavtsev
     * @since 0.13
     * @see Property
     * @see KProperty1
     */
    private inner class Property1<T, E>(obj: E, property: KProperty1<E, T?>) :
        Property<T>(property, { property.get(obj) })

    /**
     * KProperty0 based implementation of a Property
     *
     * @param property KProperty0
     *
     * @author Gennadi Kudrjavtsev
     * @since 0.13
     * @see Property
     * @see KProperty0
     */
    private inner class Property0<T>(property: KProperty0<T?>) :
        Property<T>(property, property::get)

    /**
     * Represents a property validator that contains extended functions
     *
     * @param property specifies the property to be validated
     * @param getValue specifies the function to get value of a property
     *
     * @author Rodolpho S. Couto
     * @author Gennadi Kudrjavtsev
     * @see Validator
     * @see KProperty1
     * @since 0.1.0
     */
    abstract inner class Property<T>(val property: KProperty<T?>, val getValue: () -> T?) {

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
            val value = getValue()
            if (!isValid(value)) {
                this@Validator.constraintViolations += DefaultConstraintViolation(
                    property = this.property.name,
                    value = value,
                    constraint = constraint(value)
                )
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
        fun validate(constraint: Constraint, isValid: (T?) -> Boolean): Property<T> =
            validate({ constraint }, isValid)

        /**
         * Validates the property by passing the constraint and the suspending validation function
         *
         * This function is used by all constraint validations
         *
         * @param constraint specifies the function that returns the constraint to be validated
         * @param isValid specifies the validation function
         * @return the property validator
         */
        suspend fun coValidate(constraint: (T?) -> Constraint, isValid: suspend (T?) -> Boolean): Property<T> {
            val value = getValue()
            if (!isValid(value)) {
                this@Validator.constraintViolations += DefaultConstraintViolation(
                    property = this.property.name,
                    value = value,
                    constraint = constraint(value)
                )
            }
            return this
        }

        /**
         * Validates the property by passing the constraint and the suspending validation function
         *
         * This function is used by all constraint validations
         *
         * @param constraint specifies the constraint that will be validated
         * @param isValid specifies the validation function
         * @return the property validator
         */
        suspend fun coValidate(constraint: Constraint, isValid: suspend (T?) -> Boolean): Property<T> =
            coValidate({ constraint }, isValid)

        /**
         * Adds the constraint violations to property
         *
         * @param constraintViolations specifies the constraint violations
         */
        fun addConstraintViolations(constraintViolations: Iterable<ConstraintViolation>) {
            this@Validator.constraintViolations += constraintViolations
        }
    }
}
