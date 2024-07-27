package com.makeappssimple.abhimanyu.financemanager.android.core.database.converters

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestCategory
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class CategoryConverterTest {
    private lateinit var categoryConverter: CategoryConverter
    private val testCategory = getTestCategory()
    private val testCategoryString =
        """{"id":0,"description":"","emoji":"emoji","title":"title","transaction_type":"EXPENSE"}"""

    @Before
    fun setUp() {
        categoryConverter = CategoryConverter()
    }

    @Test
    fun stringToCategory() {
        val result: CategoryEntity? = categoryConverter.stringToCategory(
            value = testCategoryString,
        )

        Assert.assertNotNull(result)
        Assert.assertEquals(
            testCategory.id,
            result?.id,
        )
        Assert.assertEquals(
            testCategory.parentCategoryId,
            result?.parentCategoryId,
        )
        Assert.assertEquals(
            testCategory.subCategoryIds,
            result?.subCategoryIds,
        )
        Assert.assertEquals(
            testCategory.description,
            result?.description,
        )
        Assert.assertEquals(
            testCategory.emoji,
            result?.emoji,
        )
        Assert.assertEquals(
            testCategory.title,
            result?.title,
        )
        Assert.assertEquals(
            testCategory.transactionType,
            result?.transactionType,
        )
    }

    @Test
    fun stringToCategory_valueIsNull() {
        val result: CategoryEntity? = categoryConverter.stringToCategory(
            value = null,
        )

        Assert.assertNull(result)
    }

    @Test
    fun stringToCategory_valueIsInvalidString() {
        val result: CategoryEntity? = categoryConverter.stringToCategory(
            value = "invalid string",
        )

        Assert.assertNull(result)
    }

    @Test
    fun categoryToString() {
        val result: String = categoryConverter.categoryToString(
            categoryEntity = testCategory,
        )

        Assert.assertEquals(
            testCategoryString,
            result,
        )
    }

    @Test
    fun categoryToString_categoryIsNull() {
        val result: String = categoryConverter.categoryToString(
            categoryEntity = null,
        )

        Assert.assertEquals(
            "",
            result,
        )
    }
}
