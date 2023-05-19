package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatastoreData(
    @SerialName(value = "default_data_id")
    val defaultDataId: DefaultDataId = DefaultDataId(),

    @SerialName(value = "initial_data_version_number")
    val initialDataVersionNumber: InitialDataVersionNumber = InitialDataVersionNumber(),

    @SerialName(value = "last_data_backup_timestamp")
    val lastDataBackupTimestamp: Long = 0L,

    @SerialName(value = "last_data_change_timestamp")
    val lastDataChangeTimestamp: Long = 0L,
)
