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

package org.valiktor.functions

import org.valiktor.Validator
import org.valiktor.constraints.NotToday
import org.valiktor.constraints.Today
import java.util.Calendar
import java.util.Date

/**
 * Validates if the [Date] property is today
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Date?>.isToday(): Validator<E>.ReceiverValidator<E, Date?> {
    val start = Calendar.getInstance()
    start.set(Calendar.HOUR_OF_DAY, 0)
    start.set(Calendar.MINUTE, 0)
    start.set(Calendar.SECOND, 0)
    start.set(Calendar.MILLISECOND, 0)

    val end = Calendar.getInstance()
    end.set(Calendar.HOUR_OF_DAY, 23)
    end.set(Calendar.MINUTE, 59)
    end.set(Calendar.SECOND, 59)
    end.set(Calendar.MILLISECOND, 999)

    return this.validate(Today) { it == null || it in start.time.rangeTo(end.time) }
}

/**
 * Validates if the [Date] property isn't today
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, Date?>.isNotToday(): Validator<E>.ReceiverValidator<E, Date?> {
    val start = Calendar.getInstance()
    start.set(Calendar.HOUR_OF_DAY, 0)
    start.set(Calendar.MINUTE, 0)
    start.set(Calendar.SECOND, 0)
    start.set(Calendar.MILLISECOND, 0)

    val end = Calendar.getInstance()
    end.set(Calendar.HOUR_OF_DAY, 23)
    end.set(Calendar.MINUTE, 59)
    end.set(Calendar.SECOND, 59)
    end.set(Calendar.MILLISECOND, 999)

    return this.validate(NotToday) { it == null || it !in start.time.rangeTo(end.time) }
}
