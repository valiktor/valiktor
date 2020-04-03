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

package org.valiktor.springframework.handler.webmvc

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.In
import org.valiktor.constraints.Valid
import java.util.Locale

/**
 * Represents the REST controller that handles [InvalidFormatException] and returns an appropriate HTTP response.
 *
 * @param constraintViolationExceptionHandler specifies the [ConstraintViolationExceptionHandler]
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolationException
 * @see ConstraintViolationExceptionHandler
 * @see ExceptionHandler
 * @since 0.4.0
 */
@RestControllerAdvice
class InvalidFormatExceptionHandler(
    private val constraintViolationExceptionHandler: ConstraintViolationExceptionHandler
) {

    /**
     * Handles [InvalidFormatException] and delegates the response to [constraintViolationExceptionHandler].
     *
     * @param ex specifies the [InvalidFormatException]
     * @param locale specifies the [Locale] of the Request
     * @return the ResponseEntity with status code, headers and body
     */
    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormatException(ex: InvalidFormatException, locale: Locale?): ResponseEntity<*> =
        constraintViolationExceptionHandler.handleConstraintViolationException(
            ex = ConstraintViolationException(
                constraintViolations = setOf(
                    DefaultConstraintViolation(
                        property = ex.path.fold("") { path, it ->
                            (path + if (it.index > -1) "[${it.index}]" else ".${it.fieldName}").removePrefix(".")
                        },
                        constraint = if (ex.targetType.isEnum) In(ex.targetType.enumConstants.toSet()) else Valid,
                        value = ex.value
                    )
                )
            ),
            locale = locale
        )
}
