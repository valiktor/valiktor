package org.valiktor.constraints

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.valiktor.i18n.Locales
import org.valiktor.i18n.interpolatedMessages

class BlankTest {

    @Test
    fun `should validate messages`() {
        assertThat(Blank().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be blank"),
                entry(Locales.EN, "Must be blank"),
                entry(Locales.PT_BR, "Deve estar em branco"))
    }
}

class NotBlankTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotBlank().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be blank"),
                entry(Locales.EN, "Must not be blank"),
                entry(Locales.PT_BR, "Não deve estar em branco"))
    }
}

class LetterTest {

    @Test
    fun `should validate messages`() {
        assertThat(Letter().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be letter"),
                entry(Locales.EN, "Must be letter"),
                entry(Locales.PT_BR, "Deve ser letra"))
    }
}

class NotLetterTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotLetter().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be letter"),
                entry(Locales.EN, "Must not be letter"),
                entry(Locales.PT_BR, "Não deve ser letra"))
    }
}

class DigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(Digit().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be digit"),
                entry(Locales.EN, "Must be digit"),
                entry(Locales.PT_BR, "Deve ser número"))
    }
}

class NotDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotDigit().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be digit"),
                entry(Locales.EN, "Must not be digit"),
                entry(Locales.PT_BR, "Não deve ser número"))
    }
}

class LetterOrDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(LetterOrDigit().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be letter or digit"),
                entry(Locales.EN, "Must be letter or digit"),
                entry(Locales.PT_BR, "Deve ser letra ou número"))
    }
}

class NotLetterOrDigitTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotLetterOrDigit().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not be letter or digit"),
                entry(Locales.EN, "Must not be letter or digit"),
                entry(Locales.PT_BR, "Não deve ser letra ou número"))
    }
}

class UpperCaseTest {

    @Test
    fun `should validate messages`() {
        assertThat(UpperCase().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be upper case"),
                entry(Locales.EN, "Must be upper case"),
                entry(Locales.PT_BR, "Deve ser maiúsculo"))
    }
}

class LowerCaseTest {

    @Test
    fun `should validate messages`() {
        assertThat(LowerCase().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be lower case"),
                entry(Locales.EN, "Must be lower case"),
                entry(Locales.PT_BR, "Deve ser minúsculo"))
    }
}

class MatchesTest {

    @Test
    fun `should validate messages`() {
        assertThat(Matches(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must match ^[0-9]*\$"),
                entry(Locales.EN, "Must match ^[0-9]*\$"),
                entry(Locales.PT_BR, "Deve corresponder ao padrão ^[0-9]*\$"))
    }
}

class NotMatchTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotMatch(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not match ^[0-9]*\$"),
                entry(Locales.EN, "Must not match ^[0-9]*\$"),
                entry(Locales.PT_BR, "Não deve corresponder ao padrão ^[0-9]*\$"))
    }
}

class ContainsRegexTest {

    @Test
    fun `should validate messages`() {
        assertThat(ContainsRegex(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must contain the pattern ^[0-9]*\$"),
                entry(Locales.EN, "Must contain the pattern ^[0-9]*\$"),
                entry(Locales.PT_BR, "Deve conter o padrão ^[0-9]*\$"))
    }
}

class NotContainRegexTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotContainRegex(Regex("^[0-9]*\$")).interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not contain the pattern ^[0-9]*\$"),
                entry(Locales.EN, "Must not contain the pattern ^[0-9]*\$"),
                entry(Locales.PT_BR, "Não deve conter o padrão ^[0-9]*\$"))
    }
}

class StartsWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(StartsWith("test").interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must start with test"),
                entry(Locales.EN, "Must start with test"),
                entry(Locales.PT_BR, "Deve começar com test"))
    }
}

class NotStartWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotStartWith("test").interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not start with test"),
                entry(Locales.EN, "Must not start with test"),
                entry(Locales.PT_BR, "Não deve começar com test"))
    }
}

class EndsWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(EndsWith("test").interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must end with test"),
                entry(Locales.EN, "Must end with test"),
                entry(Locales.PT_BR, "Deve terminar com test"))
    }
}

class NotEndWithTest {

    @Test
    fun `should validate messages`() {
        assertThat(NotEndWith("test").interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must not end with test"),
                entry(Locales.EN, "Must not end with test"),
                entry(Locales.PT_BR, "Não deve terminar com test"))
    }
}

class EmailTest {

    @Test
    fun `should validate messages`() {
        assertThat(Email().interpolatedMessages()).containsExactly(
                entry(Locales.DEFAULT, "Must be a valid email address"),
                entry(Locales.EN, "Must be a valid email address"),
                entry(Locales.PT_BR, "Deve ser um endereço de e-mail válido"))
    }
}