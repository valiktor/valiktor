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

package org.valiktor.test

import org.valiktor.functions.hasSize
import org.valiktor.functions.isBetween
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isPositive
import org.valiktor.functions.matches
import org.valiktor.functions.validate
import org.valiktor.functions.validateForEach
import org.valiktor.validate

object TestValidatorTestFixture {

    data class Company(val name: String)
    data class Address(val street: String, val number: String, val neighborhood: String, val city: City)
    data class City(val name: String, val state: State)
    data class State(val name: String, val country: Country)
    data class Country(val name: String)
    data class Dependent(val name: String, val age: Int)
    data class Employee(
        val id: Int,
        val name: String,
        val email: String,
        val company: Company,
        val address: Address,
        val dependentsList: List<Dependent> = emptyList(),
        val dependentsArray: Array<Dependent> = emptyArray()
    ) {
        init {
            validate(this) {
                validate(Employee::id).isPositive()
                validate(Employee::name).isNotBlank()
                validate(Employee::email).isEmail()
                validate(Employee::company).validate {
                    validate(Company::name).hasSize(min = 3, max = 50)
                }
                validate(Employee::address).validate {
                    validate(Address::street).isNotEmpty()
                    validate(Address::number).matches(Regex("^[0-9]*\$"))
                    validate(Address::neighborhood).hasSize(min = 1, max = 40)
                    validate(Address::city).validate {
                        validate(City::name).hasSize(min = 3, max = 30)
                        validate(City::state).validate {
                            validate(State::name).hasSize(min = 2, max = 2)
                            validate(State::country).validate {
                                validate(Country::name).hasSize(min = 3, max = 3)
                            }
                        }
                    }
                }
                validate(Employee::dependentsList).validateForEach {
                    validate(Dependent::name).isNotBlank()
                    validate(Dependent::age).isBetween(1, 16)
                }
                validate(Employee::dependentsArray).validateForEach {
                    validate(Dependent::name).isNotBlank()
                    validate(Dependent::age).isBetween(1, 18)
                }
            }
        }
    }

    fun validEmployee() = Employee(
        id = 1,
        name = "John",
        email = "john@company.com",
        company = Company(
            name = "Company"
        ),
        address = Address(
            street = "Street",
            number = "111",
            neighborhood = "Neighborhood",
            city = City(
                name = "City",
                state = State(
                    name = "SP",
                    country = Country(
                        name = "BRA"
                    )
                )
            )
        ),
        dependentsList = listOf(
            Dependent(name = "D1", age = 1),
            Dependent(name = "D2", age = 5),
            Dependent(name = "D3", age = 10),
            Dependent(name = "D4", age = 15)
        ),
        dependentsArray = arrayOf(
            Dependent(name = "D1", age = 1),
            Dependent(name = "D2", age = 5),
            Dependent(name = "D3", age = 10),
            Dependent(name = "D4", age = 15)
        )
    )

    fun invalidEmployee() = Employee(
        id = 0,
        name = " ",
        email = "john",
        company = Company(
            name = "Co"
        ),
        address = Address(
            street = "",
            number = "abc",
            neighborhood = "",
            city = City(
                name = "Ci",
                state = State(
                    name = "S",
                    country = Country(
                        name = "BR"
                    )
                )
            )
        ),
        dependentsList = listOf(
            Dependent(name = " ", age = 0),
            Dependent(name = " ", age = 17),
            Dependent(name = " ", age = 18),
            Dependent(name = " ", age = 19)
        ),
        dependentsArray = arrayOf(
            Dependent(name = " ", age = 0),
            Dependent(name = " ", age = 19),
            Dependent(name = " ", age = 20),
            Dependent(name = " ", age = 21)
        )
    )
}
