package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source

interface InsertSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class InsertSourcesUseCaseImpl(
    private val myPreferencesRepository: MyPreferencesRepository,
    private val sourceRepository: SourceRepository,
) : InsertSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        myPreferencesRepository.setLastDataChangeTimestamp()
        return sourceRepository.insertSources(
            sources = sources,
        )
    }
}
