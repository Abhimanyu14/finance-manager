package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class DeleteSourcesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : DeleteSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return sourceRepository.deleteSources(
            sources = sources,
        )
    }
}
