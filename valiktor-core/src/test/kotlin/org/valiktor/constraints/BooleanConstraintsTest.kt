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

package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages
import kotlin.test.Test

class TrueTest {

    @Test
    fun `should validate messages`() {
        assertThat(True.interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be true"),
            entry(SupportedLocales.CA, "Ha de ser cert"),
            entry(SupportedLocales.DE, "Muss wahr sein"),
            entry(SupportedLocales.EN, "Must be true"),
            entry(SupportedLocales.ES, "Tiene que ser verdadero"),
            entry(SupportedLocales.JA, "true である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser verdadeiro")
        )
    }
}

class FalseTest {

    @Test
    fun `should validate messages`() {
        assertThat(False.interpolatedMessages()).containsOnly(
            entry(SupportedLocales.DEFAULT, "Must be false"),
            entry(SupportedLocales.CA, "Ha de ser fals"),
            entry(SupportedLocales.DE, "Muss falsch sein"),
            entry(SupportedLocales.EN, "Must be false"),
            entry(SupportedLocales.ES, "Tiene que ser falso"),
            entry(SupportedLocales.JA, "false である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser falso")
        )
    }
}
