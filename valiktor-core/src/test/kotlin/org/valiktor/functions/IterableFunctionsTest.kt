package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.*
import org.valiktor.constraints.*
import org.valiktor.i18n.DefaultI18nConstraintViolation
import org.valiktor.i18n.I18nConstraintViolation
import org.valiktor.i18n.mapToI18n
import java.util.*

class IterableFunctionsTest {

    @Test
    fun `inner null iterable properties should be valid`() {
        validate(Employee(company = Company()), {
            validate(Employee::company).validate {
                validate(Company::addresses).validateForEach {
                    validate(Address::id).isNotNull()
                    validate(Address::city).validate {
                        validate(City::id).isNotNull()
                    }
                }
            }
        })
    }

    @Test
    fun `inner iterable properties should be valid`() {
        validate(Employee(company = Company(addresses = listOf(
                Address(id = 1, city = City(id = 1)),
                Address(id = 1, city = City(id = 1)),
                Address(id = 1, city = City(id = 1))))), {
            validate(Employee::company).validate {
                validate(Company::addresses).validateForEach {
                    validate(Address::id).isNotNull()
                    validate(Address::city).validate {
                        validate(City::id).isNotNull()
                    }
                }
            }
        })
    }

    @Test
    fun `inner iterable properties should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(addresses = listOf(
                    Address(city = City()),
                    Address(city = City()),
                    Address(city = City())))), {
                validate(Employee::company).validate {
                    validate(Company::addresses).validateForEach {
                        validate(Address::id).isNotNull()
                        validate(Address::city).validate {
                            validate(City::id).isNotNull()
                        }
                    }
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "company.addresses[0].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[0].city.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[1].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[1].city.id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[2].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "company.addresses[2].city.id", constraint = NotNull()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
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
    fun `isEmpty with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).isEmpty()
        })
    }

    @Test
    fun `isEmpty with empty property should be valid`() {
        validate(Company(addresses = emptyList()), {
            validate(Company::addresses).isEmpty()
        })
    }

    @Test
    fun `isEmpty with not empty property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address())), {
                validate(Company::addresses).isEmpty()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address()),
                        constraint = Empty()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address()),
                                constraint = Empty(),
                                message = "Must be empty"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address()),
                                constraint = Empty(),
                                message = "Must be empty"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address()),
                                constraint = Empty(),
                                message = "Deve ser vazio"))))
    }

    @Test
    fun `isNotEmpty with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).isNotEmpty()
        })
    }

    @Test
    fun `isNotEmpty with not empty property should be valid`() {
        validate(Company(addresses = listOf(Address())), {
            validate(Company::addresses).isNotEmpty()
        })
    }

    @Test
    fun `isNotEmpty with empty property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = emptyList()), {
                validate(Company::addresses).isNotEmpty()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = emptyList<Address>(),
                        constraint = NotEmpty()))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = NotEmpty(),
                                message = "Must not be empty"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = NotEmpty(),
                                message = "Must not be empty"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = NotEmpty(),
                                message = "Não deve ser vazio"))))
    }

    @Test
    fun `size with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).hasSize(min = 1, max = 10)
        })
    }

    @Test
    fun `size with valid min length property should be valid`() {
        validate(Company(addresses = listOf(Address())), {
            validate(Company::addresses).hasSize(min = 1)
        })
    }

    @Test
    fun `size with valid max length property should be valid`() {
        validate(Company(addresses = emptyList()), {
            validate(Company::addresses).hasSize(max = 4)
        })
    }

    @Test
    fun `size with valid min and max length property should be valid`() {
        validate(Company(addresses = listOf(Address())), {
            validate(Company::addresses).hasSize(min = 1, max = 2)
        })
    }

    @Test
    fun `size without min and max should be valid`() {
        validate(Company(addresses = emptyList()), {
            validate(Company::addresses).hasSize()
        })
    }

    @Test
    fun `size with invalid min size property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(), Address())), {
                validate(Company::addresses).hasSize(min = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(), Address()),
                        constraint = Size(min = 5)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(min = 5),
                                message = "Size must be greater than or equal to 5"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(min = 5),
                                message = "Size must be greater than or equal to 5"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(min = 5),
                                message = "O tamanho deve ser maior ou igual a 5"))))
    }

    @Test
    fun `size with invalid max size property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(), Address())), {
                validate(Company::addresses).hasSize(max = 1)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(), Address()),
                        constraint = Size(max = 1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(max = 1),
                                message = "Size must be less than or equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(max = 1),
                                message = "Size must be less than or equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(max = 1),
                                message = "O tamanho deve ser menor ou igual a 1"))))
    }

    @Test
    fun `size with invalid min and max size property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(), Address())), {
                validate(Company::addresses).hasSize(min = 3, max = 1)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(), Address()),
                        constraint = Size(min = 3, max = 1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(min = 3, max = 1),
                                message = "Size must be between 3 and 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(min = 3, max = 1),
                                message = "Size must be between 3 and 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(), Address()),
                                constraint = Size(min = 3, max = 1),
                                message = "O tamanho deve estar entre 3 e 1"))))
    }

    @Test
    fun `contains with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).contains(Address())
        })
    }

    @Test
    fun `contains with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2))), {
            validate(Company::addresses).contains(Address(id = 1))
        })
    }

    @Test
    fun `contains with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = emptyList()), {
                validate(Company::addresses).contains(Address(id = 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = emptyList<Address>(),
                        constraint = Contains(Address(id = 1))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = Contains(Address(id = 1)),
                                message = "Must contain ${Address(id = 1)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = Contains(Address(id = 1)),
                                message = "Must contain ${Address(id = 1)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = Contains(Address(id = 1)),
                                message = "Deve conter ${Address(id = 1)}"))))
    }

    @Test
    fun `containsAll vararg with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).containsAll(Address())
        })
    }

    @Test
    fun `containsAll vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3))), {
            validate(Company::addresses).containsAll(Address(id = 1), Address(id = 2))
        })
    }

    @Test
    fun `containsAll vararg with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = emptyList()), {
                validate(Company::addresses).containsAll(Address(id = 1), Address(id = 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = emptyList<Address>(),
                        constraint = ContainsAll(setOf(Address(id = 1), Address(id = 2)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAll(setOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAll(setOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAll(setOf(Address(id = 1), Address(id = 2))),
                                message = "Deve conter ${Address(id = 1)}, ${Address(id = 2)}"))))
    }

    @Test
    fun `containsAll iterable with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).containsAll(listOf(Address()))
        })
    }

    @Test
    fun `containsAll iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3))), {
            validate(Company::addresses).containsAll(listOf(Address(id = 1), Address(id = 2)))
        })
    }

    @Test
    fun `containsAll iterable with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1))), {
                validate(Company::addresses).containsAll(listOf(Address(id = 1), Address(id = 2)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(id = 1)),
                        constraint = ContainsAll(listOf(Address(id = 1), Address(id = 2)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1)),
                                constraint = ContainsAll(listOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1)),
                                constraint = ContainsAll(listOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1)),
                                constraint = ContainsAll(listOf(Address(id = 1), Address(id = 2))),
                                message = "Deve conter ${Address(id = 1)}, ${Address(id = 2)}"))))
    }

    @Test
    fun `containsAny vararg with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).containsAny(Address())
        })
    }

    @Test
    fun `containsAny vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 3))), {
            validate(Company::addresses).containsAny(Address(id = 1), Address(id = 2))
        })
    }

    @Test
    fun `containsAny vararg with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = emptyList()), {
                validate(Company::addresses).containsAny(Address(id = 1), Address(id = 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = emptyList<Address>(),
                        constraint = ContainsAny(setOf(Address(id = 1), Address(id = 2)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAny(setOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAny(setOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAny(setOf(Address(id = 1), Address(id = 2))),
                                message = "Deve conter ${Address(id = 1)}, ${Address(id = 2)}"))))
    }

    @Test
    fun `containsAny iterable with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).containsAny(listOf(Address()))
        })
    }

    @Test
    fun `containsAny iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 3))), {
            validate(Company::addresses).containsAny(listOf(Address(id = 1), Address(id = 2)))
        })
    }

    @Test
    fun `containsAny iterable with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = emptyList()), {
                validate(Company::addresses).containsAny(listOf(Address(id = 1), Address(id = 2)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = emptyList<Address>(),
                        constraint = ContainsAny(listOf(Address(id = 1), Address(id = 2)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAny(listOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAny(listOf(Address(id = 1), Address(id = 2))),
                                message = "Must contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = emptyList<Address>(),
                                constraint = ContainsAny(listOf(Address(id = 1), Address(id = 2))),
                                message = "Deve conter ${Address(id = 1)}, ${Address(id = 2)}"))))
    }

    @Test
    fun `doesNotContain with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).doesNotContain(Address())
        })
    }

    @Test
    fun `doesNotContain with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2))), {
            validate(Company::addresses).doesNotContain(Address(id = 3))
        })
    }

    @Test
    fun `doesNotContain with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1))), {
                validate(Company::addresses).doesNotContain(Address(id = 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(id = 1)),
                        constraint = NotContain(Address(id = 1))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1)),
                                constraint = NotContain(Address(id = 1)),
                                message = "Must not contain ${Address(id = 1)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1)),
                                constraint = NotContain(Address(id = 1)),
                                message = "Must not contain ${Address(id = 1)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1)),
                                constraint = NotContain(Address(id = 1)),
                                message = "Não deve conter ${Address(id = 1)}"))))
    }

    @Test
    fun `doesNotContainAll vararg with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).doesNotContainAll(Address())
        })
    }

    @Test
    fun `doesNotContainAll vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3))), {
            validate(Company::addresses).doesNotContainAll(Address(id = 1), Address(id = 5))
        })
    }

    @Test
    fun `doesNotContainAll vararg with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(3))), {
                validate(Company::addresses).doesNotContainAll(Address(id = 1), Address(id = 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                        constraint = NotContainAll(setOf(Address(id = 1), Address(id = 2)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAll(setOf(Address(id = 1), Address(id = 2))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAll(setOf(Address(id = 1), Address(id = 2))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAll(setOf(Address(id = 1), Address(id = 2))),
                                message = "Não deve conter ${Address(id = 1)}, ${Address(id = 2)}"))))
    }

    @Test
    fun `doesNotContainAll iterable with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).doesNotContainAll(listOf(Address()))
        })
    }

    @Test
    fun `doesNotContainAll iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3))), {
            validate(Company::addresses).doesNotContainAll(listOf(Address(id = 1), Address(id = 5)))
        })
    }

    @Test
    fun `doesNotContainAll iterable with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(3))), {
                validate(Company::addresses).doesNotContainAll(listOf(Address(id = 1), Address(id = 2)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                        constraint = NotContainAll(listOf(Address(id = 1), Address(id = 2)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAll(listOf(Address(id = 1), Address(id = 2))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAll(listOf(Address(id = 1), Address(id = 2))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAll(listOf(Address(id = 1), Address(id = 2))),
                                message = "Não deve conter ${Address(id = 1)}, ${Address(id = 2)}"))))
    }

    @Test
    fun `doesNotContainAny vararg with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).doesNotContainAny(Address())
        })
    }

    @Test
    fun `doesNotContainAny vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3))), {
            validate(Company::addresses).doesNotContainAny(Address(id = 4), Address(id = 5))
        })
    }

    @Test
    fun `doesNotContainAny vararg with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(3))), {
                validate(Company::addresses).doesNotContainAny(Address(id = 1), Address(id = 5))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                        constraint = NotContainAny(setOf(Address(id = 1), Address(id = 5)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAny(setOf(Address(id = 1), Address(id = 5))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 5)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAny(setOf(Address(id = 1), Address(id = 5))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 5)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAny(setOf(Address(id = 1), Address(id = 5))),
                                message = "Não deve conter ${Address(id = 1)}, ${Address(id = 5)}"))))
    }

    @Test
    fun `doesNotContainAny iterable with null property should be valid`() {
        validate(Company(), {
            validate(Company::addresses).doesNotContainAny(listOf(Address()))
        })
    }

    @Test
    fun `doesNotContainAny iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3))), {
            validate(Company::addresses).doesNotContainAny(listOf(Address(id = 4), Address(id = 5)))
        })
    }

    @Test
    fun `doesNotContainAny iterable with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(3))), {
                validate(Company::addresses).doesNotContainAny(listOf(Address(id = 1), Address(id = 5)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "addresses",
                        value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                        constraint = NotContainAny(listOf(Address(id = 1), Address(id = 5)))))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAny(listOf(Address(id = 1), Address(id = 5))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 5)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAny(listOf(Address(id = 1), Address(id = 5))),
                                message = "Must not contain ${Address(id = 1)}, ${Address(id = 5)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "addresses",
                                value = listOf(Address(id = 1), Address(id = 2), Address(3)),
                                constraint = NotContainAny(listOf(Address(id = 1), Address(id = 5))),
                                message = "Não deve conter ${Address(id = 1)}, ${Address(id = 5)}"))))
    }
}