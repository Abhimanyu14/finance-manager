package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    fun getAllAccountsFlow(): Flow<List<AccountEntity>>

    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Query(value = "SELECT COUNT(*) FROM account_table")
    suspend fun getAllAccountsCount(): Int

    @Query(value = "SELECT * from account_table WHERE id = :id")
    suspend fun getAccount(
        id: Int,
    ): AccountEntity?

    @Query(value = "SELECT * from account_table WHERE id IN (:ids)")
    suspend fun getAccounts(
        ids: List<Int>,
    ): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    )

    @Update
    suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    )

    @Query(value = "DELETE FROM account_table WHERE id = :id")
    suspend fun deleteAccount(
        id: Int,
    )

    @Delete
    suspend fun deleteAccounts(
        vararg accounts: AccountEntity,
    )

    @Query(value = "DELETE FROM account_table")
    suspend fun deleteAllAccounts()
}
