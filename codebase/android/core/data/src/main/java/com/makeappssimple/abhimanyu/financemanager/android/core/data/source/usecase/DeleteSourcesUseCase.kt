package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface DeleteSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class DeleteSourcesUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val sourceRepository: SourceRepository,
) : DeleteSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return sourceRepository.deleteSources(
            sources = sources,
        )
    }
}
