package com.makeappssimple.abhimanyu.financemanager.android.core.database.util.json

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.DatabaseBackupData

interface JsonUtil {
    fun readDatabaseBackupDataFromFile(
        uri: Uri,
    ): DatabaseBackupData?

    fun writeDatabaseBackupDataToFile(
        uri: Uri,
        databaseBackupData: DatabaseBackupData,
    )
}
