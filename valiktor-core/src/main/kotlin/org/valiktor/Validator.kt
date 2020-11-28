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

import kotlin.reflect.KFunction
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
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validate")
    fun <T> validate(property: KProperty1<E, T?>): ValueValidator<T?> = Property(obj, property)

    /**
     * Returns a [Function] for this function
     *
     * @receiver the function to be validated
     * @return the function validator
     */
    @JvmName("validate")
    fun <T> validate(function: KFunction<T?>): ValueValidator<T?> = Function(obj, function)

    /**
     * Returns a [Property] for this iterable property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateIterable")
    fun <T> validate(property: KProperty1<E, Iterable<T>?>): ValueValidator<Iterable<T>?> = Property(obj, property)

    /**
     * Returns a [Function] for this iterable function
     *
     * @receiver the function to be validated
     * @return the function validator
     */
    @JvmName("validateIterable")
    fun <T> validate(function: KFunction<Iterable<T>?>): ValueValidator<Iterable<T>?> = Function(obj, function)

    /**
     * Returns a [Property] for this array property
     *
     * @receiver the property to be validated
     * @return the property validator
     */
    @JvmName("validateArray")
    fun <T> validate(property: KProperty1<E, Array<T>?>): ValueValidator<Array<T>?> = Property(obj, property)

    /**
     * Returns a [Function] for this array function
     *
     * @receiver the function to be validated
     * @return the function validator
     */
    @JvmName("validateArray")
    fun <T> validate(property: KFunction<Array<T>?>): ValueValidator<Array<T>?> = Function(obj, property)

    /**
     * Represents a value validator that contains extended functions.
     *
     * @param obj specifies the object to be validated
     *
     * @author Rodolpho S. Couto
     * @author Justin Sexton
     * @see Validator
     * @since 0.12.0
     */
    abstract inner class ValueValidator<T>(val obj: E) {
        /**
         * @return The name of the provided values source
         */
        abstract fun name(): String

        /**
         * Extracts the value off of a given object that should be validated
         *
         * @return The value that should be validated
         */
        abstract fun value(): T

        /**
         * Validates the property by passing the constraint and the validation function
         *
         * This function is used by all constraint validations
         *
         * @param constraint specifies the function that returns the constraint to be validated
         * @param isValid specifies the validation function
         * @return the property validator
         */
        fun validate(constraint: (T?) -> Constraint, isValid: (T?) -> Boolean): ValueValidator<T> {
            val value = value()
            if (!isValid(value)) {
                this@Validator.constraintViolations += DefaultConstraintViolation(
                        property = name(),
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
        fun validate(constraint: Constraint, isValid: (T?) -> Boolean): ValueValidator<T> =
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
        suspend fun coValidate(constraint: (T?) -> Constraint, isValid: suspend (T?) -> Boolean): ValueValidator<T> {
            val value = value()
            if (!isValid(value)) {
                this@Validator.constraintViolations += DefaultConstraintViolation(
                        property = name(),
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
        suspend fun coValidate(constraint: Constraint, isValid: suspend (T?) -> Boolean): ValueValidator<T> =
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

    /**
     * Represents a property validator that contains extended functions
     *
     * @param obj specifies the object to be validated
     * @param property specifies the property to be validated
     *
     * @author Rodolpho S. Couto
     * @author Justin Sexton
     * @see Validator
     * @see KProperty1
     * @since 0.12.0
     */
    private inner class Property<T>(obj: E, val property: KProperty1<E, T?>) : ValueValidator<T?>(obj) {
        override fun value(): T? = this.property.get(obj)

        override fun name(): String = this.property.name
    }

    /**
     * Represents a validator specific to validating function return values
     *
     * @param obj specifies the object to be validated
     * @param function specifies the function return value to be validated
     *
     * @author Rodolpho S. Couto
     * @author Justin Sexton
     * @see Validator
     * @see KFunction
     * @since 0.12.0
     */
    private inner class Function<T>(obj: E, val function: KFunction<T?>) : ValueValidator<T?>(obj) {
        override fun value(): T? = this.function.call(obj)

        override fun name(): String = this.function.name
    }
}
