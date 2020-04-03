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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

/**
 * Represents the default payload for responses with 422 (Unprocessable Entity) status code
 *
 * @property errors specifies the list of [ValidationError]
 *
 * @author Rodolpho S. Couto
 * @see ValidationError
 * @since 0.1.0
 */
@JacksonXmlRootElement(localName = "unprocessableEntity")
data class UnprocessableEntity(
    @JacksonXmlProperty(localName = "error")
    @JacksonXmlElementWrapper(localName = "errors")
    val errors: List<ValidationError>
)

/**
 * Represents a validation error
 *
 * @property property specifies the invalid property
 * @property value specifies the invalid value
 * @property message specifies the internationalized message
 * @property constraint specifies the violated constraint
 *
 * @author Rodolpho S. Couto
 * @see ValidationConstraint
 * @since 0.1.0
 */
data class ValidationError(
    val property: String,
    val value: Any?,
    val message: String,
    val constraint: ValidationConstraint
)

/**
 * Represents a validation constraint
 *
 * @property name specifies the name of the constraint
 * @property params specifies the list of validation parameters
 *
 * @author Rodolpho S. Couto
 * @see ValidationParam
 * @since 0.1.0
 */
data class ValidationConstraint(
    val name: String,
    @JacksonXmlProperty(localName = "param")
    @JacksonXmlElementWrapper(localName = "params")
    val params: List<ValidationParam>
)

/**
 * Represents a validation parameter
 *
 * @property name specifies the name of the parameter
 * @property value specifies the value of the parameter
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
data class ValidationParam(
    val name: String,
    val value: Any?
)
