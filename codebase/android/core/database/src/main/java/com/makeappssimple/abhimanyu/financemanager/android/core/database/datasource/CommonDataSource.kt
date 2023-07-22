package com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity

interface CommonDataSource {
    suspend fun deleteTransaction(
        id: Int,
    )

    suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: AccountEntity?,
        accountTo: AccountEntity?,
        transaction: TransactionEntity,
    ): Long

    suspend fun restoreData(
        categories: Array<CategoryEntity>,
        emojis: Array<EmojiEntity>,
        accounts: Array<AccountEntity>,
        transactions: Array<TransactionEntity>,
        transactionForValues: Array<TransactionForEntity>,
    )
}
