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

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

/**
 * Represents the HTTP response.
 *
 * @property statusCode represents the HTTP response status code
 * @property headers represents the HTTP response headers
 * @property body represents the HTTP response body
 *
 * @author Rodolpho S. Couto
 * @see ValiktorExceptionHandler
 * @since 0.11.0
 */
data class ValiktorResponse<T : Any>(
    val statusCode: HttpStatus = UNPROCESSABLE_ENTITY,
    val headers: HttpHeaders? = null,
    val body: T
)
