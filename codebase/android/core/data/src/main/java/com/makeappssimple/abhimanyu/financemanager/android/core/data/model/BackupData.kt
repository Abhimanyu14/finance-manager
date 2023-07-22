package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class BackupData @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName(value = "last_backup_time")
    @JsonNames("lastBackupTime") // TODO-Abhi: legacy support v69
    val lastBackupTime: String? = "", // TODO-Abhi: legacy support v69

    @SerialName(value = "last_backup_timestamp")
    @JsonNames("lastBackupTimestamp") // TODO-Abhi: legacy support v69
    val lastBackupTimestamp: String? = "", // TODO-Abhi: legacy support v69

    val categories: List<Category>? = emptyList(), // TODO-Abhi: legacy support v69

    val emojis: List<Emoji>? = emptyList(), // TODO-Abhi: legacy support v69

    val accounts: List<Account>? = emptyList(), // TODO-Abhi: legacy support v69

    @SerialName(value = "transaction_for_values")
    @JsonNames("transactionForValues") // TODO-Abhi: legacy support v69
    val transactionForValues: List<TransactionFor>? = emptyList(), // TODO-Abhi: legacy support v69

    val transactions: List<Transaction>? = emptyList(), // TODO-Abhi: legacy support v69

    @SerialName(value = "database_data")
    val databaseData: DatabaseData? = null,

    @SerialName(value = "datastore_data")
    val datastoreData: DatastoreData? = null,
)
