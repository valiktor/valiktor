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

import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.valiktor.i18n.Formatter
import org.valiktor.i18n.MessageBundle

/**
 * Represents the formatter for [LocalTime] values
 *
 * @author Rodolpho S. Couto
 * @since 0.6.0
 */
object LocalTimeFormatter : Formatter<LocalTime> {

    override fun format(value: LocalTime, messageBundle: MessageBundle): String =
        value.toString(DateTimeFormat.mediumTime().withLocale(messageBundle.locale))
}