package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    @SerialName(value = "last_backup_time")
    val lastBackupTime: String? = null,

    @SerialName(value = "last_backup_timestamp")
    val lastBackupTimestamp: String? = null,

    @SerialName(value = "database_data")
    val databaseData: DatabaseData? = null,

    @SerialName(value = "datastore_data")
    val datastoreData: DatastoreData? = null,
)
