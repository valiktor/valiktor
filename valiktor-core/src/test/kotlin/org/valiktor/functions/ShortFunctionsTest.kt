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

class ShortFunctionsTest {

    @Test
    fun `isZero with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isZero()
        })
    }

    @Test
    fun `isZero with valid property should be valid`() {
        validate(Employee(age = 0), {
            validate(Employee::age).isZero()
        })
    }

    @Test
    fun `isZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 1), {
                validate(Employee::age).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 1.toShort(),
                        constraint = Equals(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = Equals(0.toShort()),
                                message = "Must be equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = Equals(0.toShort()),
                                message = "Must be equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = Equals(0.toShort()),
                                message = "Deve ser igual a 0"))))
    }

    @Test
    fun `isNotZero with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isNotZero()
        })
    }

    @Test
    fun `isNotZero with valid property should be valid`() {
        validate(Employee(age = 1), {
            validate(Employee::age).isNotZero()
        })
    }

    @Test
    fun `isNotZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 0), {
                validate(Employee::age).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 0.toShort(),
                        constraint = NotEquals(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = NotEquals(0.toShort()),
                                message = "Must not be equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = NotEquals(0.toShort()),
                                message = "Must not be equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = NotEquals(0.toShort()),
                                message = "Não deve ser igual a 0"))))
    }

    @Test
    fun `isOne with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isOne()
        })
    }

    @Test
    fun `isOne with valid property should be valid`() {
        validate(Employee(age = 1), {
            validate(Employee::age).isOne()
        })
    }

    @Test
    fun `isOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 0), {
                validate(Employee::age).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 0.toShort(),
                        constraint = Equals(1.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Equals(1.toShort()),
                                message = "Must be equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Equals(1.toShort()),
                                message = "Must be equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Equals(1.toShort()),
                                message = "Deve ser igual a 1"))))
    }

    @Test
    fun `isNotOne with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isNotOne()
        })
    }

    @Test
    fun `isNotOne with valid property should be valid`() {
        validate(Employee(age = 0), {
            validate(Employee::age).isNotOne()
        })
    }

    @Test
    fun `isNotOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 1), {
                validate(Employee::age).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 1.toShort(),
                        constraint = NotEquals(1.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = NotEquals(1.toShort()),
                                message = "Must not be equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = NotEquals(1.toShort()),
                                message = "Must not be equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = NotEquals(1.toShort()),
                                message = "Não deve ser igual a 1"))))
    }

    @Test
    fun `isPositive with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isPositive()
        })
    }

    @Test
    fun `isPositive with valid property should be valid`() {
        validate(Employee(age = 1), {
            validate(Employee::age).isPositive()
        })
    }

    @Test
    fun `isPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 0, company = Company(age = -1)), {
                validate(Employee::age).isPositive()
                validate(Employee::company).validate {
                    validate(Company::age).isPositive()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 0.toShort(),
                        constraint = Greater(0.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 1.unaryMinus().toShort(),
                        constraint = Greater(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Must be greater than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 1.unaryMinus().toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Must be greater than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Must be greater than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 1.unaryMinus().toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Must be greater than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Deve ser maior que 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 1.unaryMinus().toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Deve ser maior que 0"))))
    }

    @Test
    fun `isNotPositive with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with valid property should be valid`() {
        validate(Employee(age = 0, company = Company(age = -1)), {
            validate(Employee::age).isNotPositive()
            validate(Employee::company).validate {
                validate(Company::age).isNotPositive()
            }
        })
    }

    @Test
    fun `isNotPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 1), {
                validate(Employee::age).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 1.toShort(),
                        constraint = LessOrEqual(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = LessOrEqual(0.toShort()),
                                message = "Must be less than or equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = LessOrEqual(0.toShort()),
                                message = "Must be less than or equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.toShort(),
                                constraint = LessOrEqual(0.toShort()),
                                message = "Deve ser menor ou igual a 0"))))
    }

    @Test
    fun `isNegative with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isNegative()
        })
    }

    @Test
    fun `isNegative with valid property should be valid`() {
        validate(Employee(age = -1), {
            validate(Employee::age).isNegative()
        })
    }

    @Test
    fun `isNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 0, company = Company(age = 1)), {
                validate(Employee::age).isNegative()
                validate(Employee::company).validate {
                    validate(Company::age).isNegative()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 0.toShort(),
                        constraint = Less(0.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 1.toShort(),
                        constraint = Less(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Must be less than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 1.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Must be less than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Must be less than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 1.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Must be less than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 0.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Deve ser menor que 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 1.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Deve ser menor que 0"))))
    }

    @Test
    fun `isNotNegative with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with valid property should be valid`() {
        validate(Employee(age = 0, company = Company(age = 1)), {
            validate(Employee::age).isNotNegative()
            validate(Employee::company).validate {
                validate(Company::age).isNotNegative()
            }
        })
    }

    @Test
    fun `isNotNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = -1), {
                validate(Employee::age).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 1.unaryMinus().toShort(),
                        constraint = GreaterOrEqual(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.unaryMinus().toShort(),
                                constraint = GreaterOrEqual(0.toShort()),
                                message = "Must be greater than or equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.unaryMinus().toShort(),
                                constraint = GreaterOrEqual(0.toShort()),
                                message = "Must be greater than or equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1.unaryMinus().toShort(),
                                constraint = GreaterOrEqual(0.toShort()),
                                message = "Deve ser maior ou igual a 0"))))
    }

    @Test
    fun `isLessThan with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isLessThan(10)
        })
    }

    @Test
    fun `isLessThan with valid property should be valid`() {
        validate(Employee(age = 9, company = Company(age = -10)), {
            validate(Employee::age).isLessThan(10)
            validate(Employee::company).validate {
                validate(Company::age).isLessThan(-9)
            }
        })
    }

    @Test
    fun `isLessThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 11, company = Company(age = -9), address = Address(apartment = 0)), {
                validate(Employee::age).isLessThan(10)
                validate(Employee::company).validate {
                    validate(Company::age).isLessThan(-10)
                }
                validate(Employee::address).validate {
                    validate(Address::apartment).isLessThan(0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 11.toShort(),
                        constraint = Less(10.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 9.unaryMinus().toShort(),
                        constraint = Less(10.unaryMinus().toShort())),
                DefaultConstraintViolation(
                        property = "address.apartment",
                        value = 0.toShort(),
                        constraint = Less(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 11.toShort(),
                                constraint = Less(10.toShort()),
                                message = "Must be less than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9.unaryMinus().toShort(),
                                constraint = Less(10.unaryMinus().toShort()),
                                message = "Must be less than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Must be less than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 11.toShort(),
                                constraint = Less(10.toShort()),
                                message = "Must be less than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9.unaryMinus().toShort(),
                                constraint = Less(10.unaryMinus().toShort()),
                                message = "Must be less than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Must be less than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 11.toShort(),
                                constraint = Less(10.toShort()),
                                message = "Deve ser menor que 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9.unaryMinus().toShort(),
                                constraint = Less(10.unaryMinus().toShort()),
                                message = "Deve ser menor que -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = Less(0.toShort()),
                                message = "Deve ser menor que 0"))))
    }

    @Test
    fun `isLessThanOrEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isLessThanOrEqualTo(10)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with valid property should be valid`() {
        validate(Employee(age = 9, company = Company(age = -10), address = Address(apartment = 0)), {
            validate(Employee::age).isLessThanOrEqualTo(10)
            validate(Employee::company).validate {
                validate(Company::age).isLessThanOrEqualTo(-9)
            }
            validate(Employee::address).validate {
                validate(Address::apartment).isLessThanOrEqualTo(0)
            }
        })
    }

    @Test
    fun `isLessThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 11, company = Company(age = -9)), {
                validate(Employee::age).isLessThanOrEqualTo(10)
                validate(Employee::company).validate {
                    validate(Company::age).isLessThanOrEqualTo(-10)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 11.toShort(),
                        constraint = LessOrEqual(10.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 9.unaryMinus().toShort(),
                        constraint = LessOrEqual(10.unaryMinus().toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 11.toShort(),
                                constraint = LessOrEqual(10.toShort()),
                                message = "Must be less than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9.unaryMinus().toShort(),
                                constraint = LessOrEqual(10.unaryMinus().toShort()),
                                message = "Must be less than or equal to -10"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 11.toShort(),
                                constraint = LessOrEqual(10.toShort()),
                                message = "Must be less than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9.unaryMinus().toShort(),
                                constraint = LessOrEqual(10.unaryMinus().toShort()),
                                message = "Must be less than or equal to -10"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 11.toShort(),
                                constraint = LessOrEqual(10.toShort()),
                                message = "Deve ser menor ou igual a 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9.unaryMinus().toShort(),
                                constraint = LessOrEqual(10.unaryMinus().toShort()),
                                message = "Deve ser menor ou igual a -10"))))
    }

    @Test
    fun `isGreaterThan with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isGreaterThan(10)
        })
    }

    @Test
    fun `isGreaterThan with valid property should be valid`() {
        validate(Employee(age = 11, company = Company(age = -8)), {
            validate(Employee::age).isGreaterThan(10)
            validate(Employee::company).validate {
                validate(Company::age).isGreaterThan(-9)
            }
        })
    }

    @Test
    fun `isGreaterThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 9, company = Company(age = -11), address = Address(apartment = 0)), {
                validate(Employee::age).isGreaterThan(10)
                validate(Employee::company).validate {
                    validate(Company::age).isGreaterThan(-10)
                }
                validate(Employee::address).validate {
                    validate(Address::apartment).isGreaterThan(0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 9.toShort(),
                        constraint = Greater(10.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 11.unaryMinus().toShort(),
                        constraint = Greater(10.unaryMinus().toShort())),
                DefaultConstraintViolation(
                        property = "address.apartment",
                        value = 0.toShort(),
                        constraint = Greater(0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = Greater(10.toShort()),
                                message = "Must be greater than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = Greater(10.unaryMinus().toShort()),
                                message = "Must be greater than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Must be greater than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = Greater(10.toShort()),
                                message = "Must be greater than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = Greater(10.unaryMinus().toShort()),
                                message = "Must be greater than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Must be greater than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = Greater(10.toShort()),
                                message = "Deve ser maior que 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = Greater(10.unaryMinus().toShort()),
                                message = "Deve ser maior que -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = Greater(0.toShort()),
                                message = "Deve ser maior que 0"))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isGreaterThanOrEqualTo(10)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with valid property should be valid`() {
        validate(Employee(age = 11, company = Company(age = -8), address = Address(apartment = 0)), {
            validate(Employee::age).isGreaterThanOrEqualTo(10)
            validate(Employee::company).validate {
                validate(Company::age).isGreaterThanOrEqualTo(-9)
            }
            validate(Employee::address).validate {
                validate(Address::apartment).isGreaterThanOrEqualTo(0)
            }
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 9, company = Company(age = -11)), {
                validate(Employee::age).isGreaterThanOrEqualTo(10)
                validate(Employee::company).validate {
                    validate(Company::age).isGreaterThanOrEqualTo(-10)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 9.toShort(),
                        constraint = GreaterOrEqual(10.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 11.unaryMinus().toShort(),
                        constraint = GreaterOrEqual(10.unaryMinus().toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = GreaterOrEqual(10.toShort()),
                                message = "Must be greater than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = GreaterOrEqual(10.unaryMinus().toShort()),
                                message = "Must be greater than or equal to -10"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = GreaterOrEqual(10.toShort()),
                                message = "Must be greater than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = GreaterOrEqual(10.unaryMinus().toShort()),
                                message = "Must be greater than or equal to -10"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = GreaterOrEqual(10.toShort()),
                                message = "Deve ser maior ou igual a 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = GreaterOrEqual(10.unaryMinus().toShort()),
                                message = "Deve ser maior ou igual a -10"))))
    }


    @Test
    fun `isBetween with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isBetween(start = 1, end = 10)
        })
    }

    @Test
    fun `isBetween with valid property should be valid`() {
        validate(Employee(age = 11, company = Company(age = -8), address = Address(apartment = 0)), {
            validate(Employee::age).isBetween(start = -12, end = 12)
            validate(Employee::company).validate {
                validate(Company::age).isBetween(start = -9, end = 9)
            }
            validate(Employee::address).validate {
                validate(Address::apartment).isBetween(start = 0, end = 0)
            }
        })
    }

    @Test
    fun `isBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 9, company = Company(age = -11)), {
                validate(Employee::age).isBetween(start = 10, end = 11)
                validate(Employee::company).validate {
                    validate(Company::age).isBetween(start = -10, end = -9)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 9.toShort(),
                        constraint = Between(start = 10.toShort(), end = 11.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 11.unaryMinus().toShort(),
                        constraint = Between(start = 10.unaryMinus().toShort(), end = 9.unaryMinus().toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = Between(start = 10.toShort(), end = 11.toShort()),
                                message = "Must be between 10 and 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = Between(start = 10.unaryMinus().toShort(), end = 9.unaryMinus().toShort()),
                                message = "Must be between -10 and -9"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = Between(start = 10.toShort(), end = 11.toShort()),
                                message = "Must be between 10 and 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = Between(start = 10.unaryMinus().toShort(), end = 9.unaryMinus().toShort()),
                                message = "Must be between -10 and -9"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = Between(start = 10.toShort(), end = 11.toShort()),
                                message = "Deve estar entre 10 e 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = Between(start = 10.unaryMinus().toShort(), end = 9.unaryMinus().toShort()),
                                message = "Deve estar entre -10 e -9"))))
    }

    @Test
    fun `isNotBetween with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).isNotBetween(start = 1, end = 10)
        })
    }

    @Test
    fun `isNotBetween with valid property should be valid`() {
        validate(Employee(age = 11, company = Company(age = -8)), {
            validate(Employee::age).isNotBetween(start = 8, end = 10)
            validate(Employee::company).validate {
                validate(Company::age).isNotBetween(start = -10, end = -9)
            }
        })
    }

    @Test
    fun `isNotBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 9, company = Company(age = -11), address = Address(apartment = 0)), {
                validate(Employee::age).isNotBetween(start = 8, end = 10)
                validate(Employee::company).validate {
                    validate(Company::age).isNotBetween(start = -12, end = -10)
                }
                validate(Employee::address).validate {
                    validate(Address::apartment).isNotBetween(start = 0, end = 0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 9.toShort(),
                        constraint = NotBetween(start = 8.toShort(), end = 10.toShort())),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 11.unaryMinus().toShort(),
                        constraint = NotBetween(start = 12.unaryMinus().toShort(), end = 10.unaryMinus().toShort())),
                DefaultConstraintViolation(
                        property = "address.apartment",
                        value = 0.toShort(),
                        constraint = NotBetween(start = 0.toShort(), end = 0.toShort())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = NotBetween(start = 8.toShort(), end = 10.toShort()),
                                message = "Must not be between 8 and 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = NotBetween(start = 12.unaryMinus().toShort(), end = 10.unaryMinus().toShort()),
                                message = "Must not be between -12 and -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = NotBetween(start = 0.toShort(), end = 0.toShort()),
                                message = "Must not be between 0 and 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = NotBetween(start = 8.toShort(), end = 10.toShort()),
                                message = "Must not be between 8 and 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = NotBetween(start = 12.unaryMinus().toShort(), end = 10.unaryMinus().toShort()),
                                message = "Must not be between -12 and -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = NotBetween(start = 0.toShort(), end = 0.toShort()),
                                message = "Must not be between 0 and 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 9.toShort(),
                                constraint = NotBetween(start = 8.toShort(), end = 10.toShort()),
                                message = "Não deve estar entre 8 e 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 11.unaryMinus().toShort(),
                                constraint = NotBetween(start = 12.unaryMinus().toShort(), end = 10.unaryMinus().toShort()),
                                message = "Não deve estar entre -12 e -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 0.toShort(),
                                constraint = NotBetween(start = 0.toShort(), end = 0.toShort()),
                                message = "Não deve estar entre 0 e 0"))))
    }

    @Test
    fun `hasDigits with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::age).hasDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDigits with valid min length property should be valid`() {
        validate(Employee(age = 1000), {
            validate(Employee::age).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with valid max length property should be valid`() {
        validate(Employee(age = 1000), {
            validate(Employee::age).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with valid min and max length property should be valid`() {
        validate(Employee(age = 1000), {
            validate(Employee::age).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(age = 1000), {
            validate(Employee::age).hasDigits()
        })
    }

    @Test
    fun `hasDigits with invalid min and max length property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(age = 1000, company = Company(age = 9999), address = Address(apartment = 9876)), {
                validate(Employee::age).hasDigits(min = 5)
                validate(Employee::company).validate {
                    validate(Company::age).hasDigits(max = 3)
                }
                validate(Employee::address).validate {
                    validate(Address::apartment).hasDigits(min = 5, max = 3)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "age",
                        value = 1000.toShort(),
                        constraint = IntegerDigits(min = 5)),
                DefaultConstraintViolation(
                        property = "company.age",
                        value = 9999.toShort(),
                        constraint = IntegerDigits(max = 3)),
                DefaultConstraintViolation(
                        property = "address.apartment",
                        value = 9876.toShort(),
                        constraint = IntegerDigits(min = 5, max = 3)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1000.toShort(),
                                constraint = IntegerDigits(min = 5),
                                message = "Integer digits must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9999.toShort(),
                                constraint = IntegerDigits(max = 3),
                                message = "Integer digits must be less than or equal to 3"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 9876.toShort(),
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "Integer digits must be between 5 and 3"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1000.toShort(),
                                constraint = IntegerDigits(min = 5),
                                message = "Integer digits must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9999.toShort(),
                                constraint = IntegerDigits(max = 3),
                                message = "Integer digits must be less than or equal to 3"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 9876.toShort(),
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "Integer digits must be between 5 and 3"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "age",
                                value = 1000.toShort(),
                                constraint = IntegerDigits(min = 5),
                                message = "A quantidade de dígitos inteiros deve ser maior ou igual a 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.age",
                                value = 9999.toShort(),
                                constraint = IntegerDigits(max = 3),
                                message = "A quantidade de dígitos inteiros deve ser menor ou igual a 3"),
                        DefaultI18nConstraintViolation(
                                property = "address.apartment",
                                value = 9876.toShort(),
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "A quantidade de dígitos inteiros deve estar entre 5 e 3"))))
    }
}