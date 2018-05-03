package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.*
import org.valiktor.functions.CharFunctionsFixture.Employee
import org.valiktor.validate

private object CharFunctionsFixture {

    data class Employee(val gender: Char? = null)
}

class CharFunctionsTest {

    @Test
    fun `isNull with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNull()
        })
    }

    @Test
    fun `isNull with not null property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = Null()))
    }

    @Test
    fun `isNotNull with not null property should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotNull()
        })
    }

    @Test
    fun `isNotNull with null property should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(), {
                validate(Employee::gender).isNotNull()
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", constraint = NotNull()))
    }

    @Test
    fun `isEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isEqualTo('M')
        })
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isEqualTo('M')
        })
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isEqualTo('F')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = Equals('F')))
    }

    @Test
    fun `isEqualTo with different case value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isEqualTo('M')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'm', constraint = Equals('M')))
    }

    @Test
    fun `isNotEqualTo with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotEqualTo('M')
        })
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotEqualTo('F')
        })
    }

    @Test
    fun `isNotEqualTo with different case value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isNotEqualTo('M')
        })
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotEqualTo('M')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotEquals('M')))
    }

    @Test
    fun `isIn vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isIn('M', 'F')
        })
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isIn('M', 'F')
        })
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isIn('F')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = In(setOf('F'))))
    }

    @Test
    fun `isIn vararg with different case value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isIn('M', 'F')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'm', constraint = In(setOf('M', 'F'))))
    }

    @Test
    fun `isIn iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isIn(listOf('M', 'F'))
        })
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isIn(listOf('M', 'F'))
        })
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isIn(listOf('F'))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = In(listOf('F'))))
    }

    @Test
    fun `isIn iterable with different case value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isIn(listOf('M', 'F'))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'm', constraint = In(listOf('M', 'F'))))
    }

    @Test
    fun `isNotIn vararg with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotIn('M', 'F')
        })
    }

    @Test
    fun `isNotIn vararg with different value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotIn('F')
        })
    }

    @Test
    fun `isNotIn vararg with different case value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isNotIn('M', 'F')
        })
    }

    @Test
    fun `isNotIn vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotIn('M', 'F')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotIn(setOf('M', 'F'))))
    }

    @Test
    fun `isNotIn iterable with null property should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotIn(listOf('M', 'F'))
        })
    }

    @Test
    fun `isNotIn iterable with different value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotIn(listOf('F'))
        })
    }

    @Test
    fun `isNotIn iterable with different case value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isNotIn(listOf('M', 'F'))
        })
    }

    @Test
    fun `isNotIn iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotIn(listOf('M', 'F'))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotIn(listOf('M', 'F'))))
    }

    @Test
    fun `isWhitespace with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isWhitespace()
        })
    }

    @Test
    fun `isWhitespace with whitespace should be valid`() {
        validate(Employee(gender = ' '), {
            validate(Employee::gender).isWhitespace()
        })
    }

    @Test
    fun `isWhitespace with another character should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isWhitespace()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = Blank()))
    }

    @Test
    fun `isNotWhitespace with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotWhitespace()
        })
    }

    @Test
    fun `isNotWhitespace with another character should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotWhitespace()
        })
    }

    @Test
    fun `isNotWhitespace with whitespace should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = ' '), {
                validate(Employee::gender).isNotWhitespace()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = ' ', constraint = NotBlank()))
    }

    @Test
    fun `isLetter with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isLetter()
        })
    }

    @Test
    fun `isLetter with letter should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isLetter()
        })
    }

    @Test
    fun `isLetter with digit should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = '9'), {
                validate(Employee::gender).isLetter()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = '9', constraint = Letter()))
    }

    @Test
    fun `isNotLetter with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotLetter()
        })
    }

    @Test
    fun `isNotLetter with digit should be valid`() {
        validate(Employee(gender = '9'), {
            validate(Employee::gender).isNotLetter()
        })
    }

    @Test
    fun `isNotLetter with letter should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotLetter()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotLetter()))
    }

    @Test
    fun `isDigit with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isDigit()
        })
    }

    @Test
    fun `isDigit with digit should be valid`() {
        validate(Employee(gender = '9'), {
            validate(Employee::gender).isDigit()
        })
    }

    @Test
    fun `isDigit with letter should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isDigit()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = Digit()))
    }

    @Test
    fun `isNotDigit with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotDigit()
        })
    }

    @Test
    fun `isNotDigit with letter should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotDigit()
        })
    }

    @Test
    fun `isNotDigit with digit should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = '9'), {
                validate(Employee::gender).isNotDigit()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = '9', constraint = NotDigit()))
    }

    @Test
    fun `isLetterOrDigit with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isLetterOrDigit()
        })
    }

    @Test
    fun `isLetterOrDigit with letter should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isLetterOrDigit()
        })
    }

    @Test
    fun `isLetterOrDigit with digit should be valid`() {
        validate(Employee(gender = '9'), {
            validate(Employee::gender).isLetterOrDigit()
        })
    }

    @Test
    fun `isLetterOrDigit with special character should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = '~'), {
                validate(Employee::gender).isLetterOrDigit()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = '~', constraint = LetterOrDigit()))
    }

    @Test
    fun `isNotLetterOrDigit with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotLetterOrDigit()
        })
    }

    @Test
    fun `isNotLetterOrDigit with special character should be valid`() {
        validate(Employee(gender = '^'), {
            validate(Employee::gender).isNotLetterOrDigit()
        })
    }

    @Test
    fun `isNotLetterOrDigit with letter should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotLetterOrDigit()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotLetterOrDigit()))
    }

    @Test
    fun `isNotLetterOrDigit with digit should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = '9'), {
                validate(Employee::gender).isNotLetterOrDigit()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = '9', constraint = NotLetterOrDigit()))
    }

    @Test
    fun `isUpperCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isUpperCase()
        })
    }

    @Test
    fun `isUpperCase with upper case character should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isUpperCase()
        })
    }

    @Test
    fun `isUpperCase with lower case character should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isUpperCase()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'm', constraint = UpperCase()))
    }

    @Test
    fun `isLowerCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isLowerCase()
        })
    }

    @Test
    fun `isLowerCase with upper case character should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isLowerCase()
        })
    }

    @Test
    fun `isLowerCase with lower case character should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isLowerCase()
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = LowerCase()))
    }

    @Test
    fun `isEqualToIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isEqualToIgnoringCase('M')
        })
    }

    @Test
    fun `isEqualToIgnoringCase with same value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isEqualToIgnoringCase('m')
        })
    }

    @Test
    fun `isEqualToIgnoringCase with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isEqualToIgnoringCase('F')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = Equals('F')))
    }

    @Test
    fun `isNotEqualToIgnoringCase with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotEqualToIgnoringCase('M')
        })
    }

    @Test
    fun `isNotEqualToIgnoringCase with different value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotEqualToIgnoringCase('F')
        })
    }

    @Test
    fun `isNotEqualToIgnoringCase with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotEqualToIgnoringCase('m')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotEquals('m')))
    }

    @Test
    fun `isInIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isInIgnoringCase('M', 'F')
        })
    }

    @Test
    fun `isInIgnoringCase vararg with same value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isInIgnoringCase('M', 'F')
        })
    }

    @Test
    fun `isInIgnoringCase vararg with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isInIgnoringCase('F')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = In(setOf('F'))))
    }

    @Test
    fun `isInIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isInIgnoringCase(listOf('M', 'F'))
        })
    }

    @Test
    fun `isInIgnoringCase iterable with same value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isInIgnoringCase(listOf('M', 'F'))
        })
    }

    @Test
    fun `isInIgnoringCase iterable with different value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isInIgnoringCase(listOf('F'))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = In(listOf('F'))))
    }

    @Test
    fun `isNotInIgnoringCase vararg with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotInIgnoringCase('M', 'F')
        })
    }

    @Test
    fun `isNotInIgnoringCase vararg with different value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotInIgnoringCase('F')
        })
    }

    @Test
    fun `isNotInIgnoringCase vararg with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotInIgnoringCase('m', 'f')
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotIn(setOf('m', 'f'))))
    }

    @Test
    fun `isNotInIgnoringCase iterable with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotInIgnoringCase(listOf('M', 'F'))
        })
    }

    @Test
    fun `isNotInIgnoringCase iterable with different value should be valid`() {
        validate(Employee(gender = 'M'), {
            validate(Employee::gender).isNotInIgnoringCase(listOf('F'))
        })
    }

    @Test
    fun `isNotInIgnoringCase iterable with same value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'M'), {
                validate(Employee::gender).isNotInIgnoringCase(listOf('m', 'f'))
            })
        }
        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(property = "gender", value = 'M', constraint = NotIn(listOf('m', 'f'))))
    }

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isLessThan('a')
        })
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isLessThan('n')
        })
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isLessThan('k')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'm',
                        constraint = Less('k')))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isLessThan('m')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'm',
                        constraint = Less('m')))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isLessThanOrEqualTo('a')
        })
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isLessThanOrEqualTo('n')
        })
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isLessThanOrEqualTo('m')
        })
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isLessThanOrEqualTo('l')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'm',
                        constraint = LessOrEqual('l')))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isGreaterThan('a')
        })
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isGreaterThan('l')
        })
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isGreaterThan('n')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'm',
                        constraint = Greater('n')))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isGreaterThan('m')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'm',
                        constraint = Greater('m')))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isGreaterThanOrEqualTo('a')
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isGreaterThanOrEqualTo('l')
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(gender = 'm'), {
            validate(Employee::gender).isGreaterThanOrEqualTo('m')
        })
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'm'), {
                validate(Employee::gender).isGreaterThanOrEqualTo('n')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'm',
                        constraint = GreaterOrEqual('n')))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isBetween(start = '1', end = '9')
        })
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(gender = '0'), {
            validate(Employee::gender).isBetween(start = '0', end = '1')
        })
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(gender = '1'), {
            validate(Employee::gender).isBetween(start = '0', end = '1')
        })
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(gender = '5'), {
            validate(Employee::gender).isBetween(start = '0', end = '6')
        })
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'a'), {
                validate(Employee::gender).isBetween(start = 'b', end = 'd')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'a',
                        constraint = Between(start = 'b', end = 'd')))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'e'), {
                validate(Employee::gender).isBetween(start = 'b', end = 'd')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'e',
                        constraint = Between(start = 'b', end = 'd')))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee(), {
            validate(Employee::gender).isNotBetween(start = '1', end = '9')
        })
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(gender = '1'), {
            validate(Employee::gender).isNotBetween(start = '2', end = '3')
        })
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(gender = '4'), {
            validate(Employee::gender).isNotBetween(start = '2', end = '3')
        })
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'a'), {
                validate(Employee::gender).isNotBetween(start = 'a', end = 'c')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'a',
                        constraint = NotBetween(start = 'a', end = 'c')))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'c'), {
                validate(Employee::gender).isNotBetween(start = 'a', end = 'c')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'c',
                        constraint = NotBetween(start = 'a', end = 'c')))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertThrows<ConstraintViolationException> {
            validate(Employee(gender = 'b'), {
                validate(Employee::gender).isNotBetween(start = 'a', end = 'c')
            })
        }

        assertThat(exception.constraintViolations).containsExactly(
                DefaultConstraintViolation(
                        property = "gender",
                        value = 'b',
                        constraint = NotBetween(start = 'a', end = 'c')))
    }
}