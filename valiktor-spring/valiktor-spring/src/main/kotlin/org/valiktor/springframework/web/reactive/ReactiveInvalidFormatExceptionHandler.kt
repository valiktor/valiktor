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

package org.valiktor.springframework.web.reactive

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.core.annotation.Order
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import org.valiktor.ConstraintViolationException
import org.valiktor.DefaultConstraintViolation
import org.valiktor.constraints.In
import org.valiktor.constraints.Valid
import reactor.core.publisher.Mono

/**
 * Represents the [WebExceptionHandler] that handles [InvalidFormatException] and throws a [ConstraintViolationException].
 *
 * @author Rodolpho S. Couto
 * @see InvalidFormatException
 * @see WebExceptionHandler
 * @since 0.4.0
 */
@Order(-20)
class ReactiveInvalidFormatExceptionHandler : WebExceptionHandler {

    /**
     * Handle [InvalidFormatException] and throws a [ConstraintViolationException]
     *
     * @param exchange specifies the current webflux request
     * @param ex specifies the [InvalidFormatException]
     * @return the [Mono] with error of type [ConstraintViolationException]
     */
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> =
        ex.handle(exchange) ?: Mono.error(ex)

    private fun Throwable.handle(exchange: ServerWebExchange): Mono<Void>? =
        when (this) {
            is InvalidFormatException ->
                Mono.error(
                    ConstraintViolationException(
                        constraintViolations = setOf(
                            DefaultConstraintViolation(
                                property = this.path.fold("") { jsonPath, it ->
                                    (jsonPath + if (it.index > -1) "[${it.index}]" else ".${it.fieldName}").removePrefix(".")
                                },
                                constraint = if (this.targetType.isEnum) In(this.targetType.enumConstants.toSet()) else Valid,
                                value = this.value
                            )
                        )))
            else -> this.cause?.handle(exchange)
        }
}
