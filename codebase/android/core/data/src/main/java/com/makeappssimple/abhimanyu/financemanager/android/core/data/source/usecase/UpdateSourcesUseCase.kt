package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface UpdateSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class UpdateSourcesUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val sourceRepository: SourceRepository,
) : UpdateSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return sourceRepository.updateSources(
            sources = sources,
        )
    }
}
