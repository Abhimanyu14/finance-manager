package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

public class LocalDateTimeExtensionTest {
    private val testLocalDateTime = LocalDateTime.of(2023, 3, 30, 8, 24)
    private val testZoneId = ZoneId.of("Asia/Kolkata")

    @Test
    public fun toInstant() {
        val result = testLocalDateTime.toInstant(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680144840000,
            result.toEpochMilli(),
        )
    }

    @Test
    public fun toEpochMilli() {
        val result = testLocalDateTime.toEpochMilli(
            zoneId = testZoneId,
        )

        Assert.assertEquals(
            1680144840000,
            result,
        )
    }
}
