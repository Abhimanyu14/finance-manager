package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FakeMyPreferencesRepositoryImpl : MyPreferencesRepository {
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
    ) {
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ) {
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ) {
    }
}
