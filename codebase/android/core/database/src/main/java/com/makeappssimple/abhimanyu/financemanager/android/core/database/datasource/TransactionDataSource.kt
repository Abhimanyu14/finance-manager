package com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity

interface TransactionDataSource {
    suspend fun deleteTransaction(
        id: Int,
        vararg sources: SourceEntity,
    )

    suspend fun insertTransaction(
        amountValue: Long,
        sourceFrom: SourceEntity?,
        sourceTo: SourceEntity?,
        transaction: TransactionEntity,
    ): Long

    suspend fun restoreData(
        categories: Array<CategoryEntity>,
        emojis: Array<EmojiEntity>,
        sources: Array<SourceEntity>,
        transactions: Array<TransactionEntity>,
        transactionForValues: Array<TransactionForEntity>,
    )
}
