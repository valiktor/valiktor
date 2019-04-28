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

package org.valiktor.springframework.web.reactive

import org.springframework.core.annotation.Order
import org.springframework.http.codec.CodecConfigurer
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import org.valiktor.ConstraintViolationException
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.web.payload.toUnprocessableEntity
import reactor.core.publisher.Mono
import java.util.Locale

/**
 * Represents the [WebExceptionHandler] that handles [ConstraintViolationException] and returns an appropriate HTTP response.
 *
 * @param config specifies the [ValiktorConfiguration]
 *
 * @author Rodolpho S. Couto
 * @see ConstraintViolationException
 * @see WebExceptionHandler
 * @since 0.3.0
 */
@Order(-10)
class ReactiveConstraintViolationExceptionHandler(
    private val config: ValiktorConfiguration,
    private val codecConfigurer: CodecConfigurer
) : WebExceptionHandler {

    /**
     * Handle [ConstraintViolationException] and returns 422 (Unprocessable Entity) status code
     * with the constraint violations.
     *
     * @param exchange specifies the current webflux request
     * @param ex specifies the [ConstraintViolationException]
     * @return the ResponseEntity with 422 status code and the constraint violations
     */
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> =
        ex.handle(exchange) ?: Mono.error(ex)

    private fun Throwable.handle(exchange: ServerWebExchange): Mono<Void>? =
        when (this) {
            is ConstraintViolationException ->
                ServerResponse
                    .unprocessableEntity()
                    .body(BodyInserters.fromObject(
                        this.toUnprocessableEntity(
                            baseBundleName = config.baseBundleName,
                            locale = exchange.localeContext.locale ?: Locale.getDefault()
                        )))
                    .flatMap {
                        it.writeTo(exchange, object : ServerResponse.Context {
                            override fun messageWriters() = codecConfigurer.writers
                            override fun viewResolvers() = emptyList<ViewResolver>()
                        })
                    }
                    .flatMap {
                        Mono.empty<Void>()
                    }
            else -> this.cause?.handle(exchange)
        }
}