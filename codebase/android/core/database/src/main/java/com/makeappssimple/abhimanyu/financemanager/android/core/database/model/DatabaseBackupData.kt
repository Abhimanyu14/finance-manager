package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

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
