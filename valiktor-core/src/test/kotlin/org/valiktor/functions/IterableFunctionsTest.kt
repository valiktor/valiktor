package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.*
import org.valiktor.constraints.NotNull
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
}