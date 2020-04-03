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

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.FilteredClassLoader
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.CodecConfigurer
import org.springframework.http.codec.support.DefaultServerCodecConfigurer
import org.springframework.web.reactive.DispatcherHandler
import org.valiktor.springframework.http.ValiktorExceptionHandler
import org.valiktor.springframework.http.webflux.ReactiveConstraintViolationExceptionHandler
import org.valiktor.springframework.http.webflux.ReactiveInvalidFormatExceptionHandler
import org.valiktor.springframework.http.webflux.ReactiveMissingKotlinParameterExceptionHandler
import kotlin.test.Test

class ValiktorWebFluxAutoConfigurationTest {

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(
                ValiktorAutoConfiguration::class.java,
                ValiktorExceptionHandlerAutoConfiguration::class.java,
                ValiktorWebFluxAutoConfiguration::class.java,
                CodecsCustomConfiguration::class.java
            )
        )

    @Test
    fun `should not create ReactiveConstraintViolationExceptionHandler without ValiktorExceptionHandler`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(ValiktorExceptionHandler::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ReactiveConstraintViolationExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ReactiveConstraintViolationExceptionHandler without DispatcherHandler`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(DispatcherHandler::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ReactiveConstraintViolationExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ReactiveConstraintViolationExceptionHandler without CodecConfigurer`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(CodecConfigurer::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ReactiveConstraintViolationExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ReactiveInvalidFormatExceptionHandler without InvalidFormatException`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(InvalidFormatException::class.java))
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveConstraintViolationExceptionHandler::class.java)
                assertThat(context).doesNotHaveBean(ReactiveInvalidFormatExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ReactiveMissingKotlinParameterExceptionHandler without MissingKotlinParameterException`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(MissingKotlinParameterException::class.java))
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveConstraintViolationExceptionHandler::class.java)
                assertThat(context).doesNotHaveBean(ReactiveMissingKotlinParameterExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ReactiveConstraintViolationExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveConstraintViolationExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ReactiveConstraintViolationExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebFluxCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveConstraintViolationExceptionHandler::class.java)
                assertThat(context.getBean(ReactiveConstraintViolationExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebFluxCustomConfiguration::class.java)
                        .reactiveConstraintViolationExceptionHandler(
                            handler = context.getBean(ValiktorExceptionHandler::class.java),
                            codecConfigurer = context.getBean(CodecConfigurer::class.java)
                        )
                )
            }
    }

    @Test
    fun `should create ReactiveInvalidFormatExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveInvalidFormatExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ReactiveInvalidFormatExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebFluxCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveInvalidFormatExceptionHandler::class.java)
                assertThat(context.getBean(ReactiveInvalidFormatExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebFluxCustomConfiguration::class.java)
                        .reactiveInvalidFormatExceptionHandler()
                )
            }
    }

    @Test
    fun `should create ReactiveMissingKotlinParameterExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveMissingKotlinParameterExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ReactiveMissingKotlinParameterExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebFluxCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(ReactiveMissingKotlinParameterExceptionHandler::class.java)
                assertThat(context.getBean(ReactiveMissingKotlinParameterExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebFluxCustomConfiguration::class.java)
                        .reactiveMissingKotlinParameterExceptionHandler()
                )
            }
    }
}

@Configuration
private class CodecsCustomConfiguration {

    @Bean
    fun codecConfigurer(): CodecConfigurer = DefaultServerCodecConfigurer()
}

@Configuration
private class ValiktorWebFluxCustomConfiguration {

    @Bean
    fun reactiveConstraintViolationExceptionHandler(
        handler: ValiktorExceptionHandler<*>,
        codecConfigurer: CodecConfigurer
    ) = ReactiveConstraintViolationExceptionHandler(handler, codecConfigurer)

    @Bean
    fun reactiveInvalidFormatExceptionHandler() = ReactiveInvalidFormatExceptionHandler()

    @Bean
    fun reactiveMissingKotlinParameterExceptionHandler() = ReactiveMissingKotlinParameterExceptionHandler()
}
