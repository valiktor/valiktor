package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.ArrayFunctionsFixture.Dependent
import org.valiktor.functions.ArrayFunctionsFixture.Employee
import org.valiktor.validate

private object ArrayFunctionsFixture {

    data class Employee(val dependents: Array<Dependent>? = null)
    data class Dependent(val id: Int? = null, val name: String? = null)
}

class ArrayFunctionsTest {

    @Test
    fun `inner null array properties should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).validateForEach {
                validate(Dependent::id).isNotNull()
            }
        })
    }

    @Test
    fun `inner array properties should be valid`() {
        validate(Employee(dependents = arrayOf(
                Dependent(id = 1),
                Dependent(id = 1),
                Dependent(id = 1))), {
            validate(Employee::dependents).validateForEach {
                validate(Dependent::id).isNotNull()
            }
        })
    }

    @Test
    fun `inner array properties should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = arrayOf(
                    Dependent(),
                    Dependent(),
                    Dependent())), {
                validate(Employee::dependents).validateForEach {
                    validate(Dependent::id).isNotNull()
                }
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "dependents[0].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "dependents[1].id", constraint = NotNull()),
                DefaultConstraintViolation(property = "dependents[2].id", constraint = NotNull()))
    }

    @Test
    fun `isEmpty with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).isEmpty()
        })
    }

    @Test
    fun `isEmpty with empty property should be valid`() {
        validate(Employee(dependents = emptyArray()), {
            validate(Employee::dependents).isEmpty()
        })
    }

    @Test
    fun `isEmpty with not empty property should be invalid`() {
        val dependents = arrayOf(Dependent())

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).isEmpty()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = Empty()))
    }

    @Test
    fun `isNotEmpty with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).isNotEmpty()
        })
    }

    @Test
    fun `isNotEmpty with not empty property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent())), {
            validate(Employee::dependents).isNotEmpty()
        })
    }

    @Test
    fun `isNotEmpty with empty property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).isNotEmpty()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = NotEmpty()))
    }

    @Test
    fun `size with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).hasSize(min = 1, max = 10)
        })
    }

    @Test
    fun `size with valid min length property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent())), {
            validate(Employee::dependents).hasSize(min = 1)
        })
    }

    @Test
    fun `size with valid max length property should be valid`() {
        validate(Employee(dependents = emptyArray()), {
            validate(Employee::dependents).hasSize(max = 4)
        })
    }

    @Test
    fun `size with valid min and max length property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent())), {
            validate(Employee::dependents).hasSize(min = 1, max = 2)
        })
    }

    @Test
    fun `size without min and max should be valid`() {
        validate(Employee(dependents = emptyArray()), {
            validate(Employee::dependents).hasSize()
        })
    }

    @Test
    fun `size with invalid min size property should be invalid`() {
        val dependents = arrayOf(Dependent(), Dependent())

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).hasSize(min = 5)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = Size(min = 5)))
    }

    @Test
    fun `size with invalid max size property should be invalid`() {
        val dependents = arrayOf(Dependent(), Dependent())

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).hasSize(max = 1)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = Size(max = 1)))
    }

    @Test
    fun `size with invalid min and max size property should be invalid`() {
        val dependents = arrayOf(Dependent(), Dependent())

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).hasSize(min = 3, max = 1)
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = Size(min = 3, max = 1)))
    }

    @Test
    fun `contains with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).contains(Dependent())
        })
    }

    @Test
    fun `contains with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2))), {
            validate(Employee::dependents).contains(Dependent(id = 1))
        })
    }

    @Test
    fun `contains with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).contains(Dependent(id = 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = Contains(Dependent(id = 1))))
    }

    @Test
    fun `containsAll vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).containsAll(Dependent())
        })
    }

    @Test
    fun `containsAll vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))), {
            validate(Employee::dependents).containsAll(Dependent(id = 1), Dependent(id = 2))
        })
    }

    @Test
    fun `containsAll vararg with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).containsAll(Dependent(id = 1), Dependent(id = 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = ContainsAll(setOf(Dependent(id = 1), Dependent(id = 2)))))
    }

    @Test
    fun `containsAll iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).containsAll(listOf(Dependent()))
        })
    }

    @Test
    fun `containsAll iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))), {
            validate(Employee::dependents).containsAll(listOf(Dependent(id = 1), Dependent(id = 2)))
        })
    }

    @Test
    fun `containsAll iterable with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1))

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).containsAll(listOf(Dependent(id = 1), Dependent(id = 2)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = ContainsAll(listOf(Dependent(id = 1), Dependent(id = 2)))))
    }

    @Test
    fun `containsAny vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).containsAny(Dependent())
        })
    }

    @Test
    fun `containsAny vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 3))), {
            validate(Employee::dependents).containsAny(Dependent(id = 1), Dependent(id = 2))
        })
    }

    @Test
    fun `containsAny vararg with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).containsAny(Dependent(id = 1), Dependent(id = 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = ContainsAny(setOf(Dependent(id = 1), Dependent(id = 2)))))
    }

    @Test
    fun `containsAny iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).containsAny(listOf(Dependent()))
        })
    }

    @Test
    fun `containsAny iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 3))), {
            validate(Employee::dependents).containsAny(listOf(Dependent(id = 1), Dependent(id = 2)))
        })
    }

    @Test
    fun `containsAny iterable with invalid property should be invalid`() {
        val dependents = emptyArray<Dependent>()

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).containsAny(listOf(Dependent(id = 1), Dependent(id = 2)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = ContainsAny(listOf(Dependent(id = 1), Dependent(id = 2)))))
    }

    @Test
    fun `doesNotContain with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).doesNotContain(Dependent())
        })
    }

    @Test
    fun `doesNotContain with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2))), {
            validate(Employee::dependents).doesNotContain(Dependent(id = 3))
        })
    }

    @Test
    fun `doesNotContain with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1))

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).doesNotContain(Dependent(id = 1))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = NotContain(Dependent(id = 1))))
    }

    @Test
    fun `doesNotContainAll vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).doesNotContainAll(Dependent())
        })
    }

    @Test
    fun `doesNotContainAll vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))), {
            validate(Employee::dependents).doesNotContainAll(Dependent(id = 1), Dependent(id = 5))
        })
    }

    @Test
    fun `doesNotContainAll vararg with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).doesNotContainAll(Dependent(id = 1), Dependent(id = 2))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = NotContainAll(setOf(Dependent(id = 1), Dependent(id = 2)))))
    }

    @Test
    fun `doesNotContainAll iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).doesNotContainAll(listOf(Dependent()))
        })
    }

    @Test
    fun `doesNotContainAll iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))), {
            validate(Employee::dependents).doesNotContainAll(listOf(Dependent(id = 1), Dependent(id = 5)))
        })
    }

    @Test
    fun `doesNotContainAll iterable with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).doesNotContainAll(listOf(Dependent(id = 1), Dependent(id = 2)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = NotContainAll(listOf(Dependent(id = 1), Dependent(id = 2)))))
    }

    @Test
    fun `doesNotContainAny vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).doesNotContainAny(Dependent())
        })
    }

    @Test
    fun `doesNotContainAny vararg with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))), {
            validate(Employee::dependents).doesNotContainAny(Dependent(id = 4), Dependent(id = 5))
        })
    }

    @Test
    fun `doesNotContainAny vararg with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).doesNotContainAny(Dependent(id = 1), Dependent(id = 5))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = NotContainAny(setOf(Dependent(id = 1), Dependent(id = 5)))))
    }

    @Test
    fun `doesNotContainAny iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::dependents).doesNotContainAny(listOf(Dependent()))
        })
    }

    @Test
    fun `doesNotContainAny iterable with valid property should be valid`() {
        validate(Employee(dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))), {
            validate(Employee::dependents).doesNotContainAny(listOf(Dependent(id = 4), Dependent(id = 5)))
        })
    }

    @Test
    fun `doesNotContainAny iterable with invalid property should be invalid`() {
        val dependents = arrayOf(Dependent(id = 1), Dependent(id = 2), Dependent(id = 3))

        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(dependents = dependents), {
                validate(Employee::dependents).doesNotContainAny(listOf(Dependent(id = 1), Dependent(id = 5)))
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "dependents",
                        value = dependents,
                        constraint = NotContainAny(listOf(Dependent(id = 1), Dependent(id = 5)))))
    }
}