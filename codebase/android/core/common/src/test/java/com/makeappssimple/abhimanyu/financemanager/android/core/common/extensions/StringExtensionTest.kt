package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test

public class StringExtensionTest {

    // region isNotNullOrBlank tests
    @Test
    public fun isNotNullOrBlank_stringIsNull_returnsFalse() {
        val input: String? = null
        Assert.assertFalse(input.isNotNullOrBlank())
    }

    @Test
    public fun isNotNullOrBlank_stringIsEmpty_returnsFalse() {
        val input = ""
        Assert.assertFalse(input.isNotNullOrBlank())
    }

    @Test
    public fun isNotNullOrBlank_stringIsBlank_returnsFalse() {
        val input = "  "
        Assert.assertFalse(input.isNotNullOrBlank())
    }

    @Test
    public fun isNotNullOrBlank_stringIsNotBlank_returnsTrue() {
        val input = "input"
        Assert.assertTrue(input.isNotNullOrBlank())
    }
    // endregion

    // region padStartWithZero tests
    @Test
    public fun padStartWithZero_lengthIsGreaterThanStringLength() {
        val input = "23"
        Assert.assertEquals(
            "023",
            input.padStartWithZero(
                length = 3,
            ),
        )
    }

    @Test
    public fun padStartWithZero_lengthIsEqualToStringLength() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = 2,
            ),
        )
    }

    @Test
    public fun padStartWithZero_lengthIsLesserThanStringLength() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = 1,
            ),
        )
    }

    @Test
    public fun padStartWithZero_lengthIsZero() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = 0,
            ),
        )
    }

    @Test
    public fun padStartWithZero_lengthIsNegative() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = -3,
            ),
        )
    }
    // endregion

    // region capitalizeWords tests
    @Test
    public fun capitalizeWords_singleWordAllSmallLetters() {
        val input = "test"
        Assert.assertEquals(
            "Test",
            input.capitalizeWords(),
        )
    }

    @Test
    public fun capitalizeWords_singleWordAllCapitalLetters() {
        val input = "TEST"
        Assert.assertEquals(
            "Test",
            input.capitalizeWords(),
        )
    }

    @Test
    public fun capitalizeWords_singleWordRandomCaseLetters() {
        val input = "tEsT"
        Assert.assertEquals(
            "Test",
            input.capitalizeWords(),
        )
    }

    @Test
    public fun capitalizeWords_multipleWordsAllSmallLetters() {
        val input = "test words"
        Assert.assertEquals(
            "Test Words",
            input.capitalizeWords(),
        )
    }
    // endregion

    // region toIntOrZero
    @Test
    public fun toIntOrZero_zero() {
        val input = "0"
        Assert.assertEquals(
            0,
            input.toIntOrZero(),
        )
    }

    @Test
    public fun toIntOrZero_negative() {
        val input = "-45"
        Assert.assertEquals(
            -45,
            input.toIntOrZero(),
        )
    }

    @Test
    public fun toIntOrZero_positive() {
        val input = "45"
        Assert.assertEquals(
            45,
            input.toIntOrZero(),
        )
    }

    @Test
    public fun toIntOrZero_notNumber() {
        val input = "string"
        Assert.assertEquals(
            0,
            input.toIntOrZero(),
        )
    }

    @Test
    public fun toIntOrZero_stringWithNumber() {
        val input = "string 123"
        Assert.assertEquals(
            0,
            input.toIntOrZero(),
        )
    }
    // endregion

    // region toLongOrZero
    @Test
    public fun toLongOrZero_zero() {
        val input = "0"
        Assert.assertEquals(
            0L,
            input.toLongOrZero(),
        )
    }

    @Test
    public fun toLongOrZero_negative() {
        val input = "-45"
        Assert.assertEquals(
            -45L,
            input.toLongOrZero(),
        )
    }

    @Test
    public fun toLongOrZero_positive() {
        val input = "45"
        Assert.assertEquals(
            45L,
            input.toLongOrZero(),
        )
    }

    @Test
    public fun toLongOrZero_notNumber() {
        val input = "string"
        Assert.assertEquals(
            0L,
            input.toLongOrZero(),
        )
    }

    @Test
    public fun toLongOrZero_stringWithNumber() {
        val input = "string 123"
        Assert.assertEquals(
            0L,
            input.toLongOrZero(),
        )
    }
    // endregion
}
