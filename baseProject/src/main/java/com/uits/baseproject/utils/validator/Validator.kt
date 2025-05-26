package com.uits.baseproject.utils.validator

import android.util.Patterns
import java.util.regex.Pattern


object Validator {
    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}")
        return emailRegex.matches(email)
    }

    fun isValidPostCode(postCode: String): Boolean {
        // Check if the length is 7 and all characters are digits
        return postCode.length == 7 && postCode.all { it.isDigit() }
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Define your regex pattern for 13-character phone numbers
        val regex = "^[\\d()+-]{10,13}$" // This regex matches exactly 13 digits

        // Create a Pattern object
        val pattern = Pattern.compile(regex)

        // Check if the provided phone number matches the pattern
        val matcher = pattern.matcher(phoneNumber)

        return matcher.matches()
    }

    fun isStrongPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")
        return regex.matches(password)
    }

    private fun hasNumber(input: String): Boolean {
        // Regex pattern to match at least one digit
        val pattern = Regex("\\d+")

        // Check if the input contains at least one digit
        return pattern.containsMatchIn(input)
    }

    private fun hasCharacter(input: String): Boolean {
        // Regex pattern to match at least one character
        val pattern = Regex("[a-zA-Z]+")

        // Check if the input contains at least one character
        return pattern.containsMatchIn(input)
    }

//    fun validatePassword(input: String): Boolean {
//        // Check if the input contains at least one digit and one character
//        val regexPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9\\W]+\$")
//        return regexPattern.matches(input)
//    }

    fun validatePassword(input: String): Boolean {
        val regexPattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?!.*[\\uFF00-\\uFFFF])[\\x21-\\x7E]+\$")
        return regexPattern.matches(input)
    }

    fun validatePasswordV2(input: String): Boolean {
        // Check if the input contains at least one digit and one character
        return hasNumber(input) && hasCharacter(input) && containsJapanese(input)
    }

    private fun containsJapanese(text: String): Boolean {
        val japaneseRegex = Regex("[\u3040-\u309F\u30A0-\u30FF\u4E00-\u9FFF\u3400-\u4DBF]")
        return !japaneseRegex.containsMatchIn(text)
    }

    fun isKatakana(input: String): Boolean {
        for (char in input) {
            val unicode = char.toInt()
            if (unicode in 0x30A0..0x30FF || unicode in 0x31F0..0x31FF || unicode == 0x30FC) {
                // Katakana range
                return false
            }
        }
        return true
    }

    fun containsFullWidth(text: String): Boolean {
        val fullWidthRegex =
            Regex("[\\uFF01-\\uFF5E\\uFF10-\\uFF19\\uFF21-\\uFF3A\\uFF41-\\uFF5A\\uFF3F]")
        return fullWidthRegex.containsMatchIn(text)
    }

    fun isFullWidthText(text: String): Boolean {
        for (char in text) {
            if (!isFullWidth(char)) {
                return false
            }
        }
        return true
    }

    private fun isFullWidth(char: Char): Boolean {
        // 全角文字の範囲：U+FF01 から U+FF5E
        // 他の範囲にも拡張可能
        return char.code in 0xFF01..0xFF5E
    }

    private fun isHalfWidth(char: Char): Boolean {
        // 半角文字の範囲：U+0000 から U+007F
        // 他の範囲にも拡張可能
        return char.code in 0x0000..0x007F
    }

    fun isFullKatakana(input: String): Boolean {
        for (char in input) {
            if (!char.isKatakana()) {
                return false
            }
        }
        return true
    }

    private fun Char.isKatakana(): Boolean {
        return this.toInt() in 0x30A0..0x30FF || this.toInt() in 0x31F0..0x31FF || this.toInt() == 0x30FC
    }

    fun isFullWidth(input: String): Boolean {
        return isFullWidthKatakana(input)
                || isFullWidthHiragana(input)
                || isFullWidthKanji(input)
                || isFullWidthNumber(input)
                || isFullWidthJapanese(input)
    }


    fun isFullWidthHiragana(input: String): Boolean {
        val regex = Regex("[\\u3040-\\u309F]+") // Unicode range for full-width Hiragana characters
        return regex.matches(input)
    }

    private fun isFullWidthKanji(input: String): Boolean {
        val regex = Regex("[\\u4E00-\\u9FFF]+") // Unicode range for Kanji characters
        return regex.matches(input)
    }

    private fun isFullWidthNumber(input: String): Boolean {
        val regex = Regex("[０-９]+") // Unicode range for full-width numbers
        return regex.matches(input)
    }

    private fun isFullWidthJapanese(input: String): Boolean {
        // Combine all necessary character ranges
        val regex = Regex("[\\u30A0-\\u30FF\\u3040-\\u309F\\u4E00-\\u9FFF０-９]+")
        return regex.matches(input)
    }

    private fun isCJKCharacter(char: Char): Boolean {
        return Character.UnicodeScript.of(char.code) in setOf(
            Character.UnicodeScript.HAN,      // Chinese characters
            Character.UnicodeScript.HIRAGANA,
            Character.UnicodeScript.KATAKANA,
        )
    }

    fun allNameFullWidthCharacter(s: String): Boolean {
        return !s.all {
            isCJKCharacter(it) || it.toString()
                .matches((Regex("^[ァィゥェォッャュョヮヵヶヾゞ]+\$")))
                    || it.toString().matches((Regex("^[Ａ-ｚ]+\$")))
        }
    }

    fun katakanaFull(katakana: String): Boolean {
        return katakana.matches(Regex("^[\\u30A0-\\u30FF]+$"))
    }

    fun isFullWidthName(s: String): Boolean {
        val regex =
            Regex("^[ぁ-んァ-ン一-龥ａ-ｚＡ-Ｚ０-９ァィゥェォッャュョヮヵヶゝヽヾゞ々　！”＃＄％＆’（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝～]+$")
        return regex.matches(s)
    }

    fun isFullWidthKatakana(input: String): Boolean {
        val regex = Regex("^[ァ-ヶヮヵー]+$")
        return regex.matches(input)
    }
}