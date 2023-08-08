package com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPreferencesRepositoryImpl @Inject constructor(
    private val myPreferencesDataSource: MyPreferencesDataSource,
) : MyPreferencesRepository {
    override fun getDataTimestamp(): Flow<DataTimestamp?> {
        return myPreferencesDataSource.getDataTimestamp()
    }

    override fun getDefaultDataId(): Flow<DefaultDataId?> {
        return myPreferencesDataSource.getDefaultDataId()
    }

    override fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return myPreferencesDataSource.getInitialDataVersionNumber()
    }

    override fun getReminder(): Flow<Reminder?> {
        return myPreferencesDataSource.getReminder()
    }

    override suspend fun setCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ) {
        myPreferencesDataSource.setCategoryDataVersionNumber(
            categoryDataVersionNumber = categoryDataVersionNumber,
        )
    }

    override suspend fun setDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ) {
        myPreferencesDataSource.setDefaultExpenseCategoryId(
            defaultExpenseCategoryId = defaultExpenseCategoryId,
        )
    }

    override suspend fun setDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ) {
        return myPreferencesDataSource.setDefaultIncomeCategoryId(
            defaultIncomeCategoryId = defaultIncomeCategoryId,
        )
    }

    override suspend fun setDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ) {
        myPreferencesDataSource.setDefaultInvestmentCategoryId(
            defaultInvestmentCategoryId = defaultInvestmentCategoryId,
        )
    }

    override suspend fun setDefaultAccountId(
        defaultAccountId: Int,
    ) {
        myPreferencesDataSource.setDefaultAccountId(
            defaultAccountId = defaultAccountId,
        )
    }

    override suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
    ) {
        myPreferencesDataSource.setEmojiDataVersionNumber(
            emojiDataVersionNumber = emojiDataVersionNumber,
        )
    }

    override suspend fun setIsReminderEnabled(
        isReminderEnabled: Boolean,
    ) {
        myPreferencesDataSource.setIsReminderEnabled(
            isReminderEnabled = isReminderEnabled,
        )
    }

    override suspend fun setLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ) {
        myPreferencesDataSource.setLastDataBackupTimestamp(
            lastDataBackupTimestamp = lastDataBackupTimestamp,
        )
    }

    override suspend fun setLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ) {
        myPreferencesDataSource.setLastDataChangeTimestamp(
            lastDataChangeTimestamp = lastDataChangeTimestamp,
        )
    }

    override suspend fun setReminderTime(
        hour: Int,
        min: Int,
    ) {
        myPreferencesDataSource.setReminderTime(
            hour = hour,
            min = min,
        )
    }

    override suspend fun setTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ) {
        myPreferencesDataSource.setTransactionDataVersionNumber(
            transactionDataVersionNumber = transactionsDataVersionNumber,
        )
    }
}
