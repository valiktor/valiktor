package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ValidatorFixture.Address
import org.valiktor.ValidatorFixture.City
import org.valiktor.ValidatorFixture.Company
import org.valiktor.ValidatorFixture.Country
import org.valiktor.ValidatorFixture.Dependent
import org.valiktor.ValidatorFixture.Employee
import org.valiktor.ValidatorFixture.State
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
}

class DefaultConstraintsValidatorTest {

    @Test
    fun `isNull with null property should be valid`() {
        Employee().validate {
            Employee::id.isNull()
        }
    }

    @Test
    fun `isNull with not null property should be invalid`() {
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
    fun `isNotNull with not null property should be valid`() {
        Employee(id = 1).validate {
            Employee::id.isNotNull()
        }
    }

    @Test
    fun `isNotNull with null property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee().validate {
                Employee::id.isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", constraint = NotNull(), message = "Não deve ser nulo"))))
    }

    @Test
    fun `isEqualTo with null property should be valid`() {
        Employee().validate {
            Employee::id.isEqualTo(1)
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        Employee(id = 1).validate {
            Employee::id.isEqualTo(1)
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
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
    fun `isNotEqualTo with null property should be valid`() {
        Employee().validate {
            Employee::id.isNotEqualTo(1)
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        Employee(id = 2).validate {
            Employee::id.isNotEqualTo(1)
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
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
    fun `isIn vararg with null property should be valid`() {
        Employee().validate {
            Employee::id.isIn(1, 2, 3)
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        Employee(id = 2).validate {
            Employee::id.isIn(1, 2, 3)
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
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
    fun `isIn iterable with null property should be valid`() {
        Employee().validate {
            Employee::id.isIn(listOf(1, 2, 3))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        Employee(id = 2).validate {
            Employee::id.isIn(listOf(1, 2, 3))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
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
    fun `isNotIn vararg with null property should be valid`() {
        Employee().validate {
            Employee::id.isNotIn(0, 2, 3)
        }
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        Employee(id = 1).validate {
            Employee::id.isNotIn(0, 2, 3)
        }
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
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
    fun `isNotIn iterable with null property should be valid`() {
        Employee().validate {
            Employee::id.isNotIn(listOf(0, 2, 3))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        Employee(id = 1).validate {
            Employee::id.isNotIn(listOf(0, 2, 3))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
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
    fun `isValid with null property should be valid`() {
        Employee().validate {
            Employee::id.isValid { it == 1 }
        }
    }

    @Test
    fun `isValid with same value should be valid`() {
        Employee(id = 1).validate {
            Employee::id.isValid { it == 1 }
        }
    }

    @Test
    fun `isValid with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(id = 2).validate {
                Employee::id.isValid { it == 1 }
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", value = 2, constraint = Valid<Int?>({ it == 1 })))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 2, constraint = Valid<Int?>({ it == 1 }), message = "Must be valid"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 2, constraint = Valid<Int?>({ it == 1 }), message = "Must be valid"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "id", value = 2, constraint = Valid<Int?>({ it == 1 }), message = "Deve ser válido"))))
    }

    @Test
    fun `inner null properties should be valid`() {
        Employee().validate {
            Employee::company.validate {
                Company::id.isNotNull()
            }
            Employee::address.validate {
                Address::id.isNotNull()
                Address::city.validate {
                    City::id.isNotNull()
                    City::state.validate {
                        State::id.isNotNull()
                        State::country.validate {
                            Country::id.isNotNull()
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `inner not null properties should be valid`() {
        Employee(id = 1, company = Company(id = 1), address =
        Address(id = 1, city = City(id = 1, state =
        State(id = 1, country = Country(id = 1)))))
                .validate {
                    Employee::id.isNotNull()
                    Employee::company.validate {
                        Company::id.isNotNull()
                    }
                    Employee::address.validate {
                        Address::id.isNotNull()
                        Address::city.validate {
                            City::id.isNotNull()
                            City::state.validate {
                                State::id.isNotNull()
                                State::country.validate {
                                    Country::id.isNotNull()
                                }
                            }
                        }
                    }
                }
    }

    @Test
    fun `inner null properties should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(company = Company(), address =
            Address(city = City(state =
            State(country = Country()))))
                    .validate {
                        Employee::id.isNotNull()
                        Employee::company.validate {
                            Company::id.isNotNull()
                        }
                        Employee::address.validate {
                            Address::id.isNotNull()
                            Address::city.validate {
                                City::id.isNotNull()
                                City::state.validate {
                                    State::id.isNotNull()
                                    State::country.validate {
                                        Country::id.isNotNull()
                                    }
                                }
                            }
                        }
                    }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "address.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "address.city.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "address.city.state.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "address.city.state.country.id", constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.city.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.city.state.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.city.state.country.id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.city.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.city.state.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "address.city.state.country.id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "company.id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "address.id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "address.city.id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "address.city.state.id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "address.city.state.country.id", constraint = NotNull(), message = "Não deve ser nulo"))))
    }

    @Test
    fun `inner null iterable properties should be valid`() {
        Employee(company = Company()).validate {
            Employee::company.validate {
                Company::addresses.validateForEach {
                    Address::id.isNotNull()
                    Address::city.validate {
                        City::id.isNotNull()
                    }
                }
            }
        }
    }

    @Test
    fun `inner iterable properties should be valid`() {
        Employee(company = Company(addresses = listOf(
                Address(id = 1, city = City(id = 1)),
                Address(id = 1, city = City(id = 1)),
                Address(id = 1, city = City(id = 1)))))
                .validate {
                    Employee::company.validate {
                        Company::addresses.validateForEach {
                            Address::id.isNotNull()
                            Address::city.validate {
                                City::id.isNotNull()
                            }
                        }
                    }
                }
    }

    @Test
    fun `inner iterable properties should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(company = Company(addresses = listOf(
                    Address(city = City()),
                    Address(city = City()),
                    Address(city = City()))))
                    .validate {
                        Employee::company.validate {
                            Company::addresses.validateForEach {
                                Address::id.isNotNull()
                                Address::city.validate {
                                    City::id.isNotNull()
                                }
                            }
                        }
                    }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "company.addresses[0].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[0].city.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[1].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[1].city.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[2].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[2].city.id", constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "company.addresses[0].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[0].city.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[1].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[1].city.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[2].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[2].city.id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "company.addresses[0].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[0].city.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[1].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[1].city.id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[2].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "company.addresses[2].city.id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "company.addresses[0].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "company.addresses[0].city.id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "company.addresses[1].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "company.addresses[1].city.id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "company.addresses[2].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "company.addresses[2].city.id", constraint = NotNull(), message = "Não deve ser nulo"))))
    }

    @Test
    fun `inner null array properties should be valid`() {
        Employee().validate {
            Employee::dependents.validateForEach {
                Dependent::id.isNotNull()
            }
        }
    }

    @Test
    fun `inner array properties should be valid`() {
        Employee(dependents = arrayOf(
                Dependent(id = 1),
                Dependent(id = 1),
                Dependent(id = 1)))
                .validate {
                    Employee::dependents.validateForEach {
                        Dependent::id.isNotNull()
                    }
                }
    }

    @Test
    fun `inner array properties should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(dependents = arrayOf(
                    Dependent(),
                    Dependent(),
                    Dependent()))
                    .validate {
                        Employee::dependents.validateForEach {
                            Dependent::id.isNotNull()
                        }
                    }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dependents[0].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "dependents[1].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "dependents[2].id", constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "dependents[0].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[1].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[2].id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "dependents[0].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[1].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[2].id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "dependents[0].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "dependents[1].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "dependents[2].id", constraint = NotNull(), message = "Não deve ser nulo"))))
    }

    @Test
    fun `should not repeat the property`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee().validate {
                Employee::id.isNotNull().isEqualTo(1).isIn(1, 2, 3)
                Employee::name.isNotNull().isEqualTo("test").isIn("test1", "test2", "test3")
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", constraint = NotNull()),
                DefaultConstraintViolation(property = "name", constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "name", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "name", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "name", constraint = NotNull(), message = "Não deve ser nulo"))))
    }
}

class TextConstraintsValidatorTest {

    @Test
    fun `isEmpty with null property should be valid`() {
        Employee().validate {
            Employee::name.isEmpty()
        }
    }

    @Test
    fun `isEmpty with empty property should be valid`() {
        Employee(name = "").validate {
            Employee::name.isEmpty()
        }
    }

    @Test
    fun `isEmpty with blank property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(name = " ").validate {
                Employee::name.isEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = " ", constraint = Empty()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "name", value = " ", constraint = Empty(), message = "Must be empty"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "name", value = " ", constraint = Empty(), message = "Must be empty"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "name", value = " ", constraint = Empty(), message = "Deve ser vazio"))))
    }

    @Test
    fun `isNotEmpty with blank property should be valid`() {
        Employee(name = " ").validate {
            Employee::name.isNotEmpty()
        }
    }

    @Test
    fun `isNotEmpty with null or empty property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(email = "").validate {
                Employee::name.isNotEmpty()
                Employee::email.isNotEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", constraint = NotEmpty()),
                DefaultConstraintViolation(property = "email", value = "", constraint = NotEmpty()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "name", constraint = NotEmpty(), message = "Must not be empty"),
                        DefaultI18nConstraintViolation(property = "email", value = "", constraint = NotEmpty(), message = "Must not be empty"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "name", constraint = NotEmpty(), message = "Must not be empty"),
                        DefaultI18nConstraintViolation(property = "email", value = "", constraint = NotEmpty(), message = "Must not be empty"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "name", constraint = NotEmpty(), message = "Não deve ser vazio"),
                        DefaultI18nConstraintViolation(property = "email", value = "", constraint = NotEmpty(), message = "Não deve ser vazio"))))
    }

    @Test
    fun `isBlank with null property should be valid`() {
        Employee().validate {
            Employee::name.isBlank()
        }
    }

    @Test
    fun `isBlank with empty property should be valid`() {
        Employee(name = "").validate {
            Employee::name.isBlank()
        }
    }

    @Test
    fun `isBlank with blank property should be valid`() {
        Employee(name = " ").validate {
            Employee::name.isBlank()
        }
    }

    @Test
    fun `isBlank with not blank property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(name = "a").validate {
                Employee::name.isBlank()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "a", constraint = Blank()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(DefaultI18nConstraintViolation(
                        property = "name", value = "a", constraint = Blank(), message = "Must be blank"))),
                entry(Locales.EN, setOf(DefaultI18nConstraintViolation(
                        property = "name", value = "a", constraint = Blank(), message = "Must be blank"))),
                entry(Locales.PT_BR, setOf(DefaultI18nConstraintViolation(
                        property = "name", value = "a", constraint = Blank(), message = "Deve estar em branco"))))
    }

    @Test
    fun `isNotBlank with not blank property should be valid`() {
        Employee(name = "a").validate {
            Employee::name.isNotEmpty()
        }
    }

    @Test
    fun `isNotBlank with null or empty or blank property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(email = "", username = " ").validate {
                Employee::name.isNotBlank()
                Employee::email.isNotBlank()
                Employee::username.isNotBlank()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", constraint = NotBlank()),
                DefaultConstraintViolation(property = "email", value = "", constraint = NotBlank()),
                DefaultConstraintViolation(property = "username", value = " ", constraint = NotBlank()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "name", constraint = NotBlank(), message = "Must not be blank"),
                        DefaultI18nConstraintViolation(property = "email", value = "", constraint = NotBlank(), message = "Must not be blank"),
                        DefaultI18nConstraintViolation(property = "username", value = " ", constraint = NotBlank(), message = "Must not be blank"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "name", constraint = NotBlank(), message = "Must not be blank"),
                        DefaultI18nConstraintViolation(property = "email", value = "", constraint = NotBlank(), message = "Must not be blank"),
                        DefaultI18nConstraintViolation(property = "username", value = " ", constraint = NotBlank(), message = "Must not be blank"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "name", constraint = NotBlank(), message = "Não deve estar em branco"),
                        DefaultI18nConstraintViolation(property = "email", value = "", constraint = NotBlank(), message = "Não deve estar em branco"),
                        DefaultI18nConstraintViolation(property = "username", value = " ", constraint = NotBlank(), message = "Não deve estar em branco"))))
    }

    @Test
    fun `size with null property should be valid`() {
        Employee().validate {
            Employee::name.hasSize(min = 1, max = 10)
        }
    }

    @Test
    fun `size with valid min length property should be valid`() {
        Employee(name = "John").validate {
            Employee::name.hasSize(min = 4)
        }
    }

    @Test
    fun `size with valid max length property should be valid`() {
        Employee(name = "John").validate {
            Employee::name.hasSize(max = 4)
        }
    }

    @Test
    fun `size with invalid min and max length property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            Employee(name = "John", email = "john@company.com", username = "john").validate {
                Employee::name.hasSize(min = 5)
                Employee::email.hasSize(max = 15)
                Employee::username.hasSize(min = 5, max = 3)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = Size(min = 5)),
                DefaultConstraintViolation(property = "email", value = "john@company.com", constraint = Size(max = 15)),
                DefaultConstraintViolation(property = "username", value = "john", constraint = Size(min = 5, max = 3)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = ValidatorFixture.supportedLocales
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "name", value = "John", constraint = Size(min = 5), message = "Size must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(property = "email", value = "john@company.com", constraint = Size(max = 15), message = "Size must be less than or equal to 15"),
                        DefaultI18nConstraintViolation(property = "username", value = "john", constraint = Size(min = 5, max = 3), message = "Size must be between 5 and 3"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "name", value = "John", constraint = Size(min = 5), message = "Size must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(property = "email", value = "john@company.com", constraint = Size(max = 15), message = "Size must be less than or equal to 15"),
                        DefaultI18nConstraintViolation(property = "username", value = "john", constraint = Size(min = 5, max = 3), message = "Size must be between 5 and 3"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "name", value = "John", constraint = Size(min = 5), message = "O tamanho deve ser maior ou igual a 5"),
                        DefaultI18nConstraintViolation(property = "email", value = "john@company.com", constraint = Size(max = 15), message = "O tamanho deve ser menor ou igual a 15"),
                        DefaultI18nConstraintViolation(property = "username", value = "john", constraint = Size(min = 5, max = 3), message = "O tamanho deve estar entre 5 e 3"))))
    }
}