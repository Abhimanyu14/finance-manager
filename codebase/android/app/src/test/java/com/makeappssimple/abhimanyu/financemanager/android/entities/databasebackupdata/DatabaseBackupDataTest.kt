package com.makeappssimple.abhimanyu.financemanager.android.entities.databasebackupdata

import org.junit.Assert
import org.junit.Test

class DatabaseBackupDataTest {
    private lateinit var databaseBackupData: DatabaseBackupData

    @Test
    fun databaseBackupData_defaultValues() {
        databaseBackupData = DatabaseBackupData()

        Assert.assertEquals(
            "",
            databaseBackupData.lastBackupTime,
        )
        Assert.assertEquals(
            "",
            databaseBackupData.lastBackupTimestamp,
        )
        Assert.assertEquals(
            0,
            databaseBackupData.categories.size,
        )
        Assert.assertEquals(
            0,
            databaseBackupData.emojis.size,
        )
        Assert.assertEquals(
            0,
            databaseBackupData.sources.size,
        )
        Assert.assertEquals(
            0,
            databaseBackupData.transactions.size,
        )
    }
}
