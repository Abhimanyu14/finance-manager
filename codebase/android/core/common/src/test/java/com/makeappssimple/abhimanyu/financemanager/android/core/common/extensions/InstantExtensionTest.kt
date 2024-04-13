package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.ZoneId

public class InstantExtensionTest {
    private val testInstant = Instant.ofEpochMilli(1680144857890)
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    @Test
    public fun formattedDate() {
        Assert.assertEquals(
            "30 Mar, 2023",
            testInstant.formattedDate(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun formattedDay() {
        Assert.assertEquals(
            "30 Mar",
            testInstant.formattedDay(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun formattedMonth() {
        Assert.assertEquals(
            "March, 2023",
            testInstant.formattedMonth(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun formattedYear() {
        Assert.assertEquals(
            "2023",
            testInstant.formattedYear(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun formattedTime() {
        Assert.assertEquals(
            "08:24 AM",
            testInstant.formattedTime(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun formattedDateAndTime() {
        Assert.assertEquals(
            "2023-Mar-30, 08-24 AM",
            testInstant.formattedDateAndTime(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun formattedReadableDateAndTime() {
        Assert.assertEquals(
            "30 Mar, 2023 at 08:24 AM",
            testInstant.formattedReadableDateAndTime(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    public fun toZonedDateTime() {
        val result = testInstant.toZonedDateTime(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            testZoneId,
            result.zone,
        )
    }

    @Test
    public fun atStartOfDay() {
        val result = testInstant.atStartOfDay(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680114600000,
            result.toEpochMilli(),
        )
    }

    @Test
    public fun atEndOfDay() {
        val result = testInstant.atEndOfDay(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680200999999,
            result.toEpochMilli(),
        )
    }
}
