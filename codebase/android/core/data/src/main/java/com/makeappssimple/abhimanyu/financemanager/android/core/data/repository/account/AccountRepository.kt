package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account

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
    ): List<Long>

    suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: List<Pair<Int, Long>>,
    ): Boolean

    suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean

    suspend fun deleteAccount(
        id: Int,
    ): Boolean

    suspend fun deleteAccounts(
        vararg accounts: Account,
    ): Boolean
}
