package com.makeappssimple.abhimanyu.financemanager.android.utils

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DateTimeUtilTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCurrentMonthStartingTimestampTest() {
        val result = getCurrentMonthStartingTimestamp()
        Assert.assertEquals(
            timeInMillis_01_JUN_2022,
            result,
        )
    }
}
