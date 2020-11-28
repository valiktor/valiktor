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
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.isEmpty(): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(Empty) { it == null || it.isEmpty() }

/**
 * Validates if the [Map] property is not empty
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.isNotEmpty(): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotEmpty) { it == null || it.isNotEmpty() }

/**
 * Validates if the [Map] property contains the key
 *
 * @param key specifies the key that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsKey(key: K): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(Contains(key)) { it == null || it.containsKey(key) }

/**
 * Validates if the [Map] property contains all keys
 *
 * @param keys specifies the all keys that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAllKeys(vararg keys: K): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAll(keys.toSet())) { it == null || keys.all { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property contains all keys
 *
 * @param keys specifies the all keys that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAllKeys(keys: Iterable<K>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAll(keys)) { it == null || keys.all { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property contains any key
 *
 * @param keys specifies the keys that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAnyKey(vararg keys: K): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAny(keys.toSet())) { it == null || keys.any { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property contains any key
 *
 * @param keys specifies the keys that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAnyKey(keys: Iterable<K>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAny(keys)) { it == null || keys.any { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property doesn't contain the key
 *
 * @param key specifies the key that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainKey(key: K): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContain(key)) { it == null || !it.containsKey(key) }

/**
 * Validates if the [Map] property doesn't contain all keys
 *
 * @param keys specifies the all keys that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAllKeys(vararg keys: K): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAll(keys.toSet())) { it == null || !keys.all { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property doesn't contain all keys
 *
 * @param keys specifies the all keys that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAllKeys(keys: Iterable<K>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAll(keys)) { it == null || !keys.all { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property doesn't contain any key
 *
 * @param keys specifies the keys that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAnyKey(vararg keys: K): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAny(keys.toSet())) { it == null || !keys.any { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property doesn't contain any key
 *
 * @param keys specifies the keys that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAnyKey(keys: Iterable<K>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAny(keys)) { it == null || !keys.any { e -> it.containsKey(e) } }

/**
 * Validates if the [Map] property contains the value
 *
 * @param value specifies the value that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsValue(value: V): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(Contains(value)) { it == null || it.containsValue(value) }

/**
 * Validates if the [Map] property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAllValues(vararg values: V): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAll(values.toSet())) { it == null || values.all { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property contains all values
 *
 * @param values specifies the all values that should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAllValues(values: Iterable<V>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAll(values)) { it == null || values.all { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAnyValue(vararg values: V): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAny(values.toSet())) { it == null || values.any { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property contains any value
 *
 * @param values specifies the values that one should contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.containsAnyValue(values: Iterable<V>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(ContainsAny(values)) { it == null || values.any { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property doesn't contain the value
 *
 * @param value specifies the value that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainValue(value: V): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContain(value)) { it == null || !it.containsValue(value) }

/**
 * Validates if the [map] property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAllValues(vararg values: V): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAll(values.toSet())) { it == null || !values.all { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property doesn't contain all values
 *
 * @param values specifies the all values that shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAllValues(values: Iterable<V>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAll(values)) { it == null || !values.all { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAnyValue(vararg values: V): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAny(values.toSet())) { it == null || !values.any { e -> it.containsValue(e) } }

/**
 * Validates if the [Map] property doesn't contain any value
 *
 * @param values specifies the values that one shouldn't contain
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E, K, V> Validator<E>.ValueValidator<Map<K, V>?>.doesNotContainAnyValue(values: Iterable<V>): Validator<E>.ValueValidator<Map<K, V>?> =
    this.validate(NotContainAny(values)) { it == null || !values.any { e -> it.containsValue(e) } }
