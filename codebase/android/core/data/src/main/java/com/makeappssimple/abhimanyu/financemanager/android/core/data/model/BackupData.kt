package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class BackupData @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName(value = "last_backup_time")
    @JsonNames("lastBackupTime") // TODO-Abhi: Eventually remove this
    val lastBackupTime: String = "",

    @SerialName(value = "last_backup_timestamp")
    @JsonNames("lastBackupTimestamp") // TODO-Abhi: Eventually remove this
    val lastBackupTimestamp: String = "",

    val categories: List<Category> = emptyList(),

    val emojis: List<Emoji> = emptyList(),

    val sources: List<Source> = emptyList(),

    @SerialName(value = "transaction_for_values")
    @JsonNames("transactionForValues") // TODO-Abhi: Eventually remove this
    val transactionForValues: List<TransactionFor> = emptyList(),

    val transactions: List<Transaction> = emptyList(),
)
