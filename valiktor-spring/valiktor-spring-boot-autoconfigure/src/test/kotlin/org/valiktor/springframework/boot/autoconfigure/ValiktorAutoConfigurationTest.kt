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
import org.valiktor.springframework.config.ValiktorConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ValiktorAutoConfigurationTest {

    private val contextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations
            .of(ValiktorAutoConfiguration::class.java))

    @Test
    fun `should not create ValiktorConfiguration without ValiktorConfiguration`() {
        this.contextRunner
            .withClassLoader(FilteredClassLoader(ValiktorConfiguration::class.java))
            .run { context ->
                assertThat(context).doesNotHaveBean(ValiktorConfiguration::class.java)
            }
    }

    @Test
    fun `should create ValiktorConfiguration with default properties`() {
        this.contextRunner
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorConfiguration::class.java)
                assertNull(context.getBean(ValiktorConfiguration::class.java).baseBundleName)
            }
    }

    @Test
    fun `should create ValiktorConfiguration with custom properties`() {
        this.contextRunner
            .withPropertyValues("valiktor.baseBundleName=test")
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorConfiguration::class.java)
                assertEquals(context.getBean(ValiktorConfiguration::class.java).baseBundleName, "test")
            }
    }

    @Test
    fun `should create ValiktorConfiguration with custom bean`() {
        this.contextRunner
            .withUserConfiguration(ValiktorCustomConfiguration::class.java)
            .run { context ->
                assertThat(context).hasSingleBean(ValiktorConfiguration::class.java)
                assertEquals(context.getBean(ValiktorConfiguration::class.java).baseBundleName, "testMessages")
            }
    }
}

@Configuration
private class ValiktorCustomConfiguration {

    @Bean
    fun valiktorConfiguration(): ValiktorConfiguration =
        ValiktorConfiguration(baseBundleName = "testMessages")
}
