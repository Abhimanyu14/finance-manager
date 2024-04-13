package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test
import java.time.LocalTime
import java.time.ZoneId

public class LocalTimeExtensionTest {
    private val testLocalTime = LocalTime.of(8, 24)
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    @Test
    public fun formattedTime() {
        Assert.assertEquals(
            "08:24 AM",
            testLocalTime.formattedTime(
                zoneId = testZoneId,
            ),
        )
    }
}
