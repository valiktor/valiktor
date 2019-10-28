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

import org.valiktor.i18n.Formatter
import org.valiktor.i18n.MessageBundle
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

/**
 * Represents the formatter for [Date] values
 *
 * @author Rodolpho S. Couto
 * @since 0.1.0
 */
object DateFormatter : Formatter<Date> {

    override fun format(value: Date, messageBundle: MessageBundle): String =
        (if (value.hasTime())
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, messageBundle.locale)
        else
            DateFormat.getDateInstance(DateFormat.MEDIUM, messageBundle.locale))
            .format(value)

    private fun Date.hasTime(): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = this

        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        return hours + minutes + seconds > 0
    }
}
