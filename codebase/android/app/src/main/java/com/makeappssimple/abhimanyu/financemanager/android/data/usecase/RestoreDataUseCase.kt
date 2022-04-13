package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.InsertEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.utils.JsonUtil
import javax.inject.Inject

class RestoreDataUseCase @Inject constructor(
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val insertEmojisUseCase: InsertEmojisUseCase,
    private val insertSourcesUseCase: InsertSourcesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val jsonUtil: JsonUtil,
) {
    suspend operator fun invoke(
        uri: Uri,
    ) {
        val databaseBackupData = jsonUtil.readDatabaseBackupDataFromFile(
            uri = uri,
        )
        databaseBackupData?.let {
            insertCategoriesUseCase(
                *databaseBackupData.categories.toTypedArray(),
            )
            insertEmojisUseCase(
                *databaseBackupData.emojis.toTypedArray(),
            )
            insertSourcesUseCase(
                *databaseBackupData.sources.toTypedArray(),
            )
            insertTransactionsUseCase(
                *databaseBackupData.transactions.toTypedArray(),
            )
        }
    }
}
