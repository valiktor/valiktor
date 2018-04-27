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

class DoubleFunctionsTest {

    @Test
    fun `isZero with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isZero()
        })
    }

    @Test
    fun `isZero with valid property should be valid`() {
        validate(Company(valuation = 0.0), {
            validate(Company::valuation).isZero()
        })
    }

    @Test
    fun `isZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(valuation = 1.0), {
                validate(Company::valuation).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "valuation",
                        value = 1.0,
                        constraint = Equals(0.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = Equals(0.0),
                                message = "Must be equal to 0.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = Equals(0.0),
                                message = "Must be equal to 0.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = Equals(0.0),
                                message = "Deve ser igual a 0.0"))))
    }

    @Test
    fun `isNotZero with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isNotZero()
        })
    }

    @Test
    fun `isNotZero with valid property should be valid`() {
        validate(Company(valuation = 1.0), {
            validate(Company::valuation).isNotZero()
        })
    }

    @Test
    fun `isNotZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(valuation = 0.0), {
                validate(Company::valuation).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "valuation",
                        value = 0.0,
                        constraint = NotEquals(0.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 0.0,
                                constraint = NotEquals(0.0),
                                message = "Must not be equal to 0.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 0.0,
                                constraint = NotEquals(0.0),
                                message = "Must not be equal to 0.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 0.0,
                                constraint = NotEquals(0.0),
                                message = "Não deve ser igual a 0.0"))))
    }

    @Test
    fun `isOne with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isOne()
        })
    }

    @Test
    fun `isOne with valid property should be valid`() {
        validate(Company(valuation = 1.0), {
            validate(Company::valuation).isOne()
        })
    }

    @Test
    fun `isOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(valuation = 0.0), {
                validate(Company::valuation).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "valuation",
                        value = 0.0,
                        constraint = Equals(1.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 0.0,
                                constraint = Equals(1.0),
                                message = "Must be equal to 1.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 0.0,
                                constraint = Equals(1.0),
                                message = "Must be equal to 1.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 0.0,
                                constraint = Equals(1.0),
                                message = "Deve ser igual a 1.0"))))
    }

    @Test
    fun `isNotOne with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isNotOne()
        })
    }

    @Test
    fun `isNotOne with valid property should be valid`() {
        validate(Company(valuation = 0.0), {
            validate(Company::valuation).isNotOne()
        })
    }

    @Test
    fun `isNotOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(valuation = 1.0), {
                validate(Company::valuation).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "valuation",
                        value = 1.0,
                        constraint = NotEquals(1.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = NotEquals(1.0),
                                message = "Must not be equal to 1.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = NotEquals(1.0),
                                message = "Must not be equal to 1.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = NotEquals(1.0),
                                message = "Não deve ser igual a 1.0"))))
    }

    @Test
    fun `isPositive with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isPositive()
        })
    }

    @Test
    fun `isPositive with valid property should be valid`() {
        validate(Company(valuation = 1.0), {
            validate(Company::valuation).isPositive()
        })
    }

    @Test
    fun `isPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -47.7375833)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).isPositive()
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).isPositive()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 0.0,
                        constraint = Greater(0.0)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 47.7375833.unaryMinus(),
                        constraint = Greater(0.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Greater(0.0),
                                message = "Must be greater than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 47.7375833.unaryMinus(),
                                constraint = Greater(0.0),
                                message = "Must be greater than 0.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Greater(0.0),
                                message = "Must be greater than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 47.7375833.unaryMinus(),
                                constraint = Greater(0.0),
                                message = "Must be greater than 0.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Greater(0.0),
                                message = "Deve ser maior que 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 47.7375833.unaryMinus(),
                                constraint = Greater(0.0),
                                message = "Deve ser maior que 0.0"))))
    }

    @Test
    fun `isNotPositive with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -47.7375833)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isNotPositive()
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isNotPositive()
            }
        })
    }

    @Test
    fun `isNotPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(valuation = 1.0), {
                validate(Company::valuation).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "valuation",
                        value = 1.0,
                        constraint = LessOrEqual(0.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = LessOrEqual(0.0),
                                message = "Must be less than or equal to 0.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = LessOrEqual(0.0),
                                message = "Must be less than or equal to 0.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 1.0,
                                constraint = LessOrEqual(0.0),
                                message = "Deve ser menor ou igual a 0.0"))))
    }

    @Test
    fun `isNegative with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isNegative()
        })
    }

    @Test
    fun `isNegative with valid property should be valid`() {
        validate(Company(valuation = -1.0), {
            validate(Company::valuation).isNegative()
        })
    }

    @Test
    fun `isNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = 30.20856)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).isNegative()
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).isNegative()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 0.0,
                        constraint = Less(0.0)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 30.20856,
                        constraint = Less(0.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Less(0.0),
                                message = "Must be less than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 30.20856,
                                constraint = Less(0.0),
                                message = "Must be less than 0.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Less(0.0),
                                message = "Must be less than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 30.20856,
                                constraint = Less(0.0),
                                message = "Must be less than 0.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Less(0.0),
                                message = "Deve ser menor que 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 30.20856,
                                constraint = Less(0.0),
                                message = "Deve ser menor que 0.0"))))
    }

    @Test
    fun `isNotNegative with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = 30.20856)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isNotNegative()
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isNotNegative()
            }
        })
    }

    @Test
    fun `isNotNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Company(valuation = -20.4413004), {
                validate(Company::valuation).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "valuation",
                        value = 20.4413004.unaryMinus(),
                        constraint = GreaterOrEqual(0.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 20.4413004.unaryMinus(),
                                constraint = GreaterOrEqual(0.0),
                                message = "Must be greater than or equal to 0.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 20.4413004.unaryMinus(),
                                constraint = GreaterOrEqual(0.0),
                                message = "Must be greater than or equal to 0.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "valuation",
                                value = 20.4413004.unaryMinus(),
                                constraint = GreaterOrEqual(0.0),
                                message = "Deve ser maior ou igual a 0.0"))))
    }

    @Test
    fun `isLessThan with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isLessThan(10.0)
        })
    }

    @Test
    fun `isLessThan with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 9.99), address = Address(latitude = -10.99)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isLessThan(10.0)
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isLessThan(-10.98)
            }
        })
    }

    @Test
    fun `isLessThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -0.385783, longitude = 50.764362)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).isLessThan(0.0)
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).isLessThan(0.4.unaryMinus())
                    validate(Address::longitude).isLessThan(50.0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 0.0,
                        constraint = Less(0.0)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 0.385783.unaryMinus(),
                        constraint = Less(0.4.unaryMinus())),
                DefaultConstraintViolation(
                        property = "address.longitude",
                        value = 50.764362,
                        constraint = Less(50.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Less(0.0),
                                message = "Must be less than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 0.385783.unaryMinus(),
                                constraint = Less(0.4.unaryMinus()),
                                message = "Must be less than -0.4"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 50.764362,
                                constraint = Less(50.0),
                                message = "Must be less than 50.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Less(0.0),
                                message = "Must be less than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 0.385783.unaryMinus(),
                                constraint = Less(0.4.unaryMinus()),
                                message = "Must be less than -0.4"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 50.764362,
                                constraint = Less(50.0),
                                message = "Must be less than 50.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Less(0.0),
                                message = "Deve ser menor que 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 0.385783.unaryMinus(),
                                constraint = Less(0.4.unaryMinus()),
                                message = "Deve ser menor que -0.4"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 50.764362,
                                constraint = Less(50.0),
                                message = "Deve ser menor que 50.0"))))
    }

    @Test
    fun `isLessThanOrEqualTo with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isLessThanOrEqualTo(10.0)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -0.385783, longitude = 50.764362)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isLessThanOrEqualTo(0.0)
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isLessThanOrEqualTo(0.38.unaryMinus())
                validate(Address::longitude).isLessThanOrEqualTo(50.8)
            }
        })
    }

    @Test
    fun `isLessThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Address(latitude = 5.3673999, longitude = -8.8014766), {
                validate(Address::latitude).isLessThanOrEqualTo(5.35)
                validate(Address::longitude).isLessThanOrEqualTo(-8.9)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "latitude",
                        value = 5.3673999,
                        constraint = LessOrEqual(5.35)),
                DefaultConstraintViolation(
                        property = "longitude",
                        value = 8.8014766.unaryMinus(),
                        constraint = LessOrEqual(8.9.unaryMinus())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 5.3673999,
                                constraint = LessOrEqual(5.35),
                                message = "Must be less than or equal to 5.35"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 8.8014766.unaryMinus(),
                                constraint = LessOrEqual(8.9.unaryMinus()),
                                message = "Must be less than or equal to -8.9"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 5.3673999,
                                constraint = LessOrEqual(5.35),
                                message = "Must be less than or equal to 5.35"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 8.8014766.unaryMinus(),
                                constraint = LessOrEqual(8.9.unaryMinus()),
                                message = "Must be less than or equal to -8.9"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 5.3673999,
                                constraint = LessOrEqual(5.35),
                                message = "Deve ser menor ou igual a 5.35"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 8.8014766.unaryMinus(),
                                constraint = LessOrEqual(8.9.unaryMinus()),
                                message = "Deve ser menor ou igual a -8.9"))))
    }

    @Test
    fun `isGreaterThan with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isGreaterThan(10.0)
        })
    }

    @Test
    fun `isGreaterThan with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 9.99), address = Address(latitude = -10.99)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isGreaterThan(9.0)
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isGreaterThan(-11.0)
            }
        })
    }

    @Test
    fun `isGreaterThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -0.385783, longitude = 50.764362)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).isGreaterThan(0.0)
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).isGreaterThan(0.3.unaryMinus())
                    validate(Address::longitude).isGreaterThan(51.0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 0.0,
                        constraint = Greater(0.0)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 0.385783.unaryMinus(),
                        constraint = Greater(0.3.unaryMinus())),
                DefaultConstraintViolation(
                        property = "address.longitude",
                        value = 50.764362,
                        constraint = Greater(51.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Greater(0.0),
                                message = "Must be greater than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 0.385783.unaryMinus(),
                                constraint = Greater(0.3.unaryMinus()),
                                message = "Must be greater than -0.3"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 50.764362,
                                constraint = Greater(51.0),
                                message = "Must be greater than 51.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Greater(0.0),
                                message = "Must be greater than 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 0.385783.unaryMinus(),
                                constraint = Greater(0.3.unaryMinus()),
                                message = "Must be greater than -0.3"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 50.764362,
                                constraint = Greater(51.0),
                                message = "Must be greater than 51.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = Greater(0.0),
                                message = "Deve ser maior que 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 0.385783.unaryMinus(),
                                constraint = Greater(0.3.unaryMinus()),
                                message = "Deve ser maior que -0.3"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 50.764362,
                                constraint = Greater(51.0),
                                message = "Deve ser maior que 51.0"))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isGreaterThanOrEqualTo(10.0)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -0.385783, longitude = 50.764362)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isGreaterThanOrEqualTo(0.0)
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isGreaterThanOrEqualTo(0.39.unaryMinus())
                validate(Address::longitude).isGreaterThanOrEqualTo(50.75)
            }
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Address(latitude = 5.3673999, longitude = -8.8014766), {
                validate(Address::latitude).isGreaterThanOrEqualTo(5.38)
                validate(Address::longitude).isGreaterThanOrEqualTo(-8.7)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "latitude",
                        value = 5.3673999,
                        constraint = GreaterOrEqual(5.38)),
                DefaultConstraintViolation(
                        property = "longitude",
                        value = 8.8014766.unaryMinus(),
                        constraint = GreaterOrEqual(8.7.unaryMinus())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 5.3673999,
                                constraint = GreaterOrEqual(5.38),
                                message = "Must be greater than or equal to 5.38"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 8.8014766.unaryMinus(),
                                constraint = GreaterOrEqual(8.7.unaryMinus()),
                                message = "Must be greater than or equal to -8.7"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 5.3673999,
                                constraint = GreaterOrEqual(5.38),
                                message = "Must be greater than or equal to 5.38"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 8.8014766.unaryMinus(),
                                constraint = GreaterOrEqual(8.7.unaryMinus()),
                                message = "Must be greater than or equal to -8.7"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 5.3673999,
                                constraint = GreaterOrEqual(5.38),
                                message = "Deve ser maior ou igual a 5.38"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 8.8014766.unaryMinus(),
                                constraint = GreaterOrEqual(8.7.unaryMinus()),
                                message = "Deve ser maior ou igual a -8.7"))))
    }

    @Test
    fun `isBetween with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isBetween(start = 0.99, end = 9.99)
        })
    }

    @Test
    fun `isBetween with valid property should be valid`() {
        validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = 31.2348663, longitude = -6.9277361)), {
            validate(Employee::company).validate {
                validate(Company::valuation).isBetween(start = 0.0, end = 0.0)
            }
            validate(Employee::address).validate {
                validate(Address::latitude).isBetween(start = 31.2, end = 31.3)
                validate(Address::longitude).isBetween(start = -7.0, end = -6.9)
            }
        })
    }

    @Test
    fun `isBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Address(latitude = 58.8144355, longitude = -2.2695575), {
                validate(Address::latitude).isBetween(start = 58.9, end = 59.0)
                validate(Address::longitude).isBetween(start = -2.3, end = -2.4)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "latitude",
                        value = 58.8144355,
                        constraint = Between(start = 58.9, end = 59.0)),
                DefaultConstraintViolation(
                        property = "longitude",
                        value = 2.2695575.unaryMinus(),
                        constraint = Between(start = 2.3.unaryMinus(), end = 2.4.unaryMinus())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 58.8144355,
                                constraint = Between(start = 58.9, end = 59.0),
                                message = "Must be between 58.9 and 59.0"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 2.2695575.unaryMinus(),
                                constraint = Between(start = 2.3.unaryMinus(), end = 2.4.unaryMinus()),
                                message = "Must be between -2.3 and -2.4"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 58.8144355,
                                constraint = Between(start = 58.9, end = 59.0),
                                message = "Must be between 58.9 and 59.0"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 2.2695575.unaryMinus(),
                                constraint = Between(start = 2.3.unaryMinus(), end = 2.4.unaryMinus()),
                                message = "Must be between -2.3 and -2.4"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "latitude",
                                value = 58.8144355,
                                constraint = Between(start = 58.9, end = 59.0),
                                message = "Deve estar entre 58.9 e 59.0"),
                        DefaultI18nConstraintViolation(
                                property = "longitude",
                                value = 2.2695575.unaryMinus(),
                                constraint = Between(start = 2.3.unaryMinus(), end = 2.4.unaryMinus()),
                                message = "Deve estar entre -2.3 e -2.4"))))
    }

    @Test
    fun `isNotBetween with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).isNotBetween(start = 1.0, end = 2.0)
        })
    }

    @Test
    fun `isNotBetween with valid property should be valid`() {
        validate(Address(latitude = 17.1939768, longitude = -2.2695575), {
            validate(Address::latitude).isNotBetween(start = 18.0, end = 19.0)
            validate(Address::longitude).isNotBetween(start = -1.0, end = -2.0)
        })
    }

    @Test
    fun `isNotBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 0.0), address = Address(latitude = -8.2464, longitude = 48.977664)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).isNotBetween(start = 0.0, end = 0.0)
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).isNotBetween(start = -9.0, end = -8.0)
                    validate(Address::longitude).isNotBetween(start = 47.0, end = 50.0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 0.0,
                        constraint = NotBetween(start = 0.0, end = 0.0)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 8.2464.unaryMinus(),
                        constraint = NotBetween(start = 9.0.unaryMinus(), end = 8.0.unaryMinus())),
                DefaultConstraintViolation(
                        property = "address.longitude",
                        value = 48.977664,
                        constraint = NotBetween(start = 47.0, end = 50.0)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = NotBetween(start = 0.0, end = 0.0),
                                message = "Must not be between 0.0 and 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 8.2464.unaryMinus(),
                                constraint = NotBetween(start = 9.0.unaryMinus(), end = 8.0.unaryMinus()),
                                message = "Must not be between -9.0 and -8.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 48.977664,
                                constraint = NotBetween(start = 47.0, end = 50.0),
                                message = "Must not be between 47.0 and 50.0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = NotBetween(start = 0.0, end = 0.0),
                                message = "Must not be between 0.0 and 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 8.2464.unaryMinus(),
                                constraint = NotBetween(start = 9.0.unaryMinus(), end = 8.0.unaryMinus()),
                                message = "Must not be between -9.0 and -8.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 48.977664,
                                constraint = NotBetween(start = 47.0, end = 50.0),
                                message = "Must not be between 47.0 and 50.0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 0.0,
                                constraint = NotBetween(start = 0.0, end = 0.0),
                                message = "Não deve estar entre 0.0 e 0.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 8.2464.unaryMinus(),
                                constraint = NotBetween(start = 9.0.unaryMinus(), end = 8.0.unaryMinus()),
                                message = "Não deve estar entre -9.0 e -8.0"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 48.977664,
                                constraint = NotBetween(start = 47.0, end = 50.0),
                                message = "Não deve estar entre 47.0 e 50.0"))))
    }

    @Test
    fun `hasIntegerDigits with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).hasIntegerDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min length property should be valid`() {
        validate(Company(valuation = 9999.99), {
            validate(Company::valuation).hasIntegerDigits(min = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid max length property should be valid`() {
        validate(Company(valuation = 9999.99), {
            validate(Company::valuation).hasIntegerDigits(max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with valid min and max length property should be valid`() {
        validate(Company(valuation = 9999.99), {
            validate(Company::valuation).hasIntegerDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min length property should be valid`() {
        validate(Company(valuation = -999999.99), {
            validate(Company::valuation).hasIntegerDigits(min = 6)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid max length property should be valid`() {
        validate(Company(valuation = -999999.99), {
            validate(Company::valuation).hasIntegerDigits(max = 6)
        })
    }

    @Test
    fun `hasIntegerDigits with negative valid min and max length property should be valid`() {
        validate(Company(valuation = -999999.99), {
            validate(Company::valuation).hasIntegerDigits(min = 6, max = 6)
        })
    }

    @Test
    fun `hasIntegerDigits without min and max should be valid`() {
        validate(Company(valuation = 9999.99), {
            validate(Company::valuation).hasIntegerDigits()
        })
    }

    @Test
    fun `hasIntegerDigits with invalid min and max length property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 748536.78), address = Address(latitude = -62.9042641, longitude = 41.1572852)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).hasIntegerDigits(min = 7)
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).hasIntegerDigits(max = 1)
                    validate(Address::longitude).hasIntegerDigits(min = 3, max = 1)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 748536.78,
                        constraint = IntegerDigits(min = 7)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 62.9042641.unaryMinus(),
                        constraint = IntegerDigits(max = 1)),
                DefaultConstraintViolation(
                        property = "address.longitude",
                        value = 41.1572852,
                        constraint = IntegerDigits(min = 3, max = 1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 748536.78,
                                constraint = IntegerDigits(min = 7),
                                message = "Integer digits must be greater than or equal to 7"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 62.9042641.unaryMinus(),
                                constraint = IntegerDigits(max = 1),
                                message = "Integer digits must be less than or equal to 1"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 41.1572852,
                                constraint = IntegerDigits(min = 3, max = 1),
                                message = "Integer digits must be between 3 and 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 748536.78,
                                constraint = IntegerDigits(min = 7),
                                message = "Integer digits must be greater than or equal to 7"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 62.9042641.unaryMinus(),
                                constraint = IntegerDigits(max = 1),
                                message = "Integer digits must be less than or equal to 1"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 41.1572852,
                                constraint = IntegerDigits(min = 3, max = 1),
                                message = "Integer digits must be between 3 and 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 748536.78,
                                constraint = IntegerDigits(min = 7),
                                message = "A quantidade de dígitos inteiros deve ser maior ou igual a 7"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 62.9042641.unaryMinus(),
                                constraint = IntegerDigits(max = 1),
                                message = "A quantidade de dígitos inteiros deve ser menor ou igual a 1"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 41.1572852,
                                constraint = IntegerDigits(min = 3, max = 1),
                                message = "A quantidade de dígitos inteiros deve estar entre 3 e 1"))))
    }

    @Test
    fun `hasDecimalDigits with null property should be valid`() {
        validate(Company(), {
            validate(Company::valuation).hasDecimalDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min length property should be valid`() {
        validate(Company(valuation = 99.9999), {
            validate(Company::valuation).hasDecimalDigits(min = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid max length property should be valid`() {
        validate(Company(valuation = 99.9999), {
            validate(Company::valuation).hasDecimalDigits(max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with valid min and max length property should be valid`() {
        validate(Company(valuation = 99.9999), {
            validate(Company::valuation).hasDecimalDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min length property should be valid`() {
        validate(Company(valuation = -99.999999), {
            validate(Company::valuation).hasDecimalDigits(min = 6)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid max length property should be valid`() {
        validate(Company(valuation = -99.999999), {
            validate(Company::valuation).hasDecimalDigits(max = 6)
        })
    }

    @Test
    fun `hasDecimalDigits with negative valid min and max length property should be valid`() {
        validate(Company(valuation = -99.999999), {
            validate(Company::valuation).hasDecimalDigits(min = 6, max = 6)
        })
    }

    @Test
    fun `hasDecimalDigits without min and max should be valid`() {
        validate(Company(valuation = 99.9999), {
            validate(Company::valuation).hasDecimalDigits()
        })
    }

    @Test
    fun `hasDecimalDigits with invalid min and max length property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(company = Company(valuation = 78.748536), address = Address(latitude = -9042641.62, longitude = 1572852.41)), {
                validate(Employee::company).validate {
                    validate(Company::valuation).hasDecimalDigits(min = 7)
                }
                validate(Employee::address).validate {
                    validate(Address::latitude).hasDecimalDigits(max = 1)
                    validate(Address::longitude).hasDecimalDigits(min = 3, max = 1)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company.valuation",
                        value = 78.748536,
                        constraint = DecimalDigits(min = 7)),
                DefaultConstraintViolation(
                        property = "address.latitude",
                        value = 9042641.62.unaryMinus(),
                        constraint = DecimalDigits(max = 1)),
                DefaultConstraintViolation(
                        property = "address.longitude",
                        value = 1572852.41,
                        constraint = DecimalDigits(min = 3, max = 1)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 78.748536,
                                constraint = DecimalDigits(min = 7),
                                message = "Decimal digits must be greater than or equal to 7"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 9042641.62.unaryMinus(),
                                constraint = DecimalDigits(max = 1),
                                message = "Decimal digits must be less than or equal to 1"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 1572852.41,
                                constraint = DecimalDigits(min = 3, max = 1),
                                message = "Decimal digits must be between 3 and 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 78.748536,
                                constraint = DecimalDigits(min = 7),
                                message = "Decimal digits must be greater than or equal to 7"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 9042641.62.unaryMinus(),
                                constraint = DecimalDigits(max = 1),
                                message = "Decimal digits must be less than or equal to 1"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 1572852.41,
                                constraint = DecimalDigits(min = 3, max = 1),
                                message = "Decimal digits must be between 3 and 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "company.valuation",
                                value = 78.748536,
                                constraint = DecimalDigits(min = 7),
                                message = "A quantidade de casas decimais deve ser maior ou igual a 7"),
                        DefaultI18nConstraintViolation(
                                property = "address.latitude",
                                value = 9042641.62.unaryMinus(),
                                constraint = DecimalDigits(max = 1),
                                message = "A quantidade de casas decimais deve ser menor ou igual a 1"),
                        DefaultI18nConstraintViolation(
                                property = "address.longitude",
                                value = 1572852.41,
                                constraint = DecimalDigits(min = 3, max = 1),
                                message = "A quantidade de casas decimais deve estar entre 3 e 1"))))
    }
}