package com.makeappssimple.abhimanyu.financemanager.android.cre.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DataTimestamp(
    @SerialName(value = "last_backup")
    val lastBackup: Long = 0L,

    @SerialName(value = "last_change")
    val lastChange: Long = 0L,
)
