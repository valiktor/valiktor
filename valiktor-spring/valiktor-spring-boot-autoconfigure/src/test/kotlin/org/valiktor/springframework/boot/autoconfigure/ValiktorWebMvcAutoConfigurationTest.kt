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

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.FilteredClassLoader
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.DispatcherServlet
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.web.controller.ValiktorExceptionHandler
import org.valiktor.springframework.web.controller.MissingKotlinParameterExceptionHandler
import kotlin.test.Test

class ValiktorWebMvcAutoConfigurationTest {

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            ValiktorAutoConfiguration::class.java,
            ValiktorWebMvcAutoConfiguration::class.java
        ))

    @Test
    fun `should not create ValiktorExceptionHandler without ValiktorConfiguration`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(ValiktorConfiguration::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ValiktorExceptionHandler without DispatcherServlet`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(DispatcherServlet::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create MissingKotlinParameterExceptionHandler without MissingKotlinParameterException`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(MissingKotlinParameterException::class.java))
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorExceptionHandler::class.java)
                assertThat(context).doesNotHaveBean(MissingKotlinParameterExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ValiktorExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ValiktorExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebMvcCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorExceptionHandler::class.java)
                assertThat(context.getBean(ValiktorExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebMvcCustomConfiguration::class.java)
                        .valiktorExceptionHandler(context.getBean(ValiktorConfiguration::class.java)))
            }
    }

    @Test
    fun `should create MissingKotlinParameterExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(MissingKotlinParameterExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create MissingKotlinParameterExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebMvcCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(MissingKotlinParameterExceptionHandler::class.java)
                assertThat(context.getBean(MissingKotlinParameterExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebMvcCustomConfiguration::class.java)
                        .missingKotlinParameterExceptionHandler(context.getBean(ValiktorExceptionHandler::class.java)))
            }
    }
}

@Configuration
private class ValiktorWebMvcCustomConfiguration {

    @Bean
    fun valiktorExceptionHandler(valiktorConfiguration: ValiktorConfiguration) =
        ValiktorExceptionHandler(valiktorConfiguration)

    @Bean
    fun missingKotlinParameterExceptionHandler(valiktorExceptionHandler: ValiktorExceptionHandler) =
        MissingKotlinParameterExceptionHandler(valiktorExceptionHandler)
}