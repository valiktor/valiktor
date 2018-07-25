package org.valiktor.i18n

import java.util.*
import kotlin.reflect.full.declaredMemberProperties

object SupportedLocales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

private const val DEFAULT_BUNDLE = "org/valiktor/messages"

private val SUPPORTED_LOCALES: List<Locale> = SupportedLocales::class.declaredMemberProperties
        .map { it.get(SupportedLocales) }
        .map { it as Locale }

fun <T : Any> Formatter<T>.formatAllSupportedLocales(value: T): Map<Locale, String> = SUPPORTED_LOCALES
        .map {
            it to this.format(value, MessageBundle(
                    baseName = DEFAULT_BUNDLE,
                    locale = it,
                    fallbackBaseName = DEFAULT_BUNDLE,
                    fallbackLocale = Locale.getDefault()))
        }
        .toMap()