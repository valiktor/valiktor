package org.valiktor

import java.util.Locale

object Locales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

val SUPPORTED_LOCALES = setOf(
        Locales.DEFAULT,
        Locales.EN,
        Locales.PT_BR)

class EmptyConstraint : AbstractConstraint()

data class CustomConstraint(override val name: String,
                            override val messageKey: String,
                            override val interpolator: (String) -> String) : AbstractConstraint()

data class TestConstraint(val value1: String,
                          val value2: String) : AbstractConstraint() {

    override val interpolator: (String) -> String = { it.replace("{value1}", value1).replace("{value2}", value2) }
}

data class Employee(val id: Int? = null,
                    val name: String? = null,
                    val email: String? = null,
                    val username: String? = null,
                    val company: Company? = null,
                    val address: Address? = null,
                    val dependents: Array<Dependent>? = null)

data class Dependent(val id: Int? = null,
                     val name: String? = null)

data class Company(val id: Int? = null,
                   val name: String? = null,
                   val addresses: List<Address>? = null)

data class Address(val id: Int? = null,
                   val street: String? = null,
                   val number: Int? = null,
                   val city: City? = null)

data class City(val id: Int? = null,
                val name: String? = null,
                val state: State? = null)

data class State(val id: Int? = null,
                 val name: String? = null,
                 val country: Country? = null)

data class Country(val id: Int? = null,
                   val name: String? = null)