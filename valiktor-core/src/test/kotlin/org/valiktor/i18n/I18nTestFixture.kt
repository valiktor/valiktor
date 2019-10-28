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

package org.valiktor.i18n

import org.valiktor.Constraint
import java.util.Locale
import kotlin.reflect.full.declaredMemberProperties

object SupportedLocales {

    val DEFAULT = Locale("")
    val CA = Locale("ca")
    val DE = Locale("de")
    val EN = Locale("en")
    val ES = Locale("es")
    val PT_BR = Locale("pt", "BR")
}

val INVALID_LOCALE = Locale("INVALID")

private const val DEFAULT_BUNDLE = "org/valiktor/messages"

private val SUPPORTED_LOCALES: List<Locale> = SupportedLocales::class.declaredMemberProperties
    .map { it.get(SupportedLocales) }
    .map { it as Locale }
    .sortedBy { it.toString() }

fun Constraint.interpolatedMessages(): Map<Locale, String> =
    SUPPORTED_LOCALES
        .map {
            it to interpolate(
                MessageBundle(
                    baseName = this.messageBundle,
                    locale = it,
                    fallbackBaseName = this.messageBundle,
                    fallbackLocale = Locale.getDefault()),
                this.messageKey,
                this.messageParams)
        }
        .toMap()

fun <T : Any> Formatter<T>.formatAllSupportedLocales(value: T): Map<Locale, String> = SUPPORTED_LOCALES
    .map {
        it to this.format(value, MessageBundle(
            baseName = DEFAULT_BUNDLE,
            locale = it,
            fallbackBaseName = DEFAULT_BUNDLE,
            fallbackLocale = Locale.getDefault()))
    }
    .toMap()

fun Formatter<Array<Any>>.formatAllSupportedLocales(value: Array<Any>): Map<Locale, String> = SUPPORTED_LOCALES
    .map {
        it to this.format(value, MessageBundle(
            baseName = DEFAULT_BUNDLE,
            locale = it,
            fallbackBaseName = DEFAULT_BUNDLE,
            fallbackLocale = Locale.getDefault()))
    }
    .toMap()