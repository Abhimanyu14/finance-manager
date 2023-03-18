package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfYearTimestamp
import org.junit.Assert
import org.junit.Test

class DateTimeUtilTest {
    @Test
    fun getStartOfDayTimestampTest() {
        val result = getStartOfDayTimestamp(
            timestamp = timeInMillis_02_JUN_2022_00_00_00,
        )
        Assert.assertEquals(
            timeInMillis_02_JUN_2022_00_00_00,
            result,
        )
    }

    @Test
    fun getEndOfDayTimestampTest() {
        val result = getEndOfDayTimestamp(
            timestamp = timeInMillis_02_JUN_2022_00_00_00,
        )
        Assert.assertEquals(
            timeInMillis_02_JUN_2022_23_59_59,
            result,
        )
    }

    @Test
    fun getStartOfMonthTimestampTest() {
        val result = getStartOfMonthTimestamp(
            timestamp = timeInMillis_02_JUN_2022_00_00_00,
        )
        Assert.assertEquals(
            timeInMillis_01_JUN_2022_00_00_00,
            result,
        )
    }

    @Test
    fun getEndOfMonthTimestampTest() {
        val result = getEndOfMonthTimestamp(
            timestamp = timeInMillis_02_JUN_2022_00_00_00,
        )
        Assert.assertEquals(
            timeInMillis_30_JUN_2022_23_59_59,
            result,
        )
    }

    @Test
    fun getStartOfYearTimestampTest() {
        val result = getStartOfYearTimestamp(
            timestamp = timeInMillis_02_JUN_2022_00_00_00,
        )
        Assert.assertEquals(
            timeInMillis_01_JAN_2022_00_00_00,
            result,
        )
    }

    @Test
    fun getEndOfYearTimestampYear() {
        val result = getEndOfYearTimestamp(
            timestamp = timeInMillis_02_JUN_2022_00_00_00,
        )
        Assert.assertEquals(
            timeInMillis_31_DEC_2022_23_59_59,
            result,
        )
    }
}
