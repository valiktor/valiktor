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

package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.valiktor.i18n.SupportedLocales
import org.valiktor.i18n.interpolatedMessages
import kotlin.test.Test

class BlankTest {

    @Test
    fun `should validate messages`() {
        assertThat(Blank.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be blank"),
            entry(SupportedLocales.CA, "Ha d'estar en blanc"),
            entry(SupportedLocales.DE, "Muss blank sein"),
            entry(SupportedLocales.EN, "Must be blank"),
            entry(SupportedLocales.ES, "Tiene que estar vacío"),
            entry(SupportedLocales.JA, "空文字である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve estar em branco")
        )
    }
}

class NotBlankTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotBlank.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be blank"),
            entry(SupportedLocales.CA, "No pot estar en blanc"),
            entry(SupportedLocales.DE, "Darf nicht blank sein"),
            entry(SupportedLocales.EN, "Must not be blank"),
            entry(SupportedLocales.ES, "No puede estar vacío"),
            entry(SupportedLocales.JA, "空文字以外である必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve estar em branco")
        )
    }
}

class LetterTest {

    @Test
    fun `should validate messages`() {
        assertThat(Letter.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be letter"),
            entry(SupportedLocales.CA, "Ha de ser lletra"),
            entry(SupportedLocales.DE, "Muss ein Buchstabe sein"),
            entry(SupportedLocales.EN, "Must be letter"),
            entry(SupportedLocales.ES, "Tiene que ser letra"),
            entry(SupportedLocales.JA, "文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser letra")
        )
    }
}

class NotLetterTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotLetter.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be letter"),
            entry(SupportedLocales.CA, "No pot se lletra"),
            entry(SupportedLocales.DE, "Darf kein Buchstabe sein"),
            entry(SupportedLocales.EN, "Must not be letter"),
            entry(SupportedLocales.ES, "No puede ser letra"),
            entry(SupportedLocales.JA, "文字以外を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve ser letra")
        )
    }
}

class DigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(Digit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be digit"),
            entry(SupportedLocales.CA, "Ha de ser número"),
            entry(SupportedLocales.DE, "Muss eine Ziffer sein"),
            entry(SupportedLocales.EN, "Must be digit"),
            entry(SupportedLocales.ES, "Tiene que ser número"),
            entry(SupportedLocales.JA, "数字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser número")
        )
    }
}

class NotDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotDigit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be digit"),
            entry(SupportedLocales.CA, "No pot ser número"),
            entry(SupportedLocales.DE, "Darf keine Ziffer sein"),
            entry(SupportedLocales.EN, "Must not be digit"),
            entry(SupportedLocales.ES, "No puede ser número"),
            entry(SupportedLocales.JA, "数字以外を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve ser número")
        )
    }
}

class LetterOrDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(LetterOrDigit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be letter or digit"),
            entry(SupportedLocales.CA, "Ha de ser lletra o número"),
            entry(SupportedLocales.DE, "Muss Buchstabe oder Ziffer sein"),
            entry(SupportedLocales.EN, "Must be letter or digit"),
            entry(SupportedLocales.ES, "Tiene que ser letra o número"),
            entry(SupportedLocales.JA, "文字や数字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser letra ou número")
        )
    }
}

class NotLetterOrDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotLetterOrDigit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be letter or digit"),
            entry(SupportedLocales.CA, "No pot ser lletra o número"),
            entry(SupportedLocales.DE, "Darf weder Buchstabe noch Ziffer sein"),
            entry(SupportedLocales.EN, "Must not be letter or digit"),
            entry(SupportedLocales.ES, "No puede ser letra o número"),
            entry(SupportedLocales.JA, "文字や数字以外を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve ser letra ou número")
        )
    }
}

class UpperCaseTest {

    @Test
    fun `should validate messages`() {
        assertThat(UpperCase.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be upper case"),
            entry(SupportedLocales.CA, "Ha de ser majúscula"),
            entry(SupportedLocales.DE, "Muss in Großbuchstaben sein"),
            entry(SupportedLocales.EN, "Must be upper case"),
            entry(SupportedLocales.ES, "Tiene que ser mayúsculo"),
            entry(SupportedLocales.JA, "大文字で指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser maiúsculo")
        )
    }
}

class LowerCaseTest {

    @Test
    fun `should validate messages`() {
        assertThat(LowerCase.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be lower case"),
            entry(SupportedLocales.CA, "Ha de ser minúscula"),
            entry(SupportedLocales.DE, "Muss in Kleinbuchstaben sein"),
            entry(SupportedLocales.EN, "Must be lower case"),
            entry(SupportedLocales.ES, "No puede ser minúsculo"),
            entry(SupportedLocales.JA, "小文字で指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser minúsculo")
        )
    }
}

class MatchesTest {

