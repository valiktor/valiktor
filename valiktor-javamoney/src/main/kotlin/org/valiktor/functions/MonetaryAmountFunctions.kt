/*
 * Copyright 2018 https://www.valiktor.org
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
import org.valiktor.constraints.CurrencyEquals
import org.valiktor.constraints.CurrencyIn
import org.valiktor.constraints.CurrencyNotEquals
import org.valiktor.constraints.CurrencyNotIn
import javax.money.CurrencyUnit
import javax.money.MonetaryAmount

/**
 * Validates if the currency unit is equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyEqualTo(currency: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyEquals(currency), { it == null || it.currency == currency })

/**
 * Validates if the currency unit isn't equal to another value
 *
 * @param currency specifies the currency unit to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyNotEqualTo(currency: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyNotEquals(currency), { it == null || it.currency != currency })

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyIn(vararg currencies: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyIn(currencies.toSet()), { it == null || currencies.contains(it.currency) })

/**
 * Validates if the currency unit is equal to one of the values
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyIn(currencies: Iterable<CurrencyUnit>): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyIn(currencies), { it == null || currencies.contains(it.currency) })

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyNotIn(vararg currencies: CurrencyUnit): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyNotIn(currencies.toSet()), { it == null || !currencies.contains(it.currency) })

/**
 * Validates if the currency unit isn't equal to any value
 *
 * @param currencies specifies the currencies to be compared
 * @receiver the property to be validated
 * @return the same receiver property
 */
fun <E> Validator<E>.Property<MonetaryAmount?>.hasCurrencyNotIn(currencies: Iterable<CurrencyUnit>): Validator<E>.Property<MonetaryAmount?> =
        this.validate(CurrencyNotIn(currencies), { it == null || !currencies.contains(it.currency) })