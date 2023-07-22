package com.makeappssimple.abhimanyu.financemanager.android.core.data.account.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAccountsFlow(): Flow<List<Account>>

    suspend fun getAllAccounts(): List<Account>

    suspend fun getAllAccountsCount(): Int

    suspend fun getAccount(
        id: Int,
    ): Account?

    suspend fun getAccounts(
        ids: List<Int>,
    ): List<Account>?

    suspend fun insertAccounts(
        vararg accounts: Account,
    )

    suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    )

    suspend fun updateAccounts(
        vararg accounts: Account,
    )

    suspend fun deleteAccount(
        id: Int,
    )

    suspend fun deleteAccounts(
        vararg accounts: Account,
    )
}
