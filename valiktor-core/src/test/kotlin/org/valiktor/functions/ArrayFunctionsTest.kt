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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(property = "dependents[0].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[1].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[2].id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(property = "dependents[0].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[1].id", constraint = NotNull(), message = "Must not be null"),
                        DefaultI18nConstraintViolation(property = "dependents[2].id", constraint = NotNull(), message = "Must not be null"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(property = "dependents[0].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "dependents[1].id", constraint = NotNull(), message = "Não deve ser nulo"),
                        DefaultI18nConstraintViolation(property = "dependents[2].id", constraint = NotNull(), message = "Não deve ser nulo"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Empty(),
                                message = "Must be empty"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Empty(),
                                message = "Must be empty"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Empty(),
                                message = "Deve ser vazio"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotEmpty(),
                                message = "Must not be empty"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotEmpty(),
                                message = "Must not be empty"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotEmpty(),
                                message = "Não deve ser vazio"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(min = 5),
                                message = "Size must be greater than or equal to 5"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(min = 5),
                                message = "Size must be greater than or equal to 5"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(min = 5),
                                message = "O tamanho deve ser maior ou igual a 5"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(max = 1),
                                message = "Size must be less than or equal to 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(max = 1),
                                message = "Size must be less than or equal to 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(max = 1),
                                message = "O tamanho deve ser menor ou igual a 1"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(min = 3, max = 1),
                                message = "Size must be between 3 and 1"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(min = 3, max = 1),
                                message = "Size must be between 3 and 1"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Size(min = 3, max = 1),
                                message = "O tamanho deve estar entre 3 e 1"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Contains(Dependent(id = 1)),
                                message = "Must contain ${Dependent(id = 1)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Contains(Dependent(id = 1)),
                                message = "Must contain ${Dependent(id = 1)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = Contains(Dependent(id = 1)),
                                message = "Deve conter ${Dependent(id = 1)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAll(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAll(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAll(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Deve conter ${Dependent(id = 1)}, ${Dependent(id = 2)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAll(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAll(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAll(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Deve conter ${Dependent(id = 1)}, ${Dependent(id = 2)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAny(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAny(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAny(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Deve conter ${Dependent(id = 1)}, ${Dependent(id = 2)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAny(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAny(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = ContainsAny(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Deve conter ${Dependent(id = 1)}, ${Dependent(id = 2)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContain(Dependent(id = 1)),
                                message = "Must not contain ${Dependent(id = 1)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContain(Dependent(id = 1)),
                                message = "Must not contain ${Dependent(id = 1)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContain(Dependent(id = 1)),
                                message = "Não deve conter ${Dependent(id = 1)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAll(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAll(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAll(setOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Não deve conter ${Dependent(id = 1)}, ${Dependent(id = 2)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAll(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAll(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 2)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAll(listOf(Dependent(id = 1), Dependent(id = 2))),
                                message = "Não deve conter ${Dependent(id = 1)}, ${Dependent(id = 2)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAny(setOf(Dependent(id = 1), Dependent(id = 5))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 5)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAny(setOf(Dependent(id = 1), Dependent(id = 5))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 5)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAny(setOf(Dependent(id = 1), Dependent(id = 5))),
                                message = "Não deve conter ${Dependent(id = 1)}, ${Dependent(id = 5)}"))))
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

        val i18nMap: Map<Locale, Set<I18nConstraintViolation>> = SUPPORTED_LOCALES
                .map { it to exception.constraintViolations.mapToI18n(it) }.toMap()

        assertThat(i18nMap).containsExactly(
                entry(Locales.DEFAULT, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAny(listOf(Dependent(id = 1), Dependent(id = 5))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 5)}"))),
                entry(Locales.EN, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAny(listOf(Dependent(id = 1), Dependent(id = 5))),
                                message = "Must not contain ${Dependent(id = 1)}, ${Dependent(id = 5)}"))),
                entry(Locales.PT_BR, setOf(
                        DefaultI18nConstraintViolation(
                                property = "dependents",
                                value = dependents,
                                constraint = NotContainAny(listOf(Dependent(id = 1), Dependent(id = 5))),
                                message = "Não deve conter ${Dependent(id = 1)}, ${Dependent(id = 5)}"))))
    }
}