/*
 * Copyright 2018-2019 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.valiktor.functions

import org.assertj.core.api.Assertions.assertThat
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.Between
import org.valiktor.constraints.Greater
import org.valiktor.constraints.GreaterOrEqual
import org.valiktor.constraints.Less
import org.valiktor.constraints.LessOrEqual
import org.valiktor.constraints.NotBetween
import org.valiktor.functions.ComparableFunctionsFixture.Company
import org.valiktor.functions.ComparableFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertFailsWith

private object ComparableFunctionsFixture {

    data class Company(val id: Int) : Comparable<Company> {
        override fun compareTo(other: Company): Int = this.id.compareTo(other.id)
    }

    data class Employee(val company: Company? = null)
}

class ComparableFunctionsTest {

    @Test
    fun `isLessThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::company).isLessThan(Company(id = 10))
        }
    }

    @Test
    fun `isLessThan with less value should be valid`() {
        validate(Employee(company = Company(id = 9999))) {
            validate(Employee::company).isLessThan(Company(id = 100000))
        }
    }

    @Test
    fun `isLessThan with negative less value should be valid`() {
        validate(Employee(company = Company(id = -4))) {
            validate(Employee::company).isLessThan(Company(id = -3))
        }
    }

    @Test
    fun `isLessThan with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 50))) {
                validate(Employee::company).isLessThan(Company(id = 49))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 50),
                constraint = Less(Company(id = 49))))
    }

    @Test
    fun `isLessThan with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -50))) {
                validate(Employee::company).isLessThan(Company(id = -51))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -50),
                constraint = Less(Company(id = -51))))
    }

    @Test
    fun `isLessThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 0))) {
                validate(Employee::company).isLessThan(Company(id = 0))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 0),
                constraint = Less(Company(id = 0))))
    }

    @Test
    fun `isLessThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::company).isLessThanOrEqualTo(Company(id = 10))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with less value should be valid`() {
        validate(Employee(company = Company(id = 9999))) {
            validate(Employee::company).isLessThanOrEqualTo(Company(id = 100000))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with negative less value should be valid`() {
        validate(Employee(company = Company(id = -4))) {
            validate(Employee::company).isLessThanOrEqualTo(Company(id = -3))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with equal value should be valid`() {
        validate(Employee(company = Company(id = 0))) {
            validate(Employee::company).isLessThanOrEqualTo(Company(id = 0))
        }
    }

    @Test
    fun `isLessThanOrEqualTo with greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 56789))) {
                validate(Employee::company).isLessThanOrEqualTo(Company(id = 57))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 56789),
                constraint = LessOrEqual(Company(id = 57))))
    }

    @Test
    fun `isLessThanOrEqualTo with negative greater value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -96))) {
                validate(Employee::company).isLessThanOrEqualTo(Company(id = -97))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -96),
                constraint = LessOrEqual(Company(id = -97))))
    }

    @Test
    fun `isGreaterThan with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::company).isGreaterThan(Company(id = 10))
        }
    }

    @Test
    fun `isGreaterThan with greater value should be valid`() {
        validate(Employee(company = Company(id = 11))) {
            validate(Employee::company).isGreaterThan(Company(id = 10))
        }
    }

    @Test
    fun `isGreaterThan with negative greater value should be valid`() {
        validate(Employee(company = Company(id = -88))) {
            validate(Employee::company).isGreaterThan(Company(id = -89))
        }
    }

    @Test
    fun `isGreaterThan with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 10))) {
                validate(Employee::company).isGreaterThan(Company(id = 11))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 10),
                constraint = Greater(Company(id = 11))))
    }

    @Test
    fun `isGreaterThan with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -189))) {
                validate(Employee::company).isGreaterThan(Company(id = -180))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -189),
                constraint = Greater(Company(id = -180))))
    }

    @Test
    fun `isGreaterThan with equal value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 0))) {
                validate(Employee::company).isGreaterThan(Company(id = 0))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 0),
                constraint = Greater(Company(id = 0))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::company).isGreaterThanOrEqualTo(Company(id = 10))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with greater value should be valid`() {
        validate(Employee(company = Company(id = 10000))) {
            validate(Employee::company).isGreaterThanOrEqualTo(Company(id = 9999))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative greater value should be valid`() {
        validate(Employee(company = Company(id = -3))) {
            validate(Employee::company).isGreaterThanOrEqualTo(Company(id = -4))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with equal value should be valid`() {
        validate(Employee(company = Company(id = 0))) {
            validate(Employee::company).isGreaterThanOrEqualTo(Company(id = 0))
        }
    }

    @Test
    fun `isGreaterThanOrEqualTo with less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 57))) {
                validate(Employee::company).isGreaterThanOrEqualTo(Company(id = 56789))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 57),
                constraint = GreaterOrEqual(Company(id = 56789))))
    }

    @Test
    fun `isGreaterThanOrEqualTo with negative less value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -97))) {
                validate(Employee::company).isGreaterThanOrEqualTo(Company(id = -96))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -97),
                constraint = GreaterOrEqual(Company(id = -96))))
    }

    @Test
    fun `isBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::company).isBetween(start = Company(id = 1), end = Company(id = 9))
        }
    }

    @Test
    fun `isBetween with equal start value should be valid`() {
        validate(Employee(company = Company(id = 0))) {
            validate(Employee::company).isBetween(start = Company(id = 0), end = Company(id = 1))
        }
    }

    @Test
    fun `isBetween with equal end value should be valid`() {
        validate(Employee(company = Company(id = 1))) {
            validate(Employee::company).isBetween(start = Company(id = 0), end = Company(id = 1))
        }
    }

    @Test
    fun `isBetween with within value should be valid`() {
        validate(Employee(company = Company(id = 5))) {
            validate(Employee::company).isBetween(start = Company(id = 0), end = Company(id = 10))
        }
    }

    @Test
    fun `isBetween with equal negative start value should be valid`() {
        validate(Employee(company = Company(id = -2))) {
            validate(Employee::company).isBetween(start = Company(id = -2), end = Company(id = -1))
        }
    }

    @Test
    fun `isBetween with equal negative end value should be valid`() {
        validate(Employee(company = Company(id = -1))) {
            validate(Employee::company).isBetween(start = Company(id = -2), end = Company(id = -1))
        }
    }

    @Test
    fun `isBetween with within negative value should be valid`() {
        validate(Employee(company = Company(id = -15))) {
            validate(Employee::company).isBetween(start = Company(id = -20), end = Company(id = -10))
        }
    }

    @Test
    fun `isBetween with less start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 10))) {
                validate(Employee::company).isBetween(start = Company(id = 11), end = Company(id = 12))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 10),
                constraint = Between(start = Company(id = 11), end = Company(id = 12))))
    }

    @Test
    fun `isBetween with greater end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 12))) {
                validate(Employee::company).isBetween(start = Company(id = 10), end = Company(id = 11))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 12),
                constraint = Between(start = Company(id = 10), end = Company(id = 11))))
    }

    @Test
    fun `isBetween with less negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -10))) {
                validate(Employee::company).isBetween(start = Company(id = -9), end = Company(id = -8))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -10),
                constraint = Between(start = Company(id = -9), end = Company(id = -8))))
    }

    @Test
    fun `isBetween with greater negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -12))) {
                validate(Employee::company).isBetween(start = Company(id = -14), end = Company(id = -13))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -12),
                constraint = Between(start = Company(id = -14), end = Company(id = -13))))
    }

    @Test
    fun `isNotBetween with null value should be valid`() {
        validate(Employee()) {
            validate(Employee::company).isNotBetween(start = Company(id = 1), end = Company(id = 9))
        }
    }

    @Test
    fun `isNotBetween with less start value should be valid`() {
        validate(Employee(company = Company(id = 10))) {
            validate(Employee::company).isNotBetween(start = Company(id = 11), end = Company(id = 12))
        }
    }

    @Test
    fun `isNotBetween with greater end value should be valid`() {
        validate(Employee(company = Company(id = 12))) {
            validate(Employee::company).isNotBetween(start = Company(id = 10), end = Company(id = 11))
        }
    }

    @Test
    fun `isNotBetween with less negative start value should be valid`() {
        validate(Employee(company = Company(id = -10))) {
            validate(Employee::company).isNotBetween(start = Company(id = -9), end = Company(id = -8))
        }
    }

    @Test
    fun `isNotBetween with greater negative end value should be valid`() {
        validate(Employee(company = Company(id = -12))) {
            validate(Employee::company).isNotBetween(start = Company(id = -14), end = Company(id = -13))
        }
    }

    @Test
    fun `isNotBetween with equal start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 0))) {
                validate(Employee::company).isNotBetween(start = Company(id = 0), end = Company(id = 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 0),
                constraint = NotBetween(start = Company(id = 0), end = Company(id = 1))))
    }

    @Test
    fun `isNotBetween with equal end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 1))) {
                validate(Employee::company).isNotBetween(start = Company(id = 0), end = Company(id = 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 1),
                constraint = NotBetween(start = Company(id = 0), end = Company(id = 1))))
    }

    @Test
    fun `isNotBetween with equal negative start value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -2))) {
                validate(Employee::company).isNotBetween(start = Company(id = -2), end = Company(id = -1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -2),
                constraint = NotBetween(start = Company(id = -2), end = Company(id = -1))))
    }

    @Test
    fun `isNotBetween with equal negative end value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -1))) {
                validate(Employee::company).isNotBetween(start = Company(id = -2), end = Company(id = -1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -1),
                constraint = NotBetween(start = Company(id = -2), end = Company(id = -1))))
    }

    @Test
    fun `isNotBetween with within value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = 5))) {
                validate(Employee::company).isNotBetween(start = Company(id = 0), end = Company(id = 10))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = 5),
                constraint = NotBetween(start = Company(id = 0), end = Company(id = 10))))
    }

    @Test
    fun `isNotBetween with within negative value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(id = -15))) {
                validate(Employee::company).isNotBetween(start = Company(id = -20), end = Company(id = -10))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "company",
                value = Company(id = -15),
                constraint = NotBetween(start = Company(id = -20), end = Company(id = -10))))
    }
}
