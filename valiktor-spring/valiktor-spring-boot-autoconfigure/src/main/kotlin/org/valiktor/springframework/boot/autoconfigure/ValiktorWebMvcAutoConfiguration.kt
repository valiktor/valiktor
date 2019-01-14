/*
 * Copyright 2018 https://www.valiktor.org
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

package org.valiktor.springframework.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.web.controller.InvalidFormatExceptionHandler
import org.valiktor.springframework.web.controller.MissingKotlinParameterExceptionHandler
import org.valiktor.springframework.web.controller.ValiktorExceptionHandler

/**
 * Represents the SpringBoot Auto Configuration for Spring WebFlux exception handlers
 *
 * @author Rodolpho S. Couto
 * @since 0.3.0
 */
@Configuration
@ConditionalOnClass(name = ["org.springframework.web.servlet.DispatcherServlet"])
@ConditionalOnBean(type = ["org.valiktor.springframework.config.ValiktorConfiguration"])
class ValiktorWebMvcAutoConfiguration {

    /**
     * Creates a [ValiktorExceptionHandler]
     *
     * @return the respective [ValiktorExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    fun valiktorExceptionHandler(valiktorConfiguration: ValiktorConfiguration) =
        ValiktorExceptionHandler(valiktorConfiguration)

    /**
     * Creates a [InvalidFormatExceptionHandler]
     *
     * @return the respective [InvalidFormatExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = ["com.fasterxml.jackson.databind.exc.InvalidFormatException"])
    fun invalidFormatExceptionHandler(valiktorExceptionHandler: ValiktorExceptionHandler) =
        InvalidFormatExceptionHandler(valiktorExceptionHandler)

    /**
     * Creates a [MissingKotlinParameterExceptionHandler]
     *
     * @return the respective [MissingKotlinParameterExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = ["com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException"])
    fun missingKotlinParameterExceptionHandler(valiktorExceptionHandler: ValiktorExceptionHandler) =
        MissingKotlinParameterExceptionHandler(valiktorExceptionHandler)
}