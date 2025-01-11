package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class GetDefaultAccountIdFlowUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
) {
    public operator fun invoke(): Flow<Int?> {
        return myPreferencesRepository.getDefaultDataIdFlow().map {
            it?.account
        }
    }
}
