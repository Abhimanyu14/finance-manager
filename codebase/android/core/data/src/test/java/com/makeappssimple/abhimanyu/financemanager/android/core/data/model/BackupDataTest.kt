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
    }
}
