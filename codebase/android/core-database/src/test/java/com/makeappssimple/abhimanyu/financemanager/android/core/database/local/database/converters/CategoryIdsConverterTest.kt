package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.converters

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CategoryIdsConverterTest {
    private lateinit var categoryIdsConverter: CategoryIdsConverter
    private val testCategoryIds = listOf(1, 2, 3)
    private val testCategoryIdsString = """[1,2,3]"""

    @Before
    fun setUp() {
        categoryIdsConverter = CategoryIdsConverter()
    }

    @Test
    fun stringToCategories() {
        val result: List<Int>? = categoryIdsConverter.stringToCategoryIds(
            value = testCategoryIdsString,
        )

        Assert.assertNotNull(result)
        Assert.assertEquals(
            testCategoryIds,
            result,
        )
    }

    @Test
    fun stringToCategories_valueIsNull() {
        val result: List<Int>? = categoryIdsConverter.stringToCategoryIds(
            value = null,
        )

        Assert.assertNull(result)
    }

    @Test
    fun stringToCategories_valueIsInvalidString() {
        val result: List<Int>? = categoryIdsConverter.stringToCategoryIds(
            value = "invalid string",
        )

        Assert.assertNull(result)
    }

    @Test
    fun categoryIdsToString() {
        val result: String = categoryIdsConverter.categoryIdsToString(
            categoryIds = testCategoryIds,
        )

        Assert.assertEquals(
            testCategoryIdsString,
            result,
        )
    }

    @Test
    fun categoryToString_categoryIdsIsNull() {
        val result: String = categoryIdsConverter.categoryIdsToString(
            categoryIds = null,
        )

        Assert.assertEquals(
            "",
            result,
        )
    }
}
