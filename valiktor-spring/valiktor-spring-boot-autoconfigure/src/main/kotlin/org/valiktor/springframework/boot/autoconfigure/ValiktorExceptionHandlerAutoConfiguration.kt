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

package org.valiktor.springframework.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.http.DefaultValiktorExceptionHandler
import org.valiktor.springframework.http.ValiktorExceptionHandler

/**
 * Represents the SpringBoot Auto Configuration for [ValiktorExceptionHandler]
 *
 * @author Rodolpho S. Couto
 * @see ValiktorExceptionHandler
 * @since 0.11.0
 */
@Configuration
@ConditionalOnClass(
    name = [
        "org.valiktor.springframework.http.ValiktorExceptionHandler",
        "org.valiktor.springframework.http.DefaultValiktorExceptionHandler"
    ]
)
@ConditionalOnBean(type = ["org.valiktor.springframework.config.ValiktorConfiguration"])
class ValiktorExceptionHandlerAutoConfiguration {

    /**
     * Creates a [DefaultValiktorExceptionHandler] based on the config
     *
     * @return the respective [ValiktorConfiguration]
     */
    @Bean
    @ConditionalOnMissingBean
    fun valiktorExceptionHandler(config: ValiktorConfiguration): ValiktorExceptionHandler<*> =
        DefaultValiktorExceptionHandler(config)
}
