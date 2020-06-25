package org.valiktor.functions

import org.valiktor.Validator
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.Empty
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotEmpty

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
 * Validates if the [Key] property contains the value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsKey(value: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(Contains(value)) { it == null || it.containsKey(value) }

/**
 * Validates if the [Key] property contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAllKeys(vararg values: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.toSet().all { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAllKeys(values: Iterable<K>): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property contains any value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAnyKeys(vararg values: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.toSet().any { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAnyKeys(values: Iterable<K>): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property doesn't contains the value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsKey(value: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContain(value)) { it == null || !it.containsKey(value) }

/**
 * Validates if the [Key] property doesn't contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAllKeys(vararg values: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.toSet().all { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property doesn't contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAllKeys(values: Iterable<K>): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property doesn't contains any value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAnyKeys(vararg values: K): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.toSet().any { e -> it.containsKey(e) } }

/**
 * Validates if the [Key] property doesn't contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAnyKeys(values: Iterable<K>): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.containsKey(e) } }

/**
 * Validates if the [Value] property contains the value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsValue(value: V): Validator<E>.Property<Map<K, V>?> =
    this.validate(Contains(value)) { it == null || it.containsValue(value) }

/**
 * Validates if the [Value] property contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAllValues(vararg values: V): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.toSet().all { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAllValues(values: Iterable<V>): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property contains any value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAnyValues(vararg values: V): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.toSet().any { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property contains any value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.containsAnyValues(values: Iterable<V>): Validator<E>.Property<Map<K, V>?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property doesn't contains the value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsValue(value: V): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContain(value)) { it == null || !it.containsValue(value) }

/**
 * Validates if the [Value] property doesn't contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAllValues(vararg values: V): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.toSet().all { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property doesn't contains all values
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAllValues(values: Iterable<V>): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property doesn't contains any value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAnyValues(vararg values: V): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.toSet().any { e -> it.containsValue(e) } }

/**
 * Validates if the [Value] property doesn't contains any value
 * @param block specifies the array of values to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.Property<Map<K, V>?>.doesNotContainsAnyValues(values: Iterable<V>): Validator<E>.Property<Map<K, V>?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.containsValue(e) } }
