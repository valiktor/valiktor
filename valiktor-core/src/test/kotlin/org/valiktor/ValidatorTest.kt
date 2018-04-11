package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ValidatorFixture.Employee
import org.valiktor.constraints.*
import org.valiktor.i18n.DefaultI18nConstraintViolation
import org.valiktor.i18n.I18nConstraintViolation
import org.valiktor.i18n.mapToI18n
import java.util.*

private object Locales {

    val DEFAULT = Locale("")
    val EN = Locale("en")
    val PT_BR = Locale("pt", "BR")
}

private object ValidatorFixture {

    val supportedLocales = setOf(
            Locales.DEFAULT,
            Locales.EN,
            Locales.PT_BR)

    data class Employee(val id: Int? = null, val name: String? = null, val company: Company? = null, val addresses: List<Address>? = null)
    data class Company(val id: Int? = null, val name: String? = null)
    data class Address(val id: Int? = null, val name: String? = null, val city: City? = null)
    data class City(val id: Int? = null, val name: String? = null, val state: State? = null)
    data class State(val id: Int? = null, val name: String? = null, val country: Country? = null)
    data class Country(val id: Int? = null, val name: String? = null)
}

class ValidatorTest {

    @Test
    fun `should validate isNull`() {
        Employee().validate {
            Employee::id.isNull()
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = Null()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = Null(), message = "Must be null"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = Null(), message = "Must be null"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = Null(), message = "Deve ser nulo"))))
    }

    @Test
    fun `should validate isNotNull`() {
        Employee(id = 1).validate {
            Employee::id.isNotNull()
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee().validate {
                Employee::id.isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = null, constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = null, constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = null, constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = null, constraint = NotNull(), message = "Não deve ser nulo"))))
    }

    @Test
    fun `should validate isEqualTo`() {
        Employee(id = 1).validate {
            Employee::id.isEqualTo(1)
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 2).validate {
                Employee::id.isEqualTo(1)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 2, constraint = Equals(1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 2, constraint = Equals(1), message = "Must be equal to 1"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 2, constraint = Equals(1), message = "Must be equal to 1"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 2, constraint = Equals(1), message = "Deve ser igual a 1"))))
    }

    @Test
    fun `should validate isNotEqualTo`() {
        Employee(id = 2).validate {
            Employee::id.isNotEqualTo(1)
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isNotEqualTo(1)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = NotEquals(1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotEquals(1), message = "Must not be equal to 1"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotEquals(1), message = "Must not be equal to 1"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotEquals(1), message = "Não deve ser igual a 1"))))
    }

    @Test
    fun `should validate isIn with vararg`() {
        Employee(id = 2).validate {
            Employee::id.isIn(1, 2, 3)
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isIn(0, 2, 3)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = In(setOf(0, 2, 3))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = In(setOf(0, 2, 3)), message = "Must be in 0, 2, 3"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = In(setOf(0, 2, 3)), message = "Must be in 0, 2, 3"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = In(setOf(0, 2, 3)), message = "Deve ser um desses: 0, 2, 3"))))
    }

    @Test
    fun `should validate isIn with iterable`() {
        Employee(id = 2).validate {
            Employee::id.isIn(listOf(1, 2, 3))
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isIn(listOf(0, 2, 3))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = In(listOf(0, 2, 3))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = In(listOf(0, 2, 3)), message = "Must be in 0, 2, 3"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = In(listOf(0, 2, 3)), message = "Must be in 0, 2, 3"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = In(listOf(0, 2, 3)), message = "Deve ser um desses: 0, 2, 3"))))
    }

    @Test
    fun `should validate isNotIn with vararg`() {
        Employee(id = 1).validate {
            Employee::id.isNotIn(0, 2, 3)
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isNotIn(1, 2, 3)
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = NotIn(setOf(1, 2, 3))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotIn(setOf(1, 2, 3)), message = "Must not be in 1, 2, 3"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotIn(setOf(1, 2, 3)), message = "Must not be in 1, 2, 3"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotIn(setOf(1, 2, 3)), message = "Não deve ser um desses: 1, 2, 3"))))
    }

    @Test
    fun `should validate isNotIn with iterable`() {
        Employee(id = 1).validate {
            Employee::id.isNotIn(listOf(0, 2, 3))
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isNotIn(listOf(1, 2, 3))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = NotIn(listOf(1, 2, 3))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotIn(listOf(1, 2, 3)), message = "Must not be in 1, 2, 3"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotIn(listOf(1, 2, 3)), message = "Must not be in 1, 2, 3"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = NotIn(listOf(1, 2, 3)), message = "Não deve ser um desses: 1, 2, 3"))))
    }

    @Test
    fun `should validate isValid`() {
        Employee().validate {
            Employee::id.isValid { it == null }
        }

        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 1).validate {
                Employee::id.isValid { it == null }
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 1, constraint = Valid<Int?>({ it == null })))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = Valid<Int?>({ it == null }), message = "Must be valid"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = Valid<Int?>({ it == null }), message = "Must be valid"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 1, constraint = Valid<Int?>({ it == null }), message = "Deve ser válido"))))
    }
}