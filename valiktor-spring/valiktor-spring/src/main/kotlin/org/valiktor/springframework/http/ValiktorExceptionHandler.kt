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

package org.valiktor.springframework.http

import org.valiktor.ConstraintViolationException
import java.util.Locale

/**
 * Represents the HTTP exception handler to map [ConstraintViolationException] to an appropriate HTTP response.
 *
 * @author Rodolpho S. Couto
 * @see ValiktorResponse
 * @since 0.11.0
 */
interface ValiktorExceptionHandler<T : Any> {

    /**
     * Handles [ConstraintViolationException] and returns a [ValiktorResponse] with status code, headers and body.
     *
     * @param exception specifies the [ConstraintViolationException]
     * @param locale specifies the [Locale] extracted from request
     * @return a [ValiktorResponse] with status code, headers and body
     */
    fun handle(exception: ConstraintViolationException, locale: Locale): ValiktorResponse<T>
}
