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

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
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
    private var formatters: Map<KClass<*>, Formatter<*>> = mapOf(
            Any::class to AnyFormatter,
            Number::class to NumberFormatter,
            Date::class to DateFormatter,
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
 * Represents the formatter for [Number] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object NumberFormatter : Formatter<Number> {
    override fun format(value: Number, resourceBundle: ResourceBundle): String {
        val symbols = DecimalFormatSymbols.getInstance(resourceBundle.locale)
        symbols.groupingSeparator = resourceBundle.getString("org.valiktor.formatters.NumberFormatter.groupingSeparator")[0]
        symbols.decimalSeparator = resourceBundle.getString("org.valiktor.formatters.NumberFormatter.decimalSeparator")[0]

        val bigNum = value as? BigDecimal ?: BigDecimal(value.toString()).stripTrailingZeros()
        val integerDigits = (bigNum.precision() - bigNum.scale()).let { if (it <= 0) 1 else it }
        val fractionDigits = bigNum.scale().let { if (it < 0) 0 else it }

        val decimalFormat = DecimalFormat("#,###.#")
        decimalFormat.decimalFormatSymbols = symbols
        decimalFormat.minimumIntegerDigits = integerDigits
        decimalFormat.maximumIntegerDigits = integerDigits
        decimalFormat.minimumFractionDigits = fractionDigits
        decimalFormat.maximumFractionDigits = fractionDigits

        return decimalFormat.format(value)
    }
}

/**
 * Represents the formatter for [Date] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object DateFormatter : Formatter<Date> {
    override fun format(value: Date, resourceBundle: ResourceBundle): String =
            SimpleDateFormat(resourceBundle.getString(
                    if (value.hasTime())
                        "org.valiktor.formatters.DateFormatter.dateTimePattern"
                    else
                        "org.valiktor.formatters.DateFormatter.datePattern"
            )).format(value)

    private fun Date.hasTime(): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = this

        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        return hours + minutes + seconds > 0
    }
}

/**
 * Represents the formatter for [Iterable] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object IterableFormatter : Formatter<Iterable<Any>> {
    override fun format(value: Iterable<Any>, resourceBundle: ResourceBundle): String =
            value.joinToString(
                    separator = resourceBundle.getString("org.valiktor.formatters.IterableFormatter.separator"),
                    transform = { Formatters[it.javaClass.kotlin].format(it, resourceBundle) })
}

/**
 * Represents the formatter for [Array] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object ArrayFormatter : Formatter<Array<Any>> {
    override fun format(value: Array<Any>, resourceBundle: ResourceBundle): String =
            value.joinToString(
                    separator = resourceBundle.getString("org.valiktor.formatters.ArrayFormatter.separator"),
                    transform = { Formatters[it.javaClass.kotlin].format(it, resourceBundle) })
}