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

import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.FilteredClassLoader
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import org.valiktor.springframework.config.ValiktorConfiguration
import org.valiktor.springframework.http.DefaultValiktorExceptionHandler
import org.valiktor.springframework.http.ValiktorExceptionHandler
import org.valiktor.springframework.http.ValiktorResponse
import java.util.Locale
import kotlin.test.Test

class ValiktorExceptionHandlerAutoConfigurationTest {

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(
                ValiktorAutoConfiguration::class.java,
                ValiktorExceptionHandlerAutoConfiguration::class.java
            )
        )

    @Test
    fun `should not create ValiktorExceptionHandler without ValiktorConfiguration`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(ValiktorConfiguration::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorExceptionHandler::class.java)
            }
    }

    @Test
    fun `should not create ValiktorExceptionHandler without DefaultValiktorExceptionHandler`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(DefaultValiktorExceptionHandler::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorExceptionHandler::class.java)
            }
    }

    @Test
    fun `should create ValiktorExceptionHandler`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorExceptionHandler::class.java)
                assertThat(context.getBean(ValiktorExceptionHandler::class.java))
                    .isEqualTo(context.getBean(DefaultValiktorExceptionHandler::class.java))
            }
    }

    @Test
    fun `should create ValiktorExceptionHandler with custom bean`() {
        this.contextRunner
            .withUserConfiguration(CustomValiktorExceptionHandlerConfiguration::class.java)
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorExceptionHandler::class.java)
                assertThat(context.getBean(ValiktorExceptionHandler::class.java))
                    .isEqualTo(context.getBean(CustomValiktorExceptionHandler::class.java))
            }
    }
}

private data class CustomBody(
    val errors: Map<String, String>
)

private class CustomValiktorExceptionHandler(
    private val config: ValiktorConfiguration
) : ValiktorExceptionHandler<CustomBody> {

    override fun handle(exception: ConstraintViolationException, locale: Locale): ValiktorResponse<CustomBody> =
        ValiktorResponse(
            body = CustomBody(
                errors = exception.constraintViolations
                    .mapToMessage(baseName = config.baseBundleName, locale = locale)
                    .map { constraintViolation -> constraintViolation.property to constraintViolation.message }
                    .toMap()
            )
        )
}

@Configuration
private class CustomValiktorExceptionHandlerConfiguration {

    @Bean
    fun valiktorExceptionHandler(config: ValiktorConfiguration): ValiktorExceptionHandler<*> =
        CustomValiktorExceptionHandler(config)
}
