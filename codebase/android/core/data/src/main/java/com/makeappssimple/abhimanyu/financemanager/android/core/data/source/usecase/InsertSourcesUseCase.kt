package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface InsertSourcesUseCase {
    suspend operator fun invoke(
        vararg sources: Source,
    )
}

class InsertSourcesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : InsertSourcesUseCase {
    override suspend operator fun invoke(
        vararg sources: Source,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return sourceRepository.insertSources(
            sources = sources,
        )
    }
}
