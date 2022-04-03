package com.makeappssimple.abhimanyu.financemanager.android.entities.databasebackupdata

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DatabaseBackupData(
    var lastBackupTime: String = "",
    var lastBackupTimestamp: String = "",
    var categories: List<Category> = emptyList(),
    var emojis: List<EmojiLocalEntity> = emptyList(),
    var sources: List<Source> = emptyList(),
    var transactions: List<Transaction> = emptyList(),
)
