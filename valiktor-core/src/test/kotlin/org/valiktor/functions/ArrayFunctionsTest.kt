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
}