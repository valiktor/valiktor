package org.valiktor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ValidatorFixture.Employee
import org.valiktor.constraints.*

private object ValidatorFixture {

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
                DefaultConstraintViolation(property = "id", value = 1, constraint = Null())
        )
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
                DefaultConstraintViolation(property = "id", value = null, constraint = NotNull())
        )
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
                DefaultConstraintViolation(property = "id", value = 2, constraint = Equals(1))
        )
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
                DefaultConstraintViolation(property = "id", value = 1, constraint = NotEquals(1))
        )
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
                DefaultConstraintViolation(property = "id", value = 1, constraint = In(setOf(0, 2, 3)))
        )
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
                DefaultConstraintViolation(property = "id", value = 1, constraint = In(listOf(0, 2, 3)))
        )
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
                DefaultConstraintViolation(property = "id", value = 1, constraint = NotIn(setOf(1, 2, 3)))
        )
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
                DefaultConstraintViolation(property = "id", value = 1, constraint = NotIn(listOf(1, 2, 3)))
        )
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
                DefaultConstraintViolation(property = "id", value = 1, constraint = Valid<Int?>({ it == null }))
        )
    }
}