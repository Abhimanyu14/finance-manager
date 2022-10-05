package com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.APP_NAME
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.DEFAULT_EXPENSE_CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.DEFAULT_INCOME_CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.DEFAULT_INVESTMENT_CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.DEFAULT_SOURCE_ID
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.LAST_DATA_BACKUP_TIMESTAMP
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.constants.LAST_DATA_CHANGE_TIMESTAMP
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_NAME,
)

interface MyDataStore {
    fun getDefaultExpenseCategoryIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultExpenseCategoryIdInDataStore(
        defaultExpenseCategoryId: Int,
    )

    fun getDefaultIncomeCategoryIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultIncomeCategoryIdInDataStore(
        defaultIncomeCategoryId: Int,
    )

    fun getDefaultInvestmentCategoryIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultInvestmentCategoryIdInDataStore(
        defaultInvestmentCategoryId: Int,
    )

    fun getDefaultSourceIdFromDataStore(): Flow<Int?>

    suspend fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )

    fun getLastDataBackupTimestamp(): Flow<Long?>

    suspend fun setLastDataBackupTimestamp(
        lastChangeTimestamp: Long,
    )

    fun getLastDataChangeTimestamp(): Flow<Long?>

    suspend fun setLastDataChangeTimestamp(
        lastChangeTimestamp: Long,
    )
}

class MyDataStoreImpl(
    private val context: Context,
) : MyDataStore {
    override fun getDefaultExpenseCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_EXPENSE_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultExpenseCategoryIdInDataStore(
        defaultExpenseCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_EXPENSE_CATEGORY_ID] = defaultExpenseCategoryId
        }
    }

    override fun getDefaultIncomeCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_INCOME_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultIncomeCategoryIdInDataStore(
        defaultIncomeCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_INCOME_CATEGORY_ID] = defaultIncomeCategoryId
        }
    }

    override fun getDefaultInvestmentCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_INVESTMENT_CATEGORY_ID]
        }
    }

    override suspend fun setDefaultInvestmentCategoryIdInDataStore(
        defaultInvestmentCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_INVESTMENT_CATEGORY_ID] = defaultInvestmentCategoryId
        }
    }

    override fun getDefaultSourceIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_SOURCE_ID]
        }
    }

    override suspend fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_SOURCE_ID] = defaultSourceId
        }
    }

    override fun getLastDataBackupTimestamp(): Flow<Long?> {
        return context.dataStore.data.map {
            it[LAST_DATA_BACKUP_TIMESTAMP]
        }
    }

    override suspend fun setLastDataBackupTimestamp(
        lastChangeTimestamp: Long,
    ) {
        context.dataStore.edit {
            it[LAST_DATA_BACKUP_TIMESTAMP] = lastChangeTimestamp
        }
    }

    override fun getLastDataChangeTimestamp(): Flow<Long?> {
        return context.dataStore.data.map {
            it[LAST_DATA_CHANGE_TIMESTAMP]
        }
    }

    override suspend fun setLastDataChangeTimestamp(
        lastChangeTimestamp: Long,
    ) {
        context.dataStore.edit {
            it[LAST_DATA_CHANGE_TIMESTAMP] = lastChangeTimestamp
        }
    }
}

suspend fun updateLastDataBackupTimestamp(
    dataStore: MyDataStore,
) {
    dataStore.setLastDataBackupTimestamp(
        System.currentTimeMillis(),
    )
}

suspend fun updateLastDataChangeTimestamp(
    dataStore: MyDataStore,
) {
    dataStore.setLastDataChangeTimestamp(
        System.currentTimeMillis(),
    )
}