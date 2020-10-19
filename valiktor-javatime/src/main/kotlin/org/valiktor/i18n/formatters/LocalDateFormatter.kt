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

import org.valiktor.i18n.Formatter
import org.valiktor.i18n.MessageBundle
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Represents the formatter for [LocalDate] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object LocalDateFormatter : Formatter<LocalDate> {

    override fun format(value: LocalDate, messageBundle: MessageBundle): String =
        value.format(
            DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(messageBundle.locale)
        )
}
