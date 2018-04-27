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

class LongFunctionsTest {

    @Test
    fun `isZero with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isZero()
        })
    }

    @Test
    fun `isZero with valid property should be valid`() {
        validate(Employee(ssn = 0), {
            validate(Employee::ssn).isZero()
        })
    }

    @Test
    fun `isZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 1), {
                validate(Employee::ssn).isZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 1.toLong(),
                        constraint = Equals(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = Equals(0.toLong()),
                                message = "Must be equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = Equals(0.toLong()),
                                message = "Must be equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = Equals(0.toLong()),
                                message = "Deve ser igual a 0"))))
    }

    @Test
    fun `isNotZero with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isNotZero()
        })
    }

    @Test
    fun `isNotZero with valid property should be valid`() {
        validate(Employee(ssn = 1), {
            validate(Employee::ssn).isNotZero()
        })
    }

    @Test
    fun `isNotZero with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 0), {
                validate(Employee::ssn).isNotZero()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 0.toLong(),
                        constraint = NotEquals(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = NotEquals(0.toLong()),
                                message = "Must not be equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = NotEquals(0.toLong()),
                                message = "Must not be equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = NotEquals(0.toLong()),
                                message = "Não deve ser igual a 0"))))
    }

    @Test
    fun `isOne with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isOne()
        })
    }

    @Test
    fun `isOne with valid property should be valid`() {
        validate(Employee(ssn = 1), {
            validate(Employee::ssn).isOne()
        })
    }

    @Test
    fun `isOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 0), {
                validate(Employee::ssn).isOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 0.toLong(),
                        constraint = Equals(1.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Equals(1.toLong()),
                                message = "Must be equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Equals(1.toLong()),
                                message = "Must be equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Equals(1.toLong()),
                                message = "Deve ser igual a 1"))))
    }

    @Test
    fun `isNotOne with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isNotOne()
        })
    }

    @Test
    fun `isNotOne with valid property should be valid`() {
        validate(Employee(ssn = 0), {
            validate(Employee::ssn).isNotOne()
        })
    }

    @Test
    fun `isNotOne with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 1), {
                validate(Employee::ssn).isNotOne()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 1.toLong(),
                        constraint = NotEquals(1.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = NotEquals(1.toLong()),
                                message = "Must not be equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = NotEquals(1.toLong()),
                                message = "Must not be equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = NotEquals(1.toLong()),
                                message = "Não deve ser igual a 1"))))
    }

    @Test
    fun `isPositive with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isPositive()
        })
    }

    @Test
    fun `isPositive with valid property should be valid`() {
        validate(Employee(ssn = 1), {
            validate(Employee::ssn).isPositive()
        })
    }

    @Test
    fun `isPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 0, company = Company(code = -1)), {
                validate(Employee::ssn).isPositive()
                validate(Employee::company).validate {
                    validate(Company::code).isPositive()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 0.toLong(),
                        constraint = Greater(0.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 1.unaryMinus().toLong(),
                        constraint = Greater(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Must be greater than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 1.unaryMinus().toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Must be greater than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Must be greater than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 1.unaryMinus().toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Must be greater than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Deve ser maior que 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 1.unaryMinus().toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Deve ser maior que 0"))))
    }

    @Test
    fun `isNotPositive with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isNotPositive()
        })
    }

    @Test
    fun `isNotPositive with valid property should be valid`() {
        validate(Employee(ssn = 0, company = Company(code = -1)), {
            validate(Employee::ssn).isNotPositive()
            validate(Employee::company).validate {
                validate(Company::code).isNotPositive()
            }
        })
    }

    @Test
    fun `isNotPositive with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 1), {
                validate(Employee::ssn).isNotPositive()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 1.toLong(),
                        constraint = LessOrEqual(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = LessOrEqual(0.toLong()),
                                message = "Must be less than or equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = LessOrEqual(0.toLong()),
                                message = "Must be less than or equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.toLong(),
                                constraint = LessOrEqual(0.toLong()),
                                message = "Deve ser menor ou igual a 0"))))
    }

    @Test
    fun `isNegative with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isNegative()
        })
    }

    @Test
    fun `isNegative with valid property should be valid`() {
        validate(Employee(ssn = -1), {
            validate(Employee::ssn).isNegative()
        })
    }

    @Test
    fun `isNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 0, company = Company(code = 1)), {
                validate(Employee::ssn).isNegative()
                validate(Employee::company).validate {
                    validate(Company::code).isNegative()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 0.toLong(),
                        constraint = Less(0.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 1.toLong(),
                        constraint = Less(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Must be less than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 1.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Must be less than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Must be less than 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 1.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Must be less than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 0.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Deve ser menor que 0"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 1.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Deve ser menor que 0"))))
    }

    @Test
    fun `isNotNegative with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isNotNegative()
        })
    }

    @Test
    fun `isNotNegative with valid property should be valid`() {
        validate(Employee(ssn = 0, company = Company(code = 1)), {
            validate(Employee::ssn).isNotNegative()
            validate(Employee::company).validate {
                validate(Company::code).isNotNegative()
            }
        })
    }

    @Test
    fun `isNotNegative with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = -1), {
                validate(Employee::ssn).isNotNegative()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 1.unaryMinus().toLong(),
                        constraint = GreaterOrEqual(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.unaryMinus().toLong(),
                                constraint = GreaterOrEqual(0.toLong()),
                                message = "Must be greater than or equal to 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.unaryMinus().toLong(),
                                constraint = GreaterOrEqual(0.toLong()),
                                message = "Must be greater than or equal to 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1.unaryMinus().toLong(),
                                constraint = GreaterOrEqual(0.toLong()),
                                message = "Deve ser maior ou igual a 0"))))
    }

    @Test
    fun `isLessThan with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isLessThan(10)
        })
    }

    @Test
    fun `isLessThan with valid property should be valid`() {
        validate(Employee(ssn = 9, company = Company(code = -10)), {
            validate(Employee::ssn).isLessThan(10)
            validate(Employee::company).validate {
                validate(Company::code).isLessThan(-9)
            }
        })
    }

    @Test
    fun `isLessThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 11, company = Company(code = -9), address = Address(number = 0)), {
                validate(Employee::ssn).isLessThan(10)
                validate(Employee::company).validate {
                    validate(Company::code).isLessThan(-10)
                }
                validate(Employee::address).validate {
                    validate(Address::number).isLessThan(0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 11.toLong(),
                        constraint = Less(10.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 9.unaryMinus().toLong(),
                        constraint = Less(10.unaryMinus().toLong())),
                DefaultConstraintViolation(
                        property = "address.number",
                        value = 0.toLong(),
                        constraint = Less(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 11.toLong(),
                                constraint = Less(10.toLong()),
                                message = "Must be less than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9.unaryMinus().toLong(),
                                constraint = Less(10.unaryMinus().toLong()),
                                message = "Must be less than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Must be less than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 11.toLong(),
                                constraint = Less(10.toLong()),
                                message = "Must be less than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9.unaryMinus().toLong(),
                                constraint = Less(10.unaryMinus().toLong()),
                                message = "Must be less than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Must be less than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 11.toLong(),
                                constraint = Less(10.toLong()),
                                message = "Deve ser menor que 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9.unaryMinus().toLong(),
                                constraint = Less(10.unaryMinus().toLong()),
                                message = "Deve ser menor que -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = Less(0.toLong()),
                                message = "Deve ser menor que 0"))))
    }

    @Test
    fun `isLessThanOrEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isLessThanOrEqualTo(10)
        })
    }

    @Test
    fun `isLessThanOrEqualTo with valid property should be valid`() {
        validate(Employee(ssn = 9, company = Company(code = -10), address = Address(number = 0)), {
            validate(Employee::ssn).isLessThanOrEqualTo(10)
            validate(Employee::company).validate {
                validate(Company::code).isLessThanOrEqualTo(-9)
            }
            validate(Employee::address).validate {
                validate(Address::number).isLessThanOrEqualTo(0)
            }
        })
    }

    @Test
    fun `isLessThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 11, company = Company(code = -9)), {
                validate(Employee::ssn).isLessThanOrEqualTo(10)
                validate(Employee::company).validate {
                    validate(Company::code).isLessThanOrEqualTo(-10)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 11.toLong(),
                        constraint = LessOrEqual(10.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 9.unaryMinus().toLong(),
                        constraint = LessOrEqual(10.unaryMinus().toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 11.toLong(),
                                constraint = LessOrEqual(10.toLong()),
                                message = "Must be less than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9.unaryMinus().toLong(),
                                constraint = LessOrEqual(10.unaryMinus().toLong()),
                                message = "Must be less than or equal to -10"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 11.toLong(),
                                constraint = LessOrEqual(10.toLong()),
                                message = "Must be less than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9.unaryMinus().toLong(),
                                constraint = LessOrEqual(10.unaryMinus().toLong()),
                                message = "Must be less than or equal to -10"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 11.toLong(),
                                constraint = LessOrEqual(10.toLong()),
                                message = "Deve ser menor ou igual a 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9.unaryMinus().toLong(),
                                constraint = LessOrEqual(10.unaryMinus().toLong()),
                                message = "Deve ser menor ou igual a -10"))))
    }

    @Test
    fun `isGreaterThan with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isGreaterThan(10)
        })
    }

    @Test
    fun `isGreaterThan with valid property should be valid`() {
        validate(Employee(ssn = 11, company = Company(code = -8)), {
            validate(Employee::ssn).isGreaterThan(10)
            validate(Employee::company).validate {
                validate(Company::code).isGreaterThan(-9)
            }
        })
    }

    @Test
    fun `isGreaterThan with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 9, company = Company(code = -11), address = Address(number = 0)), {
                validate(Employee::ssn).isGreaterThan(10)
                validate(Employee::company).validate {
                    validate(Company::code).isGreaterThan(-10)
                }
                validate(Employee::address).validate {
                    validate(Address::number).isGreaterThan(0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 9.toLong(),
                        constraint = Greater(10.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 11.unaryMinus().toLong(),
                        constraint = Greater(10.unaryMinus().toLong())),
                DefaultConstraintViolation(
                        property = "address.number",
                        value = 0.toLong(),
                        constraint = Greater(0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = Greater(10.toLong()),
                                message = "Must be greater than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = Greater(10.unaryMinus().toLong()),
                                message = "Must be greater than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Must be greater than 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = Greater(10.toLong()),
                                message = "Must be greater than 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = Greater(10.unaryMinus().toLong()),
                                message = "Must be greater than -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Must be greater than 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = Greater(10.toLong()),
                                message = "Deve ser maior que 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = Greater(10.unaryMinus().toLong()),
                                message = "Deve ser maior que -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = Greater(0.toLong()),
                                message = "Deve ser maior que 0"))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isGreaterThanOrEqualTo(10)
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with valid property should be valid`() {
        validate(Employee(ssn = 11, company = Company(code = -8), address = Address(number = 0)), {
            validate(Employee::ssn).isGreaterThanOrEqualTo(10)
            validate(Employee::company).validate {
                validate(Company::code).isGreaterThanOrEqualTo(-9)
            }
            validate(Employee::address).validate {
                validate(Address::number).isGreaterThanOrEqualTo(0)
            }
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 9, company = Company(code = -11)), {
                validate(Employee::ssn).isGreaterThanOrEqualTo(10)
                validate(Employee::company).validate {
                    validate(Company::code).isGreaterThanOrEqualTo(-10)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 9.toLong(),
                        constraint = GreaterOrEqual(10.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 11.unaryMinus().toLong(),
                        constraint = GreaterOrEqual(10.unaryMinus().toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = GreaterOrEqual(10.toLong()),
                                message = "Must be greater than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = GreaterOrEqual(10.unaryMinus().toLong()),
                                message = "Must be greater than or equal to -10"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = GreaterOrEqual(10.toLong()),
                                message = "Must be greater than or equal to 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = GreaterOrEqual(10.unaryMinus().toLong()),
                                message = "Must be greater than or equal to -10"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = GreaterOrEqual(10.toLong()),
                                message = "Deve ser maior ou igual a 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = GreaterOrEqual(10.unaryMinus().toLong()),
                                message = "Deve ser maior ou igual a -10"))))
    }


    @Test
    fun `isBetween with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isBetween(start = 1, end = 10)
        })
    }

    @Test
    fun `isBetween with valid property should be valid`() {
        validate(Employee(ssn = 11, company = Company(code = -8), address = Address(number = 0)), {
            validate(Employee::ssn).isBetween(start = -12, end = 12)
            validate(Employee::company).validate {
                validate(Company::code).isBetween(start = -9, end = 9)
            }
            validate(Employee::address).validate {
                validate(Address::number).isBetween(start = 0, end = 0)
            }
        })
    }

    @Test
    fun `isBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 9, company = Company(code = -11)), {
                validate(Employee::ssn).isBetween(start = 10, end = 11)
                validate(Employee::company).validate {
                    validate(Company::code).isBetween(start = -13, end = -12)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 9.toLong(),
                        constraint = Between(start = 10.toLong(), end = 11.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 11.unaryMinus().toLong(),
                        constraint = Between(start = 13.unaryMinus().toLong(), end = 12.unaryMinus().toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = Between(start = 10.toLong(), end = 11.toLong()),
                                message = "Must be between 10 and 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = Between(start = 13.unaryMinus().toLong(), end = 12.unaryMinus().toLong()),
                                message = "Must be between -13 and -12"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = Between(start = 10.toLong(), end = 11.toLong()),
                                message = "Must be between 10 and 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = Between(start = 13.unaryMinus().toLong(), end = 12.unaryMinus().toLong()),
                                message = "Must be between -13 and -12"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = Between(start = 10.toLong(), end = 11.toLong()),
                                message = "Deve estar entre 10 e 11"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = Between(start = 13.unaryMinus().toLong(), end = 12.unaryMinus().toLong()),
                                message = "Deve estar entre -13 e -12"))))
    }

    @Test
    fun `isNotBetween with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).isNotBetween(start = 1, end = 10)
        })
    }

    @Test
    fun `isNotBetween with valid property should be valid`() {
        validate(Employee(ssn = 11, company = Company(code = -8)), {
            validate(Employee::ssn).isNotBetween(start = 8, end = 10)
            validate(Employee::company).validate {
                validate(Company::code).isNotBetween(start = -6, end = -7)
            }
        })
    }

    @Test
    fun `isNotBetween with invalid property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 9, company = Company(code = -11), address = Address(number = 0)), {
                validate(Employee::ssn).isNotBetween(start = 8, end = 10)
                validate(Employee::company).validate {
                    validate(Company::code).isNotBetween(start = -12, end = -10)
                }
                validate(Employee::address).validate {
                    validate(Address::number).isNotBetween(start = 0, end = 0)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 9.toLong(),
                        constraint = NotBetween(start = 8.toLong(), end = 10.toLong())),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 11.unaryMinus().toLong(),
                        constraint = NotBetween(start = 12.unaryMinus().toLong(), end = 10.unaryMinus().toLong())),
                DefaultConstraintViolation(
                        property = "address.number",
                        value = 0.toLong(),
                        constraint = NotBetween(start = 0.toLong(), end = 0.toLong())))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = NotBetween(start = 8.toLong(), end = 10.toLong()),
                                message = "Must not be between 8 and 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = NotBetween(start = 12.unaryMinus().toLong(), end = 10.unaryMinus().toLong()),
                                message = "Must not be between -12 and -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = NotBetween(start = 0.toLong(), end = 0.toLong()),
                                message = "Must not be between 0 and 0"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = NotBetween(start = 8.toLong(), end = 10.toLong()),
                                message = "Must not be between 8 and 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = NotBetween(start = 12.unaryMinus().toLong(), end = 10.unaryMinus().toLong()),
                                message = "Must not be between -12 and -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = NotBetween(start = 0.toLong(), end = 0.toLong()),
                                message = "Must not be between 0 and 0"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 9.toLong(),
                                constraint = NotBetween(start = 8.toLong(), end = 10.toLong()),
                                message = "Não deve estar entre 8 e 10"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 11.unaryMinus().toLong(),
                                constraint = NotBetween(start = 12.unaryMinus().toLong(), end = 10.unaryMinus().toLong()),
                                message = "Não deve estar entre -12 e -10"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 0.toLong(),
                                constraint = NotBetween(start = 0.toLong(), end = 0.toLong()),
                                message = "Não deve estar entre 0 e 0"))))
    }

    @Test
    fun `hasDigits with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::ssn).hasDigits(min = 1, max = 10)
        })
    }

    @Test
    fun `hasDigits with valid min length property should be valid`() {
        validate(Employee(ssn = 1000), {
            validate(Employee::ssn).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with valid max length property should be valid`() {
        validate(Employee(ssn = 1000), {
            validate(Employee::ssn).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with valid min and max length property should be valid`() {
        validate(Employee(ssn = 1000), {
            validate(Employee::ssn).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid min length property should be valid`() {
        validate(Employee(ssn = -1000), {
            validate(Employee::ssn).hasDigits(min = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid max length property should be valid`() {
        validate(Employee(ssn = -1000), {
            validate(Employee::ssn).hasDigits(max = 4)
        })
    }

    @Test
    fun `hasDigits with negative valid min and max length property should be valid`() {
        validate(Employee(ssn = -1000), {
            validate(Employee::ssn).hasDigits(min = 4, max = 4)
        })
    }

    @Test
    fun `hasDigits without min and max should be valid`() {
        validate(Employee(ssn = 1000), {
            validate(Employee::ssn).hasDigits()
        })
    }

    @Test
    fun `hasDigits with invalid min and max length property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(ssn = 1000, company = Company(code = 9999), address = Address(number = 9876)), {
                validate(Employee::ssn).hasDigits(min = 5)
                validate(Employee::company).validate {
                    validate(Company::code).hasDigits(max = 3)
                }
                validate(Employee::address).validate {
                    validate(Address::number).hasDigits(min = 5, max = 3)
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "ssn",
                        value = 1000.toLong(),
                        constraint = IntegerDigits(min = 5)),
                DefaultConstraintViolation(
                        property = "company.code",
                        value = 9999.toLong(),
                        constraint = IntegerDigits(max = 3)),
                DefaultConstraintViolation(
                        property = "address.number",
                        value = 9876.toLong(),
                        constraint = IntegerDigits(min = 5, max = 3)))

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1000.toLong(),
                                constraint = IntegerDigits(min = 5),
                                message = "Integer digits must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9999.toLong(),
                                constraint = IntegerDigits(max = 3),
                                message = "Integer digits must be less than or equal to 3"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 9876.toLong(),
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "Integer digits must be between 5 and 3"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1000.toLong(),
                                constraint = IntegerDigits(min = 5),
                                message = "Integer digits must be greater than or equal to 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9999.toLong(),
                                constraint = IntegerDigits(max = 3),
                                message = "Integer digits must be less than or equal to 3"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 9876.toLong(),
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "Integer digits must be between 5 and 3"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "ssn",
                                value = 1000.toLong(),
                                constraint = IntegerDigits(min = 5),
                                message = "A quantidade de dígitos inteiros deve ser maior ou igual a 5"),
                        DefaultI18nConstraintViolation(
                                property = "company.code",
                                value = 9999.toLong(),
                                constraint = IntegerDigits(max = 3),
                                message = "A quantidade de dígitos inteiros deve ser menor ou igual a 3"),
                        DefaultI18nConstraintViolation(
                                property = "address.number",
                                value = 9876.toLong(),
                                constraint = IntegerDigits(min = 5, max = 3),
                                message = "A quantidade de dígitos inteiros deve estar entre 5 e 3"))))
    }
}