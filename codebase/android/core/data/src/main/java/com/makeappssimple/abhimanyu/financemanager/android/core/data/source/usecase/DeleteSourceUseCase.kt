package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository

interface DeleteSourceUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteSourceUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val sourceRepository: SourceRepository,
) : DeleteSourceUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return sourceRepository.deleteSource(
            id = id,
        )
    }
}
