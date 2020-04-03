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

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.valiktor.ConstraintViolationException
import org.valiktor.springframework.handler.ValiktorExceptionHandler
import java.util.Locale

/**
 * Represents the REST controller that handles [ConstraintViolationException] and returns an appropriate HTTP response.
 *
 * @param handler specifies the [ValiktorExceptionHandler]
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolationException
 * @see ExceptionHandler
 * @since 0.1.0
 */
@RestControllerAdvice
class ConstraintViolationExceptionHandler(private val handler: ValiktorExceptionHandler<*>) {

    /**
     * Handles [ConstraintViolationException] and returns and delegates the response to [handler].
     *
     * @param ex specifies the [ConstraintViolationException]
     * @param locale specifies the [Locale] of the Request
     * @return the ResponseEntity with status code, headers and body
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException, locale: Locale?): ResponseEntity<*> {
        val (statusCode, headers, body) = handler.handle(
            exception = ex,
            locale = locale ?: Locale.getDefault()
        )

        return ResponseEntity.status(statusCode).headers(headers).body(body)
    }
}
