package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.StringFunctionsFixture.Employee
import org.valiktor.validate

private object StringFunctionsFixture {

    data class Employee(val name: String? = null, val email: String? = null, val username: String? = null)
}

class StringFunctionsTest {

    @Test
    fun `isNull with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNull()
        })
    }

    @Test
    fun `isNull with not null property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test"), {
                validate(Employee::name).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test", constraint = Null))
    }

    @Test
    fun `isNotNull with not null property should be valid`() {
        validate(Employee(name = "test"), {
            validate(Employee::name).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::name).isNotNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isEqualTo("test")
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(name = "test"), {
            validate(Employee::name).isEqualTo("test")
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test"), {
                validate(Employee::name).isEqualTo("test1")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test", constraint = Equals("test1")))
    }

    @Test
    fun `isEqualTo with different case value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test"), {
                validate(Employee::name).isEqualTo("TEST")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test", constraint = Equals("TEST")))
    }

    @Test
    fun `isNotEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotEqualTo("test")
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(name = "test"), {
            validate(Employee::name).isNotEqualTo("test1")
        })
    }

    @Test
    fun `isNotEqualTo with different case value should be valid`() {
        validate(Employee(name = "test"), {
            validate(Employee::name).isNotEqualTo("TEST")
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test"), {
                validate(Employee::name).isNotEqualTo("test")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test", constraint = NotEquals("test")))
    }

    @Test
    fun `isIn vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isIn("test1", "test2", "test3")
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(name = "test2"), {
            validate(Employee::name).isIn("test1", "test2", "test3")
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test1"), {
                validate(Employee::name).isIn("test0", "test2", "test3")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test1", constraint = In(setOf("test0", "test2", "test3"))))
    }

    @Test
    fun `isIn vararg with different case value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test1"), {
                validate(Employee::name).isIn("TEST1", "test2", "test3")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test1", constraint = In(setOf("TEST1", "test2", "test3"))))
    }

    @Test
    fun `isIn iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isIn(listOf("test1", "test2", "test3"))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(name = "test2"), {
            validate(Employee::name).isIn(listOf("test1", "test2", "test3"))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test1"), {
                validate(Employee::name).isIn(listOf("test0", "test2", "test3"))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test1", constraint = In(listOf("test0", "test2", "test3"))))
    }

    @Test
    fun `isIn iterable with different case value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test1"), {
                validate(Employee::name).isIn(listOf("TEST1", "test2", "test3"))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test1", constraint = In(listOf("TEST1", "test2", "test3"))))
    }

    @Test
    fun `isNotIn vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotIn("test1", "test2", "test3")
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(name = "test1"), {
            validate(Employee::name).isNotIn("test0", "test2", "test3")
        })
    }

    @Test
    fun `isNotIn vararg with different case value should be valid`() {
        validate(Employee(name = "test1"), {
            validate(Employee::name).isNotIn("TEST1", "test2", "test3")
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test1"), {
                validate(Employee::name).isNotIn("test1", "test2", "test3")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test1", constraint = NotIn(setOf("test1", "test2", "test3"))))
    }

    @Test
    fun `isNotIn iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotIn(listOf("test1", "test2", "test3"))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(name = "test1"), {
            validate(Employee::name).isNotIn(listOf("test0", "test2", "test3"))
        })
    }

    @Test
    fun `isNotIn iterable with different case value should be valid`() {
        validate(Employee(name = "test1"), {
            validate(Employee::name).isNotIn(listOf("TEST1", "test2", "test3"))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "test1"), {
                validate(Employee::name).isNotIn(listOf("test1", "test2", "test3"))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "test1", constraint = NotIn(listOf("test1", "test2", "test3"))))
    }

    @Test
    fun `isEmpty with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isEmpty()
        })
    }

    @Test
    fun `isEmpty with empty value should be valid`() {
        validate(Employee(name = ""), {
            validate(Employee::name).isEmpty()
        })
    }

    @Test
    fun `isEmpty with blank value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = " "), {
                validate(Employee::name).isEmpty()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = " ", constraint = Empty))
    }

    @Test
    fun `isNotEmpty with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotEmpty()
        })
    }

    @Test
    fun `isNotEmpty with blank value should be valid`() {
        validate(Employee(name = " "), {
            validate(Employee::name).isNotEmpty()
        })
    }

    @Test
    fun `isNotEmpty with empty value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = ""), {
                validate(Employee::name).isNotEmpty()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "", constraint = NotEmpty))
    }

    @Test
    fun `isBlank with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isBlank()
        })
    }

    @Test
    fun `isBlank with empty value should be valid`() {
        validate(Employee(name = ""), {
            validate(Employee::name).isBlank()
        })
    }

    @Test
    fun `isBlank with blank value should be valid`() {
        validate(Employee(name = " "), {
            validate(Employee::name).isBlank()
        })
    }

    @Test
    fun `isBlank with not blank value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "a"), {
                validate(Employee::name).isBlank()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "a", constraint = Blank))
    }

    @Test
    fun `isNotBlank with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotBlank()
        })
    }

    @Test
    fun `isNotBlank with not blank value should be valid`() {
        validate(Employee(name = "a"), {
            validate(Employee::name).isNotBlank()
        })
    }

    @Test
    fun `isNotBlank with empty or blank value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(email = "", username = " "), {
                validate(Employee::email).isNotBlank()
                validate(Employee::username).isNotBlank()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "email", value = "", constraint = NotBlank),
                DefaultConstraintViolation(property = "username", value = " ", constraint = NotBlank))
    }

    @Test
    fun `isEqualToIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isEqualToIgnoringCase("a")
        })
    }

    @Test
    fun `isEqualToIgnoringCase with same value should be valid`() {
        validate(Employee(name = "A"), {
            validate(Employee::name).isEqualToIgnoringCase("a")
        })
    }

    @Test
    fun `isEqualToIgnoringCase with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "a"), {
                validate(Employee::name).isEqualToIgnoringCase("b")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "a", constraint = Equals("b")))
    }

    @Test
    fun `isNotEqualToIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotEqualToIgnoringCase("a")
        })
    }

    @Test
    fun `isNotEqualToIgnoringCase with different value should be valid`() {
        validate(Employee(name = "a"), {
            validate(Employee::name).isNotEqualToIgnoringCase("b")
        })
    }

    @Test
    fun `isNotEqualToIgnoringCase with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "a"), {
                validate(Employee::name).isNotEqualToIgnoringCase("A")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "a", constraint = NotEquals("A")))
    }

    @Test
    fun `isInIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isInIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `isInIgnoringCase vararg with same value should be valid`() {
        validate(Employee(name = "A"), {
            validate(Employee::name).isInIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `isInIgnoringCase vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "a"), {
                validate(Employee::name).isInIgnoringCase("b", "c")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "a", constraint = In(setOf("b", "c"))))
    }

    @Test
    fun `isInIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isInIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `isInIgnoringCase iterable with same value should be valid`() {
        validate(Employee(name = "A"), {
            validate(Employee::name).isInIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `isInIgnoringCase iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "a"), {
                validate(Employee::name).isInIgnoringCase(listOf("b", "c"))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "a", constraint = In(listOf("b", "c"))))
    }

    @Test
    fun `isNotInIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotInIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `isNotInIgnoringCase vararg with different value should be valid`() {
        validate(Employee(name = "a"), {
            validate(Employee::name).isNotInIgnoringCase("b", "c")
        })
    }

    @Test
    fun `isNotInIgnoringCase vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "A"), {
                validate(Employee::name).isNotInIgnoringCase("a", "b", "c")
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "A", constraint = NotIn(setOf("a", "b", "c"))))
    }

    @Test
    fun `isNotInIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).isNotInIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `isNotInIgnoringCase iterable with different value should be valid`() {
        validate(Employee(name = "a"), {
            validate(Employee::name).isNotInIgnoringCase(listOf("b", "c"))
        })
    }

    @Test
    fun `isNotInIgnoringCase iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "A"), {
                validate(Employee::name).isNotInIgnoringCase(listOf("a", "b", "c"))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "A", constraint = NotIn(listOf("a", "b", "c"))))
    }

    @Test
    fun `size with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).hasSize(min = 1, max = 10)
        })
    }

    @Test
    fun `size with valid min value should be valid`() {
        validate(Employee(name = "John"), {
            validate(Employee::name).hasSize(min = 4)
        })
    }

    @Test
    fun `size with valid max value should be valid`() {
        validate(Employee(name = "John"), {
            validate(Employee::name).hasSize(max = 4)
        })
    }

    @Test
    fun `size with valid min and max value should be valid`() {
        validate(Employee(name = "John"), {
            validate(Employee::name).hasSize(min = 4, max = 4)
        })
    }

    @Test
    fun `size without min and max should be valid`() {
        validate(Employee(name = "John"), {
            validate(Employee::name).hasSize()
        })
    }

    @Test
    fun `size with invalid min and max value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John", email = "john@company.com", username = "john"), {
                validate(Employee::name).hasSize(min = 5)
                validate(Employee::email).hasSize(max = 15)
                validate(Employee::username).hasSize(min = 5, max = 3)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = Size(min = 5)),
                DefaultConstraintViolation(property = "email", value = "john@company.com", constraint = Size(max = 15)),
                DefaultConstraintViolation(property = "username", value = "john", constraint = Size(min = 5, max = 3)))
    }

    @Test
    fun `contains with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).contains("a")
        })
    }

    @Test
    fun `contains with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).contains("a")
        })
    }

    @Test
    fun `contains with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).contains("j")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = Contains("j")))
    }

    @Test
    fun `containsIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsIgnoringCase("a")
        })
    }

    @Test
    fun `containsIgnoringCase with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).containsIgnoringCase("a")
        })
    }

    @Test
    fun `containsIgnoringCase with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsIgnoringCase("g")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = Contains("g")))
    }

    @Test
    fun `containsAll vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAll("a", "b", "c")
        })
    }

    @Test
    fun `containsAll vararg with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).containsAll("a", "b", "c")
        })
    }

    @Test
    fun `containsAll vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAll("j", "o", "h", "n")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAll(setOf("j", "o", "h", "n"))))
    }

    @Test
    fun `containsAll iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAll(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `containsAll iterable with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).containsAll(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `containsAll iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAll(listOf("j", "o", "h", "n"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAll(listOf("j", "o", "h", "n"))))
    }

    @Test
    fun `containsAllIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAllIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `containsAllIgnoringCase vararg with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).containsAllIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `containsAllIgnoringCase vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAllIgnoringCase("j", "o", "h", "n", "k")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAll(setOf("j", "o", "h", "n", "k"))))
    }

    @Test
    fun `containsAllIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAllIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `containsAllIgnoringCase iterable with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).containsAllIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `containsAllIgnoringCase iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAllIgnoringCase(listOf("j", "o", "h", "n", "k"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAll(listOf("j", "o", "h", "n", "k"))))
    }

    @Test
    fun `containsAny vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAny("a", "b", "c")
        })
    }

    @Test
    fun `containsAny vararg with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).containsAny("a", "e", "f")
        })
    }

    @Test
    fun `containsAny vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAny("j", "w", "x", "e")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAny(setOf("j", "w", "x", "e"))))
    }

    @Test
    fun `containsAny iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAny(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `containsAny iterable with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).containsAny(listOf("a", "e", "f"))
        })
    }

    @Test
    fun `containsAny iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAny(listOf("j", "w", "x", "e"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAny(listOf("j", "w", "x", "e"))))
    }

    @Test
    fun `containsAnyIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAnyIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `containsAnyIgnoringCase vararg with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).containsAnyIgnoringCase("a", "e", "f")
        })
    }

    @Test
    fun `containsAnyIgnoringCase vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAnyIgnoringCase("w", "x", "e")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAny(setOf("w", "x", "e"))))
    }

    @Test
    fun `containsAnyIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).containsAnyIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `containsAnyIgnoringCase iterable with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).containsAnyIgnoringCase(listOf("a", "e", "f"))
        })
    }

    @Test
    fun `containsAnyIgnoringCase iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).containsAnyIgnoringCase(listOf("w", "x", "e"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsAny(listOf("w", "x", "e"))))
    }

    @Test
    fun `doesNotContain with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContain("a")
        })
    }

    @Test
    fun `doesNotContain with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotContain("d")
        })
    }

    @Test
    fun `doesNotContain with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContain("J")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContain("J")))
    }

    @Test
    fun `doesNotContainIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainIgnoringCase("a")
        })
    }

    @Test
    fun `doesNotContainIgnoringCase with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotContainIgnoringCase("d")
        })
    }

    @Test
    fun `doesNotContainIgnoringCase with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainIgnoringCase("j")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContain("j")))
    }

    @Test
    fun `doesNotContainAll vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAll("a", "b", "c")
        })
    }

    @Test
    fun `doesNotContainAll vararg with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotContainAll("a", "b", "c", "d")
        })
    }

    @Test
    fun `doesNotContainAll vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAll("J", "o", "h", "n")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAll(setOf("J", "o", "h", "n"))))
    }

    @Test
    fun `doesNotContainAll iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAll(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `doesNotContainAll iterable with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotContainAll(listOf("a", "b", "c", "d"))
        })
    }

    @Test
    fun `doesNotContainAll iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAll(listOf("J", "o", "h", "n"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAll(listOf("J", "o", "h", "n"))))
    }

    @Test
    fun `doesNotContainAllIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAllIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `doesNotContainAllIgnoringCase vararg with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).doesNotContainAllIgnoringCase("a", "b", "c", "d")
        })
    }

    @Test
    fun `doesNotContainAllIgnoringCase vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAllIgnoringCase("J", "O", "H", "N")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAll(setOf("J", "O", "H", "N"))))
    }

    @Test
    fun `doesNotContainAllIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAllIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `doesNotContainAllIgnoringCase iterable with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).doesNotContainAllIgnoringCase(listOf("a", "b", "c", "d"))
        })
    }

    @Test
    fun `doesNotContainAllIgnoringCase iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAllIgnoringCase(listOf("J", "O", "H", "N"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAll(listOf("J", "O", "H", "N"))))
    }

    @Test
    fun `doesNotContainAny vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAny("a", "b", "c")
        })
    }

    @Test
    fun `doesNotContainAny vararg with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotContainAny("e", "f")
        })
    }

    @Test
    fun `doesNotContainAny vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAny("J", "w", "x", "e")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAny(setOf("J", "w", "x", "e"))))
    }

    @Test
    fun `doesNotContainAny iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAny(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `doesNotContainAny iterable with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotContainAny(listOf("e", "f"))
        })
    }

    @Test
    fun `doesNotContainAny iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAny(listOf("J", "w", "x", "e"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAny(listOf("J", "w", "x", "e"))))
    }

    @Test
    fun `doesNotContainAnyIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAnyIgnoringCase("a", "b", "c")
        })
    }

    @Test
    fun `doesNotContainAnyIgnoringCase vararg with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).doesNotContainAnyIgnoringCase("e", "f")
        })
    }

    @Test
    fun `doesNotContainAnyIgnoringCase vararg with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAnyIgnoringCase("j", "w", "x", "e")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAny(setOf("j", "w", "x", "e"))))
    }

    @Test
    fun `doesNotContainAnyIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContainAnyIgnoringCase(listOf("a", "b", "c"))
        })
    }

    @Test
    fun `doesNotContainAnyIgnoringCase iterable with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).doesNotContainAnyIgnoringCase(listOf("e", "f"))
        })
    }

    @Test
    fun `doesNotContainAnyIgnoringCase iterable with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotContainAnyIgnoringCase(listOf("j", "w", "x", "e"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotContainAny(listOf("j", "w", "x", "e"))))
    }

    @Test
    fun `matches with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).matches(Regex("^[0-9]*\$"))
        })
    }

    @Test
    fun `matches with valid value should be valid`() {
        validate(Employee(name = "0123456789"), {
            validate(Employee::name).matches(Regex("^[0-9]*\$"))
        })
    }

    @Test
    fun `matches with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).matches(Regex("^[0-9]*\$"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = Matches(Regex("^[0-9]*\$"))))
    }

    @Test
    fun `doesNotMatch with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotMatch(Regex("^[0-9]*\$"))
        })
    }

    @Test
    fun `doesNotMatch with valid value should be valid`() {
        validate(Employee(name = "test"), {
            validate(Employee::name).doesNotMatch(Regex("^[0-9]*\$"))
        })
    }

    @Test
    fun `doesNotMatch with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "0123456789"), {
                validate(Employee::name).doesNotMatch(Regex("^[0-9]*\$"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "0123456789", constraint = NotMatch(Regex("^[0-9]*\$"))))
    }

    @Test
    fun `containsRegex with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).contains(Regex("a([bc]+)d?"))
        })
    }

    @Test
    fun `containsRegex with valid value should be valid`() {
        validate(Employee(name = "xabcdy"), {
            validate(Employee::name).contains(Regex("a([bc]+)d?"))
        })
    }

    @Test
    fun `containsRegex with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).contains(Regex("a([bc]+)d?"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = ContainsRegex(Regex("a([bc]+)d?"))))
    }

    @Test
    fun `doesNotContainRegex with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotContain(Regex("a([bc]+)d?"))
        })
    }

    @Test
    fun `doesNotContainRegex with valid value should be valid`() {
        validate(Employee(name = "xyz"), {
            validate(Employee::name).doesNotContain(Regex("a([bc]+)d?"))
        })
    }

    @Test
    fun `doesNotContainRegex with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "xJohny"), {
                validate(Employee::name).doesNotContain(Regex("J([oh]+)n?"))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "xJohny", constraint = NotContainRegex(Regex("J([oh]+)n?"))))
    }

    @Test
    fun `startsWith with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).startsWith("a")
        })
    }

    @Test
    fun `startsWith with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).startsWith("a")
        })
    }

    @Test
    fun `startsWith with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).startsWith("j")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = StartsWith("j")))
    }

    @Test
    fun `startsWithIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).startsWithIgnoringCase("a")
        })
    }

    @Test
    fun `startsWithIgnoringCase with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).startsWithIgnoringCase("a")
        })
    }

    @Test
    fun `startsWithIgnoringCase with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).startsWithIgnoringCase("g")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = StartsWith("g")))
    }

    @Test
    fun `doesNotStartWith with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotStartWith("a")
        })
    }

    @Test
    fun `doesNotStartWith with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotStartWith("d")
        })
    }

    @Test
    fun `doesNotStartWith with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotStartWith("J")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotStartWith("J")))
    }

    @Test
    fun `doesNotStartWithIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotStartWithIgnoringCase("a")
        })
    }

    @Test
    fun `doesNotStartWithIgnoringCase with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotStartWithIgnoringCase("d")
        })
    }

    @Test
    fun `doesNotStartWithIgnoringCase with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotStartWithIgnoringCase("j")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotStartWith("j")))
    }

    @Test
    fun `endsWith with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).endsWith("a")
        })
    }

    @Test
    fun `endsWith with valid value should be valid`() {
        validate(Employee(name = "cba"), {
            validate(Employee::name).endsWith("a")
        })
    }

    @Test
    fun `endsWith with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).endsWith("N")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = EndsWith("N")))
    }

    @Test
    fun `endsWithIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).endsWithIgnoringCase("a")
        })
    }

    @Test
    fun `endsWithIgnoringCase with valid value should be valid`() {
        validate(Employee(name = "CBA"), {
            validate(Employee::name).endsWithIgnoringCase("a")
        })
    }

    @Test
    fun `endsWithIgnoringCase with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).endsWithIgnoringCase("o")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = EndsWith("o")))
    }

    @Test
    fun `doesNotEndWith with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotEndWith("a")
        })
    }

    @Test
    fun `doesNotEndWith with valid value should be valid`() {
        validate(Employee(name = "abc"), {
            validate(Employee::name).doesNotEndWith("d")
        })
    }

    @Test
    fun `doesNotEndWith with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotEndWith("n")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotEndWith("n")))
    }

    @Test
    fun `doesNotEndWithIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::name).doesNotEndWithIgnoringCase("a")
        })
    }

    @Test
    fun `doesNotEndWithIgnoringCase with valid value should be valid`() {
        validate(Employee(name = "ABC"), {
            validate(Employee::name).doesNotEndWithIgnoringCase("d")
        })
    }

    @Test
    fun `doesNotEndWithIgnoringCase with invalid value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(name = "John"), {
                validate(Employee::name).doesNotEndWithIgnoringCase("N")
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "name", value = "John", constraint = NotEndWith("N")))
    }

    @Test
    fun `isEmail with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::email).isEmail()
        })
    }

    @Test
    fun `isEmail with valid value should be valid`() {
        setOf("test.test@test.com",
                "test_test@test.com",
                "test-test@test.com",
                "test@test.com",
                "test@test.test").forEach {
            validate(Employee(email = it), {
                validate(Employee::email).isEmail()
            })
        }
    }

    @Test
    fun `isEmail with blank value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(email = "test.test"), {
                validate(Employee::email).isEmail()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "email", value = "test.test", constraint = Email))
    }
}