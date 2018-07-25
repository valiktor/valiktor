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

import org.valiktor.i18n.formatters.*
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

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
     * @param messageBundle specifies the loaded resource bundle with the messages
     * @return the formatted value
     */
    fun format(value: T, messageBundle: MessageBundle): String
}

/**
 * Represents the service provider interface that will be implemented by formatters outside this module
 *
 * The implementations will be found through [ServiceLoader]
 *
 * @property formatters specifies the [Set] of custom formatters
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
interface FormatterSpi {

    val formatters: Set<Pair<KClass<*>, Formatter<*>>>
}

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
    private var formatters: Map<KClass<*>, Formatter<*>> = mapOf(
            Any::class to AnyFormatter,
            Number::class to NumberFormatter,
            Date::class to DateFormatter,
            Calendar::class to CalendarFormatter,
            Iterable::class to IterableFormatter,
            Array<Any>::class to ArrayFormatter
    ) + ServiceLoader.load(FormatterSpi::class.java).flatMap { it.formatters }

    /**
     * Returns the formatter of this class recursively
     *
     * @param type specifies the class
     * @return the respective formatter
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(type: KClass<T>): Formatter<T> =
            formatters.getOrElse(type) {
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
            } as Formatter<T>

    /**
     * Adds a custom formatter for a class
     *
     * @param type specifies the class
     * @param formatter specifies the respective formatter
     */
    operator fun <T : Any> set(type: KClass<T>, formatter: Formatter<T>) {
        formatters += Pair(type, formatter)
    }

    /**
     * Removes a formatter for a class
     *
     * @param type specifies the class
     */
    operator fun <T : Any> minusAssign(type: KClass<T>) {
        formatters -= type
    }
}