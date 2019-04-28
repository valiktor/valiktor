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
import org.valiktor.constraints.Contains
import org.valiktor.constraints.ContainsAll
import org.valiktor.constraints.ContainsAny
import org.valiktor.constraints.Empty
import org.valiktor.constraints.Equals
import org.valiktor.constraints.In
import org.valiktor.constraints.NotContain
import org.valiktor.constraints.NotContainAll
import org.valiktor.constraints.NotContainAny
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.NotEquals
import org.valiktor.constraints.NotIn
import org.valiktor.constraints.NotNull
import org.valiktor.constraints.Null
import org.valiktor.constraints.Size
import org.valiktor.functions.IterableFunctionsFixture.Address
import org.valiktor.functions.IterableFunctionsFixture.City
import org.valiktor.functions.IterableFunctionsFixture.Company
import org.valiktor.functions.IterableFunctionsFixture.Employee
import org.valiktor.validate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private object IterableFunctionsFixture {

    data class Employee(val company: Company? = null)
    data class Company(val addresses: List<Address>? = null)
    data class Address(val id: Int? = null, val city: City? = null)
    data class City(val id: Int? = null)
}

class IterableFunctionsTest {

    @Test
    fun `inner null iterable properties should be valid`() {
        validate(Employee(company = Company())) {
            validate(Employee::company).validate {
                validate(Company::addresses).validateForEach {
                    validate(Address::id).isNotNull()
                    validate(Address::city).validate {
                        validate(City::id).isNotNull()
                    }
                }
            }
        }
    }

    @Test
    fun `inner iterable properties should be valid`() {
        validate(Employee(company = Company(addresses = listOf(
            Address(id = 1, city = City(id = 1)),
            Address(id = 1, city = City(id = 1)),
            Address(id = 1, city = City(id = 1)))))) {
            validate(Employee::company).validate {
                validate(Company::addresses).validateForEach {
                    validate(Address::id).isNotNull()
                    validate(Address::city).validate {
                        validate(City::id).isNotNull()
                    }
                }
            }
        }
    }

