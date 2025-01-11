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
public interface AccountDao {
    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    public fun getAllAccountsFlow(): Flow<List<AccountEntity>>

    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    public suspend fun getAllAccounts(): List<AccountEntity>

    @Query(value = "SELECT COUNT(*) FROM account_table")
    public suspend fun getAllAccountsCount(): Int

    @Query(value = "SELECT * from account_table WHERE id = :id")
    public suspend fun getAccount(
        id: Int,
    ): AccountEntity?

    @Query(value = "SELECT * from account_table WHERE id IN (:ids)")
    public suspend fun getAccounts(
        ids: List<Int>,
    ): List<AccountEntity>

    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    ): List<Long>

    @Update
    public suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    ): Int

    @Query(value = "DELETE FROM account_table WHERE id = :id")
    public suspend fun deleteAccount(
        id: Int,
    ): Int

    @Delete
    public suspend fun deleteAccounts(
        vararg accounts: AccountEntity,
    ): Int

    @Query(value = "DELETE FROM account_table")
    public suspend fun deleteAllAccounts(): Int
}
