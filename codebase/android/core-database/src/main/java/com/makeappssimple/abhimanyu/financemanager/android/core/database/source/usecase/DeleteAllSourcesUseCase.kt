package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.updateLastDataChangeTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository

interface DeleteAllSourcesUseCase {
    suspend operator fun invoke()
}

class DeleteAllSourcesUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : DeleteAllSourcesUseCase {
    override suspend operator fun invoke() {
        updateLastDataChangeTimestamp(
            dataStore = dataStore,
        )
        return sourceRepository.deleteAllSources()
    }
}
