package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import org.junit.Assert
import org.junit.Test

public class BackupDataTest {
    private lateinit var backupData: BackupData

    @Test
    public fun backupData_defaultValues() {
        backupData = BackupData()

        Assert.assertNull(backupData.lastBackupTime)
        Assert.assertNull(backupData.lastBackupTimestamp)
    }
}
