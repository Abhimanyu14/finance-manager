package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import org.junit.Assert
import org.junit.Test

class BackupDataTest {
    private lateinit var backupData: BackupData

    @Test
    fun backupData_defaultValues() {
        backupData = BackupData()

        Assert.assertEquals(
            "",
            backupData.lastBackupTime,
        )
        Assert.assertEquals(
            "",
            backupData.lastBackupTimestamp,
        )
        Assert.assertEquals(
            0,
            backupData.categories.size,
        )
        Assert.assertEquals(
            0,
            backupData.emojis.size,
        )
        Assert.assertEquals(
            0,
            backupData.sources.size,
        )
        Assert.assertEquals(
            0,
            backupData.transactions.size,
        )
    }
}
