package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeMyPreferencesRepositoryImpl : MyPreferencesRepository {
    override fun getDataTimestamp(): Flow<DataTimestamp?> {
        return emptyFlow()
    }

    override fun getDefaultDataId(): Flow<DefaultDataId?> {
        return emptyFlow()
    }

    override fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return emptyFlow()
    }

    override fun getReminder(): Flow<Reminder?> {
        return emptyFlow()
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return true
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return true
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return true
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return true
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean {
        return true
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return true
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return true
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return true
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        return true
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return true
    }
}
