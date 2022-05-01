package com.makeappssimple.abhimanyu.financemanager.android.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.APP_NAME
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.DEFAULT_EXPENSE_CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.DEFAULT_INCOME_CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.utils.constants.DEFAULT_SOURCE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_NAME,
)

class MyDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getDefaultSourceIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_SOURCE_ID]
        }
    }

    suspend fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_SOURCE_ID] = defaultSourceId
        }
    }

    fun getDefaultIncomeCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_INCOME_CATEGORY_ID]
        }
    }

    suspend fun setDefaultIncomeCategoryIdInDataStore(
        defaultIncomeCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_INCOME_CATEGORY_ID] = defaultIncomeCategoryId
        }
    }

    fun getDefaultExpenseCategoryIdFromDataStore(): Flow<Int?> {
        return context.dataStore.data.map {
            it[DEFAULT_EXPENSE_CATEGORY_ID]
        }
    }

    suspend fun setDefaultExpenseCategoryIdInDataStore(
        defaultExpenseCategoryId: Int,
    ) {
        context.dataStore.edit {
            it[DEFAULT_EXPENSE_CATEGORY_ID] = defaultExpenseCategoryId
        }
    }
}