    @Test
    fun `inner iterable properties should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Employee(company = Company(addresses = listOf(
                Address(city = City()),
                Address(city = City()),
                Address(city = City()))))) {
                validate(Employee::company).validate {
                    validate(Company::addresses).validateForEach {
                        validate(Address::id).isNotNull()
                        validate(Address::city).validate {
                            validate(City::id).isNotNull()
                        }
                    }
                }
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "company.addresses[0].id", constraint = NotNull),
            DefaultConstraintViolation(property = "company.addresses[0].city.id", constraint = NotNull),
            DefaultConstraintViolation(property = "company.addresses[1].id", constraint = NotNull),
            DefaultConstraintViolation(property = "company.addresses[1].city.id", constraint = NotNull),
            DefaultConstraintViolation(property = "company.addresses[2].id", constraint = NotNull),
            DefaultConstraintViolation(property = "company.addresses[2].city.id", constraint = NotNull))
    }

    @Test
    fun `should receive the current value as function parameter`() {
        validate(Employee(company = Company(addresses = listOf(
            Address(city = City(id = 1)),
            Address(city = City(id = 2)),
            Address(city = City(id = 3)))))) {
            validate(Employee::company).validate {
                var id = 1
                validate(Company::addresses).validateForEach { address ->
                    assertEquals(address, Address(city = City(id = id)))
                    id++
                }
            }
        }
    }

    @Test
    fun `should receive the current index and value as function parameter`() {
        validate(Employee(company = Company(addresses = listOf(
            Address(city = City(id = 1)),
            Address(city = City(id = 2)),
            Address(city = City(id = 3)))))) {
            validate(Employee::company).validate {
                validate(Company::addresses).validateForEachIndexed { index, address ->
                    assertEquals(address, Address(city = City(id = index + 1)))
                }
            }
        }
    }

    @Test
    fun `isNull with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isNull()
        }
    }

    @Test
    fun `isNull with not null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).isNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = emptyList<Address>(), constraint = Null))
    }

    @Test
    fun `isNotNull with not null value should be valid`() {
        validate(Company(addresses = emptyList())) {
            validate(Company::addresses).isNotNull()
        }
    }

    @Test
    fun `isNotNull with null value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company()) {
                validate(Company::addresses).isNotNull()
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", constraint = NotNull))
    }

    @Test
    fun `isEqualTo with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isEqualTo(listOf(Address(id = 1)))
        }
    }

    @Test
    fun `isEqualTo with same value should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2)))) {
            validate(Company::addresses).isEqualTo(listOf(Address(id = 1), Address(id = 2)))
        }
    }

    @Test
    fun `isEqualTo with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2)))) {
                validate(Company::addresses).isEqualTo(listOf(Address(id = 1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = listOf(Address(id = 1), Address(id = 2)), constraint = Equals(listOf(Address(id = 1)))))
    }

    @Test
    fun `isNotEqualTo with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isNotEqualTo(listOf(Address(id = 1), Address(id = 2)))
        }
    }

    @Test
    fun `isNotEqualTo with different value should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2)))) {
            validate(Company::addresses).isNotEqualTo(listOf(Address(id = 1)))
        }
    }

    @Test
    fun `isNotEqualTo with same value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2)))) {
                validate(Company::addresses).isNotEqualTo(listOf(Address(id = 1), Address(id = 2)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = listOf(Address(id = 1), Address(id = 2)), constraint = NotEquals(listOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `isIn vararg with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isIn(listOf(Address(id = 1)))
        }
    }

    @Test
    fun `isIn vararg with same value should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1)))) {
            validate(Company::addresses).isIn(listOf(Address(id = 1)), listOf(Address(id = 1), Address(id = 2)))
        }
    }

    @Test
    fun `isIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).isIn(listOf(Address(id = 1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = emptyList<Address>(), constraint = In(setOf(listOf(Address(id = 1))))))
    }

    @Test
    fun `isIn iterable with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isIn(listOf(listOf(Address(id = 1))))
        }
    }

    @Test
    fun `isIn iterable with same value should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1)))) {
            validate(Company::addresses).isIn(listOf(listOf(Address(id = 1)), listOf(Address(id = 1), Address(id = 2))))
        }
    }

    @Test
    fun `isIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).isIn(listOf(listOf(Address(id = 1))))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = emptyList<Address>(), constraint = In(listOf(listOf(Address(id = 1))))))
    }

    @Test
    fun `isNotIn vararg with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isNotIn(listOf(Address(id = 1)))
        }
    }

    @Test
    fun `isNotIn vararg with same value should be valid`() {
        validate(Company(addresses = emptyList())) {
            validate(Company::addresses).isNotIn(listOf(Address(id = 1), Address(id = 2)))
        }
    }

    @Test
    fun `isNotIn vararg with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1)))) {
                validate(Company::addresses).isNotIn(listOf(Address(id = 1)))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = listOf(Address(id = 1)), constraint = NotIn(setOf(listOf(Address(id = 1))))))
    }

    @Test
    fun `isNotIn iterable with null value should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isNotIn(listOf(listOf(Address(id = 1))))
        }
    }

    @Test
    fun `isNotIn iterable with same value should be valid`() {
        validate(Company(addresses = emptyList())) {
            validate(Company::addresses).isNotIn(listOf(listOf(Address(id = 1), Address(id = 2))))
        }
    }

    @Test
    fun `isNotIn iterable with different value should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1)))) {
                validate(Company::addresses).isNotIn(listOf(listOf(Address(id = 1))))
            }
        }
        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(property = "addresses", value = listOf(Address(id = 1)), constraint = NotIn(listOf(listOf(Address(id = 1))))))
    }

    @Test
    fun `isEmpty with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isEmpty()
        }
    }

    @Test
    fun `isEmpty with empty property should be valid`() {
        validate(Company(addresses = emptyList())) {
            validate(Company::addresses).isEmpty()
        }
    }

    @Test
    fun `isEmpty with not empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address()))) {
                validate(Company::addresses).isEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address()),
                constraint = Empty))
    }

    @Test
    fun `isNotEmpty with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).isNotEmpty()
        }
    }

    @Test
    fun `isNotEmpty with not empty property should be valid`() {
        validate(Company(addresses = listOf(Address()))) {
            validate(Company::addresses).isNotEmpty()
        }
    }

    @Test
    fun `isNotEmpty with empty property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).isNotEmpty()
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = emptyList<Address>(),
                constraint = NotEmpty))
    }

    @Test
    fun `size with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).hasSize(min = 1, max = 10)
        }
    }

    @Test
    fun `size with valid min length property should be valid`() {
        validate(Company(addresses = listOf(Address()))) {
            validate(Company::addresses).hasSize(min = 1)
        }
    }

    @Test
    fun `size with valid max length property should be valid`() {
        validate(Company(addresses = emptyList())) {
            validate(Company::addresses).hasSize(max = 4)
        }
    }

    @Test
    fun `size with valid min and max length property should be valid`() {
        validate(Company(addresses = listOf(Address()))) {
            validate(Company::addresses).hasSize(min = 1, max = 2)
        }
    }

    @Test
    fun `size without min and max should be valid`() {
        validate(Company(addresses = emptyList())) {
            validate(Company::addresses).hasSize()
        }
    }

    @Test
    fun `size with invalid min size property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(), Address()))) {
                validate(Company::addresses).hasSize(min = 5)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(), Address()),
                constraint = Size(min = 5)))
    }

    @Test
    fun `size with invalid max size property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(), Address()))) {
                validate(Company::addresses).hasSize(max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(), Address()),
                constraint = Size(max = 1)))
    }

    @Test
    fun `size with invalid min and max size property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(), Address()))) {
                validate(Company::addresses).hasSize(min = 3, max = 1)
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(), Address()),
                constraint = Size(min = 3, max = 1)))
    }

    @Test
    fun `contains with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).contains(Address())
        }
    }

    @Test
    fun `contains with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2)))) {
            validate(Company::addresses).contains(Address(id = 1))
        }
    }

    @Test
    fun `contains with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).contains(Address(id = 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = emptyList<Address>(),
                constraint = Contains(Address(id = 1))))
    }

    @Test
    fun `containsAll vararg with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).containsAll(Address())
        }
    }

    @Test
    fun `containsAll vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
            validate(Company::addresses).containsAll(Address(id = 1), Address(id = 2))
        }
    }

    @Test
    fun `containsAll vararg with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).containsAll(Address(id = 1), Address(id = 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = emptyList<Address>(),
                constraint = ContainsAll(setOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `containsAll iterable with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).containsAll(listOf(Address()))
        }
    }

    @Test
    fun `containsAll iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
            validate(Company::addresses).containsAll(listOf(Address(id = 1), Address(id = 2)))
        }
    }

    @Test
    fun `containsAll iterable with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1)))) {
                validate(Company::addresses).containsAll(listOf(Address(id = 1), Address(id = 2)))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(id = 1)),
                constraint = ContainsAll(listOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `containsAny vararg with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).containsAny(Address())
        }
    }

    @Test
    fun `containsAny vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 3)))) {
            validate(Company::addresses).containsAny(Address(id = 1), Address(id = 2))
        }
    }

    @Test
    fun `containsAny vararg with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).containsAny(Address(id = 1), Address(id = 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = emptyList<Address>(),
                constraint = ContainsAny(setOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `containsAny iterable with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).containsAny(listOf(Address()))
        }
    }

    @Test
    fun `containsAny iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 3)))) {
            validate(Company::addresses).containsAny(listOf(Address(id = 1), Address(id = 2)))
        }
    }

    @Test
    fun `containsAny iterable with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = emptyList())) {
                validate(Company::addresses).containsAny(listOf(Address(id = 1), Address(id = 2)))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = emptyList<Address>(),
                constraint = ContainsAny(listOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `doesNotContain with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).doesNotContain(Address())
        }
    }

    @Test
    fun `doesNotContain with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2)))) {
            validate(Company::addresses).doesNotContain(Address(id = 3))
        }
    }

    @Test
    fun `doesNotContain with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1)))) {
                validate(Company::addresses).doesNotContain(Address(id = 1))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(id = 1)),
                constraint = NotContain(Address(id = 1))))
    }

    @Test
    fun `doesNotContainAll vararg with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).doesNotContainAll(Address())
        }
    }

    @Test
    fun `doesNotContainAll vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
            validate(Company::addresses).doesNotContainAll(Address(id = 1), Address(id = 5))
        }
    }

    @Test
    fun `doesNotContainAll vararg with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
                validate(Company::addresses).doesNotContainAll(Address(id = 1), Address(id = 2))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(id = 1), Address(id = 2), Address(id = 3)),
                constraint = NotContainAll(setOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `doesNotContainAll iterable with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).doesNotContainAll(listOf(Address()))
        }
    }

    @Test
    fun `doesNotContainAll iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
            validate(Company::addresses).doesNotContainAll(listOf(Address(id = 1), Address(id = 5)))
        }
    }

    @Test
    fun `doesNotContainAll iterable with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
                validate(Company::addresses).doesNotContainAll(listOf(Address(id = 1), Address(id = 2)))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(id = 1), Address(id = 2), Address(id = 3)),
                constraint = NotContainAll(listOf(Address(id = 1), Address(id = 2)))))
    }

    @Test
    fun `doesNotContainAny vararg with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).doesNotContainAny(Address())
        }
    }

    @Test
    fun `doesNotContainAny vararg with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
            validate(Company::addresses).doesNotContainAny(Address(id = 4), Address(id = 5))
        }
    }

    @Test
    fun `doesNotContainAny vararg with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
                validate(Company::addresses).doesNotContainAny(Address(id = 1), Address(id = 5))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(id = 1), Address(id = 2), Address(id = 3)),
                constraint = NotContainAny(setOf(Address(id = 1), Address(id = 5)))))
    }

    @Test
    fun `doesNotContainAny iterable with null property should be valid`() {
        validate(Company()) {
            validate(Company::addresses).doesNotContainAny(listOf(Address()))
        }
    }

    @Test
    fun `doesNotContainAny iterable with valid property should be valid`() {
        validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
            validate(Company::addresses).doesNotContainAny(listOf(Address(id = 4), Address(id = 5)))
        }
    }

    @Test
    fun `doesNotContainAny iterable with invalid property should be invalid`() {
        val exception = assertFailsWith<ConstraintViolationException> {
            validate(Company(addresses = listOf(Address(id = 1), Address(id = 2), Address(id = 3)))) {
                validate(Company::addresses).doesNotContainAny(listOf(Address(id = 1), Address(id = 5)))
            }
        }

        assertThat(exception.constraintViolations).containsExactly(
            DefaultConstraintViolation(
                property = "addresses",
                value = listOf(Address(id = 1), Address(id = 2), Address(id = 3)),
                constraint = NotContainAny(listOf(Address(id = 1), Address(id = 5)))))
    }
}