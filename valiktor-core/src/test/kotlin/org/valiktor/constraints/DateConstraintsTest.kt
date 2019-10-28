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

package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages
import kotlin.test.Test

class TodayTest {

    @Test
    fun `should validate messages`() {
        assertThat(Today.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be today"),
            entry(SupportedLocales.CA, "Ha de ser avui"),
            entry(SupportedLocales.DE, "Muss heute sein"),
            entry(SupportedLocales.EN, "Must be today"),
            entry(SupportedLocales.ES, "Tiene que ser hoy"),
            entry(SupportedLocales.PT_BR, "Deve ser hoje"))
    }
}

class NotTodayTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotToday.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be today"),
            entry(SupportedLocales.CA, "No pot ser avui"),
            entry(SupportedLocales.DE, "Darf nicht heute sein"),
            entry(SupportedLocales.EN, "Must not be today"),
            entry(SupportedLocales.ES, "No puede ser hoy"),
            entry(SupportedLocales.PT_BR, "Não deve ser hoje"))
    }
}
