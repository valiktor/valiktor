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

class IntFunctionsTest {

    @Test
    fun `isZero with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isZero()
        })
    }

    @Test
    fun `isZero with valid property should be valid`() {
        validate(Employee(id = 0), {
            validate(Employee::id).isZero()
        })
    }

    @Test
    fun `isZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1), {
                validate(Employee::id).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = Equals(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = Equals(0),
                                message = "Must be equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = Equals(0),
                                message = "Must be equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = Equals(0),
                                message = "Deve ser igual a 0"))))
    }

    @Test
    fun `isNotZero with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotZero()
        })
    }

    @Test
    fun `isNotZero with valid property should be valid`() {
        validate(Employee(id = 1), {
            validate(Employee::id).isNotZero()
        })
    }

    @Test
    fun `isNotZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0), {
                validate(Employee::id).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0,
                        constraint = NotEquals(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = NotEquals(0),
                                message = "Must not be equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = NotEquals(0),
                                message = "Must not be equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = NotEquals(0),
                                message = "Não deve ser igual a 0"))))
    }

    @Test
    fun `isOne with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isOne()
        })
    }

    @Test
    fun `isOne with valid property should be valid`() {
        validate(Employee(id = 1), {
            validate(Employee::id).isOne()
        })
    }

    @Test
    fun `isOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0), {
                validate(Employee::id).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0,
                        constraint = Equals(1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Equals(1),
                                message = "Must be equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Equals(1),
                                message = "Must be equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Equals(1),
                                message = "Deve ser igual a 1"))))
    }

    @Test
    fun `isNotOne with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotOne()
        })
    }

    @Test
    fun `isNotOne with valid property should be valid`() {
        validate(Employee(id = 0), {
            validate(Employee::id).isNotOne()
        })
    }

    @Test
    fun `isNotOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1), {
                validate(Employee::id).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = NotEquals(1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = NotEquals(1),
                                message = "Must not be equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = NotEquals(1),
                                message = "Must not be equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = NotEquals(1),
                                message = "Não deve ser igual a 1"))))
    }

    @Test
    fun `isPositive with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isPositive()
        })
    }

    @Test
    fun `isPositive with valid property should be valid`() {
        validate(Employee(id = 1), {
            validate(Employee::id).isPositive()
        })
    }

    @Test
    fun `isPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0, company = Company(id = -1)), {
                validate(Employee::id).isPositive()
                validate(Employee::company).validate {
                    validate(Company::id).isPositive()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0,
                        constraint = Greater(0)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -1,
                        constraint = Greater(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Greater(0),
                                message = "Must be greater than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -1,
                                constraint = Greater(0),
                                message = "Must be greater than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Greater(0),
                                message = "Must be greater than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -1,
                                constraint = Greater(0),
                                message = "Must be greater than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Greater(0),
                                message = "Deve ser maior que 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -1,
                                constraint = Greater(0),
                                message = "Deve ser maior que 0"))))
    }

    @Test
    fun `isNotPositive with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with valid property should be valid`() {
        validate(Employee(id = 0, company = Company(id = -1)), {
            validate(Employee::id).isNotPositive()
            validate(Employee::company).validate {
                validate(Company::id).isNotPositive()
            }
        })
    }

    @Test
    fun `isNotPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1), {
                validate(Employee::id).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1,
                        constraint = LessOrEqual(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = LessOrEqual(0),
                                message = "Must be less than or equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = LessOrEqual(0),
                                message = "Must be less than or equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1,
                                constraint = LessOrEqual(0),
                                message = "Deve ser menor ou igual a 0"))))
    }

    @Test
    fun `isNegative with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNegative()
        })
    }

    @Test
    fun `isNegative with valid property should be valid`() {
        validate(Employee(id = -1), {
            validate(Employee::id).isNegative()
        })
    }

    @Test
    fun `isNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 0, company = Company(id = 1)), {
                validate(Employee::id).isNegative()
                validate(Employee::company).validate {
                    validate(Company::id).isNegative()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 0,
                        constraint = Less(0)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = 1,
                        constraint = Less(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Less(0),
                                message = "Must be less than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = 1,
                                constraint = Less(0),
                                message = "Must be less than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Less(0),
                                message = "Must be less than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = 1,
                                constraint = Less(0),
                                message = "Must be less than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 0,
                                constraint = Less(0),
                                message = "Deve ser menor que 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = 1,
                                constraint = Less(0),
                                message = "Deve ser menor que 0"))))
    }

    @Test
    fun `isNotNegative with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with valid property should be valid`() {
        validate(Employee(id = 0, company = Company(id = 1)), {
            validate(Employee::id).isNotNegative()
            validate(Employee::company).validate {
                validate(Company::id).isNotNegative()
            }
        })
    }

    @Test
    fun `isNotNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = -1), {
                validate(Employee::id).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = -1,
                        constraint = GreaterOrEqual(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = -1,
                                constraint = GreaterOrEqual(0),
                                message = "Must be greater than or equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = -1,
                                constraint = GreaterOrEqual(0),
                                message = "Must be greater than or equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = -1,
                                constraint = GreaterOrEqual(0),
                                message = "Deve ser maior ou igual a 0"))))
    }

    @Test
    fun `isLessThan with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isLessThan(10)
        })
    }

    @Test
    fun `isLessThan with valid property should be valid`() {
        validate(Employee(id = 9, company = Company(id = -10)), {
            validate(Employee::id).isLessThan(10)
            validate(Employee::company).validate {
                validate(Company::id).isLessThan(-9)
            }
        })
    }

    @Test
    fun `isLessThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 11, company = Company(id = -9), address = Address(id = 0)), {
                validate(Employee::id).isLessThan(10)
                validate(Employee::company).validate {
                    validate(Company::id).isLessThan(-10)
                }
                validate(Employee::address).validate {
                    validate(Address::id).isLessThan(0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 11,
                        constraint = Less(10)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -9,
                        constraint = Less(-10)),
                DefaultConstraintViolation(
                        property = "address.id",
                        value = 0,
                        constraint = Less(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 11,
                                constraint = Less(10),
                                message = "Must be less than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -9,
                                constraint = Less(-10),
                                message = "Must be less than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = Less(0),
                                message = "Must be less than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 11,
                                constraint = Less(10),
                                message = "Must be less than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -9,
                                constraint = Less(-10),
                                message = "Must be less than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = Less(0),
                                message = "Must be less than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 11,
                                constraint = Less(10),
                                message = "Deve ser menor que 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -9,
                                constraint = Less(-10),
                                message = "Deve ser menor que -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = Less(0),
                                message = "Deve ser menor que 0"))))
    }

    @Test
    fun `isLessThanOrEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isLessThanOrEqualTo(10)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with valid property should be valid`() {
        validate(Employee(id = 9, company = Company(id = -10), address = Address(id = 0)), {
            validate(Employee::id).isLessThanOrEqualTo(10)
            validate(Employee::company).validate {
                validate(Company::id).isLessThanOrEqualTo(-9)
            }
            validate(Employee::address).validate {
                validate(Address::id).isLessThanOrEqualTo(0)
            }
        })
    }

    @Test
    fun `isLessThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 11, company = Company(id = -9)), {
                validate(Employee::id).isLessThanOrEqualTo(10)
                validate(Employee::company).validate {
                    validate(Company::id).isLessThanOrEqualTo(-10)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 11,
                        constraint = LessOrEqual(10)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -9,
                        constraint = LessOrEqual(-10)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 11,
                                constraint = LessOrEqual(10),
                                message = "Must be less than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -9,
                                constraint = LessOrEqual(-10),
                                message = "Must be less than or equal to -10"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 11,
                                constraint = LessOrEqual(10),
                                message = "Must be less than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -9,
                                constraint = LessOrEqual(-10),
                                message = "Must be less than or equal to -10"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 11,
                                constraint = LessOrEqual(10),
                                message = "Deve ser menor ou igual a 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -9,
                                constraint = LessOrEqual(-10),
                                message = "Deve ser menor ou igual a -10"))))
    }

    @Test
    fun `isGreaterThan with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isGreaterThan(10)
        })
    }

    @Test
    fun `isGreaterThan with valid property should be valid`() {
        validate(Employee(id = 11, company = Company(id = -8)), {
            validate(Employee::id).isGreaterThan(10)
            validate(Employee::company).validate {
                validate(Company::id).isGreaterThan(-9)
            }
        })
    }

    @Test
    fun `isGreaterThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 9, company = Company(id = -11), address = Address(id = 0)), {
                validate(Employee::id).isGreaterThan(10)
                validate(Employee::company).validate {
                    validate(Company::id).isGreaterThan(-10)
                }
                validate(Employee::address).validate {
                    validate(Address::id).isGreaterThan(0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 9,
                        constraint = Greater(10)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -11,
                        constraint = Greater(-10)),
                DefaultConstraintViolation(
                        property = "address.id",
                        value = 0,
                        constraint = Greater(0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = Greater(10),
                                message = "Must be greater than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = Greater(-10),
                                message = "Must be greater than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = Greater(0),
                                message = "Must be greater than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = Greater(10),
                                message = "Must be greater than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = Greater(-10),
                                message = "Must be greater than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = Greater(0),
                                message = "Must be greater than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = Greater(10),
                                message = "Deve ser maior que 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = Greater(-10),
                                message = "Deve ser maior que -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = Greater(0),
                                message = "Deve ser maior que 0"))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isGreaterThanOrEqualTo(10)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with valid property should be valid`() {
        validate(Employee(id = 11, company = Company(id = -8), address = Address(id = 0)), {
            validate(Employee::id).isGreaterThanOrEqualTo(10)
            validate(Employee::company).validate {
                validate(Company::id).isGreaterThanOrEqualTo(-9)
            }
            validate(Employee::address).validate {
                validate(Address::id).isGreaterThanOrEqualTo(0)
            }
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 9, company = Company(id = -11)), {
                validate(Employee::id).isGreaterThanOrEqualTo(10)
                validate(Employee::company).validate {
                    validate(Company::id).isGreaterThanOrEqualTo(-10)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 9,
                        constraint = GreaterOrEqual(10)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -11,
                        constraint = GreaterOrEqual(-10)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = GreaterOrEqual(10),
                                message = "Must be greater than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = GreaterOrEqual(-10),
                                message = "Must be greater than or equal to -10"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = GreaterOrEqual(10),
                                message = "Must be greater than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = GreaterOrEqual(-10),
                                message = "Must be greater than or equal to -10"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = GreaterOrEqual(10),
                                message = "Deve ser maior ou igual a 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = GreaterOrEqual(-10),
                                message = "Deve ser maior ou igual a -10"))))
    }


    @Test
    fun `isBetween with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isBetween(start = 1, end = 10)
        })
    }

    @Test
    fun `isBetween with valid property should be valid`() {
        validate(Employee(id = 11, company = Company(id = -8), address = Address(id = 0)), {
            validate(Employee::id).isBetween(start = -12, end = 12)
            validate(Employee::company).validate {
                validate(Company::id).isBetween(start = -9, end = 9)
            }
            validate(Employee::address).validate {
                validate(Address::id).isBetween(start = 0, end = 0)
            }
        })
    }

    @Test
    fun `isBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 9, company = Company(id = -11)), {
                validate(Employee::id).isBetween(start = 10, end = 11)
                validate(Employee::company).validate {
                    validate(Company::id).isBetween(start = -10, end = -9)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 9,
                        constraint = Between(start = 10, end = 11)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -11,
                        constraint = Between(start = -10, end = -9)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = Between(start = 10, end = 11),
                                message = "Must be between 10 and 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = Between(start = -10, end = -9),
                                message = "Must be between -10 and -9"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = Between(start = 10, end = 11),
                                message = "Must be between 10 and 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = Between(start = -10, end = -9),
                                message = "Must be between -10 and -9"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = Between(start = 10, end = 11),
                                message = "Deve estar entre 10 e 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = Between(start = -10, end = -9),
                                message = "Deve estar entre -10 e -9"))))
    }

    @Test
    fun `isNotBetween with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).isNotBetween(start = 1, end = 10)
        })
    }

    @Test
    fun `isNotBetween with valid property should be valid`() {
        validate(Employee(id = 11, company = Company(id = -8)), {
            validate(Employee::id).isNotBetween(start = 8, end = 10)
            validate(Employee::company).validate {
                validate(Company::id).isNotBetween(start = -10, end = -9)
            }
        })
    }

    @Test
    fun `isNotBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 9, company = Company(id = -11), address = Address(id = 0)), {
                validate(Employee::id).isNotBetween(start = 8, end = 10)
                validate(Employee::company).validate {
                    validate(Company::id).isNotBetween(start = -12, end = -10)
                }
                validate(Employee::address).validate {
                    validate(Address::id).isNotBetween(start = 0, end = 0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 9,
                        constraint = NotBetween(start = 8, end = 10)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = -11,
                        constraint = NotBetween(start = -12, end = -10)),
                DefaultConstraintViolation(
                        property = "address.id",
                        value = 0,
                        constraint = NotBetween(start = 0, end = 0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = NotBetween(start = 8, end = 10),
                                message = "Must not be between 8 and 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = NotBetween(start = -12, end = -10),
                                message = "Must not be between -12 and -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = NotBetween(start = 0, end = 0),
                                message = "Must not be between 0 and 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = NotBetween(start = 8, end = 10),
                                message = "Must not be between 8 and 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = NotBetween(start = -12, end = -10),
                                message = "Must not be between -12 and -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = NotBetween(start = 0, end = 0),
                                message = "Must not be between 0 and 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 9,
                                constraint = NotBetween(start = 8, end = 10),
                                message = "Não deve estar entre 8 e 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = -11,
                                constraint = NotBetween(start = -12, end = -10),
                                message = "Não deve estar entre -12 e -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 0,
                                constraint = NotBetween(start = 0, end = 0),
                                message = "Não deve estar entre 0 e 0"))))
    }

    @Test
    fun `hasDigits with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::id).hasDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDigits with valid min length property should be valid`() {
        validate(Employee(id = 1000), {
            validate(Employee::id).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with valid max length property should be valid`() {
        validate(Employee(id = 1000), {
            validate(Employee::id).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with valid min and max length property should be valid`() {
        validate(Employee(id = 1000), {
            validate(Employee::id).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid min length property should be valid`() {
        validate(Employee(id = -1000), {
            validate(Employee::id).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid max length property should be valid`() {
        validate(Employee(id = -1000), {
            validate(Employee::id).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid min and max length property should be valid`() {
        validate(Employee(id = -1000), {
            validate(Employee::id).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(id = 1000), {
            validate(Employee::id).hasDigits()
        })
    }

    @Test
    fun `hasDigits with invalid min and max length property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(id = 1000, company = Company(id = 999999999), address = Address(9876)), {
                validate(Employee::id).hasDigits(min = 5)
                validate(Employee::company).validate {
                    validate(Company::id).hasDigits(max = 8)
                }
                validate(Employee::address).validate {
                    validate(Address::id).hasDigits(min = 5, max = 3)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "id",
                        value = 1000,
                        constraint = IntegerDigits(min = 5)),
                DefaultConstraintViolation(
                        property = "company.id",
                        value = 999999999,
                        constraint = IntegerDigits(max = 8)),
                DefaultConstraintViolation(
                        property = "address.id",
                        value = 9876,
                        constraint = IntegerDigits(min = 5, max = 3)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1000,
                                constraint = IntegerDigits(min = 5),
                                message = "Integer digits must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = 999999999,
                                constraint = IntegerDigits(max = 8),
                                message = "Integer digits must be less than or equal to 8"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 9876,
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "Integer digits must be between 5 and 3"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1000,
                                constraint = IntegerDigits(min = 5),
                                message = "Integer digits must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = 999999999,
                                constraint = IntegerDigits(max = 8),
                                message = "Integer digits must be less than or equal to 8"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 9876,
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "Integer digits must be between 5 and 3"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "id",
                                value = 1000,
                                constraint = IntegerDigits(min = 5),
                                message = "A quantidade de dígitos inteiros deve ser maior ou igual a 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.id",
                                value = 999999999,
                                constraint = IntegerDigits(max = 8),
                                message = "A quantidade de dígitos inteiros deve ser menor ou igual a 8"),
                        DefaultI18nConstraintViolation(
                                property = "address.id",
                                value = 9876,
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "A quantidade de dígitos inteiros deve estar entre 5 e 3"))))
    }
}