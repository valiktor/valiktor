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