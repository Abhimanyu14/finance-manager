package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import org.junit.Assert
import org.junit.Test

class InitialDatabaseDataTest {
    private lateinit var initialDatabaseData: InitialDatabaseData

    @Test
    fun initTest() {
        val versionNumber = 1
        val emojis = Emojis(
            versionNumber = versionNumber,
            emojisData = emptyList(),
        )
        val categories = Categories(
            versionNumber = versionNumber,
            categoriesData = emptyList(),
        )
        initialDatabaseData = InitialDatabaseData(
            defaultCategories = categories,
            defaultSources = emptyList(),
            defaultTransactionForValues = emptyList(),
            emojis = emojis,
        )

        Assert.assertEquals(
            0,
            initialDatabaseData.defaultCategories.categoriesData.size,
        )
        Assert.assertEquals(
            0,
            initialDatabaseData.defaultSources.size,
        )
        Assert.assertEquals(
            0,
            initialDatabaseData.defaultTransactionForValues.size,
        )
        Assert.assertEquals(
            versionNumber,
            initialDatabaseData.emojis.versionNumber,
        )
        Assert.assertEquals(
            0,
            initialDatabaseData.emojis.emojisData.size,
        )
    }
}
