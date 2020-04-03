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

package org.valiktor.springframework.handler

import org.springframework.stereotype.Component
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import org.valiktor.springframework.config.ValiktorConfiguration
import java.util.Locale

/**
 * Represents the default HTTP exception handler to map [ConstraintViolationException] to an appropriate HTTP response.
 *
 * @author Rodolpho S. Couto
 * @see ValiktorExceptionHandler
 * @see ValiktorResponse
 * @since 0.11.0
 */
@Component
class DefaultValiktorExceptionHandler(
    private val config: ValiktorConfiguration
) : ValiktorExceptionHandler<UnprocessableEntity> {

    /**
     * Handles [ConstraintViolationException] and returns a [ValiktorResponse] with status code 422 (Unprocessable Entity)
     * and [UnprocessableEntity] as response body.
     *
     * @param exception specifies the [ConstraintViolationException]
     * @param locale specifies the [Locale] extracted from request
     * @return a [ValiktorResponse] with status code, headers and body
     */
    override fun handle(exception: ConstraintViolationException, locale: Locale) =
        ValiktorResponse(
            body = UnprocessableEntity(
                errors = exception.constraintViolations
                    .mapToMessage(baseName = config.baseBundleName, locale = locale)
                    .map { constraintViolation ->
                        ValidationError(
                            property = constraintViolation.property,
                            value = constraintViolation.value,
                            message = constraintViolation.message,
                            constraint = ValidationConstraint(
                                name = constraintViolation.constraint.name,
                                params = constraintViolation.constraint.messageParams.map { param ->
                                    ValidationParam(
                                        name = param.key,
                                        value = param.value
                                    )
                                }
                            )
                        )
                    }
            )
        )
}
