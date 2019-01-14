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
import org.springframework.http.codec.CodecConfigurer
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.web.reactive.ReactiveMissingKotlinParameterExceptionHandler
import org.valiktor.springframework.web.reactive.ReactiveValiktorExceptionHandler

/**
 * Represents the SpringBoot Auto Configuration for Spring WebMvc exception handlers
 *
 * @author Rodolpho S. Couto
 * @since 0.3.0
 */
@Configuration
@ConditionalOnClass(name = ["org.springframework.web.reactive.DispatcherHandler"])
@ConditionalOnBean(type = [
    "org.valiktor.springframework.config.ValiktorConfiguration",
    "org.springframework.http.codec.CodecConfigurer"
])
class ValiktorWebFluxAutoConfiguration {

    /**
     * Creates a [ReactiveValiktorExceptionHandler]
     *
     * @return the respective [ReactiveValiktorExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    fun reactiveValiktorExceptionHandler(valiktorConfiguration: ValiktorConfiguration, codecConfigurer: CodecConfigurer) =
        ReactiveValiktorExceptionHandler(valiktorConfiguration, codecConfigurer)

    /**
     * Creates a [ReactiveMissingKotlinParameterExceptionHandler]
     *
     * @return the respective [ReactiveMissingKotlinParameterExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = ["com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException"])
    fun reactiveMissingKotlinParameterExceptionHandler() = ReactiveMissingKotlinParameterExceptionHandler()
}