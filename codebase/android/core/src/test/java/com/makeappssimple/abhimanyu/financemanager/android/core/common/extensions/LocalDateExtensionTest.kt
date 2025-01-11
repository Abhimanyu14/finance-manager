package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

internal class LocalDateExtensionTest {
    private val testLocalDate = LocalDate.of(2023, 3, 30)
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    @Test
    fun atEndOfDay() {
        val result = testLocalDate.atEndOfDay()
        Assert.assertEquals(
            23,
            result.hour,
        )
        Assert.assertEquals(
            59,
            result.minute,
        )
        Assert.assertEquals(
            59,
            result.second,
        )
    }

    @Test
    fun formattedDate() {
        Assert.assertEquals(
            "30 Mar, 2023",
            testLocalDate.formattedDate(
                zoneId = testZoneId,
            ),
        )
    }
}
