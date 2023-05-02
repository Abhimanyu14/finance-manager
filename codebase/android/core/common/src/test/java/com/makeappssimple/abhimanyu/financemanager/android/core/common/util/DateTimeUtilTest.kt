package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getEndOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getEndOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getEndOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getStartOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getStartOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getStartOfYearTimestamp
import org.junit.Assert
import org.junit.Test
import java.time.ZoneId

class DateTimeUtilTest {
    private val testTimestamp = 1680144857890
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    @Test
    fun getCurrentLocalDate() {
    }

    @Test
    fun getCurrentLocalTime() {
    }

    @Test
    fun getCurrentLocalDateTime() {
    }

    @Test
    fun getCurrentInstant() {
    }

    @Test
    fun getCurrentTimeMillis() {
    }

    @Test
    fun getSystemDefaultZoneId() {
    }

    @Test
    fun getFormattedDate() {
        Assert.assertEquals(
            "30 Mar, 2023",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedDate(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun getFormattedDay() {
        Assert.assertEquals(
            "30 Mar",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedDay(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun getFormattedMonth() {
        Assert.assertEquals(
            "March, 2023",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedMonth(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun getFormattedYear() {
        Assert.assertEquals(
            "2023",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedYear(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun getFormattedDateAndTime() {
        Assert.assertEquals(
            "2023-Mar-30, 08-24 AM",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedDateAndTime(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun getReadableDateAndTime() {
        Assert.assertEquals(
            "30 Mar, 2023 at 08:24 AM",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getReadableDateAndTime(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun getLocalDate() {
        val result =
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getLocalDate(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            )

        Assert.assertEquals(
            30,
            result.dayOfMonth,
        )
        Assert.assertEquals(
            3,
            result.monthValue,
        )
        Assert.assertEquals(
            2023,
            result.year,
        )
    }

    @Test
    fun getLocalTime() {
        val result =
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getLocalTime(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            )

        Assert.assertEquals(
            8,
            result.hour,
        )
        Assert.assertEquals(
            24,
            result.minute,
        )
    }

    @Test
    fun getLocalDateTime() {
        val result =
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getLocalDateTime(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            )

        Assert.assertEquals(
            30,
            result.dayOfMonth,
        )
        Assert.assertEquals(
            3,
            result.monthValue,
        )
        Assert.assertEquals(
            2023,
            result.year,
        )
        Assert.assertEquals(
            8,
            result.hour,
        )
        Assert.assertEquals(
            24,
            result.minute,
        )
    }

    @Test
    fun getStartOfDayTimestamp_defaultTest() {
        val result = getStartOfDayTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680114600000,
            result,
        )
    }

    @Test
    fun getEndOfDayTimestamp_defaultTest() {
        val result = getEndOfDayTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680200999999,
            result,
        )
    }

    @Test
    fun getStartOfMonthTimestamp_defaultTest() {
        val result = getStartOfMonthTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1677609000000,
            result,
        )
    }

    @Test
    fun getEndOfMonthTimestamp_defaultTest() {
        val result = getEndOfMonthTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680287399999,
            result,
        )
    }

    @Test
    fun getStartOfYearTimestamp_defaultTest() {
        val result = getStartOfYearTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1672511400000,
            result,
        )
    }

    @Test
    fun getEndOfYearTimestamp_defaultTest() {
        val result = getEndOfYearTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1704047399999,
            result,
        )
    }
}
