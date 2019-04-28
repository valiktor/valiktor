/*
 * Copyright 2018-2019 https://www.valiktor.org
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

package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import kotlin.test.Test

private object AnyFormatterFixture {

    enum class TestEnum { E1, E2 }
    object TestObject {
        override fun toString(): String = "TestObject"
    }
}

class AnyFormatterTest {

    @Test
    fun `should format Any value`() {
        assertThat(Formatters[AnyFormatterFixture.TestObject::class].formatAllSupportedLocales(AnyFormatterFixture.TestObject)).containsExactly(
            entry(SupportedLocales.DEFAULT, "TestObject"),
            entry(SupportedLocales.EN, "TestObject"),
            entry(SupportedLocales.PT_BR, "TestObject"))
    }

    @Test
    fun `should format Enum value`() {
        assertThat(Formatters[Enum::class].formatAllSupportedLocales(AnyFormatterFixture.TestEnum.E1)).containsExactly(
            entry(SupportedLocales.DEFAULT, "E1"),
            entry(SupportedLocales.EN, "E1"),
            entry(SupportedLocales.PT_BR, "E1"))
    }

    @Test
    fun `should format String value`() {
        assertThat(Formatters[String::class].formatAllSupportedLocales("test")).containsExactly(
            entry(SupportedLocales.DEFAULT, "test"),
            entry(SupportedLocales.EN, "test"),
            entry(SupportedLocales.PT_BR, "test"))
    }

    @Test
    fun `should format Char value`() {
        assertThat(Formatters[Char::class].formatAllSupportedLocales('A')).containsExactly(
            entry(SupportedLocales.DEFAULT, "A"),
            entry(SupportedLocales.EN, "A"),
            entry(SupportedLocales.PT_BR, "A"))
    }

    @Test
    fun `should format Boolean value`() {
        assertThat(Formatters[Boolean::class].formatAllSupportedLocales(true)).containsExactly(
            entry(SupportedLocales.DEFAULT, "true"),
            entry(SupportedLocales.EN, "true"),
            entry(SupportedLocales.PT_BR, "true"))
    }
}