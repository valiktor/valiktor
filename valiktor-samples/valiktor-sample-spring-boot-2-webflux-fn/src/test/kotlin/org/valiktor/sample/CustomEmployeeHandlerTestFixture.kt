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

package org.valiktor.sample

import java.time.LocalDate
import java.time.Month
import java.util.Locale
import javax.money.Monetary

object CustomEmployeeHandlerTestFixture {

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

    val validationErrors = mapOf(
        Locale.ENGLISH to ValidationError(
            errors = mapOf(
                "documentNumber" to "Must be a valid document",
                "name" to "Size must be between 3 and 100",
                "email" to "Must be a valid email address",
                "dateOfBirth" to "Must be greater than Jan 1, 1950",
                "salary" to "Currency unit must be equal to USD",
                "company.name" to "Size must be between 3 and 50",
                "dependents[0].name" to "Size must be between 3 and 50",
                "dependents[0].age" to "Must be between 1 and 18"
            )
        ),

        Locale("pt", "BR") to ValidationError(
            errors = mapOf(
                "documentNumber" to "Deve ser um documento válido",
                "name" to "O tamanho deve estar entre 3 e 100",
                "email" to "Deve ser um endereço de e-mail válido",
                "dateOfBirth" to "Deve ser maior que 01/01/1950",
                "salary" to "A unidade monetária deve ser igual a USD",
                "company.name" to "O tamanho deve estar entre 3 e 50",
                "dependents[0].name" to "O tamanho deve estar entre 3 e 50",
                "dependents[0].age" to "Deve estar entre 1 e 18"
            )
        )
    )
}
