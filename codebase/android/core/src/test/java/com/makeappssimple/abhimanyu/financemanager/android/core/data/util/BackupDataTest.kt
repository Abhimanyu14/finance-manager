package com.makeappssimple.abhimanyu.financemanager.android.core.data.util

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.BackupData
import org.junit.Assert
import org.junit.Test

internal class BackupDataTest {
    private lateinit var backupData: BackupData

    @Test
    fun backupData_defaultValues() {
        backupData = BackupData()

        Assert.assertNull(backupData.lastBackupTime)
        Assert.assertNull(backupData.lastBackupTimestamp)
    }
}
