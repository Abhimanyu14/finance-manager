package com.makeappssimple.abhimanyu.financemanager.android.core.database.databasebackupdata.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DatabaseBackupData(
    val lastBackupTime: String = "",
    val lastBackupTimestamp: String = "",
    val categories: List<Category> = emptyList(),
    val emojis: List<EmojiLocalEntity> = emptyList(),
    val sources: List<Source> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val transactionForValues: List<TransactionFor> = emptyList(),
)
