package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeMyPreferencesRepositoryImpl : MyPreferencesRepository {
    override fun getDataTimestampFlow(): Flow<DataTimestamp?> {
        return emptyFlow()
    }

    override fun getDefaultDataIdFlow(): Flow<DefaultDataId?> {
        return emptyFlow()
    }

    override fun getInitialDataVersionNumberFlow(): Flow<InitialDataVersionNumber?> {
        return emptyFlow()
    }

    override fun getReminderFlow(): Flow<Reminder?> {
        return emptyFlow()
    }

    override suspend fun getDataTimestamp(): DataTimestamp? {
        return null
    }

    override suspend fun getDefaultDataId(): DefaultDataId? {
        return null
    }

    override suspend fun getInitialDataVersionNumber(): InitialDataVersionNumber? {
        return null
    }

    override suspend fun getReminder(): Reminder? {
        return null
    }

    override suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return true
    }

    override suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return true
    }

    override suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return true
    }

    override suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return true
    }

    override suspend fun updateDefaultAccountId(
        accountId: Int,
    ): Boolean {
        return true
    }

    override suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return true
    }

    override suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return true
    }

    override suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return true
    }

    override suspend fun updateTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        return true
    }

    override suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return true
    }
}
