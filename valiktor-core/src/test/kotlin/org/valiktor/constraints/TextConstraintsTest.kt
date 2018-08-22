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
            entry(SupportedLocales.EN, "Must be blank"),
            entry(SupportedLocales.PT_BR, "Deve estar em branco"))
    }
}

class NotBlankTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotBlank.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be blank"),
            entry(SupportedLocales.EN, "Must not be blank"),
            entry(SupportedLocales.PT_BR, "Não deve estar em branco"))
    }
}

class LetterTest {

    @Test
    fun `should validate messages`() {
        assertThat(Letter.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be letter"),
            entry(SupportedLocales.EN, "Must be letter"),
            entry(SupportedLocales.PT_BR, "Deve ser letra"))
    }
}

class NotLetterTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotLetter.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be letter"),
            entry(SupportedLocales.EN, "Must not be letter"),
            entry(SupportedLocales.PT_BR, "Não deve ser letra"))
    }
}

class DigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(Digit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be digit"),
            entry(SupportedLocales.EN, "Must be digit"),
            entry(SupportedLocales.PT_BR, "Deve ser número"))
    }
}

class NotDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotDigit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be digit"),
            entry(SupportedLocales.EN, "Must not be digit"),
            entry(SupportedLocales.PT_BR, "Não deve ser número"))
    }
}

class LetterOrDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(LetterOrDigit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be letter or digit"),
            entry(SupportedLocales.EN, "Must be letter or digit"),
            entry(SupportedLocales.PT_BR, "Deve ser letra ou número"))
    }
}

class NotLetterOrDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotLetterOrDigit.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not be letter or digit"),
            entry(SupportedLocales.EN, "Must not be letter or digit"),
            entry(SupportedLocales.PT_BR, "Não deve ser letra ou número"))
    }
}

class UpperCaseTest {

    @Test
    fun `should validate messages`() {
        assertThat(UpperCase.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be upper case"),
            entry(SupportedLocales.EN, "Must be upper case"),
            entry(SupportedLocales.PT_BR, "Deve ser maiúsculo"))
    }
}

class LowerCaseTest {

    @Test
    fun `should validate messages`() {
        assertThat(LowerCase.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be lower case"),
            entry(SupportedLocales.EN, "Must be lower case"),
            entry(SupportedLocales.PT_BR, "Deve ser minúsculo"))
    }
}

class MatchesTest {

    @Test
    fun `should validate messages`() {
        assertThat(Matches(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must match ^[0-9]*\$"),
            entry(SupportedLocales.EN, "Must match ^[0-9]*\$"),
            entry(SupportedLocales.PT_BR, "Deve corresponder ao padrão ^[0-9]*\$"))
    }
}

class NotMatchTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotMatch(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not match ^[0-9]*\$"),
            entry(SupportedLocales.EN, "Must not match ^[0-9]*\$"),
            entry(SupportedLocales.PT_BR, "Não deve corresponder ao padrão ^[0-9]*\$"))
    }
}

class ContainsRegexTest {

    @Test
    fun `should validate messages`() {
        assertThat(ContainsRegex(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.EN, "Must contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.PT_BR, "Deve conter o padrão ^[0-9]*\$"))
    }
}

class NotContainRegexTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotContainRegex(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.EN, "Must not contain the pattern ^[0-9]*\$"),
            entry(SupportedLocales.PT_BR, "Não deve conter o padrão ^[0-9]*\$"))
    }
}

class StartsWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(StartsWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must start with test"),
            entry(SupportedLocales.EN, "Must start with test"),
            entry(SupportedLocales.PT_BR, "Deve começar com test"))
    }
}

class NotStartWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotStartWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not start with test"),
            entry(SupportedLocales.EN, "Must not start with test"),
            entry(SupportedLocales.PT_BR, "Não deve começar com test"))
    }
}

class EndsWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(EndsWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must end with test"),
            entry(SupportedLocales.EN, "Must end with test"),
            entry(SupportedLocales.PT_BR, "Deve terminar com test"))
    }
}

class NotEndWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEndWith("test").interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must not end with test"),
            entry(SupportedLocales.EN, "Must not end with test"),
            entry(SupportedLocales.PT_BR, "Não deve terminar com test"))
    }
}

class EmailTest {

    @Test
    fun `should validate messages`() {
        assertThat(Email.interpolatedMessages()).containsExactly(
            entry(SupportedLocales.DEFAULT, "Must be a valid email address"),
            entry(SupportedLocales.EN, "Must be a valid email address"),
            entry(SupportedLocales.PT_BR, "Deve ser um endereço de e-mail válido"))
    }
}