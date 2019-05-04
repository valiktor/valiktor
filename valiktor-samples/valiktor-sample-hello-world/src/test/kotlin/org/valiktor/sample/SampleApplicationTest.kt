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

package org.valiktor.sample

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class SampleApplicationTest {

    @Test
    fun `should validate employee and print properties and messages`() {
        val out = ByteArrayOutputStream().use { out ->
            PrintStream(out).use { stdout ->
                System.setOut(stdout)
                main()
                out.toString()
            }
        }

        assertEquals(out,
            "id: Must be greater than 0${System.lineSeparator()}" +
                "name: Size must be between 3 and 30${System.lineSeparator()}" +
                "email: Must be a valid email address${System.lineSeparator()}" +
                "salary: Decimal digits must be less than or equal to 2${System.lineSeparator()}"
        )
    }
}