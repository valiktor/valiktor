package org.valiktor.i18n

import org.valiktor.Constraint
import java.util.*
import java.util.ResourceBundle.getBundle

object Locales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

val SUPPORTED_LOCALES = setOf(
        Locales.DEFAULT,
        Locales.EN,
        Locales.PT_BR)

fun Constraint.interpolatedMessages(): Map<Locale, String> =
        SUPPORTED_LOCALES
                .map { it to interpolate(getBundle("org/valiktor/messages", it), this.messageKey, this.messageParams) }
                .toMap()