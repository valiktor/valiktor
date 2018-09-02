package org.valiktor.sample

import org.valiktor.springframework.web.payload.UnprocessableEntity
import org.valiktor.springframework.web.payload.ValidationConstraint
import org.valiktor.springframework.web.payload.ValidationError
import org.valiktor.springframework.web.payload.ValidationParam
import java.time.LocalDate
import java.time.Month
import javax.money.Monetary

object EmployeeControllerFixture {

    fun validEmployee(id: Int) = Employee(
        id = id,
        name = "John",
        email = "john@john.com",
        dateOfBirth = LocalDate.of(1980, Month.JANUARY, 1),
        salary = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(Monetary.getCurrency("USD")).create(),
        company = Company(
            name = "Valiktor",
            foundedDate = LocalDate.of(2000, 1, 1)
        ),
        dependents = listOf(
            Dependent(
                name = "Little John",
                age = 5
            )
        )
    )

    fun invalidEmployee(id: Int) = Employee(
        id = id,
        name = "J",
        email = "john",
        dateOfBirth = LocalDate.of(1900, Month.JANUARY, 1),
        salary = Monetary.getDefaultAmountFactory().setNumber(1000).setCurrency(Monetary.getCurrency("BRL")).create(),
        company = Company(
            name = "C",
            foundedDate = LocalDate.of(2000, 1, 1)
        ),
        dependents = listOf(
            Dependent(
                name = "Li",
                age = 20
            )
        )
    )

    fun unprocessableEntityEn(id: Int) = UnprocessableEntity(
        errors = listOf(
            ValidationError(
                property = "id",
                value = id,
                message = "Must be unique",
                constraint = ValidationConstraint(
                    name = "Unique",
                    params = emptyList()
                )
            ),
            ValidationError(
                property = "name",
                value = "J",
                message = "Size must be between 3 and 30",
                constraint = ValidationConstraint(
                    name = "Size",
                    params = listOf(
                        ValidationParam(
                            name = "min",
                            value = 3
                        ),
                        ValidationParam(
                            name = "max",
                            value = 30
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
                message = "Must be between 1 and 16",
                constraint = ValidationConstraint(
                    name = "Between",
                    params = listOf(
                        ValidationParam(
                            name = "end",
                            value = 16
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

    fun unprocessableEntityPtBr(id: Int) = UnprocessableEntity(
        errors = listOf(
            ValidationError(
                property = "id",
                value = id,
                message = "Deve ser único",
                constraint = ValidationConstraint(
                    name = "Unique",
                    params = emptyList()
                )
            ),
            ValidationError(
                property = "name",
                value = "J",
                message = "O tamanho deve estar entre 3 e 30",
                constraint = ValidationConstraint(
                    name = "Size",
                    params = listOf(
                        ValidationParam(
                            name = "min",
                            value = 3
                        ),
                        ValidationParam(
                            name = "max",
                            value = 30
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
                message = "Deve estar entre 1 e 16",
                constraint = ValidationConstraint(
                    name = "Between",
                    params = listOf(
                        ValidationParam(
                            name = "end",
                            value = 16
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
}