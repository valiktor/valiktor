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

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.CodecConfigurer
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.web.reactive.ValiktorJacksonReactiveExceptionHandler
import org.valiktor.springframework.web.reactive.ValiktorReactiveExceptionHandler

/**
 * Represents the SpringBoot Auto Configuration for Spring WebMvc exception handlers
 *
 * @author Rodolpho S. Couto
 * @since 0.3.0
 */
@Configuration
@ConditionalOnClass(name = [
    "org.valiktor.springframework.config.ValiktorConfiguration",
    "org.springframework.web.reactive.DispatcherHandler",
    "org.springframework.http.codec.CodecConfigurer"])
class ValiktorWebFluxAutoConfiguration {

    /**
     * Creates a [ValiktorReactiveExceptionHandler]
     *
     * @return the respective [ValiktorReactiveExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    fun valiktorReactiveExceptionHandler(valiktorConfiguration: ValiktorConfiguration, codecConfigurer: CodecConfigurer) =
        ValiktorReactiveExceptionHandler(valiktorConfiguration, codecConfigurer)

    /**
     * Creates a [ValiktorJacksonReactiveExceptionHandler]
     *
     * @return the respective [ValiktorJacksonReactiveExceptionHandler]
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = ["com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException"])
    fun valiktorJacksonReactiveExceptionHandler() = ValiktorJacksonReactiveExceptionHandler()
}