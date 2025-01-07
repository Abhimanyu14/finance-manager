package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.common

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.preferences.MyPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class ShouldShowBackupCardUseCase @Inject constructor(
    private val myPreferencesRepository: MyPreferencesRepository,
) {
    public operator fun invoke(): Flow<Boolean> {
        return myPreferencesRepository.getDataTimestampFlow().map {
            it.isNotNull() && (it.lastBackup < it.lastChange)
        }
    }
}
