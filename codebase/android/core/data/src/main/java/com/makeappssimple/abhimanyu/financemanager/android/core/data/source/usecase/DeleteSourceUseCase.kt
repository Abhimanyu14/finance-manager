package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteSourceUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteSourceUseCaseImpl(
    private val dataStore: MyDataStore,
    private val sourceRepository: SourceRepository,
) : DeleteSourceUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return sourceRepository.deleteSource(
            id = id,
        )
    }
}
