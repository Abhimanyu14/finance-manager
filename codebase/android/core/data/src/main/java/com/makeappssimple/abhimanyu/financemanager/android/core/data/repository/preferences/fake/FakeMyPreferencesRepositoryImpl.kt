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
//        return myPreferencesDataSource.getDataTimestamp()
    }

    override fun getDefaultDataId(): Flow<DefaultDataId?> {
        return emptyFlow()
//        return myPreferencesDataSource.getDefaultDataId()
    }

    override fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return emptyFlow()
//        return myPreferencesDataSource.getInitialDataVersionNumber()
    }

    override fun getReminder(): Flow<Reminder?> {
        return emptyFlow()
//        return myPreferencesDataSource.getInitialDataVersionNumber()
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
//        return myPreferencesDataSource.setCategoryDataVersionNumber(
//            categoryDataVersionNumber = categoryDataVersionNumber,
//        )
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
//        return myPreferencesDataSource.setDefaultExpenseCategoryId(
//            defaultExpenseCategoryId = defaultExpenseCategoryId,
//        )
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
//        return myPreferencesDataSource.setDefaultIncomeCategoryId(
//            defaultIncomeCategoryId = defaultIncomeCategoryId,
//        )
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
//        return myPreferencesDataSource.setDefaultInvestmentCategoryId(
//            defaultInvestmentCategoryId = defaultInvestmentCategoryId,
//        )
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
//        return myPreferencesDataSource.setDefaultSourceId(
//            defaultSourceId = defaultSourceId,
//        )
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ) {
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
//        return myPreferencesDataSource.setLastDataBackupTimestamp(
//            lastDataBackupTimestamp = lastDataBackupTimestamp,
//        )
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
//        return myPreferencesDataSource.setLastDataChangeTimestamp(
//            lastDataChangeTimestamp = lastDataChangeTimestamp,
//        )
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
//        return myPreferencesDataSource.setTransactionsDataVersionNumber(
//            transactionsDataVersionNumber = transactionsDataVersionNumber,
//        )
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ) {
    }
}
