package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteAllSourcesUseCase {
    suspend operator fun invoke()
}

class DeleteAllSourcesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : DeleteAllSourcesUseCase {
    override suspend operator fun invoke() {
        dataStore.updateLastDataChangeTimestamp()
        return sourceRepository.deleteAllSources()
    }
}
