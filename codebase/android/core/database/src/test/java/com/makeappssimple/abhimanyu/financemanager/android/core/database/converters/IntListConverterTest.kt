package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IntListConverterTest {
    private lateinit var intListConverter: IntListConverter
    private val testIntList = listOf(1, 2, 3)
    private val testIntListString = """[1,2,3]"""

    @Before
    fun setUp() {
        intListConverter = IntListConverter()
    }

    @Test
    fun stringToIntList() {
        val result: List<Int>? = intListConverter.stringToIntList(
            value = testIntListString,
        )

        Assert.assertNotNull(result)
        Assert.assertEquals(
            testIntList,
            result,
        )
    }

    @Test
    fun stringToIntList_valueIsNull() {
        val result: List<Int>? = intListConverter.stringToIntList(
            value = null,
        )

        Assert.assertNull(result)
    }

    @Test
    fun stringToIntList_valueIsInvalidString() {
        val result: List<Int>? = intListConverter.stringToIntList(
            value = "invalid string",
        )

        Assert.assertNull(result)
    }

    @Test
    fun intListToString() {
        val result: String = intListConverter.intListToString(
            intList = testIntList,
        )

        Assert.assertEquals(
            testIntListString,
            result,
        )
    }

    @Test
    fun intListToString_intListIsNull() {
        val result: String = intListConverter.intListToString(
            intList = null,
        )

        Assert.assertEquals(
            "",
            result,
        )
    }
}
