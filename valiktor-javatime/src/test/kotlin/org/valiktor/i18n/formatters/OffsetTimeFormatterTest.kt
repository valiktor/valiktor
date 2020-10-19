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

package org.valiktor.i18n.formatters

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.Formatters
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.formatAllSupportedLocales
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneOffset
import kotlin.test.Test

class OffsetTimeFormatterTest {

    @Test
    fun `should format time`() {
        assertThat(Formatters[OffsetTime::class].formatAllSupportedLocales(OffsetTime.of(LocalTime.of(23, 58, 59), ZoneOffset.UTC))).contains(
            entry(SupportedLocales.DEFAULT, "11:58:59 PM"),
            entry(SupportedLocales.DE, "23:58:59"),
            entry(SupportedLocales.EN, "11:58:59 PM"),
            entry(SupportedLocales.JA, "23:58:59"),
            entry(SupportedLocales.PT_BR, "23:58:59")
        )
    }
}
