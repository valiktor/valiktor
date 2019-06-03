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

package org.valiktor.functions

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.valiktor.Validator
import org.valiktor.constraints.NotToday
import org.valiktor.constraints.Today

/**
 * Validates if the [LocalDateTime] property is today
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<LocalDateTime?>.isToday(): Validator<E>.Property<LocalDateTime?> =
    this.validate(Today) { it == null || it.toLocalDate() == LocalDate.now() }

/**
 * Validates if the [LocalDateTime] property isn't today
 *
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<LocalDateTime?>.isNotToday(): Validator<E>.Property<LocalDateTime?> =
    this.validate(NotToday) { it == null || it.toLocalDate() != LocalDate.now() }