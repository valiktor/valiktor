package org.valiktor.i18n

import org.valiktor.Constraint
import java.util.*
import kotlin.reflect.full.declaredMemberProperties

object SupportedLocales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

private val SUPPORTED_LOCALES: List<Locale> = SupportedLocales::class.declaredMemberProperties
        .map { it.get(SupportedLocales) }
        .map { it as Locale }

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