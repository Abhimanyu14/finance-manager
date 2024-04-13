package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import java.time.ZoneId

public class DateTimeUtilTest {
    private val testTimestamp = 1680144857890
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    /*
    // TODO(Abhi): Fix tests
    @Test
    public fun getCurrentLocalDate() {
    }

    @Test
    public fun getCurrentLocalTime() {
    }

    @Test
    public fun getCurrentLocalDateTime() {
    }

    @Test
    public fun getCurrentInstant() {
    }

    @Test
    public fun getCurrentTimeMillis() {
    }

    @Test
    public fun getSystemDefaultZoneId() {
    }

    @Test
    public fun getFormattedDateTest() {
        Assert.assertEquals(
            "30 Mar, 2023",
            getFormattedDate(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun getFormattedDay() {
        Assert.assertEquals(
            "30 Mar",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedDay(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun getFormattedMonth() {
        Assert.assertEquals(
            "March, 2023",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedMonth(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun getFormattedYear() {
        Assert.assertEquals(
            "2023",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedYear(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun getFormattedDateAndTime() {
        Assert.assertEquals(
            "2023-Mar-30, 08-24 AM",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getFormattedDateAndTime(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun getReadableDateAndTime() {
        Assert.assertEquals(
            "30 Mar, 2023 at 08:24 AM",
            com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.getReadableDateAndTime(
                timestamp = testTimestamp,
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun getLocalDate() {
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
    public fun getLocalTime() {
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
    public fun getLocalDateTime() {
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
    public fun getStartOfDayTimestamp_defaultTest() {
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
    public fun getEndOfDayTimestamp_defaultTest() {
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
    public fun getStartOfMonthTimestamp_defaultTest() {
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
    public fun getEndOfMonthTimestamp_defaultTest() {
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
    public fun getStartOfYearTimestamp_defaultTest() {
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
    public fun getEndOfYearTimestamp_defaultTest() {
        val result = getEndOfYearTimestamp(
            timestamp = testTimestamp,
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1704047399999,
            result,
        )
    }*/
}
