package com.makeappssimple.abhimanyu.financemanager.android.data.usecase

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.DeleteAllEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase.InsertEmojisUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteAllSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.InsertSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.utils.JsonUtil
import javax.inject.Inject

class RestoreDataUseCase @Inject constructor(
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val insertEmojisUseCase: InsertEmojisUseCase,
    private val insertSourcesUseCase: InsertSourcesUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val deleteAllCategoriesUseCase: DeleteAllCategoriesUseCase,
    private val deleteAllEmojisUseCase: DeleteAllEmojisUseCase,
    private val deleteAllSourcesUseCase: DeleteAllSourcesUseCase,
    private val deleteAllTransactionsUseCase: DeleteAllTransactionsUseCase,
    private val jsonUtil: JsonUtil,
) {
    suspend operator fun invoke(
        uri: Uri,
    ) {
        val databaseBackupData = jsonUtil.readDatabaseBackupDataFromFile(
            uri = uri,
        )
        databaseBackupData?.let {
            deleteAllCategoriesUseCase()
            deleteAllEmojisUseCase()
            deleteAllSourcesUseCase()
            deleteAllTransactionsUseCase()

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
