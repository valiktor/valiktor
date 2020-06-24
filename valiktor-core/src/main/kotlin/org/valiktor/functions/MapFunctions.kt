package org.valiktor.functions

import org.valiktor.DefaultConstraintViolation
import org.valiktor.Validator
import org.valiktor.constraints.Empty
import org.valiktor.constraints.In
import org.valiktor.constraints.NotEmpty

/**
 * Validates the map keys property initializing another DSL function recursively
 * @param block specifies the function DSL
 * @receiver the property to be validated
 * @return the same receiver property
 */
@JvmName("validateKeys")
inline fun <E, K, V> Validator<E>.Property<Map<K, V>?>.validateKeys(
    block: Validator<K>.(K) -> Unit
): Validator<E>.Property<Map<K, V>?> {
    this.property.get(this.obj)?.keys?.forEach { key ->
        this.addConstraintViolations(
            Validator(key).apply { block(key) }.constraintViolations.map {
                DefaultConstraintViolation(
                    property = "${this.property.name}[${it.property}]",
                    value = it.value,
                    constraint = it.constraint
                )
            }
        )
    }
    return this
}

/**
 * Validates if the [Map] property is equal to one of the values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.isIn(vararg values: Map<K, V>): Validator<E>.Property<Map<K, V>?> =
    this.validate(In(values.toSet())) { it == null || values.contains(it) }

/**
 * Validates if the [Map] property is empty
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.isEmpty(): Validator<E>.Property<Map<K, V>?> =
    this.validate(Empty) { it == null || it.count() == 0 }

/**
 * Validates if the [Map] property is not empty
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.isNotEmpty(): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotEmpty) { it == null || it.count() > 0 }


/**
 * Validates if the [Key] property is equal to one of the values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.keyIsIn(vararg values: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(In(values.toSet())) { it == null || it.keys.any { key -> values.contains(key) } }