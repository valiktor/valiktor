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

import org.joda.time.LocalDate
import org.valiktor.Validator
import org.valiktor.constraints.NotToday
import org.valiktor.constraints.Today

/**
 * Validates if the [LocalDate] property is today
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, LocalDate?>.isToday(): Validator<E>.ReceiverValidator<E, LocalDate?> =
    this.validate(Today) { it == null || it == LocalDate.now() }

/**
 * Validates if the [LocalDate] property isn't today
 *
 * @receiver the property to be validated
 * @return the same receiver validator
 */
fun <E> Validator<E>.ReceiverValidator<E, LocalDate?>.isNotToday(): Validator<E>.ReceiverValidator<E, LocalDate?> =
    this.validate(NotToday) { it == null || it != LocalDate.now() }
