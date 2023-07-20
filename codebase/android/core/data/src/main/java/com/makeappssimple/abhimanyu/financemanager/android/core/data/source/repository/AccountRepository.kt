package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAccountsFlow(): Flow<List<Source>>

    suspend fun getAllAccounts(): List<Source>

    suspend fun getAllAccountsCount(): Int

    suspend fun getAccount(
        id: Int,
    ): Source?

    suspend fun getAccounts(
        ids: List<Int>,
    ): List<Source>?

    suspend fun insertAccounts(
        vararg sources: Source,
    )

    suspend fun updateAccountBalanceAmount(
        sourcesBalanceAmountChange: List<Pair<Int, Long>>,
    )

    suspend fun updateAccounts(
        vararg sources: Source,
    )

    suspend fun deleteAccount(
        id: Int,
    )

    suspend fun deleteAccounts(
        vararg sources: Source,
    )
}
