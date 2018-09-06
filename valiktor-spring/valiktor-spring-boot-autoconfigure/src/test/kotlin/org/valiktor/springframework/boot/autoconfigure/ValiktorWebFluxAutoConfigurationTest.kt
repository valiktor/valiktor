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
import org.springframework.http.codec.CodecConfigurer
import org.springframework.http.codec.support.DefaultServerCodecConfigurer
import org.springframework.web.reactive.DispatcherHandler
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.web.reactive.ValiktorJacksonReactiveExceptionHandler
import org.valiktor.springframework.web.reactive.ValiktorReactiveExceptionHandler
import kotlin.test.Test

class ValiktorWebFluxAutoConfigurationTest {

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            ValiktorAutoConfiguration::class.java,
            ValiktorWebFluxAutoConfiguration::class.java,
            CodecsCustomConfiguration::class.java
        ))

    @Test
    fun `should not create ValiktorReactiveExceptionHandler without ValiktorConfiguration`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(ValiktorConfiguration::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorReactiveExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ValiktorReactiveExceptionHandler without DispatcherHandler`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(DispatcherHandler::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorReactiveExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ValiktorJacksonReactiveExceptionHandler without MissingKotlinParameterException`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(MissingKotlinParameterException::class.java))
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorReactiveExceptionHandler::class.java)
                assertThat(context).doesNotHaveBean(ValiktorJacksonReactiveExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ValiktorReactiveExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorReactiveExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ValiktorReactiveExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebFluxCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorReactiveExceptionHandler::class.java)
                assertThat(context.getBean(ValiktorReactiveExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebFluxCustomConfiguration::class.java)
                        .valiktorReactiveExceptionHandler(
                            context.getBean(ValiktorConfiguration::class.java),
                            context.getBean(CodecConfigurer::class.java)
                        ))
            }
    }

    @Test
    fun `should create ValiktorJacksonReactiveExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorJacksonReactiveExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ValiktorJacksonReactiveExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(
                ValiktorWebFluxCustomConfiguration::class.java
            )
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorJacksonReactiveExceptionHandler::class.java)
                assertThat(context.getBean(ValiktorJacksonReactiveExceptionHandler::class.java)).isSameAs(
                    context.getBean(ValiktorWebFluxCustomConfiguration::class.java)
                        .valiktorJacksonReactiveExceptionHandler())
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
    fun valiktorReactiveExceptionHandler(valiktorConfiguration: ValiktorConfiguration, codecConfigurer: CodecConfigurer) =
        ValiktorReactiveExceptionHandler(valiktorConfiguration, codecConfigurer)

    @Bean
    fun valiktorJacksonReactiveExceptionHandler() = ValiktorJacksonReactiveExceptionHandler()
}