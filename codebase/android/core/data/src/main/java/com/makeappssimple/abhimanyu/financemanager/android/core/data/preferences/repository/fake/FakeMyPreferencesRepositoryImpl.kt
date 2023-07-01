package com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
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

    override suspend fun setDefaultSourceId(
        defaultSourceId: Int,
    ) {
//        return myPreferencesDataSource.setDefaultSourceId(
//            defaultSourceId = defaultSourceId,
//        )
    }

    override suspend fun setEmojiDataVersionNumber(
        emojiDataVersionNumber: Int,
    ) {
//        return myPreferencesDataSource.setEmojiDataVersionNumber(
//            emojiDataVersionNumber = emojiDataVersionNumber,
//        )
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
}
