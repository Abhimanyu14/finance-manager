package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface UpdateSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class UpdateSourcesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : UpdateSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return sourceRepository.updateSources(
            sources = sources,
        )
    }
}
