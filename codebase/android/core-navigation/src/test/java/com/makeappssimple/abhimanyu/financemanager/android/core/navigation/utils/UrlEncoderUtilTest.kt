package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UrlEncoderUtilTest {
    private lateinit var urlEncoderUtil: UrlEncoderUtil

    @Before
    fun setUp() {
        urlEncoderUtil = UrlEncoderUtil()
    }

    @Test
    fun decodeString_stringIsNull() {
        val result = urlEncoderUtil.decodeString(
            string = null,
            defaultValue = "",
        )

        Assert.assertEquals(
            "",
            result
        )
    }
}
