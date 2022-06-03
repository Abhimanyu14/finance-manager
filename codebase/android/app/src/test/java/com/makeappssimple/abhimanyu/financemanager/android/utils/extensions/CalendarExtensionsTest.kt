package com.makeappssimple.abhimanyu.financemanager.android.utils.extensions

import org.junit.Assert
import org.junit.Test
import java.util.Calendar
import java.util.Locale

class CalendarExtensionsTest {
    private val calendar: Calendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = 1649354842327
    }

    @Test
    fun getDayOfMonth() {
        Assert.assertEquals(
            7,
            calendar.dayOfMonth,
        )
    }

    @Test
    fun setDayOfMonth() {
    }

    @Test
    fun getMonth() {
        Assert.assertEquals(
            3,
            calendar.month,
        )
    }

    @Test
    fun setMonth() {
    }

    @Test
    fun getYear() {
        Assert.assertEquals(
            2022,
            calendar.year,
        )
    }

    @Test
    fun setYear() {
    }

    @Test
    fun getHour() {
        Assert.assertEquals(
            11,
            calendar.hour,
        )
    }

    @Test
    fun setHour() {
    }

    @Test
    fun getMinute() {
        Assert.assertEquals(
            37,
            calendar.minute,
        )
    }

    @Test
    fun setMinute() {
    }

    @Test
    fun setDate() {
        val dayOfMonth = 3
        val month = 2
        val year = 1996
        calendar.setDate(
            dayOfMonth = dayOfMonth,
            month = month,
            year = year,
        )
        Assert.assertEquals(
            dayOfMonth,
            calendar.dayOfMonth,
        )
        Assert.assertEquals(
            month,
            calendar.month,
        )
        Assert.assertEquals(
            year,
            calendar.year,
        )
    }

    @Test
    fun setTime() {
        val hour = 6
        val minute = 24
        calendar.setTime(
            hour = hour,
            minute = minute,
        )
        Assert.assertEquals(
            hour,
            calendar.hour,
        )
        Assert.assertEquals(
            minute,
            calendar.minute,
        )
    }

    @Test
    fun formattedDate() {
        //        Assert.assertEquals(
        //            "07/04/2022",
        //            calendar.formattedDate(),
        //        )
    }

    @Test
    fun formattedTime() {
        //        Assert.assertEquals(
        //            "11:37 PM",
        //            calendar.formattedTime(),
        //        )
    }
}
