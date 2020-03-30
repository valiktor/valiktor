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

package org.valiktor

import org.joda.money.BigMoney
import org.joda.money.CurrencyMismatchException
import org.joda.money.Money
import java.math.BigDecimal
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

internal fun <T : Number> T.asBigDecimal() =
    when (this) {
        is Byte -> this.toInt().toBigDecimal()
        is Short -> this.toInt().toBigDecimal()
        is Int -> this.toBigDecimal()
        is Long -> this.toBigDecimal()
        is Float -> this.toBigDecimal()
        is Double -> this.toBigDecimal()
        is BigInteger -> this.toBigDecimal()
        is BigDecimal -> this
        is AtomicInteger -> this.toInt().toBigDecimal()
        is AtomicLong -> this.toLong().toBigDecimal()
        else -> BigDecimal(this.toString())
    }

internal fun BigMoney.safelyEquals(value: BigMoney) =
    try {
        this.compareTo(value) == 0
    } catch (ex: CurrencyMismatchException) {
        false
    }

internal fun Money.safelyEquals(value: Money) =
    try {
        this.compareTo(value) == 0
    } catch (ex: CurrencyMismatchException) {
        false
    }
