package com.makeappssimple.abhimanyu.financemanager.android.utils.extensions

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StringExtensionTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun isNotNullOrBlank_stringIsNull_returnsFalse() {
        val input: String? = null
        Assert.assertFalse(input.isNotNullOrBlank())
    }

    @Test
    fun isNotNullOrBlank_stringIsEmpty_returnsFalse() {
        val input = ""
        Assert.assertFalse(input.isNotNullOrBlank())
    }

    @Test
    fun isNotNullOrBlank_stringIsBlank_returnsFalse() {
        val input = "  "
        Assert.assertFalse(input.isNotNullOrBlank())
    }

    @Test
    fun isNotNullOrBlank_stringIsNotBlank_returnsTrue() {
        val input = "input"
        Assert.assertTrue(input.isNotNullOrBlank())
    }

    @Test
    fun padStartWithZero_lengthIsGreaterThanStringLength() {
        val input = "23"
        Assert.assertEquals(
            "023",
            input.padStartWithZero(
                length = 3,
            ),
        )
    }

    @Test
    fun padStartWithZero_lengthIsEqualToStringLength() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = 2,
            ),
        )
    }

    @Test
    fun padStartWithZero_lengthIsLesserThanStringLength() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = 1,
            ),
        )
    }

    @Test
    fun padStartWithZero_lengthIsZero() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = 0,
            ),
        )
    }

    @Test
    fun padStartWithZero_lengthIsNegative() {
        val input = "23"
        Assert.assertEquals(
            "23",
            input.padStartWithZero(
                length = -3,
            ),
        )
    }
}
