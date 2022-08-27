package com.makeappssimple.abhimanyu.financemanager.android.core.database.databasebackupdata.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DatabaseBackupData(
    var lastBackupTime: String = "",
    var lastBackupTimestamp: String = "",
    var categories: List<Category> = emptyList(),
    var emojis: List<EmojiLocalEntity> = emptyList(),
    var sources: List<Source> = emptyList(),
    var transactions: List<Transaction> = emptyList(),
    var transactionForValues: List<TransactionFor> = emptyList(),
)
