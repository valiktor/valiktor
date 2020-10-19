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
import java.text.DateFormat
import java.util.Calendar

/**
 * Represents the formatter for [Calendar] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object CalendarFormatter : Formatter<Calendar> {

    override fun format(value: Calendar, messageBundle: MessageBundle): String =
        (
            if (value.hasTime())
                DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, messageBundle.locale)
            else
                DateFormat.getDateInstance(DateFormat.MEDIUM, messageBundle.locale)
            )
            .format(value.time)

    private fun Calendar.hasTime(): Boolean {
        val hours = this.get(Calendar.HOUR_OF_DAY)
        val minutes = this.get(Calendar.MINUTE)
        val seconds = this.get(Calendar.SECOND)

        return hours + minutes + seconds > 0
    }
}
