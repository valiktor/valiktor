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

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.handler.ValiktorExceptionHandler
import org.valiktor.springframework.handler.ValiktorResponse
import java.util.Locale

data class ValidationError(
    val errors: Map<String, String>
)

@Component
@Profile("custom-exception-handler")
class ValidationExceptionHandler(
    private val config: ValiktorConfiguration
) : ValiktorExceptionHandler<ValidationError> {

    override fun handle(exception: ConstraintViolationException, locale: Locale): ValiktorResponse<ValidationError> =
        ValiktorResponse(
            statusCode = HttpStatus.BAD_REQUEST,
            headers = HttpHeaders().apply {
                this.set("X-Custom-Header", "OK")
            },
            body = ValidationError(
                errors = exception.constraintViolations
                    .mapToMessage(baseName = config.baseBundleName, locale = locale)
                    .map { it.property to it.message }
                    .toMap()
            )
        )
}
