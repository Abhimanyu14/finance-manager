@file:Suppress("UnderscoresInNumericLiterals")

package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.ZoneId

internal class InstantExtensionTest {
    private val testInstant = Instant.ofEpochMilli(1680144857890)
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    @Test
    fun formattedDate() {
        Assert.assertEquals(
            "30 Mar, 2023",
            testInstant.formattedDate(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun formattedDay() {
        Assert.assertEquals(
            "30 Mar",
            testInstant.formattedDay(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun formattedMonth() {
        Assert.assertEquals(
            "March, 2023",
            testInstant.formattedMonth(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun formattedYear() {
        Assert.assertEquals(
            "2023",
            testInstant.formattedYear(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun formattedTime() {
        Assert.assertEquals(
            "08:24 AM",
            testInstant.formattedTime(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun formattedDateAndTime() {
        Assert.assertEquals(
            "2023-Mar-30, 08-24 AM",
            testInstant.formattedDateAndTime(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun formattedReadableDateAndTime() {
        Assert.assertEquals(
            "30 Mar, 2023 at 08:24 AM",
            testInstant.formattedReadableDateAndTime(
                zoneId = testZoneId,
            ),
        )
    }

    @Test
    fun toZonedDateTime() {
        val result = testInstant.toZonedDateTime(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            testZoneId,
            result.zone,
        )
    }

    @Test
    fun atStartOfDay() {
        val result = testInstant.atStartOfDay(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680114600000,
            result.toEpochMilli(),
        )
    }

    @Test
    fun atEndOfDay() {
        val result = testInstant.atEndOfDay(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680200999999,
            result.toEpochMilli(),
        )
    }
}
