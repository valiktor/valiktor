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

package org.valiktor.i18n

import org.valiktor.i18n.FormattersFixture.TestFormatter
import org.valiktor.i18n.FormattersFixture.TestObject
import org.valiktor.i18n.FormattersFixture.TestParentL
import org.valiktor.i18n.FormattersFixture.TestParentLFormatter
import org.valiktor.i18n.FormattersFixture.TestParentParentL
import org.valiktor.i18n.FormattersFixture.TestParentParentLFormatter
import org.valiktor.i18n.FormattersFixture.TestParentParentParent
import org.valiktor.i18n.FormattersFixture.TestParentParentParentFormatter
import org.valiktor.i18n.FormattersFixture.TestParentParentR
import org.valiktor.i18n.FormattersFixture.TestParentParentRFormatter
import org.valiktor.i18n.FormattersFixture.TestParentR
import org.valiktor.i18n.FormattersFixture.TestParentRFormatter
import org.valiktor.i18n.formatters.AnyFormatter
import org.valiktor.i18n.formatters.ArrayFormatter
import org.valiktor.i18n.formatters.CalendarFormatter
import org.valiktor.i18n.formatters.DateFormatter
import org.valiktor.i18n.formatters.IterableFormatter
import org.valiktor.i18n.formatters.NumberFormatter
import java.util.Calendar
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

private object FormattersFixture {

    interface TestParentParentParent
    interface TestParentParentL : TestParentParentParent
    interface TestParentParentR : TestParentParentParent
    interface TestParentL : TestParentParentL, TestParentParentR
    interface TestParentR : TestParentParentL, TestParentParentR

    object TestObject : TestParentL, TestParentR {
        override fun toString(): String = "TestObject"
    }

    object TestFormatter : Formatter<TestObject> {
        override fun format(value: TestObject, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentLFormatter : Formatter<TestParentL> {
        override fun format(value: TestParentL, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentRFormatter : Formatter<TestParentR> {
        override fun format(value: TestParentR, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentParentLFormatter : Formatter<TestParentParentL> {
        override fun format(value: TestParentParentL, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentParentRFormatter : Formatter<TestParentParentR> {
        override fun format(value: TestParentParentR, messageBundle: MessageBundle): String = value.toString()
    }

    object TestParentParentParentFormatter : Formatter<TestParentParentParent> {
        override fun format(value: TestParentParentParent, messageBundle: MessageBundle): String = value.toString()
    }
}

class FormattersTest {

    @BeforeTest
    fun `remove formatters`() {
        Formatters -= TestObject::class
        Formatters -= TestParentL::class
        Formatters -= TestParentR::class
        Formatters -= TestParentParentL::class
        Formatters -= TestParentParentR::class
        Formatters -= TestParentParentParent::class
    }

    @Test
    fun `should get default formatters`() {
        assertEquals(Formatters[Any::class], AnyFormatter)
        assertEquals(Formatters[Number::class], NumberFormatter)
        assertEquals(Formatters[Date::class], DateFormatter)
        assertEquals(Formatters[Calendar::class], CalendarFormatter)
        assertEquals<Formatter<Iterable<Any>>>(Formatters[Iterable::class], IterableFormatter)
        assertEquals(Formatters[Array<Any>::class], ArrayFormatter)
    }

    @Test
    fun `should get any formatter`() {
        assertEquals(Formatters[TestObject::class], AnyFormatter)
    }

    @Test
    fun `should get object formatter`() {
        Formatters[TestObject::class] = TestFormatter
        Formatters[TestParentL::class] = TestParentLFormatter
        Formatters[TestParentR::class] = TestParentRFormatter

        assertEquals(Formatters[TestObject::class], TestFormatter)
    }

    @Test
    fun `should get left parent formatter`() {
        Formatters[TestParentL::class] = TestParentLFormatter
        Formatters[TestParentR::class] = TestParentRFormatter

        assertEquals(Formatters[TestObject::class], TestParentLFormatter)
    }

    @Test
    fun `should get right parent formatter`() {
        Formatters[TestParentR::class] = TestParentRFormatter
        Formatters[TestParentParentL::class] = TestParentParentLFormatter

        assertEquals(Formatters[TestObject::class], TestParentRFormatter)
    }

    @Test
    fun `should get left parent parent formatter`() {
        Formatters[TestParentParentL::class] = TestParentParentLFormatter
        Formatters[TestParentParentR::class] = TestParentParentRFormatter

        assertEquals(Formatters[TestObject::class], TestParentParentLFormatter)
    }

    @Test
    fun `should get right parent parent formatter`() {
        Formatters[TestParentParentR::class] = TestParentParentRFormatter
        Formatters[TestParentParentParent::class] = TestParentParentParentFormatter

        assertEquals(Formatters[TestObject::class], TestParentParentRFormatter)
    }

    @Test
    fun `should get parent parent parent formatter`() {
        Formatters[TestParentParentParent::class] = TestParentParentParentFormatter

        assertEquals(Formatters[TestObject::class], TestParentParentParentFormatter)
    }
}
