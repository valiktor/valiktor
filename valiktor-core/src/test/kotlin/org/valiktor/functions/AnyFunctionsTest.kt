package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.AnyFunctionsFixture.Address
import org.valiktor.functions.AnyFunctionsFixture.City
import org.valiktor.functions.AnyFunctionsFixture.Company
import org.valiktor.functions.AnyFunctionsFixture.Country
import org.valiktor.functions.AnyFunctionsFixture.Employee
import org.valiktor.functions.AnyFunctionsFixture.State
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object AnyFunctionsFixture {

    data class Employee(val id: Int? = null, val name: String? = null, val company: Company? = null, val address: Address? = null)
    data class Company(val id: Int? = null)
    data class Address(val id: Int? = null, val city: City? = null)
    data class City(val id: Int? = null, val state: State? = null)
    data class State(val id: Int? = null, val country: Country? = null)
    data class Country(val id: Int? = null)
}

class AnyFunctionsTest {

    @Test
    fun `isNull with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isNull()
        })
    }

    @Test
    fun `isNull with not null property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1)), {
                validate(Employee::company).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 1),
                        constraint = Null))
    }

    @Test
    fun `isNotNull with not null property should be valid`() {
        validate(Employee(company = Company(id = 1)), {
            validate(Employee::company).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::company).isNotNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isEqualTo(Company(id = 1))
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(company = Company(id = 1)), {
            validate(Employee::company).isEqualTo(Company(id = 1))
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 2)), {
                validate(Employee::company).isEqualTo(Company(id = 1))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 2),
                        constraint = Equals(Company(id = 1))))
    }

    @Test
    fun `isNotEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isNotEqualTo(Company(id = 1))
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(company = Company(id = 2)), {
            validate(Employee::company).isNotEqualTo(Company(id = 1))
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1)), {
                validate(Employee::company).isNotEqualTo(Company(id = 1))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 1),
                        constraint = NotEquals(Company(id = 1))))
    }

    @Test
    fun `isIn vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isIn(Company(id = 1), Company(id = 2), Company(id = 3))
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(company = Company(id = 2)), {
            validate(Employee::company).isIn(Company(id = 1), Company(id = 2), Company(id = 3))
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1)), {
                validate(Employee::company).isIn(Company(id = 0), Company(id = 2), Company(id = 3))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 1),
                        constraint = In(setOf(Company(id = 0), Company(id = 2), Company(id = 3)))))
    }

    @Test
    fun `isIn iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isIn(listOf(Company(id = 1), Company(id = 2), Company(id = 3)))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(company = Company(id = 2)), {
            validate(Employee::company).isIn(listOf(Company(id = 1), Company(id = 2), Company(id = 3)))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1)), {
                validate(Employee::company).isIn(listOf(Company(id = 0), Company(id = 2), Company(id = 3)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 1),
                        constraint = In(listOf(Company(id = 0), Company(id = 2), Company(id = 3)))))
    }

    @Test
    fun `isNotIn vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isNotIn(Company(id = 0), Company(id = 2), Company(id = 3))
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(company = Company(id = 1)), {
            validate(Employee::company).isNotIn(Company(id = 0), Company(id = 2), Company(id = 3))
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1)), {
                validate(Employee::company).isNotIn(Company(id = 1), Company(id = 2), Company(id = 3))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 1),
                        constraint = NotIn(setOf(Company(id = 1), Company(id = 2), Company(id = 3)))))
    }

    @Test
    fun `isNotIn iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isNotIn(listOf(Company(id = 0), Company(id = 2), Company(id = 3)))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(company = Company(id = 1)), {
            validate(Employee::company).isNotIn(listOf(Company(id = 0), Company(id = 2), Company(id = 3)))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1)), {
                validate(Employee::company).isNotIn(listOf(Company(id = 1), Company(id = 2), Company(id = 3)))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 1),
                        constraint = NotIn(listOf(Company(id = 1), Company(id = 2), Company(id = 3)))))
    }

    @Test
    fun `isValid with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::company).isValid { it == Company(id = 1) }
        })
    }

    @Test
    fun `isValid with same value should be valid`() {
        validate(Employee(company = Company(id = 1)), {
            validate(Employee::company).isValid { it == Company(id = 1) }
        })
    }

    @Test
    fun `isValid with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 2)), {
                validate(Employee::company).isValid { it == Company(id = 1) }
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "company",
                        value = Company(id = 2),
                        constraint = Valid))
    }

    @Test
    fun `inner null properties should be valid`() {
        validate(Employee(), {
            validate(Employee::company).validate {
                validate(Company::id).isNotNull()
            }
            validate(Employee::address).validate {
                validate(Address::id).isNotNull()
                validate(Address::city).validate {
                    validate(City::id).isNotNull()
                    validate(City::state).validate {
                        validate(State::id).isNotNull()
                        validate(State::country).validate {
                            validate(Country::id).isNotNull()
                        }
                    }
                }
            }
        })
    }

    @Test
    fun `inner not null properties should be valid`() {
        validate(Employee(id = 1, company = Company(id = 1), address =
        Address(id = 1, city = City(id = 1, state =
        State(id = 1, country = Country(id = 1))))), {
            validate(Employee::id).isNotNull()
            validate(Employee::company).validate {
                validate(Company::id).isNotNull()
            }
            validate(Employee::address).validate {
                validate(Address::id).isNotNull()
                validate(Address::city).validate {
                    validate(City::id).isNotNull()
                    validate(City::state).validate {
                        validate(State::id).isNotNull()
                        validate(State::country).validate {
                            validate(Country::id).isNotNull()
                        }
                    }
                }
            }
        })
    }

    @Test
    fun `inner null properties should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(), address =
            Address(city = City(state =
            State(country = Country())))), {
                validate(Employee::id).isNotNull()
                validate(Employee::company).validate {
                    validate(Company::id).isNotNull()
                }
                validate(Employee::address).validate {
                    validate(Address::id).isNotNull()
                    validate(Address::city).validate {
                        validate(City::id).isNotNull()
                        validate(City::state).validate {
                            validate(State::id).isNotNull()
                            validate(State::country).validate {
                                validate(Country::id).isNotNull()
                            }
                        }
                    }
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", constraint = NotNull),
                DefaultConstraintViolation(property = "company.id", constraint = NotNull),
                DefaultConstraintViolation(property = "address.id", constraint = NotNull),
                DefaultConstraintViolation(property = "address.city.id", constraint = NotNull),
                DefaultConstraintViolation(property = "address.city.state.id", constraint = NotNull),
                DefaultConstraintViolation(property = "address.city.state.country.id", constraint = NotNull))
    }

    @Test
    fun `should not repeat the property`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::id).isNotNull().isEqualTo(1).isIn(1, 2, 3)
                validate(Employee::name).isNotNull().isEqualTo("test").isIn("test1", "test2", "test3")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "id", constraint = NotNull),
                DefaultConstraintViolation(property = "name", constraint = NotNull))
    }
}