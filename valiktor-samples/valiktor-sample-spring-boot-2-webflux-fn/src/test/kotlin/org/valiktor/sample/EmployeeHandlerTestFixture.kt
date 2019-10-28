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

package org.valiktor.sample

import org.valiktor.springframework.web.payload.UnprocessableEntity
import org.valiktor.springframework.web.payload.ValidationConstraint
import org.valiktor.springframework.web.payload.ValidationError
import org.valiktor.springframework.web.payload.ValidationParam
import java.time.LocalDate
import java.time.Month
import java.util.Locale
import javax.money.Monetary

object EmployeeHandlerTestFixture {

    val validEmployee = Employee(
        documentNumber = "111.111.111-11",
        name = "John",
        email = "john@john.com",
        dateOfBirth = LocalDate.of(1980, Month.JANUARY, 1),
        salary = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(Monetary.getCurrency("USD")).create(),
        company = Company(
            name = "Valiktor",
            foundationDate = LocalDate.of(2000, 1, 1)
        ),
        dependents = listOf(
            Dependent(
                name = "Little John",
                age = 5
            )
        )
    )

    val invalidEmployee = Employee(
        documentNumber = "1",
        name = "J",
        email = "john",
        dateOfBirth = LocalDate.of(1900, Month.JANUARY, 1),
        salary = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(Monetary.getCurrency("BRL")).create(),
        company = Company(
            name = "C",
            foundationDate = LocalDate.of(2000, 1, 1)
        ),
        dependents = listOf(
            Dependent(
                name = "Li",
                age = 20
            )
        )
    )

    val unprocessableEntity = mapOf(
        Locale.ENGLISH to UnprocessableEntity(
            errors = listOf(
                ValidationError(
                    property = "documentNumber",
                    value = "1",
                    message = "Must be a valid document",
                    constraint = ValidationConstraint(
                        name = "Document",
                        params = emptyList()
                    )
                ),
                ValidationError(
                    property = "name",
                    value = "J",
                    message = "Size must be between 3 and 100",
                    constraint = ValidationConstraint(
                        name = "Size",
                        params = listOf(
                            ValidationParam(
                                name = "min",
                                value = 3
                            ),
                            ValidationParam(
                                name = "max",
                                value = 100
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "email",
                    value = "john",
                    message = "Must be a valid email address",
                    constraint = ValidationConstraint(
                        name = "Email",
                        params = emptyList()
                    )
                ),
                ValidationError(
                    property = "dateOfBirth",
                    value = "1900-01-01",
                    message = "Must be greater than Jan 1, 1950",
                    constraint = ValidationConstraint(
                        name = "Greater",
                        params = listOf(
                            ValidationParam(
                                name = "value",
                                value = "1950-01-01"
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "salary",
                    value = mapOf(
                        "amount" to 1000.0,
                        "currency" to "BRL"
                    ),
                    message = "Currency unit must be equal to USD",
                    constraint = ValidationConstraint(
                        name = "CurrencyEquals",
                        params = listOf(
                            ValidationParam(
                                name = "currency",
                                value = "USD"
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "company.name",
                    value = "C",
                    message = "Size must be between 3 and 50",
                    constraint = ValidationConstraint(
                        name = "Size",
                        params = listOf(
                            ValidationParam(
                                name = "min",
                                value = 3
                            ),
                            ValidationParam(
                                name = "max",
                                value = 50
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "dependents[0].name",
                    value = "Li",
                    message = "Size must be between 3 and 50",
                    constraint = ValidationConstraint(
                        name = "Size",
                        params = listOf(
                            ValidationParam(
                                name = "min",
                                value = 3
                            ),
                            ValidationParam(
                                name = "max",
                                value = 50
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "dependents[0].age",
                    value = 20,
                    message = "Must be between 1 and 18",
                    constraint = ValidationConstraint(
                        name = "Between",
                        params = listOf(
                            ValidationParam(
                                name = "end",
                                value = 18
                            ),
                            ValidationParam(
                                name = "start",
                                value = 1
                            )
                        )
                    )
                )
            )
        ),

        Locale("pt", "BR") to UnprocessableEntity(
            errors = listOf(
                ValidationError(
                    property = "documentNumber",
                    value = "1",
                    message = "Deve ser um documento válido",
                    constraint = ValidationConstraint(
                        name = "Document",
                        params = emptyList()
                    )
                ),
                ValidationError(
                    property = "name",
                    value = "J",
                    message = "O tamanho deve estar entre 3 e 100",
                    constraint = ValidationConstraint(
                        name = "Size",
                        params = listOf(
                            ValidationParam(
                                name = "min",
                                value = 3
                            ),
                            ValidationParam(
                                name = "max",
                                value = 100
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "email",
                    value = "john",
                    message = "Deve ser um endereço de e-mail válido",
                    constraint = ValidationConstraint(
                        name = "Email",
                        params = emptyList()
                    )
                ),
                ValidationError(
                    property = "dateOfBirth",
                    value = "1900-01-01",
                    message = "Deve ser maior que 01/01/1950",
                    constraint = ValidationConstraint(
                        name = "Greater",
                        params = listOf(
                            ValidationParam(
                                name = "value",
                                value = "1950-01-01"
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "salary",
                    value = mapOf(
                        "amount" to 1000.0,
                        "currency" to "BRL"
                    ),
                    message = "A unidade monetária deve ser igual a USD",
                    constraint = ValidationConstraint(
                        name = "CurrencyEquals",
                        params = listOf(
                            ValidationParam(
                                name = "currency",
                                value = "USD"
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "company.name",
                    value = "C",
                    message = "O tamanho deve estar entre 3 e 50",
                    constraint = ValidationConstraint(
                        name = "Size",
                        params = listOf(
                            ValidationParam(
                                name = "min",
                                value = 3
                            ),
                            ValidationParam(
                                name = "max",
                                value = 50
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "dependents[0].name",
                    value = "Li",
                    message = "O tamanho deve estar entre 3 e 50",
                    constraint = ValidationConstraint(
                        name = "Size",
                        params = listOf(
                            ValidationParam(
                                name = "min",
                                value = 3
                            ),
                            ValidationParam(
                                name = "max",
                                value = 50
                            )
                        )
                    )
                ),
                ValidationError(
                    property = "dependents[0].age",
                    value = 20,
                    message = "Deve estar entre 1 e 18",
                    constraint = ValidationConstraint(
                        name = "Between",
                        params = listOf(
                            ValidationParam(
                                name = "end",
                                value = 18
                            ),
                            ValidationParam(
                                name = "start",
                                value = 1
                            )
                        )
                    )
                )
            )
        )
    )
}
