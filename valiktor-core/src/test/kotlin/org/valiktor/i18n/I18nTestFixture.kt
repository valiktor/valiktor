package org.valiktor.i18n

import org.valiktor.Constraint
import java.util.*
import java.util.ResourceBundle.getBundle
import kotlin.reflect.full.declaredMemberProperties

object Locales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

private val SUPPORTED_LOCALES: List<Locale> = Locales::class.declaredMemberProperties
        .map { it.get(Locales) }
        .map { it as Locale }

private val RESOURCE_BUNDLES: List<ResourceBundle> = SUPPORTED_LOCALES
        .map { getBundle("org/valiktor/messages", it) }

fun Constraint.interpolatedMessages(): Map<Locale, String> =
        RESOURCE_BUNDLES
                .map { it.locale to interpolate(it, this.messageKey, this.messageParams) }
                .toMap()

fun <T : Any> Formatter<T>.format(value: T): Map<Locale, String> = RESOURCE_BUNDLES
        .map { it.locale to this.format(value, it) }
        .toMap()

fun Formatter<Array<Any>>.format(value: Array<Any>): Map<Locale, String> = RESOURCE_BUNDLES
        .map { it.locale to this.format(value, it) }
        .toMap()