    @Test
    fun `should validate messages`() {
        assertThat(Matches(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must match ^[0-9]*\$"),
            entry(SupportedLocales.CA, "Ha de correspondre a l'expressió regular ^[0-9]*\$"),
            entry(SupportedLocales.DE, "Muss mit ^[0-9]*\$ übereinstimmen"),
            entry(SupportedLocales.EN, "Must match ^[0-9]*\$"),
            entry(SupportedLocales.ES, "Tiene que corresponder a la expresión regular ^[0-9]*\$"),
            entry(SupportedLocales.JA, "^[0-9]*\$ と一致する文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve corresponder ao padrão ^[0-9]*\$")
        )
    }
}

class NotMatchTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotMatch(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not match ^[0-9]*\$"),
            entry(SupportedLocales.CA, "No pot correspondre a l'expressió regular ^[0-9]*\$"),
            entry(SupportedLocales.DE, "Darf nicht mit ^[0-9]*\$ übereinstimmen"),
            entry(SupportedLocales.EN, "Must not match ^[0-9]*\$"),
            entry(SupportedLocales.ES, "No puede corresponder la expresión regular ^[0-9]*\$"),
            entry(SupportedLocales.JA, "^[0-9]*\$ と一致しない文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve corresponder ao padrão ^[0-9]*\$")
        )
    }
}

class ContainsRegexTest {

    @Test
    fun `should validate messages`() {
        assertThat(ContainsRegex(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.CA, "Ha de contenir l'expressió regular ^[0-9]*\$"),
            entry(SupportedLocales.DE, "Muss das Muster ^[0-9]*\$ enthalten"),
            entry(SupportedLocales.EN, "Must contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.ES, "Tiene que contener la expresión regular ^[0-9]*\$"),
            entry(SupportedLocales.JA, "^[0-9]*\$ を含む文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve conter o padrão ^[0-9]*\$")
        )
    }
}

class NotContainRegexTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotContainRegex(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.CA, "No pot contenir l'expressió regular ^[0-9]*\$"),
            entry(SupportedLocales.DE, "Darf das Muster ^[0-9]*\$ nicht enthalten"),
            entry(SupportedLocales.EN, "Must not contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.ES, "No puede contener la expresión regular ^[0-9]*\$"),
            entry(SupportedLocales.JA, "^[0-9]*\$ を含まない文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve conter o padrão ^[0-9]*\$")
        )
    }
}

class StartsWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(StartsWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must start with test"),
            entry(SupportedLocales.CA, "Ha de començar amb test"),
            entry(SupportedLocales.DE, "Muss mit test beginnen"),
            entry(SupportedLocales.EN, "Must start with test"),
            entry(SupportedLocales.ES, "Tiene que comenzar con test"),
            entry(SupportedLocales.JA, "test から始まる文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve começar com test")
        )
    }
}

class NotStartWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotStartWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not start with test"),
            entry(SupportedLocales.CA, "No pot començar amb test"),
            entry(SupportedLocales.DE, "Darf nicht mit test beginnen"),
            entry(SupportedLocales.EN, "Must not start with test"),
            entry(SupportedLocales.ES, "No puede comenzar con test"),
            entry(SupportedLocales.JA, "test 以外から始まる文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve começar com test")
        )
    }
}

class EndsWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(EndsWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must end with test"),
            entry(SupportedLocales.CA, "Ha d'acabar amb test"),
            entry(SupportedLocales.DE, "Muss mit test enden"),
            entry(SupportedLocales.EN, "Must end with test"),
            entry(SupportedLocales.ES, "Tiene que terminar con test"),
            entry(SupportedLocales.JA, "test で終わる文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Deve terminar com test")
        )
    }
}

class NotEndWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEndWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not end with test"),
            entry(SupportedLocales.CA, "No pot acabar amb test"),
            entry(SupportedLocales.DE, "Darf nicht mit test enden"),
            entry(SupportedLocales.EN, "Must not end with test"),
            entry(SupportedLocales.ES, "No puede terminar con test"),
            entry(SupportedLocales.JA, "test 以外で終わる文字を指定する必要があります"),
            entry(SupportedLocales.PT_BR, "Não deve terminar com test")
        )
    }
}

class EmailTest {

    @Test
    fun `should validate messages`() {
        assertThat(Email.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be a valid email address"),
            entry(SupportedLocales.CA, "Ha de ser una adreça d'e-mail vàlida"),
            entry(SupportedLocales.DE, "Muss eine gültige E-Mail-Adresse sein"),
            entry(SupportedLocales.EN, "Must be a valid email address"),
            entry(SupportedLocales.ES, "Tiene que ser una dirección de e-mail válida"),
            entry(SupportedLocales.JA, "有効なメールアドレス形式である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser um endereço de e-mail válido")
        )
    }
}

class WebsiteTest {

    @Test
    fun `should validate messages`() {
        assertThat(Website.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be a valid website"),
            entry(SupportedLocales.CA, "Ha de ser un lloc web vàlid"),
            entry(SupportedLocales.DE, "Muss eine gültige Website sein"),
            entry(SupportedLocales.EN, "Must be a valid website"),
            entry(SupportedLocales.ES, "Tiene que ser un sitio web válido"),
            entry(SupportedLocales.JA, "有効なウェブサイト形式である必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser um website válido")
        )
    }
}

class UUIDTest {

    @Test
    fun `should validate messages`() {
        assertThat(UUID.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be a valid UUID"),
            entry(SupportedLocales.CA, "Ha de ser un UUID vàlid"),
            entry(SupportedLocales.DE, "Muss eine gültige UUID sein"),
            entry(SupportedLocales.EN, "Must be a valid UUID"),
            entry(SupportedLocales.ES, "Tiene que ser uno UUID válido"),
            entry(SupportedLocales.JA, "有効なUUIDである必要があります"),
            entry(SupportedLocales.PT_BR, "Deve ser um UUID válido")
        )
    }
}
