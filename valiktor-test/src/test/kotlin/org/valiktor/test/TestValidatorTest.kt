/*
 * Copyright 2018-2020 https://www.valiktor.org
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

package org.valiktor.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.opentest4j.AssertionFailedError
import org.valiktor.constraints.Between
import org.valiktor.constraints.Email
import org.valiktor.constraints.Greater
import org.valiktor.constraints.Matches
import org.valiktor.constraints.NotBlank
import org.valiktor.constraints.NotEmpty
import org.valiktor.constraints.Size
import org.valiktor.test.TestValidatorTestFixture.Address
import org.valiktor.test.TestValidatorTestFixture.City
import org.valiktor.test.TestValidatorTestFixture.Company
import org.valiktor.test.TestValidatorTestFixture.Country
import org.valiktor.test.TestValidatorTestFixture.Dependent
import org.valiktor.test.TestValidatorTestFixture.Employee
import org.valiktor.test.TestValidatorTestFixture.State
import java.lang.System.lineSeparator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class TestValidatorTest {

    @Test
    fun `should not fail validation`() {
        assertFailsWith<AssertionFailedError> {
            shouldFailValidation<Employee> {
                TestValidatorTestFixture.validEmployee()
            }
        }
    }

    @Test
    fun `should not fail validation with suspending function`() {
        suspend fun validEmployee(): Employee {
            delay(10L)
            return TestValidatorTestFixture.validEmployee()
        }

        runBlockingTest {
            assertFailsWith<AssertionFailedError> {
                shouldFailValidation<Employee> {
                    validEmployee()
                }
            }
        }
    }

    @Test
    fun `should fail validation`() {
        shouldFailValidation<Employee> {
            TestValidatorTestFixture.invalidEmployee()
        }
    }

    @Test
    fun `should fail validation with suspending function`() {
        suspend fun invalidEmployee(): Employee {
            delay(10L)
            return TestValidatorTestFixture.invalidEmployee()
        }

        runBlockingTest {
            shouldFailValidation<Employee> {
                invalidEmployee()
            }
        }
    }

    @Test
    fun `should fail validation and verify correct constraint violations`() {
        shouldFailValidation<Employee> {
            TestValidatorTestFixture.invalidEmployee()
        }.verify {
            expect(Employee::id, 0, Greater(0))
            expect(Employee::name, " ", NotBlank)
            expect(Employee::email, "john", Email)
            expect(Employee::company) {
                expect(Company::name, "Co", Size(min = 3, max = 50))
            }
            expect(Employee::address) {
                expect(Address::street, "", NotEmpty)
                expect(Address::number, "abc", Matches(Regex("^[0-9]*\$")))
                expect(Address::neighborhood, "", Size(min = 1, max = 40))
                expect(Address::city) {
                    expect(City::name, "Ci", Size(min = 3, max = 30))
                    expect(City::state) {
                        expect(State::name, "S", Size(min = 2, max = 2))
                        expect(State::country) {
                            expect(Country::name, "BR", Size(min = 3, max = 3))
                        }
                    }
                }
            }
            expectAll(Employee::dependentsList) {
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 0, Between(1, 16))
                }
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 17, Between(1, 16))
                }
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 18, Between(1, 16))
                }
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 19, Between(1, 16))
                }
            }
            expectAll(Employee::dependentsArray) {
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 0, Between(1, 18))
                }
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 19, Between(1, 18))
                }
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 20, Between(1, 18))
                }
                expectElement {
                    expect(Dependent::name, " ", NotBlank)
                    expect(Dependent::age, 21, Between(1, 18))
                }
            }
        }
    }

    @Test
    fun `should fail validation and verify incorrect constraint violations`() {
        val ex = assertFailsWith<AssertionFailedError> {
            shouldFailValidation<Employee> {
                TestValidatorTestFixture.invalidEmployee()
            }.verify {
                expect(Employee::id, 1, Greater(1))
                expect(Employee::name, "", NotEmpty)
                expect(Employee::email, "john", Email)
                expect(Employee::company) {
                    expect(Company::name, "C", Size(min = 2, max = 100))
                }
                expect(Employee::address) {
                    expect(Address::street, " ", NotBlank)
                    expect(Address::number, "foo", Matches(Regex("^[0-9]*\$")))
                    expect(Address::neighborhood, " ", Size(min = 2, max = 50))
                    expect(Address::city) {
                        expect(City::name, "C", Size(min = 2, max = 60))
                        expect(City::state) {
                            expect(State::name, "SP", Size(min = 3, max = 3))
                            expect(State::country) {
                                expect(Country::name, "BRA", Size(min = 2, max = 2))
                            }
                        }
                    }
                }
                expectAll(Employee::dependentsList) {
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, -1, Between(0, 15))
                    }
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, 16, Between(0, 15))
                    }
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, 17, Between(0, 15))
                    }
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, 18, Between(0, 15))
                    }
                }
                expectAll(Employee::dependentsArray) {
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, -1, Between(0, 17))
                    }
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, 18, Between(0, 17))
                    }
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, 19, Between(0, 17))
                    }
                    expectElement {
                        expect(Dependent::name, "", NotEmpty)
                        expect(Dependent::age, 20, Between(0, 17))
                    }
                }
            }
        }

        assertEquals(
            ex.message,
            "Expected:${lineSeparator()}${lineSeparator()}" +
                "id                              | 1    | Greater(value = 1)${lineSeparator()}" +
                "name                            |      | NotEmpty${lineSeparator()}" +
                "email                           | john | Email${lineSeparator()}" +
                "company.name                    | C    | Size(min = 2, max = 100)${lineSeparator()}" +
                "address.street                  |      | NotBlank${lineSeparator()}" +
                "address.number                  | foo  | Matches(pattern = ^[0-9]*\$)${lineSeparator()}" +
                "address.neighborhood            |      | Size(min = 2, max = 50)${lineSeparator()}" +
                "address.city.name               | C    | Size(min = 2, max = 60)${lineSeparator()}" +
                "address.city.state.name         | SP   | Size(min = 3, max = 3)${lineSeparator()}" +
                "address.city.state.country.name | BRA  | Size(min = 2, max = 2)${lineSeparator()}" +
                "dependentsList[0].name          |      | NotEmpty${lineSeparator()}" +
                "dependentsList[0].age           | -1   | Between(end = 15, start = 0)${lineSeparator()}" +
                "dependentsList[1].name          |      | NotEmpty${lineSeparator()}" +
                "dependentsList[1].age           | 16   | Between(end = 15, start = 0)${lineSeparator()}" +
                "dependentsList[2].name          |      | NotEmpty${lineSeparator()}" +
                "dependentsList[2].age           | 17   | Between(end = 15, start = 0)${lineSeparator()}" +
                "dependentsList[3].name          |      | NotEmpty${lineSeparator()}" +
                "dependentsList[3].age           | 18   | Between(end = 15, start = 0)${lineSeparator()}" +
                "dependentsArray[0].name         |      | NotEmpty${lineSeparator()}" +
                "dependentsArray[0].age          | -1   | Between(end = 17, start = 0)${lineSeparator()}" +
                "dependentsArray[1].name         |      | NotEmpty${lineSeparator()}" +
                "dependentsArray[1].age          | 18   | Between(end = 17, start = 0)${lineSeparator()}" +
                "dependentsArray[2].name         |      | NotEmpty${lineSeparator()}" +
                "dependentsArray[2].age          | 19   | Between(end = 17, start = 0)${lineSeparator()}" +
                "dependentsArray[3].name         |      | NotEmpty${lineSeparator()}" +
                "dependentsArray[3].age          | 20   | Between(end = 17, start = 0)${lineSeparator()}" +
                "${lineSeparator()}but was:${lineSeparator()}${lineSeparator()}" +
                "id                              | 0    | Greater(value = 0)${lineSeparator()}" +
                "name                            |      | NotBlank${lineSeparator()}" +
                "email                           | john | Email${lineSeparator()}" +
                "company.name                    | Co   | Size(min = 3, max = 50)${lineSeparator()}" +
                "address.street                  |      | NotEmpty${lineSeparator()}" +
                "address.number                  | abc  | Matches(pattern = ^[0-9]*\$)${lineSeparator()}" +
                "address.neighborhood            |      | Size(min = 1, max = 40)${lineSeparator()}" +
                "address.city.name               | Ci   | Size(min = 3, max = 30)${lineSeparator()}" +
                "address.city.state.name         | S    | Size(min = 2, max = 2)${lineSeparator()}" +
                "address.city.state.country.name | BR   | Size(min = 3, max = 3)${lineSeparator()}" +
                "dependentsList[0].name          |      | NotBlank${lineSeparator()}" +
                "dependentsList[0].age           | 0    | Between(end = 16, start = 1)${lineSeparator()}" +
                "dependentsList[1].name          |      | NotBlank${lineSeparator()}" +
                "dependentsList[1].age           | 17   | Between(end = 16, start = 1)${lineSeparator()}" +
                "dependentsList[2].name          |      | NotBlank${lineSeparator()}" +
                "dependentsList[2].age           | 18   | Between(end = 16, start = 1)${lineSeparator()}" +
                "dependentsList[3].name          |      | NotBlank${lineSeparator()}" +
                "dependentsList[3].age           | 19   | Between(end = 16, start = 1)${lineSeparator()}" +
                "dependentsArray[0].name         |      | NotBlank${lineSeparator()}" +
                "dependentsArray[0].age          | 0    | Between(end = 18, start = 1)${lineSeparator()}" +
                "dependentsArray[1].name         |      | NotBlank${lineSeparator()}" +
                "dependentsArray[1].age          | 19   | Between(end = 18, start = 1)${lineSeparator()}" +
                "dependentsArray[2].name         |      | NotBlank${lineSeparator()}" +
                "dependentsArray[2].age          | 20   | Between(end = 18, start = 1)${lineSeparator()}" +
                "dependentsArray[3].name         |      | NotBlank${lineSeparator()}" +
                "dependentsArray[3].age          | 21   | Between(end = 18, start = 1)${lineSeparator()}"
        )
    }
}
