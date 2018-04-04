package org.valiktor

import kotlin.reflect.KProperty1

/**
 * Validate an object
 *
 * Represents the extension function for validating any Kotlin object
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
fun <E> E.validate(block: ValidatorDsl<E>.() -> Unit): E {
    val validator = ValidatorDsl(this).apply(block)
    if (validator.constraints.isNotEmpty()) {
        throw ConstraintViolationException(validator.constraints)
    }
    return this
}

/**
 * Represents the DSL class that contains extended validation functions
 *
 * @param obj specifies the object to be validated
 * @property constraints specifies the set of [ConstraintViolation]
 * @constructor creates new DSL object
 *
 * @author Rodolpho S. Couto
 * @see Constraint
 * @see ConstraintViolation
 * @since 0.1.0
 */
open class ValidatorDsl<E>(private val obj: E) {

    /**
     * Specifies the violated constraints
     */
    val constraints = mutableSetOf<ConstraintViolation>()

    /**
     * Validates the property initializing another DSL function recursively
     *
     * @param block specifies the function DSL
     * @receiver the property to be validated
     * @return the same receiver property
     */
    fun <T> KProperty1<E, T>.validate(block: ValidatorDsl<T>.() -> Unit): KProperty1<E, T> {
        constraints += ValidatorDsl(this.get(obj)).apply(block).constraints.map {
            it.copy(property = "${this.name}.${it.property}",
                    value = this.get(obj) as Any)
        }
        return this
    }

    /**
     * Validates the collection property initializing another DSL function recursively
     *
     * @param block specifies the function DSL
     * @receiver the property to be validated
     * @return the same receiver property
     */
    fun <T> KProperty1<E, Collection<T>>.validateForEach(block: ValidatorDsl<T>.() -> Unit): KProperty1<E, Collection<T>> {
        this.get(obj).forEachIndexed { index, value ->
            constraints += ValidatorDsl(value).apply(block).constraints.map {
                it.copy(property = "${this.name}[$index].${it.property}",
                        value = value as Any)
            }
        }
        return this
    }
}