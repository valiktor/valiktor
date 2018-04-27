package org.valiktor

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
                .map { it to this.interpolator(getBundle("org/valiktor/messages", it).getString(this.messageKey)) }
                .toMap()

data class Employee(val id: Int? = null,
                    val name: String? = null,
                    val email: String? = null,
                    val age: Short? = null,
                    val ssn: Long? = null,
                    val username: String? = null,
                    val company: Company? = null,
                    val address: Address? = null)

data class Company(val id: Int? = null,
                   val name: String? = null,
                   val age: Short? = null,
                   val code: Long? = null,
                   val valuation: Double? = null,
                   val addresses: List<Address>? = null)

data class Address(val id: Int? = null,
                   val street: String? = null,
                   val number: Long? = null,
                   val apartment: Short? = null,
                   val city: City? = null,
                   val latitude: Double? = null,
                   val longitude: Double? = null)

data class City(val id: Int? = null,
                val name: String? = null,
                val state: State? = null)

data class State(val id: Int? = null,
                 val name: String? = null,
                 val country: Country? = null)

data class Country(val id: Int? = null,
                   val name: String? = null)