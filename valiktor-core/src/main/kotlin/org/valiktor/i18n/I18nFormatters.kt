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

package org.valiktor.i18n

import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

/**
 * Represents the formatters used in message interpolations
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object Formatters {

    /**
     * Map containing the classes and their respective formatters
     */
    private val formatters: Map<KClass<*>, Formatter<*>> = mutableMapOf(
            Any::class to AnyFormatter,
            Iterable::class to IterableFormatter,
            Array<Any>::class to ArrayFormatter
    )

    /**
     * Returns the formatter of this class recursively
     *
     * @param type specifies the class
     * @return the respective formatter
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(type: KClass<T>): Formatter<T> =
            formatters.getOrElse(type, {
                val superclass = type.superclasses.firstOrNull { formatters.containsKey(it) } ?: Any::class
                if (superclass != Any::class) {
                    return formatters.getValue(superclass) as Formatter<T>
                }

                type.superclasses.forEach {
                    val formatter = get(it)
                    if (formatter != AnyFormatter) {
                        return formatter as Formatter<T>
                    }
                }

                return AnyFormatter
            }) as Formatter<T>

    /**
     * Adds a custom formatter for a class
     *
     * @param type specifies the class
     * @param formatter specifies the respective formatter
     */
    operator fun <T : Any> set(type: KClass<T>, formatter: Formatter<T>) {
        formatters.plus(Pair(type, formatter))
    }
}

/**
 * Represents the type formatter
 *
 * @param T specifies the formatter type
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
interface Formatter<in T : Any> {

    /**
     * Format the value
     *
     * @param value specifies the value to be formatted
     * @param resourceBundle specifies the loaded resource bundle with the messages
     * @return the formatted value
     */
    fun format(value: T, resourceBundle: ResourceBundle): String
}

/**
 * Represents the formatter for [Any] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object AnyFormatter : Formatter<Any> {
    override fun format(value: Any, resourceBundle: ResourceBundle): String =
            value.toString()
}

/**
 * Represents the formatter for [Iterable] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object IterableFormatter : Formatter<Iterable<Any>> {
    override fun format(value: Iterable<Any>, resourceBundle: ResourceBundle): String =
            value.joinToString { Formatters[it.javaClass.kotlin].format(it, resourceBundle) }
}

/**
 * Represents the formatter for [Array] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object ArrayFormatter : Formatter<Array<Any>> {
    override fun format(value: Array<Any>, resourceBundle: ResourceBundle): String =
            value.joinToString { Formatters[it.javaClass.kotlin].format(it, resourceBundle) }
}