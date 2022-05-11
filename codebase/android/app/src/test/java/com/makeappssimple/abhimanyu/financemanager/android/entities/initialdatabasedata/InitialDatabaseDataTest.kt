package com.makeappssimple.abhimanyu.financemanager.android.entities.initialdatabasedata

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
        initialDatabaseData = InitialDatabaseData(
            defaultCategories = emptyList(),
            defaultSources = emptyList(),
            emojis = emojis,
        )

        Assert.assertEquals(
            0,
            initialDatabaseData.defaultCategories.size,
        )
        Assert.assertEquals(
            0,
            initialDatabaseData.defaultSources.size,
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
